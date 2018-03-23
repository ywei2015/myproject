var request = fastDev.Browser.getRequest();
var userID = request['userID'];
var permissionID = request['permissionID'];

function onAfterInitRender() {
	fastDev('#grid-height-checkbox').bind('click', onCheckboxSelectChange);
	var dataRight = fastDev.Ui.Window.parent.getDataRight();
	var values = this.getAllValue();
	var ruleIds = {};
	var permissionIds = permissionID.split(',');
	var len = permissionIds.length;
	for(var i = 0; i < len; i++) {
		var ruleId = dataRight[permissionIds[i]] || "";
		if (ruleId != undefined)
	    {
		    for(var j = 0; j < ruleId.length; j++)
		    {
			    if(ruleIds[ruleId[j]]) {ruleIds[ruleId[j]]++;}
			    else {ruleIds[ruleId[j]] = 1;}
		    }
	    }
	}
	for(var i=0; i<values.length;i++) {
		var value = values[i];
		var ruleId = value['ruleId'];
		var row = {
			ruleId: ruleId
		};
		if (ruleIds[ruleId] != undefined){
		if(ruleIds[ruleId] == len) {
			row.chk = 'true';
		} else if(ruleIds[ruleId] > 0) {
			row.chk = 'part';
		} else {
			row.chk = 'false';
		}
		this.updateRow(row);}
	}
}

function onAfterConstruct() {
	this.addInitReqParam({
		userID : userID,
		permissionID : permissionID,
		showType : 'user'
	});
}

function checkboxRenderer(value) {
	var cls;
	if(value == 'true') {
		cls = 'ico-checkbox-checked';
	} else if(value == 'part') {
		cls = 'ico-checkbox-half';
	} else {
		cls = 'ico-checkbox';
	}
	return '<div id="checkbox" class="ui-grid-checkbox ' + cls + '"></div>';
}

function onRowClick(event, rowindex, data) {
	var target = event.target;
	if(target.id == 'checkbox') {
		if(data.chk == 'true') {
			data.chk = 'false';
		} else {
			data.chk = 'true';
		}
		this.updateRow(data);
	}
}

function updateDatagridCheckbox(checked) {
	var datagrid = fastDev.getInstance('datagrid');
	var items = datagrid.getAllValue();
	var value = checked ? 'true' : 'false';
	var len = items.length;
	for(var i = 0; i < len; i++) {
		if(items[i].chk != value) {
			items[i].chk = value;
			datagrid.updateRow(items[i], i==len-1);
		}
	}
}

function onCheckboxSelectChange(event) {
	var target = fastDev(event.target);
	if(target.hasClass('ico-checkbox-checked')) {
		target.removeClass('ico-checkbox-checked');
		target.addClass('ico-checkbox');
		updateDatagridCheckbox(false);
	} else {
		target.removeClass('ico-checkbox');
		target.addClass('ico-checkbox-checked');
		updateDatagridCheckbox(true);
	}
}

function doSave() {
	var items = fastDev.getInstance('datagrid').getAllValue();
	var ruleIds = [];
	for(var i = 0; i < items.length; i++){
		if(items[i].chk == 'true') {
			ruleIds.push(items[i].ruleId);
		}
	}
	fastDev.Ui.Window.parent.setChildDataRight(permissionID, ruleIds.join("_@@_"));
	doClose();
}
function doClose() {
	fastDev.Ui.Window.parent.closeDialog();
}