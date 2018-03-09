
    var zTree;
    var demoIframe;

    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button remove' id='removeBtn_" + treeNode.tId
                + "' title='add node' onfocus='this.blur();'></span>";

        addStr += "<span class='button add' id='addBtn_" + treeNode.tId + "'></span>";
        addStr += "<span class='button edit' id='editBtn_" + treeNode.tId + "'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        if (btn) btn.bind("click", function(){
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.addNodes(treeNode, {id:(1000 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
            return false;
        });
    };

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
        $("#removeBtn_"+treeNode.tId).unbind().remove();
        $("#editBtn_"+treeNode.tId).unbind().remove();
    };
	
	//官方给出的方法
	/*function addHoverDom(treeId, treeNode) {
			var aObj = $("#" + treeNode.tId + "_a");
			if ($("#diyBtn_"+treeNode.id).length>0) return;
			var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' > </span>"
				+ "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
				+ "' title='"+treeNode.name+"' onfocus='this.blur();'></button>";
			aObj.append(editStr);
			var btn = $("#diyBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
		};
	function removeHoverDom(treeId, treeNode) {
		$("#diyBtn_"+treeNode.id).unbind().remove();
		$("#diyBtn_space_" +treeNode.id).unbind().remove();
	};*/
    var zTree = null;
    var setting = {
      /*  check: {
            enable: true //是否可选中
        },*/
        view: {
           /* addHoverDom: addHoverDom,//用于当鼠标移动到节点上时，显示用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
            removeHoverDom: removeHoverDom,//用于当鼠标移出节点时，隐藏用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮*/
            dblClickExpand: true,//双击节点时，是否自动展开父节点的标识
            showLine: true,//设置 zTree 是否显示节点的图标。
            selectedMulti: false//设置是否允许同时选中多个节点。
        },
        data: {
            simpleData: {
            	//如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey / pIdKey / rootPId，并且让数据满足父子关系。
                enable:true,//使用简单数据模式
                idKey: "id",//唯一标识名称
                pIdKey: "pid",//父节点唯一标识名称
                rootPId: 9999//。[setting.data.simpleData.enable = true 时生效]
            }
        },
        callback: {
        	
        	//用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作
            beforeClick: function(treeId, treeNode) {
               var demoIframe=$("#rightIframe");
               zTree = $.fn.zTree.getZTreeObj("tree");
                if (treeNode.isParent) {
//                    zTree.expandNode(treeNode);
//                    demoIframe.attr("src","../../../dynamic/ObjStructure/fetchDicDetailFormaintain?structId="+treeNode.id);
                	$('#structId').val(treeNode.relationId);
                    demoIframe.attr("src","../dic/data_type_list.html?parentId="+treeNode.id+"&structId="+treeNode.relationId+"&keyId="+treeNode.keyId+"&objPid="+treeNode.objPid+"&objRefPid="+treeNode.objRefPid);
                    return true;
                } else {
                	$('#structId').val(treeNode.relationId);
                	demoIframe.attr("src","../dic/data_type_list.html?parentId="+treeNode.id+"&structId="+treeNode.relationId+"&keyId="+treeNode.keyId+"&objPid="+treeNode.objPid+"&objRefPid="+treeNode.objRefPid);
                    //demoIframe.attr("src","http://www.baidu.com");
                    return true;
                }
            }
        },
        edit:{
        	enable :true,
        	showRemoveBtn : false,
        	showRenameBtn : false
        },
       /*async : {
    	   autoParam : ["id"],
			//contentType : "application/x-www-form-urlencoded",
			dataFilter : null,
			dataType : "text",
			enable : true,
			type : "post",
			url : "../../../dynamic/ObjStructure/fetchDicTreeByComboboxSub?structId="
		}*/
        async : {
			autoParam : ["id"],
			contentType : "application/x-www-form-urlencoded",
			dataFilter : null,
			dataType : "text",
			enable : true,
			otherParam:["structId",function(){ return $('#structId').val() }],
			type : "post",
			url : "../../../dynamic/ObjStructure/fetchDicTreeByComboboxSubById"
		}
    };

  

    $(document).ready(function(){
    	//$.fn.zTree.init($("#tree"), setting);
    	//zTree = $.fn.zTree.getZTreeObj("tree");
        /*var t = $("#tree");
        $.getJSON('http://localhost:8087/msbase/dynamic/ObjStructure/fetchDicTree?objStructureId=4028a35d227d13ff01227d6403fd0002',function(result){
	        t = $.fn.zTree.init(t, setting,result.dataset.treenode);
	        demoIframe=$("#rightIframe");
	       
	      // console.log(demoIframe);
	     //demoIframe.attr("src","www.baidu.com");
           
	     //  demoIframe.bind("load", loadReady);
	     
		});*/
			
    });

  function loadReady() {
  		console.log(demoIframe);
        var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
                htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
                maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
                h = demoIframe.height() >= maxH ? minH:maxH ;
        if (h < 1000) h = 530;
        demoIframe.height(h);
    }
  /*function ztreeOnAsyncSuccess(event, treeId, treeNode){ 
	  alert(11);
	    var url = "../../../dynamic/ObjStructure/fetchDicTreeByComboboxSub?structId=";  
//	    var treeObj = $.fn.zTree.getZTreeObj("tree");
	    var treeObj = $("#tree");
	    if(treeNode == undefined){  
	        url += "1";  
	    }  
	    else{  
	        url += "10001";  
	    }  
	    $.ajax({  
	        type : "post",  
	        url : url,  
	        data : "",  
	        dataType : "json",  
	        async : true,  
	        success : function(jsonData) {                
	            if (jsonData != null) {
	                var data = jsonData.dataset.treenode; 
	                if(data != null && data.length != 0){  
	                    if(treeNode == undefined){  
	                        //treeObj.addNodes(null,data,true);// 如果是根节点，那么就在null后面加载数据  
	                    }  
	                    else{  
	                    	//treeObj = $.fn.zTree.init(treeObj, setting,data);
	                    	treeObj.expandAll(true);
	                        //treeObj.expandNode(treeNode.parentId,data,true);//如果是加载子节点，那么就是父节点下面加载
	                    	//treeObj.reAsyncChildNodes(nodes[0], "refresh", false);
	                    	//treeObj.expandNode(nodes[0],true, true, false);
	                    }  
	                }  
	                //treeObj.expandNode(nodes[0],true, true, false);// 将新获取的子节点展开  
	            }  
	        },  
	        error : function() {  
	            alert("请求错误！");  
	        }  
	    });  
	      
	};*/ 
