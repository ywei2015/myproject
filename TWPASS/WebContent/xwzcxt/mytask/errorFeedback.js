function doReset() {
	fastDev.getInstance('checkform').resetData();
}

//保存组织信息
function doSave(fn) {
	fastDev.getInstance('checkform').submit();
}

//表单提交后回调
function onSubmitSuccess(data) {
	if(data.result) {
		
		fastDev.Ui.Window.parent.refreshDatagrid();
		fastDev.Ui.Window.parent.closeDialog();
		fastDev.tips(data.msg);
	} else {
		fastDev.alert(data.msg, '信息提示', data.status);
	}
}

/*
	tableId:表格的Id值 
	dtName:表现形式为dt的td的name属性值 
	ddName:表现形式为dd的td的name属性值 
*/
function FormTable(tableId,dtName,ddName){
	//table属性 
	var borderSpacing=0;
	var borderCollapse="collapse";
	var border="1px solid #D8E2EB";
	var fontSize="12px";
	var margin="0px 0px";
	var width="500px";
	var color="#333";
	var fontFamily="宋体";
	//dt属性
	var dt_backgroundColor="#E7EEF7";
	var dt_padding="1px 4px";
	var dt_textAlign="right";
	var dt_whiteSpace="nowrap";
	var dt_width="100px";
	var dt_height="26px";
	//dd属性
	var dd_padding="1px 4px";
	//属性
	var td_border="1px solid #D8E2EB";
	
	this.getBorderSpacing=function(){
		return borderSpacing;
	};
	this.getBorderCollapse=function(){
		return borderCollapse;
	};
	this.getBorder=function(){
		return border;
	};
	this.getFontSize=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(fontSize);
		}
		return fontSize;
	};
	this.getMargin=function(){
		return margin;
	};
	this.getWidth=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(width);
		}
		return width;
	};
	this.getDt_height=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(dt_height);
		}
		return dt_height;
	};
	this.getColor=function(){
		return color;
	};
	this.getDt_backgroundColor=function(){
		return dt_backgroundColor;
	};
	this.getFontFamily=function(){
		return fontFamily;
	};
	this.getDt_padding=function(){
		return dt_padding;
	};
	this.getDt_textAlign=function(){
		return dt_textAlign;
	};
	this.getDt_whiteSpace=function(){
		return dt_whiteSpace;
	};
	this.getDt_width=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(dt_width);
		}
		return dt_width;
	};
	this.getDd_padding=function(){
		return dd_padding;
	};
	this.getTd_border=function(){
		return td_border;
	};
	//Set方法
	this.setWidth=function(wid){
		width=arguments[0];
	};
	this.setDt_width=function(width){
		dt_width=arguments[0];
	};
	this.setDt_height=function(height){
		dt_height=arguments[0];
	};
	this.setMargin=function(marginStyle){
		margin=arguments[0];
	};
	this.setDt_textAlign=function(alignType){
		dt_textAlign=arguments[0];
	};
	this.tableElement=document.getElementById(tableId);
	//获取所有td元素
	this.getAllTdElements=function(){
		return document.getElementsByTagName("td");
	};
	//获取所有dt,dd元素 
	this.getAllDlElements=function(tdElements,tdName){
		var dts=[];
		for(var i in tdElements){
			var currentTd=tdElements[i];
			if(currentTd.nodeName){
				var currentTdName=currentTd.getAttribute("name");
				if(currentTdName==tdName){
					dts.push(currentTd);
				}
			}
		}
		return dts;
	};
	//所有td元素 
	this.tdElements=this.getAllTdElements();
	//所有dt元素 
	this.dtElements=this.getAllDlElements(this.tdElements,dtName);
	//获取所有dd元素
	this.ddElements=this.getAllDlElements(this.tdElements,ddName);
	//初始化表格
	this.initTable=function(){
		//初始化table 
		this.tableElement.style.borderSpacing=this.getBorderSpacing();
		this.tableElement.style.borderCollapse=this.getBorderCollapse();
		//this.tableElement.style.border=this.getBorder();
		this.tableElement.style.fontSize=this.getFontSize();
		this.tableElement.style.margin=this.getMargin();
		this.tableElement.style.width=this.getWidth();
		this.tableElement.style.color=this.getColor();
		this.tableElement.style.fontFamily=this.getFontFamily();
		//初始化td
		for(var k in this.tdElements){
			var currentElement=this.tdElements[k];
			if(typeof currentElement=="object"){
				currentElement.style.border=this.getTd_border();
			}
		}
		//初始化dt
		for(var i in this.dtElements){
			var curElement=this.dtElements[i];
			if(typeof curElement=="object"){
				curElement.style.padding=this.getDt_padding();
				curElement.style.backgroundColor=this.getDt_backgroundColor();
				curElement.style.textAlign=this.getDt_textAlign();
				curElement.style.whiteSpace=this.getDt_whiteSpace();
				curElement.style.width=this.getDt_width();
				curElement.style.height=this.getDt_height();
			}
		}
		//初始化dd
		for(var j in this.ddElements){
			var curEle=this.ddElements[j];
			if(typeof curEle=="object"){
				curEle.style.padding=this.getDd_padding();
			}
		}
	};
}