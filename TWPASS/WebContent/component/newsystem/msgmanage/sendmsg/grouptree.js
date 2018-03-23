talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.Tree",
		options:{
			id:"grouptree",
			container:"tree1",
			openFloor : 3,
			treeType:"multitree",
			multiple: true,
			showIco: false,
            saveInstance: true,
			initSource:"msg_initGroupTree.action"
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"确定",
			className:"btn_save",
			container:"buttons",
			click:ok
		}
	}
	]
});

function ok(){
	var treeob = talkweb.ControlBus.getInstance("grouptree");
	var values = treeob.getValues("chkedNodesArray");
	var parm = "";
	var names = "";
	for(var i = 0; i < values.length; i++){
		if(i > 0) parm += ",";
		if(i > 0) parm += ",";
		parm += values[i].id;
		parm += values[i].val;
	}
	window.parent.closeAndUptxt(parm,names);
}
