var orgId = $("#orgId").val();
if(orgId!=""){

$(function () {	
	var setting = {
			check : {
				autoCheckTrigger : false,
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				},
				chkStyle : "checkbox",
				enable : true
			// 是否可选中
			},
			view : {
				dblClickExpand : false,// 双击节点时，是否自动展开父节点的标识
				showLine : true,// 设置 zTree 是否显示节点的图标。
				selectedMulti : false
			// 设置是否允许同时选中多个节点。
			},
			data : {
				simpleData : {
					// 如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey /
					// pIdKey / rootPId，并且让数据满足父子关系。
					enable : true,// 使用简单数据模式
					idKey : "id",// 唯一标识名称
					pIdKey : "pid",// 父节点唯一标识名称
					rootPId : 1000
				// 。[setting.data.simpleData.enable = true 时生效]
				}
			},
			callback : {
				// 用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作
				beforeClick : function(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("tree");
					zTree.expandAll(true);
				},
				beforeDblClick : function(treeId, treeNode) {
					$("#" + obj_id).val(treeNode.name);
					$("#" + target).val(treeNode.id);
					layer.close(index);
				}
			},
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			}
		};
		var t = $("#tree");
		$.getJSON("../../../dynamic/ObjStructure/funTreeDataByIdAndParentId?id=4028802934e54c060134e58955c7000d&parentId="+orgId,
				function(result1) {
			console.log(result1.dataset.treenode);
			t = $.fn.zTree.init(t, setting, result1.dataset.treenode);
		});
	   
		
	  //	var t = $(this).parent().find(".tree");
	  	$("#modal-save").click(function(){
	  		var treeObj = $.fn.zTree.getZTreeObj("tree");
			nodes = treeObj.getCheckedNodes(true);
			var ids = "";
			var names = "";
			for (var i = 0; i < nodes.length; i++) {
				ids += nodes[i].id + ",";
				console.log(nodes[i].id); // 获取选中节点的值
			}
			for (var j = 0; j < nodes.length; j++) {
				names += nodes[j].name + ",";
				console.log(nodes[j].name); // 获取选中节点的值
			}
			
	  		var keys=[names];
		    var value=[ids];
		    console.log(nodes);
	  		//SF.ChoiceBindResult(this,keys,value);
	  		SF.ChoiceBindSelections("postsId",keys,value);
	  		/* $("#myModal1").modal("hide");
	  		$("#myModal2").modal("hide"); */
	  		$("#btncancel").click(); 
	  		//向上查询匹配的父节点，查找到删除 ，存在bug， 当不是点击保存时，第二次就会报错。
	  		$("#btncancel").closest(".modal-content").empty();
	  	});
	});	
}else{
	BootstrapDialog.alert("请先选择部门！");
	$("#btncancel").click(); 
}


function geturl(id,name) {
    var reg = new RegExp("[^\?&]?" + encodeURI(name) + "=[^&]+");
    var arr = window.parent.document.getElementById(id).contentWindow.location.search.match(reg);
    if (arr != null) {
        return decodeURI(arr[0].substring(arr[0].search("=") + 1));
    }
    return "";
}

//获取所有的传过来的参数
function parseUrl(){
  var url=location.href;
  var i=url.indexOf('?');
  if(i==-1)return;
  var querystr=url.substr(i+1);
  var arr1=querystr.split('&');
  var arr2=new Object();
  for  (i in arr1){
      var ta=arr1[i].split('=');
      arr2[ta[0]]=ta[1];
  }
  return arr2;
}