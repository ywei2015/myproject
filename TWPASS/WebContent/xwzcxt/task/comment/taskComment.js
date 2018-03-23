/**
 * 
 */

	var request = fastDev.Browser.getRequest();
	// 通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
	var item = request['item'];
	var extype = getUrlParam('extype');
	var userCode = getUrlParam('userCode');
	// 刷新条件
	var condition = null;

	function doResetOrg() {
		fastDev.getInstance('orgform').cleanData();
		condition = null;
	}
	window.onload = function(){ 
		  if(extype!=null&&extype==1){
		  fastDev.getInstance("extype").setValue(1);
		  fastDev.getInstance("userCode").setValue(userCode);
		  doSearch();
		  }
	}
	
	function getUrlParam(name) {
		var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); //匹配目标参数
		if (null != r) {
			return unescape(r[2]);
		} else {
			return null; //返回参数值
		}
	}
	/**
	 * 过滤信息
	 */
	function doSearch() {
		condition = null;
		var form = fastDev.getInstance("orgform");
		condition = form.getItems();
		condition['c_task_kind']=request['c_task_kind'];
		//alert(fastDev.Util.JsonUtil.parseString(condition));
		fastDev.getInstance('grid1').refreshData(condition);
		console.info(condition);
	}

	/** ************************************************************** */
	function onBeforeReady() {
		this.setOptions({
			initSource : 'taskVerifyAndComment/taskVerifyAndComment_comment.action?c_task_kind='+request['c_task_kind']
		});
	};
	function onAfterInitRender() {
		var body = fastDev(window).height();
		this.setHeight(body - 122);
		showResourceModes("rwpj");
	}

	function openstd(obj) {
		var std=fastDev.getInstance('grid1').getValue(obj)[0].c_review_std;

				fastDev.create("Window", {
					// 字符串中可以使用HTML标签
					// 无需显式为文本内容指定窗体的宽高值，弹窗默认将根据当前内容以及可视窗口区域的大小自适应弹窗的尺寸
					content : "<span style='margin:20px;color:green;text-align:left;'>" +(std||"").replace(/\\n/g, "<br/>") + "</span>",
					title : "评价标准"
				});
	}
	
	function toEventLink(value){
		var cTaskId=fastDev.getInstance('grid1').getValue(this)[0].cTaskId;
		if(value=='已评价未发起事件'){
				return '<a href="javaScript:void(0); onclick=getUserCode('+cTaskId+')">已评价未发起事件</a>';
		}
		return value;

	}
	
	function exportXLS() {
		loadingWindow = fastDev.create("ProgressBar", {
			container : "orgform",
			text : "导出中..."
		});
		var formData = fastDev.getInstance("orgform").getItems();
		formData['c_task_kind']=request['c_task_kind'];
		fastDev.create("Proxy", {
			action : 'taskVerifyAndComment/taskVerifyAndComment_exportComment.action?c_task_kind='+request['c_task_kind']
		}).save(formData, function(data) {
			fastDev("#dc").prop("src", data.url);
			loadingWindow.close();
		});	
	}
	
	function onchange(value) {
		var c_task_kind=request['c_task_kind'];
		var cTaskType = fastDev.getInstance("cTaskType");
		if(c_task_kind=='1'){
			cTaskType.enable();
			cTaskType.enableItems("1");
			cTaskType.enableItems("2");
			cTaskType.enableItems("3");
			cTaskType.enableItems("5");
			cTaskType.enableItems("6");
			cTaskType.disableItems("20");
		}else if(c_task_kind=='3'){
			cTaskType.enableItems("20");
			cTaskType.disableItems("1");
			cTaskType.disableItems("2");
			cTaskType.disableItems("3");
			cTaskType.disableItems("6");
			cTaskType.disableItems("5");
		}
		
	}

	function operLinkRenderer(value) {
		return [ '<a href="javascript:void(0);" onclick=openstd(this) style="text-align:center;" class="btn"  name="verifyStd">'
				+ '查看</a>' ].join('');
	}

    function commentResult(event,item){
    	var rowid=getRowId(this);
    	var obj=fastDev.getInstance("result"+rowid);
	 	var value=obj.getValue();
	 	var url="../../taskVerifyAndComment_commentResultUpdate.action";
	 	var cTaskId= fastDev.getInstance('grid1').getValue(event.target)[0].cTaskId;
	 	fastDev.confirm("是否确定修改评价结果？","信息提示",function(result){
	 		if(result){
	 			 if(value=='NG' ){
	 		    	   fastDev.post(url,{
	 		     		  success:function(data){
	 		     			/*getUserCode(cTaskId);*/
	 		     		  },
	 		     		  data:{
	 		     			  "cTaskId":cTaskId,
	 		     			  "cEvaluateResult":"NG"
	 		     		  }
	 		     	  });
	 		    	  
	 		   		
	 		       }else if(value==''||value==null||value=='OK'){
	 		    	   obj.setValue("OK");
	 		    	 
	 		    	  fastDev.post(url,{
	 		    		  success:function(data){
	 		    		  },
	 		    		  data:{
	 		    			  "cTaskId":cTaskId,
	 		    			  "cEvaluateResult":"OK"
	 		    		  }
	 		    	  });
	 		       }
	 		      
	 		}else{
	 			var val=obj.getValue();
	 			if(val==null||val==''){
	 				obj.setValue(item.value);
	 			}
	 			else if(val=='OK'){
	 				obj.setValue("NG");
	 			}
	 			else if(val=="NG"){
	 				obj.setValue("OK");
	 			}
	 		}
	 	});
      
    }
 
function commentStatusUpdate (cTaskId){
	var url="taskVerifyAndComment_commentStatusUpdate.action";
		fastDev.post(url,{
			success:function(data){
				//fastDev.tips(data);
				 getUserCode(cTaskId); 
			},
			data:{
				  "cEvaluateResult":"NG",
				  "cTaskId":cTaskId	
				}
		});
}

var  dlWin=null;
function getUserCode(cTaskId){
	url="taskVerifyAndComment_getUserCode.action";
//	$.ajax({
//	     type: 'POST',
//	     url: url ,
//	    data: data ,
//	    success: success ,
//	    dataType: dataType
//	});
	   fastDev.post(url,{
		  success:function(data){
			var emis_url= data.emis_url+"?rnd=1--taskId="+cTaskId+"--userId="+data.userId+"%22&SourcePage=Mes&LoginUserCode="+data.userCode;
			//var emis_url="http://www.baidu.com";
//			dlWin=fastDev.create("Window",{
//				title:"发起度量系统事件",
//				src:emis_url,
//				height:"90%",
//				width:"90%",
//				inside:false,
//				onBeforeClose:function(){
//					fastDev.getInstance('grid1').refreshData(false);
//				}
//			});  
//			$('#duliangFrame').attr("src",emis_url);
//			$('#duliang').dialog('open');
			var returnValue=window.showModalDialog("emis.html",emis_url,"dialogWidth=850px;dialogHeight=700px");
			if(returnValue=="success")
				{
				fastDev.getInstance('grid1').refreshData(false);
				}
		  }
	  });
}


  var myGrid;
  function getRowId(target){
	  if(!myGrid){
		  myGrid=fastDev.getInstance("grid1");
	  }
	  
	  return myGrid.getValue(target)[0].dg_seq;
  }
  
  
  function toRadioBoxes(value){
	 
	  var rowid= getRowId(this);
	  var text="合格";  
	    if(value!="OK" &&  value!=null &&  value!=""  ){
	    	value="NG";
	    	text="不合格";
	    }else{
	    	value="OK";
	    }
	    //alert(value);
	 	var commentTpl=	 
		     ['<div style="display:none; position:relative;" id="comment">'+
		          '<div itype="RadioGroup" onchange="commentResult()" value='+
		           value+'   saveInstance="true"   name="result" id="result'+rowid+'">'+
					   '<div value="OK" text="合格" ></div>'+
				       '<div value="NG" text="不合格"></div>'+
                  '</div>'+
                  '<div id="commentText" style="display:none;">'+text+'</div>'+
             '</div>'];
	 	
		return commentTpl.join('');
  }
  
	
	function isCommented(){
	   var status= fastDev.getInstance('status').getValue();
	   var obj=  fastDev.getInstance('cEvaluateResult');
	   if(status=='35'){
	     	obj.enable();
	   }else{
		    obj.setValue("");
	        obj.disable();
	   }
	}
	
	

	function toSubmit(value){
		var status=fastDev.getInstance('grid1').getValueByIndex(getRowId(this)).cStatus;
		if(status=='35'){
			return '';
		}
		var submit=['<a href="javaScript:void(0);"  id="submit"  style="display:none;"'+
		            'tilte="提交"  onclick=itemComment(this) >提交</a>'];
		return submit.join('');
	}
	
	
	function itemComment(obj){
		var cTaskId=fastDev.getInstance("grid1").getValue(obj)[0].cTaskId;
		var url="taskVerifyAndComment_commentStatusUpdate.action";
		fastDev.confirm("是否提交评价？","信息提示",function(result){
			if(result){
				fastDev.post(url,{
					success:function(data){
						fastDev.tips(data);
						refreshCurrrnt();
					},
					data:{
						"cTaskId":cTaskId	
						}
				});
			}
		});
		
	}
	
	function refreshCurrrnt(){
		var dg=fastDev.getInstance('grid1');
		dg.refreshData(false);
		
	}
	
	
	function mutiSubmit(){
		var url="taskVerifyAndComment_commentStatusUpdate.action";
		var data=fastDev.getInstance('grid1').getValue();
		if(!data){
			fastDev.alert("请选择行！");
			return;
		}
		var cTaskId='';
		for(var i=0;i<data.length;i++){
			if(data[i].cStatusName=="未评价"){				
				cTaskId+=data[i].cTaskId+",";
			}
		}
		if(!cTaskId){
			fastDev.alert("请选择至少一行未评价的数据！");
			return;
		}
		
		fastDev.confirm("是否提交评价结果？","信息提示",function(result){
			if(result){
				fastDev.post(url,{
			    	success:function(data){
			    		fastDev.getInstance('grid1').refreshData(false);
			    		fastDev.tips(data);
			    	},
			    	data:{
			    		"cTaskId":cTaskId
			    	}
			    });
			}
		});
	    
			
	}
	
	
	
	function doOpenTaskDetails(c_task_id){
		var url = window.location.href;
		var p_url = url.substring(0, url.lastIndexOf("/"));
		var p_path = p_url.substring(0, p_url.lastIndexOf("/")+1);
		fastDev.create("Dialog", {
			height:500,
			width:900,
			inside:false,
			showMaxBtn:false,
		    title:"任务详情",
		    src : p_path+"taskDetails.html?edit=details&taskId="+ c_task_id,
			buttons : [{
				text : "关闭",
				align : "center",
				iconCls : "icon-close",
				onclick : function(event, that, win, fast) {
					// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
					// 参数that为当前对话框控件实例
					that.close();
				}
			}]
		});
	}
	
	function toDetailsLink(value){
		var rowid=getRowId(this);
		var cTaskId=fastDev.getInstance('grid1').getValueByIndex(rowid).cTaskId;
		
		var link=['<a href=javaScript:viod(0);  onclick = doOpenTaskDetails("'+
		          cTaskId+'")>'+value+'<a/>'];
		return link.join('');
	}	
	
	
	function showResourceModes(type, datagrid) {
		fastDev.create('Proxy', {
			action: 'accessmode/getPrivilegeResourceModesAction.action'
		}).save({
			module: type
		}, function(result) {
			//alert(JSON.stringify(result));
			var flag=true;
			if(result != null) {
				for(var i = 0; i < result.length; i++) {
					if(flag==true&&result[i]=='comment'){
						flag=false;
					}
					fastDev('[id=' + result[i] + ']').show();
				}
				
			}
			if(flag){
				fastDev('[id=' + 'commentText' + ']').show();
			}
			if(datagrid) {
				var height = datagrid.getOptions().height;
				datagrid.setHeight(height);
			}
		});
	}

	function writeYear() {
		var curYear = new Date().getFullYear();
		var i = 3; // 年份可以选择去年、今年、明年
		var text = "";
		var startYear = curYear - 1;
		// alert(curYear+","+i);
		while (i > 0) {

			if (curYear != startYear) {
				text += '<option value="' + startYear + '">' + startYear
						+ '</option>';
			} else {
				text += '<option value="' + curYear + '" >' + curYear
						+ '</option>';
			}
			startYear++;
			i--;
		}
		document.write(text);
		document.close();
	}

	function writeMonth() { // 生成月份下拉框的选项，并把当前月份设为所选
		var curMonth = new Date().getMonth() + 1;
		var text = '';

		for ( var i = 1; i <= 12; i++) {
			if (i != curMonth) {
				text += '<option value="' + i + '">' + i + '</option>';

			} else {
				text += '<option value="' + curMonth + '" >' + curMonth
						+ '</option>';
			}
		}
		// alert(text);
		document.write(text);
		document.close();
	}
