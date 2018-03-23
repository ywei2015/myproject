var request = talkweb.ControlBus.getRequest();
var roleID = request['roleID'];
var roleName = request['roleName'];
var userName = request['userName'];
var currentTextName = "当前角色：";
if(!!userName)
{
	roleName = userName;
	currentTextName = "当前用户：";
}
var popupObj;
var roleRuleFuncValue = {};
talkweb.Bus.ready({
	items : [{
		classPath : "BaseControl.Label",
		width : "50%",
		options : {
			container : "roleNameTextBox",
			value:currentTextName,
			width : "195px"
		}
	}, {
		classPath : "BaseControl.Label",
		width : "50%",
		options : {
			container : "roleNameTextBox",
			value:roleName,
			width : "195px"
		}
	},{
		classPath : "Components.Form",
		options : {
			container : "form",
			name : "addRoleFuncForm",
			id: "addRoleFuncForm",
			saveInstance: true,
            haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            submitJson:false,
			action : "role_restrictPermissions.action",
			requestType:"post",
			submitCallback : function(msg) {   				
					if(msg.result=='1'){
						alert("提交成功");	
						parent.addMemberPopupObj.close();
					}else{
						alert("提交失败")
					}
			},
			items : [{
				classPath : "BaseControl.Hidden",//权限ID
				width:"0%",
				options : {
					id : "pids",
					name : "pids"
				}
			},{
				classPath : "BaseControl.Hidden",//权限ID
				width:"0%",
				options : {
					id : "roleId",
					name : "roleId"
				}
			}]
		}
	}, {
                classPath: "BaseControl.Button",
                options: {
                	container:"buttons",
                    value: "提交",
            		name:"Button1",
                    click: function(){					    
						var tree = talkweb.ControlBus.getInstance("roleFuncTree");
					    var addRoleFuncForm = talkweb.ControlBus.getInstance("addRoleFuncForm");
						var idArray = tree.getChkedIds("onlyLeafValue").split(",");
						var permissionIds = new Array();
					    for(var i=0;i<idArray.length;i++)
					    {
					    	if(!idArray[i]||idArray[i].length==0)
					    		continue;
					    	var node = tree.getNodeByid(idArray[i]);
					    	//过滤掉不是权限节点的ID
					    	if(node["type"]=="P")
					    		permissionIds.push(idArray[i]);		    	
					    }						    
					    var pids = permissionIds.join(",");
					    fastDev('#pids').val(pids);//权限节点的集合
					    fastDev('#roleId').val(roleID);//角色ID					    				    
					    addRoleFuncForm.submit();
					},
					className:"btn_save"
                }
            }, {
                classPath: "BaseControl.Button",			                
                options: {
                	container:"buttons",
                    value: "关闭",
                    name:"Button2",
                    click: function(){
            			parent.addMemberPopupObj.close();
            		},
					className:"btn_reset"
                }
            },{
        classPath: "Components.Tree",
        options: {
			id: "roleFuncTree",
			saveInstance: true,
			container : "tree",
			topParentid:"-1", 
			initSource :"role_showRolePermissionTree.action?limit=false&roleId="+roleID,
			openFloor:2,
			showIco:false,
			treeType:"multitree",
			onclick:function(id){

			}	
        }					
	}]
});