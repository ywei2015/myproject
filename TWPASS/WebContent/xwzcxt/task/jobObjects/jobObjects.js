var request = fastDev.Browser.getRequest();
var type = request['type'];
var cPositionID = request['cPositionID'];
var column=null;  //DataGrid的列数组
var tmpColumn=null; //临时DataGrid的列数组
var grid=null;   //DataGrid对象
var form=null;  //表单对象
var cTabletypeId=null;  //表单里面的隐藏域中的类型对象
var enable = null;

function onBeforeReady(){
	if(type=='set'){
		url="jobObjects_getObjectInfo.action?positionId="+cPositionID;
	} else {
		url="jobObjects_getObjectInfo.action";
	}
	this.setOptions({
		initSource:url
	});
}

function onAfterInitRender(){
	var body=fastDev(window).height();
    this.setHeight(body-200);
	if(type!='set'){
		showResourceModes("JC_1");
	}
}

function onTableTypeAfterInitRender() {
	if(type!='set'){
		showResourceModes("JC_1");
	}
}

/**
 * 取得资源所拥有的按钮级权限进行按钮级权限控制，同时支持对DataGrid设定高度
 * @param type
 * @param datagrid
 */
function showResourceModes(type, datagrid) {
	fastDev.create('Proxy', {
		action: 'authorityManage/getPrivilegeResourceModesAction.action'
	}).save({
		module: type
	}, function(result) {
		//alert(JSON.stringify(result));
		if(result != null) {
			for(var i = 0; i < result.length; i++) {
				if(result[i] == 'add'){
					setButtonsEnable();
				}
				else{
					fastDev('[id=' + result[i] + ']').show();
				}
			}
		}
		if(datagrid) {
			var height = datagrid.getOptions().height;
			datagrid.setHeight(height);
		}
	});
}

function writeColumn(cTabletypeId){
	var url="jobObjects_getColumnsConfig.action";
	if(cTabletypeId==null||cTabletypeId==undefined){
		return;
	}

	fastDev.post(url,{
		success:function(data){
			column=data;
			//test(data);

			if(grid==null){
				grid=fastDev.getInstance('jobObjectsInfo');
			}
			for(var j=0;tmpColumn!=null&&j<tmpColumn.length;j++){   //移除之前存在的所有列
				grid.removeColumn(tmpColumn[j].name);
			}
			if(column.length==undefined){
				return;
			}
			
			for(var i=0;i<column.length;i++){  //重新设定新得到的列
				//alert(column[i]);
				grid.addColumn(column[i],i+12);
				//alert("增加第"+i+"列："+fastDev.Util.JsonUtil.parseString(column[i]));
			}
			//alert(fastDev.Util.JsonUtil.parseString(column));
			
			tmpColumn=column;

			var table=document.getElementById("tbgrid");

			var rowNum=table.rows.length; 
			if(rowNum>0){ 
				for(i=4;i<rowNum;i++){ 
					table.deleteRow(i);
					rowNum=rowNum-1;
			        i=i-1;
				} 
			}

			var tr,td1,td2,textNode,input,div;
			var j=0;
			var s=0;
			for(var i=0;i<column.length;i++){
				if(column[i].isshow=="1"){
					if(s%2==0){
						tr=document.createElement("tr");
					}
					td1=document.createElement('td');
				    td1.setAttribute("class", "ui-form-table-dt");
				    textNode=document.createTextNode(column[i].text);
				    td1.appendChild(textNode);
				    td2=document.createElement('td');
				    td2.setAttribute("class", "ui-form-table-dd");
				    div=document.createElement("div");
				    div.setAttribute("class", "ui-form-wrap ui-input");
				    
				    
				    input=document.createElement('input');
				    input.setAttribute('id', "tbIntegrateInfo."+column[i].name);
				    input.setAttribute('type', 'text');
				    input.setAttribute("class", "ui-form-field ui-form-input");
				    div.appendChild(input);
				    td2.appendChild(div);
				    tr.appendChild(td1);
				    tr.appendChild(td2);  
					    
					j++;
					s++;
					if(j==2){
						
						table.appendChild(tr);
						j=0;
					}
				}
			}
			if(j!=0){
//				td2.setAttribute("colspan", 3);
				table.appendChild(tr);
			}
		},
		data:{"cTabletypeId":cTabletypeId}
	});
}

function doResetOrg() {
	fastDev.getInstance('jobObjects').cleanData();
	fastDev.getInstance('cTabletypeId').setValue(fastDev.getInstance('tableTypeTree').getValue());
}


/**
 * 过滤信息
 */
function doSearch() {
	var cTabletypeId=fastDev.getInstance('cTabletypeId').getValue();
	if(!cTabletypeId){
		fastDev.alert("清先选择作业对象类型!");
		return;
	}
	var condition = null;
	if(form==null){
		form = fastDev.getInstance("jobObjects");
	}
	condition = form.getItems();
	//alert(fastDev.Util.JsonUtil.parseString(condition));
	if(grid==null){
		grid=fastDev.getInstance('jobObjectsInfo');
	}
	if(tmpColumn!=null){
		for(var i=0;i<tmpColumn.length;i++){
			if(column[i].isshow=="1"){
				var id="tbIntegrateInfo."+tmpColumn[i].name;
				var value=document.getElementById(id).value;
				condition[id]=value;
			}
		}
	}
	grid.refreshData(condition);
}

function getTypeValue(event,value){
	if(cTabletypeId==null){
		cTabletypeId=fastDev.getInstance('cTabletypeId');
	}
	console.info(value);
	cTabletypeId.setValue(value);
	currentTreeeNode = this.getNodeByid(value);
	enable = currentTreeeNode.allowmodify;
	if(type!='set'){
		setButtonsEnable();
	}
	writeColumn(value);
	doSearch();
}

function setButtonsEnable() {
	var cTabletypeId=fastDev.getInstance('tableTypeTree').getValue();
	if(cTabletypeId.trim()=='-1'){
		fastDev('[id=addObjectBtn]').show();
		fastDev('[id=addTableTypeBtn]').show(); 
	}else{
		if(enable=="1"){
			fastDev('[id=addTableTypeBtn]').hide(); 
			fastDev('[id=addObjectBtn]').show();
			
		}
		else if(enable=="2"){
			fastDev('[id=addTableTypeBtn]').show(); 
			fastDev('[id=addObjectBtn]').hide();
		}
	}
}

function multiDelete(){
    if(grid==null || grid.getValue().length==0){
    	fastDev.tips("请选择数据！");
    	return;
    }
	fastDev.confirm('是否确定删除选择行的数据？','数据删除提示',function(result){
		if(result){
			   id="";
			   var items=grid.getValue();
			   for(var i=0;i<items.length;i++){
				   	id+=items[i].cBasedataId+",";
				   
			   }
			   deleteData(id);
			  
			   }
	});
	
	
	   
}

function deleteOne(cBasedataId){
	fastDev.confirm("是否确定删除当前行的数据？","单条数据删除提示",function(result){
		if(result){
			deleteData(cBasedataId);
		}
	});
}

function deleteData(cBasedataId){
	var url="jobObjects_deleteObjects.action";
	fastDev.post(url,{
		success:function(data){
			grid.refreshData(false);
			fastDev.tips(data);
		},
	    data:{"cBasedataId":cBasedataId}
	});
}

function searchObject(){
	if(cTabletypeId==null){
		cTabletypeId=fastDev.getInstance('cTabletypeId');
	}
	var tabletypeId=cTabletypeId.getValue();
	if(tabletypeId==''||tabletypeId==null||tabletypeId==undefined){
		fastDev.alert('请选择作业对象类型!');
		return;
	}
	modifyObject(null,'search');
}

function modify(){
	modifyObject(null,'add');
}

function modifyObject(cBasedataId,type){
	var tabletypeId=cTabletypeId.getValue();
	var url=window.location.href;
	
	var index=-1;
	for(var i=0;i<4;i++){
		index=url.indexOf("/", index+1);
	}
	url=url.substring(0,index)+"/xwzcxt/task/jobObjects/";
    url+="objectDetails.html?cBasedataId="+cBasedataId+'&cTabletypeId='+tabletypeId+'&type='+type;
    var titleName='修改作业对象';
    var btnText="提交";
    var isModel=true;
	if(type=='add'){
		titleName="新增作业对象";
	}else if(type=='search'){
		titleName="查询作业对象";
		btnText="查询";
		isModel=false;
	}
	
	
	fastDev.create("Window",{
		title:titleName,
		width:"800px",
		height:"300px",
		src:url,
		buttons:[{
			modal:isModel,
			text:btnText,
			width:"100px",
			align:"center",
			iconCls : 'icon-save', 
			onclick : function(e,win,cwin, fd){
				if(type=='search'){
					cwin.doSave(grid,win);
				}else{
					fastDev.confirm("是否确定提交当前数据?","数据提交提示",function(result){
						if(result){
							 cwin.doSave(grid,win);
							
						}
					});
				}
				
				
			}
		},{
			text:"重置",
			width:"100px",
			iconCls : "icon-reset",
			align:"center",
			onclick:function(event,win,cwin,fast){
				//alert(111);
				cwin.resetForm();
			}
		}
		         ]
	});
}

function toOperationLink(){
	//grid=fastDev.getInstance('jobObjectsInfo');
	var value=grid.getValue(this)[0].cBasedataId;
	var locStatus = grid.getValue(this)[0].locStatus;
	//alert(value);
	
	return '<a href="javaScript:void(0);" id="modify" style="display:none;" onclick=modifyObject('+value+',"") >修改</a>&nbsp;&nbsp;'+
	       '<a href="javaScript:void(0);" id="delete" style="display:none;" onclick=deleteOne('+value+') >删除</a>&nbsp;&nbsp;'+
	       '<a href="javaScript:void(0);" id="printCode" style="display:none;" onclick=printCode(this,'+value+','+locStatus+') >打印二维码</a>';
}


var currentTarget=null;
function changeColor(event, rowindex,data) {
    var obj=event.target;
	if(currentTarget!=null){
		fastDev(currentTarget).parents("tr").children("td").css(
				"background-color", "");
	}

	fastDev(obj).parents("tr").children("td").css("background-color", "#CABBCC");
	currentTarget=obj;
}

function printCode(obj,cBasedataId,locStatus){
	if(locStatus==0){
		var cTabletypeId=fastDev.getInstance('tableTypeTree').getValue();
		fastDev.confirm("你选择的作业对象还未维护地理坐标,是否维护？", "信息提示", function(result) {
			if (result) {
				fastDev.create("Window", {
					height : window.innerHeight,
					width : window.innerWidth,
					src : "../../../mobile/map/map.html?type=setPosition&cBasedataId=" + cBasedataId + "&cObjectTypeid=" + cTabletypeId,
					onBeforeClose : function() {
						var condition = null;
						if (form == null) {
							form = fastDev.getInstance("searchForm");
						}
						var cTabletypeId = fastDev.getInstance('tableTypeTree').getValue();
						condition = form.getItems();
						condition["cTabletypeId"] = cTabletypeId;
						if (grid == null) {
							grid = fastDev.getInstance('datagrid');
						}
						grid.refreshData(condition);
					}
				});
			}
		});
	}else{
	fastDev.post("jobObjects_getCurrentPrintCount.action",{
		success:function(data){

			data=eval('('+data+')');
			count = data.cPrintCount;
			if(isNaN(count+"")){
				fastDev.alert("服务器响应失败！"+count);
				return;
			}
			
			var printInfo=cBasedataId;
			if(data.cScanCode==undefined ||data.cScanCode=="undefined" || data.cScanCode== null || data.cScanCode=="null" || data.cScanCode==""){
				count++;
				var len=(count+"").length;
				for(var i=0;i<4-len;i++){
					printInfo+="0";
				}
				printInfo+=count;
			}else{
				printInfo = data.cScanCode;
			}
			
			var s=QRCodePrint(printInfo);
			if(s==false){
				return;
			};
			
			
		
			fastDev.confirm("打印是否成功？","打印成功确认",function(result){
				
				if(result){
					fastDev.post("jobObjects_printCode.action",{
						success:function(data){			
							fastDev.tips(data);	
							grid.refreshData(false);
						},
						data:{
							"cBasedataId":cBasedataId,
							"cScanCode":printInfo,
							"cPrintCount":count
						}
					});
				}
				
			});
		},
		data:{
			"cBasedataId":cBasedataId
		}
	});
	
	}
}

function dateformat(value){
	
	return value.replace('T'," ");
	
}

var scanDetails=[];
function toScanDetails(){
	
	var index=grid.getValue(this)[0].dg_seq-1;
	//alert(index+","+this.innerHTML);
	scanDetails[index]=this.innerHTML;
	//alert(fastDev.Util.JsonUtil.parseString(grid.getValue(this)[0]));
	return '<a href="javaScript:void(0);" onclick=getScanDetails('+index+') >点击查看扫码详情</a>';
}

function getScanDetails(index){	
	var obj=eval('('+scanDetails[index]+')');
	var details="";
	for(var key in obj){
		details+=key+":"+obj[key]+"<br/>";
	}

	fastDev.create("Window",{
		title:"扫码详情",
		width:'400px',
		height:'400px',
		content:'<span style="font-size:18px;">'+details+'</span>'
	});
}

function showObjectDefine(type,cTabletypeId){
	var path=window.location.href;
	var k=-1;
	var url="/xwzcxt/task/jobObjects/objectDefine.html?type="+type+"&cTabletypeId="+cTabletypeId;
	for(var i=0;i<4;i++){
		k=path.indexOf("/", k+1);
	}
	url=path.substring(0, k)+url;
	var title="修改作业对象类型";
	var backbtnDisabled=false;
	if(type=='add'){
		title="新增作业对象类型";
		backbtnDisabled=true;
	}
	//alert(url);
	fastDev.create("Window",{
		title:title,
		height:"400px",
		width:"800px",
		src:url,
		buttons:[{
			text:"提交",
			width:"100px",
			align:"center",
			iconCls : 'icon-save', 
			onclick : function(e,win,cwin, fd){
				fastDev.confirm("是否确定提交当前数据?","数据提交提示",function(result){
					if(result){
						var tree=fastDev.getInstance('tableTypeTree');
						if(type!='add'){
							//var loc=document.getElementById('gridDiv');
							cwin.doSave(tree,win,location);
						}else{
							cwin.doSave(tree,win);	
						}
					}
				});
				
			}
		},{
			text:"恢复",
			width:"100px",
			iconCls : "icon-reset",
			align:"center",
			disabled:backbtnDisabled,
			onclick:function(event,win,cwin,fast){
				cwin.getBack();
			}
		},{
			text:"全部清空",
			width:"100px",
			iconCls : "icon-close",
			align:"center",
			disabled:!backbtnDisabled,
			onclick:function(event,win,cwin,fast){
				cwin.clear();
			}
		}
		         ]
		});
	
}


function addTabletype(){
	var cTabletypeId=fastDev.getInstance('tableTypeTree').getValue();
	if(cTabletypeId==null ||cTabletypeId.trim()==''){
		fastDev.tips("请选择作业对象类型！ ");
		return;
	}
	showObjectDefine('add',cTabletypeId);
}

function modifyTabletye(){
	var cTabletypeId=fastDev.getInstance('tableTypeTree').getValue();
	if(cTabletypeId.trim()=='-1'){
		fastDev.alert('应用节点不能修改','信息提示','warning');
		return;
	}
	if(cTabletypeId==null ||cTabletypeId.trim()==''){
		fastDev.tips("请选择作业对象类型！ ");
		return;
	}
	showObjectDefine("",cTabletypeId);
}

function deleteTabletype(){
	fastDev.confirm("是否确定删除该节点？","删除确认提示",function(result){
		if(result){
			var tree =fastDev.getInstance('tableTypeTree');
			var cTabletypeId=tree.getValue();
			if(cTabletypeId=='-1'){
				fastDev.alert("不能删除根节点！");
				return;
			}
			fastDev.post("jobObjects_deleteTabletype.action",{
				success:function(data){
					fastDev.alert(data);
					tree.reLoad();
					fastDev.getInstance('jobObjectsInfo').refreshData(false);
				},
				data:{"cTabletypeId":cTabletypeId}
			});
		}
	});
	
}


function  setPrinter(){
	var path=window.location.href;
	var index=0;
	var url="";
	for(var i=0;i<4;i++){
		index=path.indexOf("/", index+1);
	}
	path=path.substring(0, index);
	url+=path+"/xwzcxt/mytask/personSelect.html";
   
	
	
	fastDev.create("Window",{
		title:"选择最后打印人",
		height:"200px",
		width:"450px",
		src:url,
		buttons:[{
			text:"确定",
			width:"100px",
			align:"center",
			iconCls : 'icon-save', 
			onclick : function(e,win,cwin, fd){
				 
				 var user= cwin.getUserInfo();
				 fastDev.getInstance("username").setValue(user.username);
				 fastDev.getInstance("cPrintUser").setValue(user.userid);
				 win.close();
			}
		},{
			text:"关闭",
			width:"100px",
			align:"center",
			iconCls : 'icon-close', 
			onclick : function(e,win,cwin, fd){
				 win.close();
			}
		}]
		});
}


function importObject(){
	var cTabletypeId=fastDev.getInstance('tableTypeTree').getValue();
	if(cTabletypeId==null ||cTabletypeId.trim()=='-1'||cTabletypeId.trim()==''){
		fastDev.tips("请选择作业对象类型！ ");
		return;
	}
	
	var path=window.location.href;
	var index=0;
	var url="";
	for(var i=0;i<4;i++){
		index=path.indexOf("/", index+1);
	}
	path=path.substring(0, index);
	url+=path+"/xwzcxt/task/jobObjects/objectImport.html?cTabletypeId="+cTabletypeId;
   
	
	var isNotImporting=false;
	fastDev.create("Window",{
		title:"作业对象导入",
		height:"400px",
		width:"650px",
		id:"ok",
		src:url,
		buttons:[{
			text:"提交",
			width:"100px",
			align:"center",
			iconCls : 'icon-save', 
			onclick : function(e,win,cwin, fd){
				isNotImporting=cwin.getIsSuccess();
				if(!isNotImporting){
					fastDev.alert('正在导入中，请稍等!');
					return;
				}
				fastDev.confirm("是否确定导入","作业导入确认",function(res){
					if(res){
						var grid=fastDev.getInstance('jobObjectsInfo');
						isImporting=true;
						cwin.doSave(win,grid);
					}
				});
				
			}
		},{
			text:"重置",
			width:"100px",
			align:"center",
			iconCls : 'icon-reset', 
			onclick : function(e,win,cwin, fd){
				 cwin.doReset();
			}
		},{
			text:"关闭",
			width:"100px",
			align:"center",
			iconCls : 'icon-close', 
			onclick : function(e,win,cwin, fd){
				 win.close();
			}
		}]
		});
}

	var loadingWindow = null;
	function exportXLS(){
		
		var condition = null;
		if(form==null){
			form = fastDev.getInstance("jobObjects");
		}
		condition = form.getItems();
		
		if(tmpColumn!=null){
			for(var i=0;i<tmpColumn.length;i++){
				if(column[i].isshow=="1"){
					var id="tbIntegrateInfo."+tmpColumn[i].name;
					var value=document.getElementById(id).value;
					condition[id]=value;
				}
			}
		}
		
		loadingWindow = fastDev.create("ProgressBar", {
			container : "jobObjects",
			text : "导出中..."
		});
		var formData = fastDev.getInstance("jobObjects").getItems();
		var cTabletypeId=fastDev.getInstance('tableTypeTree').getValue();
		fastDev.create("Proxy", {
			action : "jobObjects_getObjectsByExport.action?cTabletypeId="+cTabletypeId
		}).save(condition, function(data) {
			fastDev("#dc").prop("src", data.url);
			loadingWindow.close();
		});	
	}

function toLocatedStatus(value){
	var s=['否','是'];
	return s[value];
}

