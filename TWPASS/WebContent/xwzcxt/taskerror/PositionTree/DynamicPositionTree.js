var setting = {
	view : {
		selectedMulti : false
	},
	data : {
		key : {
			name : "name"
		},
		simpleData : {
			enable : true,
			pIdKey : "pId",
			rootPId : "1--1"
		}
	},
	async : {
		enable : true,
		url : "taskSchedular_getDymanicPositionTreeNodes.action",
		autoParam : [ 'id' ],
		dataFilter : filter
	},
	callback : {
	/*
	 * beforeClick: beforeClick, 
	 * beforeAsync: getTime, 
	 * */
	  onAsyncSuccess:zTreeOnAsyncSuccess,
	 
	   onClick:onNodeClick,
	// onCheck:onNodeCheck
	}
};

function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for ( var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}


var node={};
function onNodeClick(event, treeId, treeNode, clickFlag) {
	/*
	 * 用于捕获节点被点击的事件回调函数 如果设置了 setting.callback.beforeClick 方法，且返回 false，将无法触发
	 * onClick 事件回调函数。 默认值：null
	 * 
	 * Function 参数说明 event js event 对象 标准的 js event 对象
	 * 
	 * treeIdString 对应 zTree 的 treeId，便于用户操控
	 * 
	 * treeNodeJSON 被点击的节点 JSON 数据对象
	 * 
	 * clickFlagNumber 节点被点击后的选中操作类型，详细看下表 clickFlag selectedMulti
	 * autoCancelSelected isSelected 选中操作 && event.ctrlKey/metaKey 1 true false
	 * false 普通选中 1 true false true 普通选中 2 true true false 追加选中 0 true true true
	 * 取消选中 1 false false false 普通选中 1 false false true 普通选中 1 false true false
	 * 普通选中 0 false true true 取消选中
	 */
	
	node['id']=treeNode.id;
	node['name']=treeNode.name;
	node['typeCode']=treeNode.typeCode;
}

function getNode(){
	return node;
}

function onNodeCheck(event, treeId, treeNode) {
}

function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	$("#log").append("<li>" + h + ":" + m + ":" + s + " " + ms + "</li>");
}

$(document).ready(function() {
	var rootNode = {
		id : "1--1",
		name : "所有组织",
		isParent : true
	};
	$.fn.zTree.init($("#position"), setting, rootNode);
/*	var tree=$.fn.zTree.getZTreeObj("position");
	var root=tree.getNodesByParam("level",0);
	tree.expandNode(root,true);
    alert(1);*/
	zTreeOnAsyncSuccess();
});


function getSelectedNodes(){
	var tree=	$.fn.zTree.getZTreeObj('position');
	return tree.getSelectedNodes();
}

function getLeafValue(){  //单选模式
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


/*在Ztree的官网Demo中，有自动展开的例子，是通过设置节点属性open:true来实现自动展开的，但是在异步加载中，这个属性设置为true也不会自动展开，因为open:true是指在有子节点的情况下展开，在异步加载之前，当前节点是没有子节点的，所以无法打开。 异步加载自动展开解决方法如下：  
1.设置ztree的加载完成的回调函数:  
setting = {   
    ......                   callback: {         ...... 
        onAsyncSuccess: zTreeOnAsyncSuccess       }   }; 
onAsyncSuccess是指加载完成后要调用的方法，其他节点的设置请参考ztree的官网Demo. 
2.编写方法zTreeOnAsyncSuccess */

var expendCount=0;   //结点计数器从0开始，根节点的计为0
function zTreeOnAsyncSuccess(event,treeId,msg){
	if(expendCount<2){  //展开第1个节点
		try{
				 //调用默认展开第一个结点
			     var zTree = $.fn.zTree.getZTreeObj('position');
				 var nodes=zTree.getNodes();   //这里获得的是按层次品拼好的json数据格式
				 var childNodes=zTree.transformToArray(nodes[0]);
				 zTree.expandNode(childNodes[expendCount],true);
				 expendCount++;
		 }catch(err){}
	}
}