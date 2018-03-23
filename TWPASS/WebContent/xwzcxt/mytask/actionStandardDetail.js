var request = fastDev.Browser.getRequest();

var g_event = request['event'];
var g_id = request['id'];
var edit = request['edit'];
var actid = request['actid'];
var actnodeid = request['actnodeid'];

var formatdata={C_ACTNODE_ID:"user1",C_ACTNODE_NAME:'',C_ACTION_ID:''
	,C_PDCA:'',C_ISKEYCTRL:'',C_ISSEQUENCE:'',C_AREA_ID:'',C_AREA_NAME:'',C_POSITION_ID:'',
	C_POSITION_NAME:'',C_STD_EXEC:'',C_STD_CHECK:'',C_POSITION_ID_CHECK:'',C_STD_REVIEW:'',
	C_POSITION_ID_REVIEW:'',C_POSITION_NAME_REVIEW:'',C_ACTION_NAME:'',C_TIMEPLAN_ID:''};


var form;
var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息提示", data.result,function(){
		if (data.result >= 0){//成功
			parent.appDialog.close();
			parent.refreshTree();
			//modify模式下，需要刷新父窗口的grid
			if (g_event == "modify")
				parent.refreshData();
		}
	});
};

function handleTrigger(){
	
	var triggerkey = fastDev.getInstance("taskMouldPojo.c_trigger_type").getValue();
	if (triggerkey=='1'){
		showColumn();
	}
	else{
		hideColumn();
	}
}



function onFormBeforeReady(){
	
	
	
	
}

//若是修改模式，则要初始化获取原本的数据
function onGridBeforeReady(){
	/*
	if (edit == "modify"){
		this.setOptions({
			"initSource":"sdaction_getActionPositionInfoById.action?id="+g_id,
		});
	}
	*/
}

function FormAfter(){
	if (edit == "modify"){
		pageformatdata();
	}
}

function onGridRowClick(event,rowindex,data) {
	
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(fastDev.isFunction(window['do' + name])) {
			window['do' + name].call(window, data);
		}
	}
}


function doAddPart() {
	fastDev.getInstance("partgrid").addRow();
}

function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+width+'px;">',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="deletePosition">删除</a>',
	        '</div>'].join('');
}

function doDeletePosition(data) {
	fastDev.getInstance('partgrid').delRow(data.c_id);
}

//选择完部门后，获取部门下对应的岗位
function onPositionAfterConstruct() {
	var value = fastDev.getInstance("partgrid").getValue(this);
	this.addInitReqParam({
		orgid : value[0]['c_respons_orgid']
	});
}

//保存页面内容
function doSave(){
	fastDev.getInstance('partgrid').writeBackCell();
	var datagridValues = fastDev.getInstance('partgrid').getAllValue();
	for(var i = 0; i < datagridValues.length; i++) {
		var rowValue = datagridValues[i];
		if(!rowValue.c_respons_orgid || rowValue.c_respons_orgid == '-1') {
			fastDev.alert('第' + (i + 1) + '条职责信息，职责部门不能为空', '信息提示', 'warning');
			return;
		}
		if(!rowValue.c_respons_positionid || rowValue.c_respons_positionid == '-1') {
			fastDev.alert('第' + (i + 1) + '条职责信息，职责岗位不能为空', '信息提示', 'warning');
			return;
		}
		for(var j = 0; j < datagridValues.length; j++) {
			if(j != i) {
				if(rowValue.c_respons_positionid == datagridValues[j].c_respons_positionid) {
					fastDev.alert('第' + (i + 1) + '条职责信息与第' + (j + 1) + '条职责信息职责岗位重复', '信息提示', 'warning');
					return;
				}
			}
		}
	}
	fastDev.getInstance('positionParts').setValue(fastDev.Util.JsonUtil.parseString(datagridValues));
	fastDev.getInstance('addActionForm').submit();
}


function showColumn() {
	

	fastDev.getInstance("partgrid").showColumn("c_validate_request");
	var tb = document.getElementById("maintable");
	tb.rows[5].style.display = "block";
	tb.rows[6].style.display = "block";
	tb.rows[7].style.display = "block";
	tb.rows[8].style.display = "block";
	tb.rows[9].style.display = "block";
	tb.rows[10].style.display = "block";
	tb.rows[11].style.display = "block";
	tb.rows[12].style.display = "block";

    
}

function hideColumn() { 
	

	fastDev.getInstance("partgrid").hideColumn("c_validate_request");
	var tb = document.getElementById("maintable");
	//var tb = fastDev.getInstance("maintable") ;
	tb.rows[5].style.display = "none";
	tb.rows[6].style.display = "none";
	tb.rows[7].style.display = "none";
	tb.rows[8].style.display = "none";
	tb.rows[9].style.display = "none";
	tb.rows[10].style.display = "none";
	tb.rows[11].style.display = "none";
	tb.rows[12].style.display = "none";
	
}

function pageformatdata(){
		
		fastDev.getInstance("taskMouldPojo.c_tasktemplet_name").setValue('安全设施检查');
		fastDev.getInstance("activepost").setValue('灭火器检查');
		fastDev.getInstance("pdcaoption").setValue('2');
		fastDev.getInstance("taskMouldPojo.c_trigger_type").setValue('1');
		fastDev.getInstance("taskMouldPojo.c_timerule_id").setValue('1');
		fastDev.getInstance("workareatree").setValue("1");
		fastDev.getInstance("exepost_tree").setValue('117');
	    //alert(fastDev.getInstance("taskMouldPojo.c_ok_triggertype"));
		fastDev.getInstance("taskMouldPojo.c_ok_triggertype").setValue('1');
		fastDev.getInstance("taskMouldPojo.exerecord").setValue('《安全值日交接班记录》');
		fastDev.getInstance("taskMouldPojo.validaterecord").setValue('《班长交接班记录》');
		fastDev.getInstance("validatepost_tree").setValue('117');
		fastDev.getInstance("levepost_tree").setValue('117');
		
		
		fastDev.getInstance("validate_stand").setValue('1）对执行人上传的证据进行验证，上传的照片信息应包括单个灭火器全貌、灭火器压力表指针指示情况。同时，照片中的灭火器应有编号或文字说明。2）如执行人上传的完工证据不规范或不能依据照片进行验证，应通知执行人重新上传证据。');
		fastDev.getInstance("validate_process").setValue('1）发现照片异常或照片中的场景异常，应在1小时内（条件许可的情况下）到现场实地验证。2）复核确存在异常情况或不符合情况，应报告安全员，必要时可直接通知部门领导、安保部人员等，并作好记录。 ');
		fastDev.getInstance("appraisal_stand").setValue('1）***执行不及时，按D类考核；2）***执行不到位或不符要求，按C类考核。 ');
		
		
	
}



