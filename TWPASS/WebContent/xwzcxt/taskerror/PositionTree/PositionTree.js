var setting = {
			view: {
				selectedMulti: false
			},
			data:{
				key:{
					  name:"name"
					},
				simpleData:{
					enable:true,
					pIdKey:"pId",
					rootPId:"1--1"
				}
			}
		};




$(document).ready(function(){
	var rootNode={id:"1--1",name:"所有组织",isParent:true};
	$.post("taskSchedular_getAllPostionTreeNodes.action",function(data,status){
		    if(status=="success"){
		    	$("#tip").remove();
		    	if(data.constructor==Array){
		    		data.push(rootNode);
		    	}
		    	$.fn.zTree.init($("#position"),setting,data);
		    	var tree=$.fn.zTree.getZTreeObj("position");
		    	var nodes=tree.getNodesByParam("level",1);
		    	if(nodes.length>0){
		    			tree.expandNode(nodes[0], true, false, false);
		    	}
		    }
	});
	
	
});



function getLeafValue(){
	var tree=	$.fn.zTree.getZTreeObj('position');
	var nodes=tree.getSelectedNodes();
	var value = '';
	var name='';
	var user={};
	if (nodes.length > 0) {
		for ( var i = 0; i < nodes.length; i++) {
			if (!nodes[i].isParent) {
				value += nodes[i].id.replace('3-','') + ",";
				name+= nodes[i].name + ",";
			}
		}
	}
	if (value.length > 0&&name.length>0) {
		value = value.substring(0, value.length - 1);
		name  =  name.substring(0, name.length - 1);
		user['name']=name;
		user['id']=value;
	}else{
		user=null;
	}
	return user;
}

