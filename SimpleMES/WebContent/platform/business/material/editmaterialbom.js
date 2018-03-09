
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "基础物料BOM维护",
    berth : "detailForm",
    items : [
    	{
	        dataIndex: 'fBomName',
	        title: 'BOM名称', 
	        type:"textinput"
	    },{ 
        	type:"combox",   
			title:"BOM类型",
			dataIndex: 'fBomType',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':0,'name':'点检'},{'code':1,'name':'保养'},{'code':2,'name':'润滑'}],
			url: "../../../../dynamic/dicView/listDic?view=v_mat_bomtype",
			root: "dataset.data"
        }, {
	        dataIndex: 'fMatCode',
	        title: '产出物编码', 
	        type:"textinput"
	    },
	    {
	        dataIndex: 'fMatName',
	        title: '产出物料名', 
			type:"textinput",
	    },{
	        dataIndex: 'fQuantity',
	        title: '数量',
			type:"numberinput",
	    },{ 
        	type:"combox",   
			title:"单位",
			dataIndex: 'fUnit',
			fieldText: 'name',
			fieldValue: 'pid',
			url: "../../../../dynamic/dicView/listDic?view=v_mat_units",
			root: "dataset.data"
        }, {
	        dataIndex: 'fVersion',
	        title: '版本' ,
			type:"textinput",
	    }, 
	    {
	        dataIndex: 'fEnableDt_vo',
	        title: '启用时间',
			type:"datetime",
			config:{
	            format: 'yyyy-mm-dd HH:mm:ss',
		    	minView:'day',
				initialDate: new Date()
			}
	    }, {
	        dataIndex: 'fDisableDt_vo',
	        title: '停用时间' ,
	        type:"datetime",
			config:{
	            format: 'yyyy-mm-dd HH:mm:ss',
		    	minView:'1',
				initialDate: new Date()
			}
	    }, 
	    {
	        dataIndex: 'fStatus',
	        title: '状态',
			type:"combox",
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'启用'},{'code':0,'name':'停用'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
	    }
    ],
    layoutConfig : {
        columns : 2
    },
    hidefields:["fPid"],
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	if(mode == 'add'){
    		var result=forceAjaxSynchro("../../../dynamic/materialbom/savematerialbom",objs,function(){
        	});
    		if(result.responseJSON.code==200){
    			BootstrapDialog.show({
    				message:"保存成功"
    			});
    			window.returnValue = "ok"; 
    			setTimeout(function(){
                	window.close();
                	return false;
    			},850)
    		}else {
    			SF.validForm(result);
    		}
    		
    	}else if(mode == 'update'){
    		var result=forceAjaxSynchro("../../../dynamic/materialbom/updatematerialbom",objs,function(){
        	});
    		if(result.responseJSON.code==200){
    			BootstrapDialog.show({
    				message:"修改成功"
    			});
    			window.returnValue = "ok"; 
    			setTimeout(function(){
                	window.close();
                	return false;
    			},850)
    		}else {
    			SF.validForm(result);
    		}
    	}
    }
};


$(document).ready(function() {   
	SF.FormBody.onload(formbodyConfig);  
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	//var superClass=getQueryString("superClass");
	//$("#Formbody input[name='superClass.id']").val(superClass);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);
		$("#fDisableDt_vo").val(data.fDisableDt_vo);
		$("#fEnableDt_vo").val(data.fEnableDt_vo);
	}	
	
});
