
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "基础物料BOM配方维护",
    berth : "detailForm",
    items : [
    	{ 
        	type:"combox",   
			title:"物料子类",
			dataIndex: 'fSubtype',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':0,'name':'点检'},{'code':1,'name':'保养'},{'code':2,'name':'润滑'}],
			url: "../../../../dynamic/dicView/listDic?view=v_mat_subtype",
			root: "dataset.data"
        },{
	        dataIndex: 'fMatName',
	        title: '物料名称' ,
			type:"textinput",
	    }, {
	        dataIndex: 'fMatCode',
	        title: '物料编码' ,
			type:"textinput",
	    },{
	        dataIndex: 'fIoFlag',
	        title: '投入产出标记',
			type:"combox",
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'投入'},{'code':0,'name':'产出'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
	    },{
	        dataIndex: 'fIndex',
	        title: '排序号',
			type:"numberinput",
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
        }
    ],
    layoutConfig : {
        columns : 2
    },
    hidefields:["fBomPid","fPid"],
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	if(mode == 'add'){
    		var result=forceAjaxSynchro("../../../dynamic/materialbom/savematerialbom_detail",objs,function(){
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
    		var result=forceAjaxSynchro("../../../dynamic/materialbom/updatematerialbom_detail",objs,function(){
    			console.info(objs);
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
	var superClass=getQueryString("superClass");
	//$("#Formbody input[name='superClass.id']").val(superClass);
	if(mode == 'add'){
		var fBomPid=getQueryString("fBomPid");
		if(fBomPid!=undefined&&fBomPid.length>0) {
			$("#fBomPid").val(fBomPid);
			console.info($("#fBomPid").val());
		}
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);

	}	
	
});