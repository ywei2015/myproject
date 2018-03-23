/**
 * 
 */

	var request = fastDev.Browser.getRequest();
	// 通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
	var item = request['item'];
	// 刷新条件
	var condition = null;
	var extype = getUrlParam('extype');
	var userCode = getUrlParam('userCode');
	window.onload = function(){ 
		  if(extype!=null&&extype==1){
		  fastDev.getInstance("extype").setValue(1);
		  fastDev.getInstance("userCode").setValue(userCode);
		  doSearch();
		  }
	}
	function doResetOrg() {
		fastDev.getInstance('orgform').cleanData();
		condition = null;
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
		var dg=fastDev.getInstance('grid1');
		dg.refreshData(condition);
		console.info(condition);
		
	}

	/** ************************************************************** */
	function onBeforeReady() {
		
		
		this.setOptions({
			initSource : 'taskVerifyAndComment/taskVerifyAndComment_verify.action?c_task_kind='+request['c_task_kind']
		});
	};
	function onAfterInitRender() {
		var body = fastDev(window).height();
		this.setHeight(body - 130);
		showResourceModes("rwyz");
	}

	function exportXLS() {
		loadingWindow = fastDev.create("ProgressBar", {
			container : "orgform",
			text : "导出中..."
		});
		var formData = fastDev.getInstance("orgform").getItems();
		formData['c_task_kind']=request['c_task_kind'];
		fastDev.create("Proxy", {
			action : 'taskVerifyAndComment/taskVerifyAndComment_exportVerify.action?c_task_kind='+request['c_task_kind']
		}).save(formData, function(data) {
			fastDev("#dc").prop("src", data.url);
			loadingWindow.close();
		});	
	}
	
	function openstd(obj) {
		var data=fastDev.getInstance('grid1').getValue(obj)[0];
		var c_actnode_id=data.cActnodeId;
		if(!c_actnode_id){
			fastDev.alert("没有相关标准!");
			return;
		}
		fastDev.create("Window", {
					src:"taskVerifyStd.html?c_actnode_id="+c_actnode_id,
					width:600,
					height:450,
					title : "验证标准",
					buttons:[{
						text:"关闭",
						align:'center',
						iconCls:'icon-close',
						onclick:function(event,win,cwin,fast){
							win.close();
						}
					}]
					
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
			cTaskType.disableItems("6");
			cTaskType.disableItems("1");
			cTaskType.disableItems("2");
			cTaskType.disableItems("3");
			cTaskType.disableItems("5");
		}
		
	}

	
	function showError(cTaskId) {
		
		var url="../../taskVerifyAndComment_verifyResultUpdate.action";
 	   fastDev.post(url,{
 		   success:function(data){
 			   //fastDev.tips(data);
 			  //fastDev.getInstance('grid1').refreshData(false);
 		   },
 		   data:{
 			   "cTaskId":cTaskId,
 			   "cChkResult":"NG"
 		   }
 	   });
 	   
 	  url=window.location.href;
 	  var index=-1,index2=0;
 	  for(var i=0;i<4;i++){
 		 index=url.indexOf("/")+1;
 		 index2+=index;
 		 url=url.substring(index, url.length-1);
 		 //fastDev.alert(url);
 	  }
 	  url=window.location.href.substring(0, index2);
 	 //fastDev.alert(url+"TWPASS/xwzcxt/task/verifyRecord.html");
 	 
		fastDev.create("Dialog", {
			titile:"验证时异常信息录入",
			width : "800px",
			height : "500px",
			src:url+"xwzcxt/task/verifyRecord.html?cTaskId="+cTaskId,
			inside : false,
			modal : false,
			buttons:[{
				text:"提交",
				width:"100px",
				align:"center",
				iconCls : 'icon-save', 
				onclick : function(e,win,cwin, fd){
					 var grid=fastDev.getInstance('grid1');
					 cwin.submit(grid,win);
					 
				}
			},{
				text:"重置",
				width:"100px",
				align:"center",
				iconCls : 'icon-reset', 
				onclick : function(e,win,cwin, fd){
					 cwin.reset();
				}
			},{
				text:"关闭",
				width:"100px",
				align:"center",
				iconCls : 'icon-reset', 
				onclick : function(e,win,cwin, fd){
					win.close();
				}
			}]
		}
		
		);
	}

	function operLinkRenderer(value) {
		return [ '<a href="javascript:void(0);" onclick=openstd(this) style="text-align:center;" class="btn"  name="verifyStd">'
				+ '查看 </a>' ].join('');
	}

    function verifyResult(event,item){
    	var rowid=getRowId(this);
    	var obj=fastDev.getInstance("result"+rowid);
	 	var value=obj.getValue();
	 	var cTaskId=fastDev.getInstance("grid1").getValue(event.target)[0].cTaskId;
	 	
	 	
    	fastDev.confirm("是否确定改变验证结果？","信息提示",function(result){
    		if(result){
    			if(value==''||value==null||value=="OK"){
    		    	   obj.setValue("OK");
    		    	   var url="../../taskVerifyAndComment_verifyResultUpdate.action";
    		    	   fastDev.post(url,{
    		    		   success:function(data){
    		    			   //fastDev.tips(data);
    		    			   fastDev.getInstance('grid1').refreshData(false);
    		    		   },
    		    		   data:{
    		    			   "cTaskId":cTaskId,
    		    			   "cChkResult":"OK"
    		    		   }
    		    	   });
    		    	  // fastDev.getInstance('grid1').refreshData(false);
    		    	   return;
    		        }
    		    	   showError(cTaskId);
    		 
    		}else{
    			//alert(value+","+item.value);
    			if(value==''||value==null){
    				obj.setValue(item.value);
    				return;
    			}
    			if(value=='OK'){
    				obj.setValue('NG');
    			}else{
    				obj.setValue('OK');
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
	  var  text="正常";  
	    if(value!="OK" &&  value!=null &&  value!=""  ){
	    	value="NG";
	    	text="异常";
	    }else{
	    	value="OK";
	    }
	    //alert(value);
	 	var verifyTpl=	 
		     ['<div style="display:none;" id="verify"><div itype="RadioGroup" onchange="verifyResult()" value='+
		             value+'   saveInstance="true"   name="result" id="result'+rowid+'">'+
					'<div value="OK" text="正常" ></div>'+
				    '<div value="NG" text="异常"></div>'+
             '</div><div id="verifyText" style="display:none">'+text+'<div></div>'];
	 	
		return verifyTpl.join('');
  }
  
	
	function isVerified(){
	   var status= fastDev.getInstance('status').getValue();
	   var obj=  fastDev.getInstance('cChkResult');
	   if(status=='33'){
	     	obj.enable();
	   }else{
		    //obj.setValue("");
	        obj.disable();
	   }
	}
	
	
	function toSubmit(){
		 var status=fastDev.getInstance('grid1').getValue(this)[0].cStatus;
         if(status=='34'){
        	 return [''].join('');
         }
		 var submit=['<a  href="javaScript:void(0);"  id="submit"  style="display:none;" '+
		            'onclick=openSubmit() name="verSubmit">提交</a>'];
		 //alert(submit);
		return submit.join('');
	}
	
	function openSubmit(){
		var cTaskId=fastDev.getInstance('grid1').getValue(event.target)[0].cTaskId;
		var url="../../taskVerifyAndComment_verifyStatusUpdate.action?cTaskId="+cTaskId;
		fastDev.confirm("是否提交验证结果？","信息提示",function(result){
			if(result){
				fastDev.post(url,{
					success: function(data){
						refreshCurrrnt();
						fastDev.tips(data);
						
					}
				}
				);
			}
		});
		
	}
	
	function refreshCurrrnt(){ 
		var dg=fastDev.getInstance('grid1');
		dg.refreshData(false);
		
	}
	
	/* 当a[index]=rowid时，表示多选框被选中
	 * 当a[index]=-1时，表示多选框被选中后又被取消
	 * 当a[index]=-2时，表示多选框被创建
	 * 当a[index]=-3时，表示在DataGrid的行号为rowid的行未创建多选框
	 */
	

	function mutiSubmit(){
		var data=fastDev.getInstance('grid1').getValue();
		var url="taskVerifyAndComment_verifyStatusUpdate.action";
		if(!data){
			fastDev.alert("请选择行！");
			return;
		}
		var cTaskId='';
		for(var i=0;i<data.length;i++){
			if(data[i].cStatus=='33'){				
				cTaskId+=data[i].cTaskId+",";
			}
		}
		fastDev.confirm("是否确定提交验证结果?","信息提示",function(result){
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
				if(flag==true&&result[i]=='verify'){
					flag=false;
				}
				fastDev('[id=' + result[i] + ']').show();
			}
			
		}
		//alert(flag);
		if(flag){
			fastDev('[id=' + 'verifyText' + ']').show();
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
	document.close()
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