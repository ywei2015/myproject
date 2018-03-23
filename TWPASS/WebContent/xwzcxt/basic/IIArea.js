var tree;
var curareanode;

function doSearchArea(){
	
}

function onNodeClick(event, id, value) {
	currentTypeId = id;
	for ( var i = 0; i < tree.dataset.records.length; i++) {
		if (tree.dataset.records[i].id == id) {
			//console.info(tree.dataset.records[i]); //Object {val: "", id: "", c_node_level: 2, c_area_fullname: "", pid: ""…}
			curareanode = tree.dataset.records[i];
			//console.info(curareanode);
			upTypeCode = tree.dataset.records[i].val;
		}
	}
	//console.info(fastDev.getInstance('grid1'));
	refreshDatagrid();
}

//操作列渲染函数
function operRender() {
	var oper = [];
	oper.push('<a href="#" name="modify">修改</a>&nbsp;');
	oper.push('<a href="#" name="del">删除</a>');
	return oper.join('');
}

function refreshTree() {
	tree.reLoad();
}


function treeAfterReady() {
	tree = this;
	//console.info('--1-------------');
}


function doAddType(){}
function doBatchDelete(){}


//操作列单击事件响应函数
function onRowClick(event, rowIndex, rowData) {
	var oper = event.target.name, flag;
	switch (oper) {
	case "modify":
		modifyById(rowData.c_objtype_id);
		return;
	case "del":
		deleteById(rowData.c_objtype_id);
		return;

	default:
		return;
	}
}

/**
 * 刷新datagrid
 * 
 * @param o
 */
function refreshDatagrid(o) {
	o = fastDev.apply(o || {}, currentCondition || {});
	o['v_area_id'] = currentTypeId;
	fastDev.getInstance('areadatagrid').refreshData(o);
}

/**
 * 刷新树
 */
function refreshTree() {
	fastDev.getInstance('orgtree').reLoad();
}

