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
		//alert(fastDev.Util.JsonUtil.parseString(condition));
		fastDev.getInstance('grid1').refreshData(condition);
		console.info(condition);
	}

	/** ************************************************************** */
	function onBeforeReady() {
		this.setOptions({
			initSource : 'taskVerifyAndComment/taskVerifyAndComment_comment.action'
		});
	};
	function onAfterInitRender() {
		var body = fastDev(window).height();
		this.setHeight(body - 112);
		showResourceModes("rwpj");
	}

	function openstd(value,obj) {
		var version=fastDev.getInstance('grid1').getValue(obj)[0].c_std_verflag;
	
		var url="actNode_getCommStdInfoById.action?actnodeid="+value+"&version="+version;
		fastDev.get(url,{
			
			success:function(data){
				fastDev.create("Window", {
					// 字符串中可以使用HTML标签
					// 无需显式为文本内容指定窗体的宽高值，弹窗默认将根据当前内容以及可视窗口区域的大小自适应弹窗的尺寸
					content : "<span style='margin:20px;color:green;'>" +(data["StdInfo.c_review_std"]||"").replace(/\\n/g, "<br/>") + "</span>",
					title : "评价标准"
				});
			}
		});

	}
	
	

	function operLinkRenderer(value) {
		return [ '<a href="javascript:void(0);" onclick=openstd("'
				+ value
				+ '",this) style="text-align:center;" class="btn"  name="verifyStd">'
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
	 		     			getUserCode(cTaskId);
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
	   fastDev.post(url,{
		  success:function(data){
			var emis_url= data.emis_url+"?rnd=1--taskId="+cTaskId+"%22&SourcePage=Mes&LoginUserCode="+data.userCode;
			//var emis_url="http://www.baidu.com";
			dlWin=fastDev.create("Window",{
				title:"发起度量系统事件",
				src:emis_url,
				height:"90%",
				width:"90%",
				inside:false,
				onBeforeClose:function(){
					fastDev.getInstance('grid1').refreshData(false);
				}
			});  
			
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
	
	function showStatus(value){
		var cstatus='';
		var grid=fastDev.getInstance("grid1");
		var c_ex_iemisevent=grid.getValue(this)[0].c_ex_iemisevent;
		var result=grid.getValue(this)[0].cEvaluateResult;
		var cTaskId=grid.getValue(this)[0].cTaskId;
		result=result.substring(0,2);
		switch(value){
		case '34':
			cstatus=['未评价'] ;
			break;
		case '35':
			if(result=='NG'&&c_ex_iemisevent=='0'){
				cstatus=['<a href="javaScript:void(0); onclick=getUserCode('+cTaskId+')">已评价未发起事件</a>'] ;
			}else{
				cstatus=['已评价'] ;
			}
			break;
		
		}
		
		return cstatus.join('');
	}
	
	function toVerifyResult(value){
		var rs=["异常"];
		if(value=="OK" ||value=='' ||value==null ){
			rs=["正常"];
		}
		return rs.join('');
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
		if(value=='34'){
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
			checkAll();
			getAllChecked();
			rd.setValue(1);
		}else if(item.value=='2'){
			clearCheck();
			rd.setValue(2);
		}
		
	}
	
	
	function mutiSubmit(){
		
		var cTaskId=getAllChecked();
		var url="taskVerifyAndComment_commentStatusUpdate.action";
		if(cTaskId==null || cTaskId==''){
			fastDev.alert("请选择行！");
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
