
    var zTree;
    var demoIframe;
    var setting = {
      /*  check: {
            enable: true //是否可选中
        },*/
        view: {
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
                rootPId: 1000//。[setting.data.simpleData.enable = true 时生效]
            }
        },
        callback: {
        	
        	//用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作
            beforeClick: function(treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree");
               var demoIframe=$("#rightIframe");
                
                if (treeNode.isParent) {
//               		 zTree.expandNode(treeNode);
                    demoIframe.attr("src","obj_data_list.html?id="+treeNode.id);
                    return true;
                } else {
                    demoIframe.attr("src","dic_data_list.html?id="+treeNode.id);
                    return true;
                }
            },
       	 /*    beforeExpand:function(treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree");
               var demoIframe=$("#rightIframe");
                
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);
                 	 demoIframe.attr("src","obj_data_list.html?id="+treeNode.id);
                    return true;
                } else {
        			
                   demoIframe.attr("src","dic_data_list.html?id="+treeNode.id);
                    return true;
                }
            },*/
            edit:{
            	enable :true,
            	showRemoveBtn : false,
            	showRenameBtn : false
            }
            /*beforeRename:function(treeId,treeNode){
            	console.log("修改触发函数"+"treeid=   "+treeId+",treenode=   "+treeNode);
            },
            beforeRemove:function(treeId,treeNode){
            	console.log("删除触发函数"+"treeid=   "+treeId+",treenode=   "+treeNode);
            }*/
            
        },
     /*   edit:{
        	enable :true,
        	showRemoveBtn : true
        },*/
        async : {
			autoParam : ["id"],
			//contentType : "application/x-www-form-urlencoded",
			dataFilter : null,
			dataType : "text",
			enable : true,
			
			otherParam : [],
			type : "post",
			url : "../../../dynamic/objBase/getList"
		}

    };

  

    $(document).ready(function(){
        var t = $("#tree");
      //  $.getJSON('json/type1.json',function(result){
	        t = $.fn.zTree.init(t, setting,null);
	        demoIframe=$("#rightIframe");
	 
			
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
