/*
This file is part of SmartForm JS 1.0
Copyright (c) 2017-2027 talkweb-mi
Contact:  SmartForm@talkweb.com.cn
本组件用于快速开发企业应用，基于jquery和bootstrap快速构建组件及布局模板
Build date: 2017-12-14
*/
//var SmartForm = SmartForm||{};

var SF = SF||{};

SF.IsEnableBrowserShowModal = function(){
	if(window.showModalDialog == undefined ) return false;
	return true;
}();


if(!SF.SearchPanel) {
	SF.SearchPanel = {}
} 

if(!SF.PanelHeader) {
	SF.PanelHeader = {}
} 
if(!SF.PanelFooter) {
	SF.PanelFooter = {}
} 
if(!SF.PanelBody) {
	SF.PanelBody = {}
} 

if(!SF.FormBody) {
	SF.FormBody = {}
} 

if(!SF.SearchButton) {
	SF.SearchButton = {}
} 

if(!SF.FormButton) {
	SF.FormButton = {}
} 

if(!SF.Label) {
	SF.Label = {}
} 

if(!SF.TextInput) {
	SF.TextInput = {}
} 
if(!SF.NumberInput) {
	SF.NumberInput = {}
} 

if(!SF.TimePicker) {
	SF.TimePicker = {}
} 

if(!SF.Textarea){
	SF.Textarea = {}
}

if(!SF.Legend){
	SF.Legend = {}
}

$.fn.datetimerun = function() {   
	for(var i=0; i<this.length; i++) {   
        $("#"+this[i].id).datetimepicker(this[i].cfg);
   }
}; 
 
if(!SF.Combox) {
	SF.Combox = {}
} 
$.fn.comboxrun = function() {   
	for(var i=0; i<this.length; i++) {  
		var el = this[i];
		if(el.cfg.data!=undefined){ 
			$.each(el.cfg.data, function(idx, obj) { 
				var code =  obj[el.cfg.fieldValue];
				var name =  obj[el.cfg.fieldText];
				$("#"+el.id).append("<option value='"+code+"'>"+name+"</option>"); 
			});
		}else if(el.cfg.url!=undefined) { 
			$.ajax({
				url: el.cfg.url, 
				async:false, 
				success:function(da){  
					if(da.result.code==0||da.result==undefined){
						var data = da;
						if(el.cfg.root!=undefined && el.cfg.root!=''){
							data = eval("da."+el.cfg.root); 
						} 
				    	$.each(data, function(idx,obj) { 
							var code =  obj[el.cfg.fieldValue];
							var name =  obj[el.cfg.fieldText];
							$("#"+el.id).append("<option value='"+code+"'>"+name+"</option>");
						});
					}
			    }
			});
		}
   }
};  

if(!SF.Choice) {
	SF.Choice = {}
} 
 
//$.fn.
SF.ChoiceBindResult = function(subElement,keys,values){ 
	var elHideId = SF.Choice.getBinElement(subElement);
	$("#"+elHideId).val(values);
	$("#n"+elHideId).val(keys);
}
SF.ChoiceBindSelections = function(fieldname,keys,values){  
	$("#"+fieldname).val(values);
	$("#n"+fieldname).val(keys);
}
SF.Choice.getBinElement = function(subElement){ 
	var pel = subElement.parentElement;  
	while(true){ 
		pel = pel.parentElement; 
		if(pel.tagName=='BODY'||pel.tagName=='HTML') return undefined;
		if(pel.tagName=='DIV'&&pel.role=='dialog') {
			return pel.getAttribute('data-bin');
		} 
	}
	return undefined;
}

SF.PanelHeader.create = function(title){ 
	var divElement = document.createElement("div"); 
	divElement.className = "panel-heading";
	divElement.innerText = title;
	return divElement;
};

SF.PanelFooter.create = function(){ 
	var divElement = document.createElement("div"); 
	divElement.className = "panel-footer"; 
	return divElement;
};

SF.PanelBody.create = function(){ 
	var divElement = document.createElement("div"); 
	divElement.className = "panel-body"; 
	return divElement;
};
SF.Label.create = function(elid,forelid,title){
	var label = document.createElement("label"); 
	label.id = label.name = 'slabel_'+elid;
	label.for = forelid; 
	label.className = 'col-form-label';  
	label.innerHTML = "<font>"+title+"</font>"; 
	return label;
};
SF.TextInput.create = function(itemcfg){ 
	var divElement = document.createElement("div"); 
	divElement.className = "input-form form-group";
	divElement.appendChild(SF.Label.create(itemcfg.dataIndex,itemcfg.name,itemcfg.title)); 
	var el_input = document.createElement('input'); 
	el_input.id = el_input.name = itemcfg.dataIndex;
	el_input.type = 'text';
	el_input.className = 'form-control input-sm';
	if(itemcfg.disabled!=undefined) el_input.disabled = itemcfg.disabled; 
	if(itemcfg.readonly!=undefined) el_input.readOnly = itemcfg.readonly; 
	divElement.appendChild(el_input);
	return divElement;
};
SF.NumberInput.create = function(itemcfg){ 
	var divElement = document.createElement("div"); 
	divElement.className = "input-form form-group";
	divElement.appendChild(SF.Label.create(itemcfg.dataIndex,itemcfg.name,itemcfg.title)); 
	var el_input = document.createElement('input'); 
	el_input.id = el_input.name = itemcfg.dataIndex;
	el_input.type = 'number';
	el_input.placeholder="输入数字" ;
	el_input.className = 'orm-control input-sm';
	if(itemcfg.disabled!=undefined) el_input.disabled = itemcfg.disabled; 
	if(itemcfg.readonly!=undefined) el_input.readOnly = itemcfg.readonly; 
	divElement.appendChild(el_input);
	return divElement;
};
SF.TimePicker.create = function(itemcfg){ 
	var divElement = document.createElement("div"); 
	divElement.className = "input-form form-group";
	divElement.appendChild(SF.Label.create(itemcfg.dataIndex,itemcfg.name,itemcfg.title)); 
	var el_input = document.createElement('input'); 
	el_input.id = el_input.name = itemcfg.dataIndex; 
	el_input.setAttribute('type','datetime');  
	el_input.className = 'form_datetime form-control';
	if(itemcfg.disabled!=undefined) el_input.disabled = itemcfg.disabled; 
	if(itemcfg.readonly!=undefined) el_input.readOnly = itemcfg.readonly; 
	divElement.appendChild(el_input);
	if(itemcfg.config.todayBtn==undefined) itemcfg.config.todayBtn=true;
	if(itemcfg.config.language==undefined) itemcfg.config.language='zh-CN';
	if(itemcfg.config.autoclose==undefined) itemcfg.config.autoclose=true;
	el_input.cfg = itemcfg.config; 
	//divElement.datetimepicker(itemcfg.config);
	return divElement;
};
SF.Combox.create = function(itemcfg){ 
	var divElement = document.createElement("div");
	divElement.className = "input-form form-group";
	divElement.appendChild(SF.Label.create(itemcfg.dataIndex,itemcfg.name,itemcfg.title)); 
	var elvalue = document.createElement('select'); 
	elvalue.id = elvalue.name = itemcfg.dataIndex; 
	elvalue.cfg = itemcfg;
	elvalue.className = 'multiple form-control select2-search--inline';
	elvalue.innerHTML = '<option value="">--请选择--</option>';
	if(itemcfg.disabled!=undefined) elvalue.disabled = itemcfg.disabled; 
	if(itemcfg.readonly!=undefined) elvalue.readOnly = itemcfg.readonly;  
	divElement.appendChild(elvalue); 
	return divElement;
}; 
SF.Textarea.create = function(itemcfg){  
	var divElement = document.createElement("div");
	divElement.className = "input-form form-group";
	divElement.appendChild(SF.Label.create(itemcfg.dataIndex,itemcfg.name,itemcfg.title)); 
	var elvalue = document.createElement('textarea'); 
	elvalue.id = elvalue.name = itemcfg.dataIndex; 
	elvalue.cfg = itemcfg;
	elvalue.className = 'form-control'; //multiple  select2-search--inline
	var elrowspan = itemcfg.rowspan; 
	(elrowspan==undefined)?elrowspan=3:elrowspan=itemcfg.rowspan;
	elvalue.rows = elrowspan;
	elvalue.colSpan = 5; 
	if(itemcfg.disabled!=undefined) elvalue.disabled = itemcfg.disabled; 
	if(itemcfg.readonly!=undefined) elvalue.readOnly = itemcfg.readonly; 
	divElement.appendChild(elvalue); 
	return divElement;
}

SF.CtrlN = 0;
SF.Choice.create = function(itemcfg){ 
	SF.CtrlN ++;
	var divElement = document.createElement("div");
	divElement.className = "input-form form-group";
	divElement.appendChild(SF.Label.create(itemcfg.dataIndex,itemcfg.name,itemcfg.title));
	var modaldiv = document.createElement("div"); 
	modaldiv.className = "input-group modal-input"; 
	var elvalue = document.createElement('input'); 
	elvalue.id = elvalue.name = 'n'+itemcfg.dataIndex; 
	elvalue.cfg = itemcfg; 
	elvalue.className = 'form-control input-sm'; 
	elvalue.type = "text";
	modaldiv.appendChild(elvalue);
	var elHide = document.createElement('input'); 
	elHide.id = elHide.name = itemcfg.dataIndex;
	elHide.type = "text"; 
	elHide.className="hidden";
	elHide.value = "";
	modaldiv.appendChild(elHide);
	var elalink = document.createElement('a'); 
	elalink.className = "input-group-addon btn btn-default";
	elalink.setAttribute('data-toggle','modal');
	elalink.setAttribute('data-target','#'+itemcfg.modaltarget);
	elalink.href = itemcfg.modalurl;
	elalink.innerHTML = "<font>...</font>";
	modaldiv.appendChild(elalink);
	divElement.appendChild(modaldiv);  
	var mdlg=document.createElement("div");
	mdlg.className = 'modal fade';
	mdlg.id = itemcfg.modaltarget;
	mdlg.tabIndex = '-1';
	mdlg.role = 'dialog';
	mdlg.setAttribute('aria-labelledby','myModalLabel'+SF.CtrlN);
	mdlg.setAttribute('value-bin',elvalue.id ); //数据绑定值元素ID
	mdlg.setAttribute('data-bin',elHide.id ); //数据绑定主键元素ID
	mdlg.innerHTML = "<div class='modal-dialog' role='document'><div class='modal-content' id='bmdlg_"+SF.CtrlN+"'/></div>";	 
 	divElement.appendChild(mdlg);  
	return divElement;
}; 

SF.SearchButton.create = function(){ 
	var divElement = document.createElement("div");
	divElement.className = "btn-form form-group "; 
	var btnsearch = document.createElement('input');
	btnsearch.type = "submit";//"button"; 
	btnsearch.id = "btn_SearchPanel_Search";
	btnsearch.className = "btn-md form-control btn-primary ";
	btnsearch.value = "查询";
	divElement.appendChild(btnsearch);
	var btnsearch = document.createElement('input');
	btnsearch.type = "reset"; //"button"; 
	btnsearch.id = "btn_SearchPanel_reset";
	btnsearch.className = "btn-md form-control btn-primary ";
	btnsearch.value = "重置";
	divElement.appendChild(btnsearch); 
	return divElement;			
};

SF.Legend.create = function(str){ 
	var elLegend = document.createElement('legend'); 
	elLegend.innerText = str;
	return elLegend;
}

SF.SearchButton.onReset = function(){  
	var paramctrls = SF.paramctrls;
	if(paramctrls==undefined) return;
	for(var i=0;i<paramctrls.length;i++){
		$("#"+paramctrls[i].dataIndex).val("");
	}  
};

SF.getSearchParams = function(){  
	var paramctrls = SF.paramctrls;
	if(paramctrls==undefined) return;
	var params = {};
	var jsonstr = "";
	for(var i=0;i<paramctrls.length;i++){
		var key = '"'+paramctrls[i].dataIndex+'":';
		var v = "";
		var ctr_type = (paramctrls[i].type + "").trim().toLowerCase(); 
		if(ctr_type=='numberinput')
			v = $("#"+paramctrls[i].dataIndex).val(); 
		else 
			v = '"' + $("#"+paramctrls[i].dataIndex).val() + '"';
		if(v=='') v = '""';
		if(i<paramctrls.length-1) v = v + ',';
		jsonstr += (key + v);
	} 
	jsonstr = '{'+jsonstr+'}'; 
	jsonstr = jsonstr.replace(/\r?\n/g,"\\n"); //回车转义
	//console.info(jsonstr);
	params =  eval('(' + jsonstr + ')');  
	return params;
};

SF.getFormValues = function(){  
	var paramctrls = SF.paramctrls;
	if(paramctrls==undefined) paramctrls=[];
	if(SF.hidefields==undefined) SF.hidefields = [];
	var params = {};
	var jsonstr = "";
	for(var i=0;i<paramctrls.length;i++){
		var key = '"'+paramctrls[i].dataIndex+'":';
		var v = "";
		var ctr_type = (paramctrls[i].type + "").trim().toLowerCase(); 
		if(ctr_type=='numberinput')
			v = $("#"+paramctrls[i].dataIndex).val(); 
		else 
			v = '"' + $("#"+paramctrls[i].dataIndex).val() + '"';
		if(v=='') v = '""';
		if(i<paramctrls.length-1||SF.hidefields.length>0) v = v + ',';
		jsonstr += (key + v);
	} 
	for(var j=0;j<SF.hidefields.length;j++){
		var key = '"'+SF.hidefields[j]+'":';
		//var v = $("#"+SF.hidefields[j]).val();
		var v = '"' + $("#"+SF.hidefields[j]).val() + '"';
		if(v=='') v = '"' + v + '"';
		//v = '""';
		if(j<SF.hidefields.length-1) v = v + ',';
		jsonstr += (key + v);
	} 
	jsonstr = '{'+jsonstr+'}'; 
	jsonstr = jsonstr.replace(/\r?\n/g,"\\n"); //回车转义
	//console.info(jsonstr);
	params =  eval('(' + jsonstr + ')');  
	return params;
};

SF.FormButton.create = function(){  
	//var rowdiv = SF.RowElement();
	var divElement = document.createElement("div");
	divElement.className = "btn-form form-group "; //"btn-form form-group col-sm-offset-4 col-sm-4"; //
	var btnok = document.createElement('input');
	btnok.type = "button"; //"submit";//"button"; 
	btnok.id = "btn_FormBody_commit";
	btnok.className = "btn-md form-control btn-primary";
	btnok.value = "提交";
	divElement.appendChild(btnok);
	var btnsearch = document.createElement('input');
	btnsearch.type = "button"; //"reset";//
	btnsearch.id = "btn_FormBody_cancel";
	btnsearch.className = "btn-md form-control btn-primary";
	btnsearch.value = "关闭";
	divElement.appendChild(btnsearch); 
	//rowdiv.appendChild(divElement)
	return divElement;			
};  

SF.FormButton.oncancel = function(){  
    window.returnValue = "cancel";
	window.close();
};

SF.paramctrls = [];
SF.createitems = function(itemcfg){
	var pi = {}; pi.dataIndex=itemcfg.dataIndex; pi.type = itemcfg.type;
	SF.paramctrls.push(pi);
	if(itemcfg.type=='textinput'){
		return SF.TextInput.create(itemcfg); 
	}else if(itemcfg.type=='numberinput'||itemcfg.type=='numinput'){
		return SF.NumberInput.create(itemcfg); 
	}else if(itemcfg.type=='date'||itemcfg.type=='time'||itemcfg.type=='datetime'){
		return SF.TimePicker.create(itemcfg); 
	}else if(itemcfg.type=='combox'||itemcfg.type=='select'){
		return SF.Combox.create(itemcfg); 
	}else if(itemcfg.type=='choice'){
		return SF.Choice.create(itemcfg); 
	}else if(itemcfg.type=='textarea'){
		return SF.Textarea.create(itemcfg); 
	}
	return undefined;
};

SF.hidefields = [];
/*创建隐藏字段  */
SF.createHideFields = function(hides){
	if(hides==undefined) hides = [];
	SF.hidefields = hides;
	var hiddenDiv = document.createElement('div'); 
	var flen = hides.length;
	if(hides!=undefined&&flen>0){
		for(var c=0; c<flen;c++){  
			var tmpfield = document.createElement('input');
			tmpfield.type = "hidden";
			tmpfield.id = tmpfield.name = hides[c]; 
			hiddenDiv.appendChild(tmpfield);
		}
	}
	return hiddenDiv;
};

SF.RowElement = function(){
	var rowele = document.createElement('div'); //row_ele=$('<div class="row form-inline"></div>')
	rowele.className = 'row form-inline';
	return rowele;
};

SF.createElements = function(type, items, col_num, row_num){ 
	var elFieldset = document.createElement('fieldset'); 
	//elFieldset.append(SF.Legend.create("查询条件"));  
	for (var r = 0; r < row_num; r++) { 
		var row_ele= SF.RowElement();
		for (var c = 0; c < col_num; c++) {
			var num=r*col_num+c+1;   //目前是第几个元素;
			if(num>items.length+1) break;
			//最后加入button
			var group_ele;
			var item=items[num-1];
			if(num==items.length+1){
				if(type=='searchpanel'){
					group_ele=SF.SearchButton.create(); 
				}else{
					group_ele=SF.FormButton.create(); 
				} 
			}else{
				var group_ele = SF.createitems(item);
			}
			if(group_ele==null||group_ele==undefined)  continue;
			row_ele.appendChild(group_ele); 
		}
		elFieldset.appendChild(row_ele);
	} 
	return elFieldset;
}

function initFormCss(){
	$(".smart-form").each(function(){
		var form=$(this);
	var form_groups=form.find(".form-group");
	$(".smart-form .form-control").addClass('input-sm');
	$(".smart-form font").attr('size',2);
	$(".smart-form a font").css('font-weight','bold');
	$(".smart-form .row").find(".form-group").css("margin-top",10);
	//$(".smart-form .row").eq(0).find(".form-group").css("margin-top",0);
	var num_label=form.find(".row").eq(0).find("label").length;//第一行labe个数
	var sum_row=form.find(".row").length;         //行数
	var sum_col=form.find(".row").first().find(".form-group").length; //列数
	var row_width=form.find(".row").width();  //行宽
    var row_dis=row_width/35;                   //行边距
    row_width=row_width-row_dis;
	var left_margin=(row_width/sum_col)/15;  //左边距
	var colum_width=row_width/sum_col;           //列宽
	var group_width=colum_width-left_margin; //实体宽
	var dis=group_width/15;                 //btn间距
	var label_rtm=5;                        //label 右边距
	if(group_width<200)
		form.find(".row font").attr('size',1);
	form.find(".row .form-group").each(function(){
		var label_with=$(this).find('label').width();
		var input_width=group_width-label_with-label_rtm;
		if($(this).find('.modal-input').length>0){              //设置表单input宽
			$(this).find(".modal-input").css('width',input_width);
			$(this).find(".modal-input a").css('padding-left',5);
			$(this).find(".modal-input a").css('padding-right',5);
		}else{
			$(this).find("input").css('width',input_width);
		}   
		$(this).find('select').css('width',input_width);			   //设置表单select宽
		$(this).find('.btn-md').css('width',(group_width-dis)/2);		//设置按钮宽度
		$(this).find('.btn-md').eq(1).css('margin-left',dis);
		$(this).find("label").css('margin-right',label_rtm);                     //设置label右边距
	});
	form.find(".row .form-group").css("margin-left",left_margin);
	//设置每一行第一个元素的左边距
	form.find(".row").each(function(){
		var form_group= $(this).find('.form-group').eq(0);
		form_group.css('margin-left',left_margin/2+row_dis/2);
	});
	//设置btn位于最后一列
	var lastrow_num=form.find(".row").last().find('.form-group').length;
	var margin_dis=(sum_col-lastrow_num)*(colum_width)+left_margin;
	if(lastrow_num==1)
		margin_dis=(sum_col-lastrow_num)*(colum_width)+left_margin/2+row_dis/2;
	form.find(".row").last().find('.btn-form').css('margin-left',margin_dis);
	//label对齐设置
	var sum=form_groups.length;//元素总数
	for (var int = 0; int < sum_col; int++) {
		var element=[];
		var max=0;
		for (var int2 = 0; int2 < sum_row; int2++) {
			if(((int2)*sum_col+int+1)>=(sum)) break;
			var $ele=element[int2]=form.find(".row").eq(int2).find(".form-group").eq(int);
			var lab_width=$ele.find("label").width();
			if(lab_width>max){
				max=lab_width;
				element[int2]=element[0];
				element[0]=$ele;
			}
		};
		for (var int3 = 1; int3 < element.length; int3++) {
			var input_widthj=group_width-max-label_rtm;
			element[int3].find("label").css('width',max);
			element[int3].find("label").css('text-align','right');
			element[int3].find("select").css('width',input_widthj);
			if(element[int3].find('.modal-input').length>0){
				element[int3].find(".modal-input").css('width',input_widthj);
				element[int3].find(".modal-input a").css('padding-left',5);
				element[int3].find(".modal-input a").css('padding-right',5);
			}else{
				if(element[int3].find(".btn-md").length>0) continue;
				element[int3].find("input").css('width',input_widthj);
			}
		}
	} 
})
}
SF.SearchPanel.create = function(cfg){ 
	var searchfrom = document.createElement('form'); //Document.createElement("form");
	searchfrom.className = "smart-form";
	searchfrom.role = "role";  
	searchfrom.id =searchfrom.name = cfg.name;
	var col_num=cfg.layoutConfig.columns;//列数 
	if(col_num==null||col_num==undefined){
		if(cfg.items.length>4)
			col_num=4;
		else
			col_num=cfg.items.length;
	}
	
	var row_num= Math.ceil((cfg.items.length+1)/col_num);  //行数
	var elFieldset = SF.createElements(cfg.type, cfg.items, col_num, row_num);
	searchfrom.appendChild(elFieldset);
	return searchfrom;
}
SF.SearchPanel.onload = function(cfg){ 
	$(window).resize(function() {
  		initFormCss(); 
	});
	var elSearchPanel = $("#"+cfg.berth);   
	var searchfrom = SF.SearchPanel.create(cfg);
	elSearchPanel.append(searchfrom);
	$(".smart-form #btn_SearchPanel_Search").click(
		function(){
			searchpanel.onsearch();
			return false;
		}
	);
	$(".smart-form #btn_SearchPanel_reset").click(SF.SearchButton.onReset); 
	initFormCss();  //初始化表单样式 
	$(".smart-form .form_datetime[type='datetime']").datetimerun();
	$(".smart-form select").comboxrun();
};

SF.LoadData = function(cfg){
	if(cfg.url!=undefined){ 
		$.ajax({ url: cfg.url, success: function(da){
			var data = undefined;
			if(cfg.dataroot==undefined) data = da;
			var data = eval("da."+cfg.dataroot); 
	    	SF.BindFormData(data);
	    }});
	}else{ 
		SF.BindFormData(cfg.data);
	}
}
/*加载数据*/
SF.BindFormData = function(data){
	if(data!=undefined){
		var paramctrls = SF.paramctrls;
		if(paramctrls==undefined) paramctrls=[];
		if(SF.hidefields==undefined) SF.hidefields = [];  
		for(var i=0;i<paramctrls.length;i++){
			var key = paramctrls[i].dataIndex; 
			var v = data[key];
			if(v==undefined) v = '';
			$("#"+key).val(v);  
		} 
		for(var j=0;j<SF.hidefields.length;j++){
			var key = SF.hidefields[j];
			var v = data[key];
			if(v==undefined) v = '';
			$("#"+key).val(v);    
		} 
	} 
};
SF.FormBody.create = function(cfg){  
	var formboby = document.createElement('form'); 
	formboby.className = "smart-form";
	formboby.role = "role";  
	formboby.id =formboby.name = cfg.name;
	var col_num=cfg.layoutConfig.columns;//列数 
	if(col_num==null||col_num==undefined){
		if(cfg.items.length>4)
			col_num=4;
		else
			col_num=cfg.items.length;
	}
	var row_num = Math.ceil((cfg.items.length+1)/col_num);  //行数
	var elFieldset = SF.createElements(cfg.type, cfg.items, col_num, row_num);
	//var btns=SF.FormButton.create(); 
	//elFieldset.appendChild(btns);
	formboby.appendChild(elFieldset); 
	var hidefieldsDiv = SF.createHideFields(cfg.hidefields);
	formboby.appendChild(hidefieldsDiv); 
	return formboby;
};
SF.FormBody.onload = function(cfg){ 
	$(window).resize(function() {
  		initFormCss(); 
	});
	var berthPanel = $("#"+cfg.berth); 
	var formboby = SF.FormBody.create(cfg);
	var pBody = SF.PanelBody.create();
	pBody.appendChild(formboby);
	var pHeader = SF.PanelHeader.create(cfg.title);
	var pFooter = SF.PanelFooter.create();
	berthPanel.append(pHeader);
	berthPanel.append(pBody);
	berthPanel.append(pFooter);  
	$(".smart-form #btn_FormBody_commit").click( function(){
		cfg.onsubmit(); 
	    //window.returnValue = "ok"; window.close();
		if(!SF.IsEnableBrowserShowModal) window.onCloseCallback(window.returnValue);
    });
	$(".smart-form #btn_FormBody_cancel").click( function(){
		SF.FormButton.oncancel();
		if(!SF.IsEnableBrowserShowModal) window.onCloseCallback(window.returnValue);
    }); 
	
	$("#detailForm .panel-body input").change(function(){
		$(this).css("border-color","#ccc");
	})
	$("#detailForm .panel-body select").change(function(){
		$(this).css("border-color","#ccc");
	})
	
	initFormCss(); //初始化表单样式 
	$(".smart-form .form_datetime[type='datetime']").datetimerun();
	$(".smart-form select").comboxrun();
	SF.LoadData(cfg);
}; 


/* ********************************************************************
 * 打开对话窗口 _mode: add|update 
 * ********************************************************************
 * */
SF.showModalDlg = function(_url, _mode, _data, w, h, onCloseCallback){
    var sendData;
    if(!_mode && _mode === undefined)
        sendData = _data || {};
    else 
        sendData = {
            mode : _mode,
            data : _data
        }; 
    sendData['parent_window'] = window;
    var chrValue=undefined;
    //定义window.showModalDialog如果它不存在      
    //if(window.showModalDialog == undefined ){     
    if(!SF.IsEnableBrowserShowModal){ 
    	if(!!(window.opener)){         
    	    window.onbeforeunload=function(){  
    	        window.opener.hasOpenWindow = false;//弹窗关闭时告诉opener：它子窗口已经关闭  
    	    }  
    	}  
    	window.showModalDialog = function(url){
            var left = (window.screen.availWidth-w)/2;
            var top = (window.screen.availHeight-h)/2;
            var hasModalDialog = true;
            //window.open('page.html', '_blank', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no')   //该句写成一行代码
            var dlg_style0 = 'height='+h+',width='+w + ',top='+top+',left='+left+',toolbar=no,directories=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,titlebar=no';
            window.myNewWindow = window.open(url,'_blank',dlg_style0);
            window.myNewWindow.opener = window;
            return window.myNewWindow;
        }
    } 
    var dlg_style = 'dialogWidth:'+w+'px;dialogHeight:'+h+'px;center:1;scroll:1;help:0;status:0';
	chrValue=window.showModalDialog(_url,sendData,dlg_style);
     //for chrome
    if(chrValue==undefined) chrValue = {}; 
    if(!chrValue.opener||chrValue.opener==undefined) chrValue["opener"] = window; 
    if(!chrValue.dialogArguments||chrValue.dialogArguments==undefined) chrValue["dialogArguments"] = sendData; 
    if(!chrValue.onCloseCallback||chrValue.onCloseCallback==undefined) chrValue["onCloseCallback"] = onCloseCallback; 
  	if(SF.IsEnableBrowserShowModal){ 
 		//chrValue.onbeforeunload = onCloseCallback(chrValue.returnValue);
 		chrValue.onclose = onCloseCallback(chrValue);
 	}
	return chrValue;
}; 

SF.validForm = function (result) {
	if(result.responseJSON.code=="-1005"){
		var form_mes = $('<div id="form_error" class="alert alert-danger" role="alert"></div>');
		$("#form_error").remove();
		var $pd = $("#detailForm .panel-body");
		var content = [];
		content = result.responseJSON.content;
		var message = "错误！ ";
		for (var i = 0; i < content.length; i++) {
			$("#"+content[i].field).css("border-color","#c92027");
			if(i == content.length-1) {
				message = message + content[i].defaultMessage
			} else {
				message = message + content[i].defaultMessage+"， ";
			}
		}
		form_mes.html(message);
		$pd.prepend(form_mes);
	}
}
