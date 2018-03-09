
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "关联关系维护",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"编号",
            dataIndex:'code'
        },
        { 
        	type:"textinput",
            title:"名称",
            dataIndex:'name'
        },
        { 
         	type:"textinput",
             title:"全名称",
             dataIndex:'fullName'
         },
        { 
         	type:"numberinput",
             title:"排序值",
             dataIndex:'num1'
         },
         { 
          	type:"numberinput",
              title:"关系排序",
              dataIndex:'trdOrder'
          }, 
        { 
        	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        },
        
        {
			type:"combox",   
			title:"是否有效",
			dataIndex: 'sysFlag',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'有效'},{'code':0,'name':'无效'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
		}
    ],
    hidefields:['typeRefDicId','objEntityRefId','parentId','type','id','typeChild'],
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	//暂时不校验，因为dic表，很容易存在相同name和code，
    	if(validField("../../../dynamic/Dic/validCodeAndName",objs.serialize())){
    		return;
    	}
    	if(mode == 'add'){
    		var result=forceAjaxSynchro("../../../dynamic/ObjStructure/saveDicRef",objs,function(){
			});
    		if(result.responseJSON.status==200){
    			window.returnValue = "ok"; 
    		
    		}
    		BootstrapDialog.show({
	            title: '提示',
	            message: result.responseJSON.message,
	            buttons: [{
	                label: '确认',
	                action: function(dialog) {
	                	dialog.close();
	                	
	                	window.close();
	                	return false;
	                }
	            }]
	        });
    	}else if(mode == 'update'){
    		var result=forceAjaxSynchro("../../../dynamic/ObjStructure/saveDic",objs,function(){
				});
				if(result.responseJSON.status==200){
					window.returnValue = "ok"; 
		    		
		    		}
					BootstrapDialog.show({
	    	            title: '提示',
	    	            message: result.responseJSON.message,
	    	            buttons: [{
	    	                label: '确认',
	    	                action: function(dialog) {
	    	                	dialog.close();
	    	                	
	    	                	window.close();
	    	                	return false;
	    	                }
	    	            }]
	    	        });
					}
    		
    	
    	
    }
};


$(document).ready(function() {
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	var objRefPid = "";
	if(mode == 'add'){
		if(data.struct=="10001" || data.structId=="10001"){
			objRefPid = "10000";
		}else{
			objRefPid = data.radioValue;
		}
	}else if(mode == 'update'){
		objRefPid = data.type;
	}
	var field="";
	$.ajax({
        url: '../../../dynamic/objBase/fetchObjAttributeList?objId='+objRefPid,
        async: false,
        type: "get",
        data: {},
        success: function (data, status) {
            $.each(data.dataset.objAttribute, function (key, value) {
            	if(value.classType == '1005'){
            		field += "{\"type\":\"textinput\",\"title\":\""+value.name+"\",\"dataIndex\":\""+value.column+"\"}"+",";
            	}
            	if(value.classType == '1006'){
            		field += "{\"type\":\"combox\",\"title\":\""+value.name+"\",\"dataIndex\": \""+value.column+"\",\"fieldText\":\"name\",\"fieldValue\":\"code\",\"url\":\"../../../DataDict?type=\""+value.verifyRule+"\",\"root\": \"dataset.item\"}"+",";
            	}
            	if(value.classType == '1008'){
            		field += "{\"type\":\"datetime\",\"title\":\""+value.name+"\",\"dataIndex\":\""+value.column+"\",\"config\":{\"format\": \"yyyy-mm-dd\",\"minView\":\"month\",\"initialDate\": new Date()}}"+",";
            	}
            	if(value.classType == '1009'){
            		field += "{\"type\":\"numberinput\",\"title\":\""+value.name+"\",\"dataIndex\":\""+value.column+"\"}"+",";
            	}
            });
        }
    });
	field = field.substring(0, field.lastIndexOf(','));
	var items = formbodyConfig.items;
	var jsonStr = "";
	for(var i = 0; i < items.length; i++){
		jsonStr += JSON.stringify(items[i]) + ",";
	}
	var newJsonStr = "["+jsonStr+field+"]";

	formbodyConfig.items =  eval('(' + newJsonStr + ')');
	
	SF.FormBody.onload(formbodyConfig);  
	
	$('#value2').parent().find('font').html(data.value22);
	if(data.struct=="10001" || data.structId=="10001"){}else{
		$("#value3").parent().hide();
	}
	if(mode == 'add'){
		SF.BindFormData(data);
		if(data.struct!="10001" || data.structId!="10001"){
			$("#type").val(data.radioValue);
		}
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);
		if(data.struct!="10001" || data.structId!="10001"){
			$("#type").val(data.radioValue);
		}
	}	
	
	
});