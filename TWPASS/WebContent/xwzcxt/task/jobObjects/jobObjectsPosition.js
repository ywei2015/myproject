var column = null; //DataGrid的列数组
var tmpColumn = null; //临时DataGrid的列数组
var grid = null; //DataGrid对象
var form = null; //表单对象

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#centerPanel').css('width', (fastDev(window).width() - 211) + 'px');
	fastDev('#tableTypeTree').attr('height', (height - 35) + 'px');
	fastDev('#datagrid').attr('height', (height - 58) + 'px');
}

function onBeforeReady() {
	this.setOptions({
		initSource : "jobObjects_queryPtObject.action"
	});
}

function onAfterInitRender() {
	showResourceModes('DLZBGL');
}

function onNodeClick(event, value) {
	writeColumn(value);
	doSearch(value);
}

function doSearch(value) {
	if (!value) {
		fastDev.alert("清先选择作业对象类型!");
		return;
	}
	var condition = null;
	if (form == null) {
		form = fastDev.getInstance("searchForm");
	}
	condition = form.getItems();
	condition["cTabletypeId"] = value;
	if (grid == null) {
		grid = fastDev.getInstance('datagrid');
	}
	grid.refreshData(condition);
}

function writeColumn(cTabletypeId) {
	if (cTabletypeId == null || cTabletypeId == undefined) {
		return;
	}
	fastDev.post("jobObjects_getColumnsConfig.action", {
		success : function(data) {
			column = data;
			if (grid == null) {
				grid=fastDev.getInstance('datagrid');
			}
			for (var j = 0; tmpColumn != null && j < tmpColumn.length; j++) { //移除之前存在的所有列
				grid.removeColumn(tmpColumn[j].name);
			}
			if (column.length == undefined) {
				return;
			}
			for (var i = 0; i < column.length; i++) { //重新设定新得到的列
				grid.addColumn(column[i], i + 6);
			}
			tmpColumn = column;
		},
		data : {"cTabletypeId" : cTabletypeId}
	});
}

function operRenderer() {
	return [ '<div style="width:' + fastDev(this).width() + 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;color:#4B0082;}" id="setPosition" name="setPosition">设置</a>'].join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
			case 'setPosition': fastDev.create("Window", {
				height : window.innerHeight,
				width : window.innerWidth-60,
				src : "../../../mobile/map/map.html?type=setPosition&cBasedataId=" + data.cBasedataId + "&cObjectTypeid=" + data.cTabletypeId,
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
	}
}

function ptionRenderer(value) {
	switch (value) {
		case "1":
			return "已设置";
		case "0":
			fastDev(this).parents("tr").children("td").css("color", "#FF2400");
			return "未设置";
	}
}

function onChange(value) {
	var condition = null;
	if (form == null){
		form = fastDev.getInstance("searchForm");
	}
	var cTabletypeId = fastDev.getInstance('tableTypeTree').getValue();
	condition = form.getItems();
	condition["cTabletypeId"] = cTabletypeId;
	if (grid == null){
		grid = fastDev.getInstance('datagrid');
	}
	grid.refreshData(condition);
}