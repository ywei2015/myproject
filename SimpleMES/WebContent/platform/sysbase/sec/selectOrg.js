//var orgId = parseUrl().orgId;
//console.log(parseUrl());
var t = $("#tree");
	var setting = {
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
			check: {
	            enable: true,
	            chkStyle: "radio",  //单选框
	            radioType: "all",  //对所有节点设置单选
	            nocheckInherit: true
	        },
			callback : {},
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			}
		};

	$(function () {		
	  //	var t = $(this).parent().find(".tree");
	  	$("#modal-save").click(function(){
	  		var nodes = t.getCheckedNodes(); 
	  		var keys=[nodes[0].name];
		    var value=[nodes[0].id];
		    console.log(nodes);
	  		//SF.ChoiceBindResult(this,keys,value);
	  		SF.ChoiceBindSelections("orgId",keys,value);
	  		/* $("#myModal1").modal("hide");
	  		$("#myModal2").modal("hide"); */
	  		//$('form').bootstrapValidator('resetForm');
	  		//向上查询匹配的父节点，查找到删除 ，存在bug， 当不是点击保存时，第二次就会报错。
	  		$("#btncancel").click(); 
	  		$("#btncancel").closest(".modal-content").empty();
	  	});
	});	


$(document).ready(function(){ 
	$.getJSON("../../../dynamic/ObjStructure/fetchDicTreeByComboboxSub?structId=7001", function(result) {
		console.log(result.dataset.treenode);
		t = $.fn.zTree.init(t, setting, result.dataset.treenode);
	}); 
	
})

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