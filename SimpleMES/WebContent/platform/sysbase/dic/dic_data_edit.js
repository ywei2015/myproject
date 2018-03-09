
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "基础数据维护",
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
        	type:"textinput",
            title:"排序值",
            dataIndex:'num1'
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
    hidefields:["type","id"],
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
    	var objs = $("#Formbody"); // SF.getSearchParams();
		console.log(objs);
		var mode = window.dialogArguments.mode;
		// 开启验证
		$('#Formbody').data('bootstrapValidator').validate();
		if (!$('#Formbody').data('bootstrapValidator').isValid()) {
			return;
		}
		// 保存之前做校验
		var flag = false;
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "../../../dynamic/Dic/validCodeAndName",
			async : false,// 同步
			data : objs.serialize(),
			success : function(result) {
				if (result.status != 200) {
					BootstrapDialog.show({
						title : constant.TIP,
						message : result.message,
						buttons : [ {
							label : constant.CONFIRM,
							action : function(dialog) {
								dialog.close();
								return false;
							}
						} ]
					});
					flag = true;
				}

			},
		});
		if (flag) {
			return;
		}
		if (mode == 'add') {
			var result = forceAjaxSynchro("../../../dynamic/Dic/save", objs,
					function() {
					});
			if (result.responseJSON.status == 200) {
				window.returnValue = "ok";
			}
			BootstrapDialog.show({
				title : constant.TIP,
				message : result.responseJSON.message,
				buttons : [ {
					label : constant.CONFIRM,
					action : function(dialog) {
						dialog.close();

						window.close();
						return false;
					}
				} ]
			});
		} else if (mode == 'update') {
			var result = forceAjaxSynchro("../../../dynamic/Dic/save", objs,
					function() {
					});
			if (result.responseJSON.status == 200) {
				window.returnValue = "ok";

			}
			BootstrapDialog.show({
				title : constant.TIP,
				message : result.responseJSON.message,
				buttons : [ {
					label : constant.CONFIRM,
					action : function(dialog) {
						dialog.close();

						window.close();
						return false;
					}
				} ]
			});
		} 
    }// --END onstubmit
};


$(document).ready(function() {
	var type=getQueryString("type");
// var field =
// "{\"type\":\"textinput\",\"title\":\"网页地址\",\"dataIndex\":\"value2\"}";
	var field="";
	$.ajax({
        url: '../../../dynamic/objBase/fetchObjAttributeList?objId='+type,
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
	
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	
	//console.log(superClass);
	$("#Formbody input[name='type']").val(type);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);

	}
	
	/*************表单验证  stasrt**************/
	$('#Formbody').bootstrapValidator({
		      message: 'This value is not valid',
		      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
		        valid: 'glyphicon glyphicon-ok',
		        invalid: 'glyphicon glyphicon-remove',
		        validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {/*验证*/
		        code: {/*键名username和input name值对应*/
		          message: 'The name is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '不能为空'
		            }
		          }
		        },
				name: {/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        },
				
				sysFlag:{/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        },
					fullName:{/*键名username和input name值对应*/
						          message: 'The name is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						              message: '不能为空'
						            }
						          }
						        }
					},
			});
	/*************end*******************/
	
});