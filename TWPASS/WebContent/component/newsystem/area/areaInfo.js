var currentAreaId;
var op = null;
var parentId;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#areatree').attr('height',(height-35)+'px');
	fastDev('#centerPanel').css('width', (fastDev(window).width() - 220) + 'px');
}

function onAfterCompile() {
	showResourceModes('area');
}



function onNodeClick(event, id, value) {
	currentAreaId = id; 
	if(id=="-1"){
		fastDev.getInstance('areaform').cleanData();
	}

	parentId = this.getParentid(id);
	var parentText = this.getValByid(parentId);
	fastDev.getInstance("area.uparea").setValue(parentId);	
	//alert(fastDev.getInstance("area.uparea").getValue());
	op = 'view';
	setReadOnly(true);
    //refreshUpAreaTree();
	refreshForm(id);
	enableText();
}


function  onChangeAreaName(){
	  onChangeAreaFullName();
}

function onChangeUpArea(value){
		 onChangeAreaFullName();

}

/*function onAfterDataRenderUparea(){
	
		fastDev.getInstance('area.uparea').setValue(parentId);
		 alert(fastDev.getInstance('area.uparea').getValue());
      
		
}*/

// 刷新上级地区树
function refreshUpAreaTree() {
	var upAreaTree = fastDev.getInstance('area.uparea');
	upAreaTree.setOptions({
		items : fastDev.getInstance("areatree").getItems()
	});
	upAreaTree.initRefresh();
}

function refreshForm(id) {
	fastDev.getInstance('areaform').dataRefresh({
		url : 'areaInfo_init.action?id='+id
	});

}

function onAfterDataRender(){
	var isror=fastDev.getInstance('area.c_isaor').getValue();
	if(isror=="0"){
		fastDev('#ResPart').hide();
	}else{
		fastDev('#ResPart').show();
	}
		
	fastDev.getInstance('area.uparea').setValue(parentId);
	

}

/**
 * 设置只读
 * @param readOnly
 */
function setReadOnly(readOnly) {

	fastDev.getInstance('area.uparea').setReadonly(readOnly);
	//fastDev.getInstance('area.c_area_upid').setReadonly(readOnly);
	fastDev.getInstance('area.c_area_name').setReadonly(readOnly);
	fastDev.getInstance('area.c_area_type').setReadonly(readOnly);
	//fastDev.getInstance('area.c_area_fullname').setReadonly(readOnly);
	fastDev.getInstance('area.c_isaor').setReadonly(readOnly);
	fastDev.getInstance('area.c_org_name').setReadonly(readOnly);
	fastDev.getInstance('area.c_firstpic_username').setReadonly(readOnly);
	fastDev.getInstance('area.c_mainpic_username').setReadonly(readOnly);
	fastDev.getInstance('area.c_directpic_username').setReadonly(readOnly);
	fastDev.getInstance('area.c_directpic_tel').setReadonly(readOnly);
	fastDev('#buttonBar')[readOnly?'hide':'show']();
}

function  disableText(){
	fastDev.getInstance('area.c_basedata_id').disable();
	fastDev.getInstance('area.c_area_fullname').disable();
	fastDev.getInstance('area.c_update_user').disable();
	fastDev.getInstance('area.c_update_time').disable();
	fastDev.getInstance('area.c_remark').disable();
	fastDev.getInstance('area.c_scan_code').disable();
	fastDev.getInstance('area.c_print_count').disable();
	fastDev.getInstance('area.c_scan_detail').disable();
	fastDev.getInstance('area.c_print_user').disable();
	fastDev.getInstance('area.c_print_time').disable();
}

function enableText(){
	fastDev.getInstance('area.c_basedata_id').enable();
	fastDev.getInstance('area.c_area_fullname').enable();
	//fastDev.getInstance('area.uparea').enable();  //快框的限制，selectTree不要设置此属性
	fastDev.getInstance('area.c_update_user').enable();
	fastDev.getInstance('area.c_update_time').enable();
	fastDev.getInstance('area.c_remark').enable();
	fastDev.getInstance('area.c_scan_code').enable();
	fastDev.getInstance('area.c_print_count').enable();
	fastDev.getInstance('area.c_scan_detail').enable();
	fastDev.getInstance('area.c_print_user').enable();
	fastDev.getInstance('area.c_print_time').enable();
	
}

function onChange(value) {
	if(value=="0"){
		fastDev('#ResPart').hide();
     	fastDev.getInstance('area.c_org_name').setValue('');
		fastDev.getInstance('area.c_firstpic_username').setValue('');
		fastDev.getInstance('area.c_mainpic_username').setValue('');
		fastDev.getInstance('area.c_directpic_username').setValue('');
		fastDev.getInstance('area.c_directpic_tel').setValue('');
	}else{
		fastDev('#ResPart').show();
	}
}


/**
 * 删除区域信息
 */
function doDeleteArea() {
	if(!currentAreaId || currentAreaId == -1) {
		fastDev.alert('请选择需要删除的区域节点', '信息提示', 'warning');
	} else {
		fastDev.confirm('是否删除所选记录？', '信息提示', function(result) {
			if(result) {
				setReadOnly(true);
				op = 'delete';
				var areaTree = fastDev.getInstance('areatree');
				var nodes = areaTree.getNodesByPid(currentAreaId);
				var ids = currentAreaId;
				for(var i = 0; i < nodes.length; i++) {
					 ids = ids + "," + nodes[i].id;
				}
				var proxy = fastDev.create('Proxy', {
					action : 'areaInfo_deleteArea.action'
				});
				proxy.save({ids:ids}, function(result){
					fastDev.alert(result.msg, '信息提示', result.status, function(){
						if(result.status == 'ok') {
							areaTree.delNode(currentAreaId);
                        	currentAreaId = null;
							refreshUpAreaTree();
							//fastDev.getInstance("areatree").reLoad();
							fastDev.getInstance("areaform").cleanData();
							setReadOnly(true);
						}
					});
				});
			}
		});
	}
}

/**
 * 修改区域信息
 */
function doModifyArea() {
	if(!currentAreaId || currentAreaId == -1) {
		fastDev.alert('请选择需要修改的区域节点', '信息提示', 'warning');
	} else {
		refreshUpAreaTree();
		//fastDev.getInstance('area.uparea').setValue(parentId);
		//fastDev.getInstance('area.uparea').setReadonly(false);
		refreshForm(currentAreaId);
		op = 'modify';
		setReadOnly(false);
		disableText();
	}
}

function doAddArea() {
	if(!currentAreaId || currentAreaId == -1) {
		fastDev.alert('请选择需要新增的区域节点', '信息提示', 'warning');
	} else {
	op = 'add';
	fastDev.getInstance('areaform').cleanData();	
	fastDev.getInstance('area.c_basedata_id').setValue('由系统自动生成');
	fastDev.getInstance('area.c_print_count').setValue("0");

	if (currentAreaId && currentAreaId!= "-1") {
		refreshUpAreaTree();
		fastDev.getInstance('areaform').setValue({
			'area.uparea' : currentAreaId
		});

	}	
	setReadOnly(false);
	disableText();
	}
}

/**
 * 保存区域信息
 */
function doSave() {
	fastDev.getInstance('areaform').setOptions({
		action:op=='modify'?'areaInfo_updateArea.action':'areaInfo_addArea.action'
	});
    console.info(fastDev.getInstance('areaform').getItems());
	fastDev.getInstance('areaform').submit();
}


function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function(){
		if(result.status == 'ok') {
			if(result.id) {
				currentAreaId = result.id;
				
			} 
			//fastDev.getInstance("areatree").reLoad();
			//refreshForm(currentAreaId);			
         	//setReadOnly(true);
         	//enableText();
			var areaTree=fastDev.getInstance("areatree");
			var items=fastDev.getInstance('areaform').getItems();
		    if(op == 'add') {
				if(areaTree.getNodeByid(items['area.uparea'] || '-1')) {
					op = 'modify';
					  areaTree.addNode({
			            id : items['area.c_basedata_id'],
						val : items['area.c_area_name'],
						pid : items['area.uparea'] || '-1'
					});
					fastDev.getInstance('areatree').initRefresh();
					areaTree.setValue(currentAreaId);
					refreshForm(currentAreaId);
					setReadOnly(true);
					enableText();
		
				}
				
			} else {
				if(areaTree.getNodeByid(items['area.c_basedata_id'])) {
				   	areaTree.editNode({
			           id : items['area.c_basedata_id'],
						val : items['area.c_area_name'],
						pid : items['area.uparea'] || '-1'
					});
					fastDev.getInstance('areatree').initRefresh();				
					refreshForm(currentAreaId);
					setReadOnly(true);
					enableText();
				}
			}
		}
	});
	
}

function onAfterInitRender() {
	if(currentAreaId) {
		fastDev.getInstance('areatree').setValue(currentAreaId);
	}
}


/**
 * 重置表单
 */
function doReset() {

	if(op == 'add'){
		fastDev.getInstance('areaform').cleanData();
		fastDev.getInstance('area.c_basedata_id').setValue("由系统自动生成");
		fastDev.getInstance('area.uparea').setValue(currentAreaId);
	}else if(op == 'modify'){
		fastDev('#reset').hide();
	}
}


function onChangeAreaFullName(){
	parentId = fastDev.getInstance("area.uparea").getValue();
	var pathname = getTreeNodePathNameByID(parentId);
	pathname = pathname + ','+fastDev.getInstance("area.c_area_name").getValue();
	fastDev.getInstance("area.c_area_fullname").setValue(pathname);
}

function getTreeNodePathNameByID(treeid)
{
	var tmpid = treeid;
	var tree1 = fastDev.getInstance("areatree");
	var strpathname = tree1.getValByid(treeid);
	for(var i=0;i<10;i++){
		tmpid = tree1.getParentid(tmpid);
		if(tmpid==-1|tmpid=='-1') break;
		var tmpname = tree1.getValByid(tmpid);
		strpathname=(tmpname==undefined?'':tmpname+',')+strpathname;
	}
	return strpathname; 
}

function printCode(){

	var cBasedataId=fastDev.getInstance("area.c_basedata_id").getValue();
	
	if(cBasedataId==null||""==cBasedataId){
		fastDev.alert('请选择需要打印的区域节点', '信息提示', 'warning');
	}else{
	 fastDev.post("areaInfo_getCurrentPrintCount.action",{
		success:function(count){
				if(isNaN(count+"")){
					fastDev.alert("服务器响应失败！");
					return;
				}
		var s=(count+"").length;
		var printInfo=cBasedataId;
		for(var i=0;i<4-s;i++){
			printInfo+="0";
		}
		count++;
		printInfo+=count;
		//alert(printInfo);
		var s=QRCodePrint(printInfo);
		if(s==false){
			return;
		}
	   fastDev.confirm("打印机打印是否成功？","打印成功确认",function(result){
	   if(result){
		
    	   fastDev.post("areaInfo_PrintCode.action",{      
			  success:function(data){
				fastDev.tips(data.msg);	     //弹出一个框，提示数据传输失败或成功
				fastDev.getInstance('areaform').dataRefresh(false);	//数据库数据修改后，在当前页面刷新这两个数据。
			  },
			 data:{
				"area.c_basedata_id":cBasedataId
			}
		  });
		 }
	  });
	 },
	  data:{
		     "area.c_basedata_id":cBasedataId
	  }
  });
 }
}



