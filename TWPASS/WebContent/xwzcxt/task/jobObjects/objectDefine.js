
var request=fastDev.Browser.getRequest();
var formValue;

var setFormValue=fastDev(function(){
	var type=request['type'];
	if(type!='add'){
		cTabletypeId=request['cTabletypeId'];
		var url="jobObjects_getTabletypeById.action";
		fastDev.post(url,{
			success:function(data){
				try{
				data=eval('('+data+')');
				}catch(e){
					
				}
				formValue=data;
				fastDev.getInstance('tbTypeInfo').setValues(data);
				var count=data.colCount*1;
				for(var i=1;i<=count;i++){
					document.getElementById("tableDiv"+i).style.display="block";
					var obj=  fastDev.getInstance('tbIntegrateInfo.col'+i);
					obj.disable();
				}
			},
			data:{
				"cTabletypeId":cTabletypeId
			}
		});
		
	}
});

function onchange(event, data) {
	if ('1' == data.value) {
		document.getElementById('table2').style.display="table";
		document.getElementById('table3').style.display="table";
		document.getElementById('table4').style.display="table";
		document.getElementById('table5').style.display="table";
		document.getElementById('title5').style.display="table";
	} else if ('2' == data.value) {
		document.getElementById('table2').style.display="none";
		document.getElementById('table3').style.display="none";
		document.getElementById('table4').style.display="none";
		document.getElementById('table5').style.display="none";
		document.getElementById('title5').style.display="none";
	}
}

function writeTable(){
	var table='';
    for(var i=1;i<=30;i++){
		table+='<div id="tableDiv'+i+'" style="display:none;"><table class="ui-form-table" id="tb'+i+'"><tr valign="middle">'
			+'<td class="ui-form-table-dt"><font color="red" >*</font>列名'+i+'</td>'
			+'<td class="ui-form-table-dd" colSpan="7">'
			+'<div style="display:none;"><div itype="TextBox" id="colid'+(i+3)+'" saveInstance=true></div></div>'
            +'<div itype="TextBox" width="100%" id="tbIntegrateInfo.col'+i+'" saveInstance=true></div>'
            +'</td></tr>'
            +'<tr valign="middle"><td class="ui-form-table-dt">'
            +'是否为扫码属性'
            +'</td>'
            +'<td class="ui-form-table-dd" colSpan="3">'
            +'<div itype="RadioGroup" value="2" id="c_isscandata'+(i+3)+'" saveInstance=true>'
            +'<div value="2" text="是"></div>'
            +'<div value="1" text="否"></div>'
            +'</div>'
            +'</td>'
            +'<td class="ui-form-table-dt">'
            +'是否为查询条件'
            +'</td>'
            +'<td class="ui-form-table-dd" colSpan="3">'
            +'<div itype="CheckBoxGroup" value="1" id="c_isshowscan'+(i+3)+'">'
            +'<div value="2" text="是"></div>'
            +'</div>'
            +'</td>'            
			+'</tr>'
			/*+'<tr valign="middle" >'
			+'<td class="ui-form-table-dt" >备注</td>'
			+'<td class="ui-form-table-dd" colSpan="3">'
			+'<div saveInstance=true itype="TextBox" width="100%" id="c_remark'+(i+2)+'"></div>'
			+'</td>'
			+'</tr>'
			+'<tr valign="middle"><td class="ui-form-table-dd" colSpan="4" align="center">'
			+'<div itype="Button" width="100px" onClick="resetTable('+i+')" text="清空数据" ></div></td>'
			+'</tr>'*/
			+'</table><br/></div>';
    }

	document.write(table);
/*	if(request['type']!='add'){
		setFormValue();
	}*/
}


function  writeColCount(){
	var options="";
	for(var i=0;i<=30;i++){
		options+='<option value="'+i+'" text="'+i+'"></option>';	
	}
	document.write(options);
}

function showTables(event,value){
	if(request['type']!='add'){
		var count=formValue.colCount;
		if(count*1>value*1){
			fastDev.alert('所选列数小于原有列数('+count+')，请重新选择！');
			return;
		}
	}
	for(var i=1;i<=30;i++){
		if(i<=value){
			document.getElementById('tableDiv'+i).style.display="block";
		}else{
			var colname=fastDev.getInstance('tbIntegrateInfo.col'+i).getValue();
			if(colname.trim()!=''&&colname!=null){
				fastDev.alert("您所选择的列数小于现有列数，\n并且第"+i+"列的列名不为空，请先清空列名!");
				return;
			}
			document.getElementById('tableDiv'+i).style.display="none";
		}
	}
}




function checkNull(){
	var name=fastDev.getInstance('cTabletypeUpid').getTree().getValue();
	var tabId = fastDev.getInstance('cIsproperty').getValue();
//	alert(tabId);
	if(request['type']!='add'&&formValue.cTabletypeId==name){
		fastDev.alert('上级节点不能选择自己！');
		return false;
	}
	if(name==null || name.trim()==''){
		fastDev.alert('上级节点不能为空！');
		return false;
	}
	name=fastDev.getInstance('cTabletypeName').getValue();
	if(name==null || name.trim()==''){
		fastDev.alert('作业对象类型名称不能为空！');
		return false;
	}
	name=fastDev.getInstance('tbIntegrateInfo.c_basedata_name').getValue();
	if(tabId==1&&(name==null || name.trim()=='')){
		fastDev.alert('基础数据名称不能为空！');
		return false;
	}
	name= fastDev.getInstance('tbIntegrateInfo.c_basedata_code').getValue();
	if(tabId==1&&(name==null || name.trim()=='')){
		fastDev.alert('基础数据编号不能为空！');
		return false;
	}
	name= fastDev.getInstance('tbIntegrateInfo.c_basedata_usercode').getValue();
	if(tabId==1&&(name==null || name.trim()=='')){
		fastDev.alert('责任人工号不能为空！');
		return false;
	}
	var count=fastDev.getInstance('colCount').getValue();
	
	count=count*1;
	
	for(var i=1;i<=count;i++){		
		name=fastDev.getInstance('tbIntegrateInfo.col'+i).getValue();
		//alert(name+","+i);
		if(name==null ||name.trim()==''){
			fastDev.alert('列名'+i+"不能为空！");
			return false;
		}
	}
}


function doSave(tree,win,loc){
	if(checkNull()==false){
		return;
	}
	
	var fm=fastDev.getInstance('tbTypeInfo');
	var items=fm.getItems();
	if(items.cTabletypeUpid==''){
		items.cTabletypeUpid='-1';   //当选择的是根节点的时候
	}
	var url='jobObjects_updateTabletype.action';
	if(request['type']=='add'){
		url='jobObjects_addTabletype.action';
	}
	fastDev.post(url,{
		success:function(data){
			fastDev.alert(data);
			win.close();
			if(request['type']!='add'){
				loc.reload();
				//alert("***********");
			}else{
				tree.reLoad();
			}
		},
		data:items
	});
}

function clear(){
	fastDev.getInstance('tbTypeInfo').cleanData();
}

function getBack(){
	if(formValue!=null && fastDev.Util.JsonUtil.parseString(formValue).trim()!=''){
		if(formValue.cTabletypeName==undefined){
			return;
		}
		fastDev.getInstance('tbTypeInfo').setValues(formValue);
	}
}

function onAfterInitRender() {
	if(request['type']=='add'){
		cTabletypeId=request['cTabletypeId'];
		var cTabletypeTree = fastDev.getInstance("cTabletypeUpid");
		if (cTabletypeId) {
			cTabletypeTree.setValue(cTabletypeId);
			cTabletypeTree.disable();
		}
	}
}

/*
function clearTable1(){
	fastDev.getInstance('cTabletypeUpid').setValue(-1);
	fastDev.getInstance('cTabletypeName').setValue('');
	fastDev.getInstance('cTabletypeDes').setValue('');
	fastDev.getInstance('c_remark').setValue('');
	
}
function clearTable2(){
	fastDev.getInstance('tbIntegrateInfo.c_basedata_name').setValue('');
	fastDev.getInstance('c_isscandata1').setValue(2);
	fastDev.getInstance('c_remark1').setValue('');
}
function clearTable3(){
	fastDev.getInstance('tbIntegrateInfo.c_basedata_code').setValue('');
	fastDev.getInstance('c_isscandata2').setValue(2);
	fastDev.getInstance('c_remark2').setValue('');
}*/