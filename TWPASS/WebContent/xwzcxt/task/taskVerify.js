/**
 * 
 */

	var request = fastDev.Browser.getRequest();
	// 通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
	var item = request['item'];
	// 刷新条件
	var condition = null;


	
	function doResetOrg() {
		fastDev.getInstance('orgform').cleanData();
		condition = null;
	}
	
	
	/**
	 * 过滤信息
	 */
	function doSearch() {
		condition = null;
		var form = fastDev.getInstance("orgform");
		condition = form.getItems();
		var dg=fastDev.getInstance('grid1');
		dg.refreshData(condition);
		console.info(condition);
		
	}

	/** ************************************************************** */
	function onBeforeReady() {
		
		
		this.setOptions({
			initSource : 'taskVerifyAndComment/taskVerifyAndComment_verify.action'
		});
	};
	function onAfterInitRender() {
		var body = fastDev(window).height();
		this.setHeight(body - 112);
		showResourceModes("rwyz");
	}

	function showStatus(value){
		var cstatus;
		switch(value){
		case '33':
			cstatus=['未验证'] ;
			break;
		case '34':
			cstatus=['已验证'] ;
			break;
		
		}
		return cstatus.join('');
	}
	
	
	
	function openstd(value,obj) {
	
		var version=fastDev.getInstance('grid1').getValue(obj)[0].c_std_verflag;
		var url="actNode_getCommStdInfoById.action?actnodeid="+value+"&version="+version;
		fastDev.get(url,{
			success:function(data){
				
				fastDev.create("Window", {
					
					// 字符串中可以使用HTML标签
					// 无需显式为文本内容指定窗体的宽高值，弹窗默认将根据当前内容以及可视窗口区域的大小自适应弹窗的尺寸
					content : "<span style='margin:20px;color:green;'>" +(data["StdInfo.c_check_std"]||"").replace(/\\n/g, "<br/>") + "</span>",
					title : "验证标准",
					
				});
				
			}
		});
		
		
	}
	
	function timeRender(value){
		if(value){
			return value.replace("T"," ");
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
		return [ '<a href="javascript:void(0);" onclick=openstd("'
				+ value
				+ '",this) style="text-align:center;" class="btn"  name="verifyStd">'
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
	   if(status=='34'){
	     	obj.enable();
	   }else{
		    obj.setValue("");
	        obj.disable();
	   }
	}
	
	
	function toSubmit(){
		 var status=fastDev.getInstance('grid1').getValueByIndex(getRowId(this)).cStatus;
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
	var a=new Array();  
	var maxRowid=0;
	function addCheckBox(value){
		var cbox=[""];
		var rowid=getRowId(this);
		if(value=='33'){
			//alert(value+"^^^^^^");
			a[rowid]=-2;
			cbox=['<div itype="CheckBoxGroup" onchange="isRowCheck()" saveInstance=true id="cb'+rowid+'">'+
			      '<div value="'+rowid+'"></div></div>'];
		}else{
			//alert(value+"*******");
			a[rowid]=-3;
		}
		//alert(cbox);
		maxRowid=rowid;
		return cbox.join('');
	}
	
	function isRowCheck(event,item){   //标记行是否被选择
		var rowid=fastDev.getInstance('grid1').getValue(event.target)[0].dg_seq;
		
		if(fastDev.getInstance('cb'+rowid).getValue()){
			a[rowid]=rowid;
		}else{
			a[rowid]=-1;  
		}
		
	}
	
	function getAllChecked(){    //获得所有被选择的行
		var dg=fastDev.getInstance('grid1');
		var cTaskId="";
		for(var i=1;i<=maxRowid;i++){
			if(a[i]>=1){
				cTaskId+=dg.getValueByIndex(i).cTaskId+",";
			}
		}

	  return cTaskId;
	}
	
	function clearCheck(){   //清空所有被选择的行
		fastDev("#allBtn").show();
		fastDev("#clearBtn").hide();
		var dg=fastDev.getInstance('grid1');
		for(var i=1;i<=maxRowid;i++){
			if(a[i]>=1){
				fastDev.getInstance("cb"+i).setValue(-1);
				a[i]=-1;
			}
		}
	}
	
	function checkAll(){    //选择所有行
		fastDev("#allBtn").hide();
		fastDev("#clearBtn").show();
		for(var i=0;i<=maxRowid;i++){
			if(a[i]!=-3&&a[i]<0){
				fastDev.getInstance("cb"+i).setValue(i);
				a[i]=i;
			}
		}
	}
	
	function showCheck(event,item){   //点击全选或取消已选的时候设定选多选框的状态
		var rd=fastDev.getInstance('rowCheck');
		if(item.value=='1'){
			rd.setValue('1');
			checkAll();
			getAllChecked();
		}else if(item.value=='2'){
			rd.setValue('2');
			clearCheck();
		}
		//fastDev.getInstance('rowCheck').setValue(0);
	}
	
	
	function mutiSubmit(){
		var cTaskId=getAllChecked();
		var url="taskVerifyAndComment_verifyStatusUpdate.action";
		if(cTaskId==null || cTaskId==''){
			fastDev.alert("请选择行！");
			return;
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
	
function toIsExpired(){
	var rowid=getRowId(this);
	var val=fastDev.getInstance('grid1').getValueByIndex(rowid);
	
	if(val.cFactEndtime>val.cEndTime){
		return ['已逾期'].join('');
	}else{
		return ['按期完成'].join('');
	}
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
	    src : p_path+"task/taskDetails.html?edit=details&taskId="+ c_task_id,
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


function toResult(res){
	return res.substring(3);
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