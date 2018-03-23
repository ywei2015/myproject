talkweb.Bus.ready({
	items : [{
		classPath : "Components.Tree",
		options : {
			name: "orgid",
			notes: "所属组织：",
			id: "orgtree",
			container: "orgtree",
			initSource: "initOrgTree.action",
			showIco: true,
			saveInstance: true,
			openFloor: 10,
            multiple: false,
           	treeType: "selecttree",
           	onclick:test
		}
	},{
		classPath: "BaseControl.Select",
		options: {
			name: "jianzhi",
			id: "jianzhi",
			container: "selectjz",
			onchange:dd
		}
	},{
		classPath: "BaseControl.Button",
		options: {
			container: "",
			value: "添加",
			click: function (){
				alert('hahahha');
			}
		}
	},{
		classPath: "BaseControl.Button",
		options: {
			container: "",
			value: "确定",
			click: function (){
				alert('hahahha');
			}
		}
	}]
});



//selecttree点击事件，动态刷新兼职信息
function changejianzhi(){
	var orgtreeob = talkweb.ControlBus.getInstance("datagrid");
	var orgid = orgtreeob.getCurrentId();
	//勤能给出职位下拉框中除了第一个option的其他所有option元素
	$("#org >option").not($("#org >option").first()).remove();
	//调用添加职位下拉框的ajax请求函数
	getInfos(orgid);
}

//添加职位下拉框的ajax请求函数
function getInfos(fid){
   $.ajax({
   url: "user_getPosition.action",
   data:{ogid:fid},
   dataType: "json",
   success: function(data){
      $.each(data,function(index,item){
         var op = $('<option value='+item.positionid+'>'+item.posiname+'</option>');
         $("#jianzhi").append(op);
       });    
       }
   });
}
    
    
$.use(['jquery-ui-dialog'], function($) {
	
     $("#bt").click(function(){   //文档加载完毕后给id为bt的按钮添加点击事件执行添加row的函数addrow
           addrow();
           });
       
     $("#sure").click(function(){ //文档加载完毕后给id为sure的按钮添加点击事件去除table中的所有删除按钮并将table的html传送回去到父页面
	      $("#jianzhi").contents().find("td").has("input.btn").remove();
	      $(window.parent.document).find("th[name='jianzhis']").html($("#jianzhi").html());
	        window.parent.addjianzhidia.dialog('close');
	    
          });
//定义全局变量组织id和组织内容
    var fid;
    var ftxt;
//定义全局变量职位id和职位内容  
    var orgid = $("#org").val();
    var org = $("#org").find("option:selected").text();
    
    $("#org").change(function(){   //给职位的下拉框组建添加onchange事件  
        orgid = $(this).val();
        org = $("#org").find("option:selected").text();
        });
    $("#jianzhi").delegate('input[type="button"]', 'click', function() {  //给页面后添加的删除链接委托点击事件用于删除当行
        $(this).parents("tr").remove();
    	});  
//初始化下拉框树组件
$.use('jqselecttree', function($) {
	$("#orgTree").selecttree({				
				dataType:"json",
				showLine: true,
				dataSource:"initOrgTree.action",
				valueShowAll:true,
				nodeonclick:true				
			});	
	$.fn.selecttree.node_onclick = function (id,txt) {  //选中下拉框树中节点时触发的函数
   		fid = id ;
   		ftxt = txt;
   		$("#org >option").not($("#org >option").first()).remove(); //勤能给出职位下拉框中除了第一个option的其他所有option元素
   		getInfos(id);                                //调用添加职位下拉框的ajax请求函数
   		orgid=-1;                                    //初始化职位id为-1
	};
	
});
//动态组装tr
    function addrow(){
    var flag = 0;
    if(orgid==-1){
    	$.dialogmsg({
          		msg:"请选择职位"
          	});	
       flag=1;
       return;
    }
    $("#table1 tr").each(function(index,item){
        var $it = $(this);
        var td0 = $it.find("td").eq(0).text();
        var td1 = $it.find("td").eq(1).text();
        alert
        if( td0 === ftxt &  td1 === org){
          $.dialogmsg({
          		msg:"不能重复添加"
          	});
        flag = 1;
        return;
        }
    });
    if(flag === 0 ){
       $("#table1").append('<tr><th><input type="hidden" name="orgids" value='+fid+'>兼职组织:</th><td>'+ftxt+'</td><th><input type="hidden" name="positions" value='+orgid+'>兼职岗位:</th><td>'+org+'</td><td><input type="button" class="btn" value="删除"/></td></tr>');
        }
    }
//添加职位下拉框的ajax请求函数
    function getInfos(fid){
    	$.ajax({
    	 url: "user_getPosition.action",
    	 data:{ogid:fid},
    	 dataType: "json",
         success: function(data){
         	$.each(data,function(index,item){
         		var op = $('<option value='+item.positionid+'>'+item.posiname+'</option>');
         		$("#org").append(op);
         	});    
         }
    	});
    }
    
  //****************************************其他************************************************************** 
    window.parent.addjianzhidia.dialog("fit_height");	
});