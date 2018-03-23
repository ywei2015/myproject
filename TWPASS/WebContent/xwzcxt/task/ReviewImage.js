/*
 * Image preview script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by GuveXie 2014-09-02 
 * 
 *
 */
 
//灯光控制
var onlightSwitcher =function(){
	$("#shadow").toggle();
	if ($("#shadow").is(":hidden")){
		$("body").css("overflow","scroll"); //$(this).html("关灯").removeClass("turnedOff");
		$(document).scrollTop(0);
	}
	else{
		$("body").css("overflow","scroll"); //$(this).html("开灯").addClass("turnedOff");
	}
};
	
var imgwin = null;
//var closeimgwin = function(){
function closeimgwin(){	
	if(imgwin==null) return;
	imgwin.close();
};
var popimgwindow = function(path){
	// 创建跨级子页面弹窗 
	//var imgwincontenthtml = "<img src='" + path + "' alt='点我返回' title='点我返回' onclick='alert(\"abcd\");'/>";
	imgwin = fastDev.create("Window", {
			id : "window_img",
			saveInstance : true,
			inside : false,
			width : "60%",
			height : "70%",
			title : "图片浏览窗口", 
			src : "img.html?path="+path,
			//content: imgwincontenthtml,
			//showMinBtn : false, showMaxBtn : false,
			showCollapseBtn : false,
			buttons : {
			    iconCls:"icon-close",
				text : "关闭",
				align : "center",
				id : "btnclose",
				// 按钮配置中，回调事件可接收4个参数
				onclick : function(event, that, win, fast){
					// event为点击事件对象
					// that为当前弹窗实例
					// win为iframe子页面的window对象，若子页面为跨域页面，则该参数为undefined
					// fast为iframe子页面上的fastDev对象，若子页面为跨域页面或者子页面未加载fastDev库，则此参数为undefined
					// 除使用数据存取接口与内部子页面交互外，也可通过此种方式与内部子页面交互
					// 此回调的作用域（this）为当前点击的按钮控件
					//fastDev.tips(that);
					imgwin.close();
				}
			}
		}); 
	
	/*imgwin = window.open('img.html?path='+path,
			'newwindow',
			'height=70%,width=60%,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no'
			); */
};


var imagePreviewInitEx = function(){ 
	//鼠标点击控制
	$("a.imglinkpreview").mousedown(function(e){
		//this.t = this.title; this.title = "";	 var c = (this.t != "") ? "<br/><br/><br/><br/><br/>" + this.t : "";
		//alert(this.rel);
		popimgwindow(this.rel);
	});			 
};


//starting the script on page load
$(document).ready(function(){
	//imagePreview(); //初始化灯光
	//alert("--d-d-d-d-");
	imagePreviewInitEx();
	$("#shadow").css("height", $(document).height()).hide();
});


/*
//图片放大控制	 
var imagePreview = function(){	
	xOffset = 10;
	yOffset = 30;
	//鼠标点击控制
	$("a.imglinkpreview").mousedown(function(e){
		this.t = this.title;
		this.title = "";	
		var c = (this.t != "") ? "<br/><br/><br/><br/><br/>" + this.t : "";
		$("body").append("<div id='preview'  alt='点我返回' title='点我返回'  style='text-align:center;overflow:scroll;width:95%;height:100%' onclick='javascript:$(\"#preview\").remove();onlightSwitcher();'><img src='"+ this.rel +"' alt='点我返回' title='点我返回' />"+ c +"</div>");
		$("#preview") //style='overflow:scroll;width:100%;height:100%' 
		.css("top",(0) + "px")
		.css("left",(0) + "px")
		//.css("overflow","scroll") 
		.css("border","5px solid blue")
		.css("margin","10px")
		.css("padding","10px")
		.css("z-index",1000)
		.css("position","absolute");
		onlightSwitcher();
	});			
	$("a.imglinkpreview").mouseup(function(e){
		$("#preview").remove(); 
		onlightSwitcher();
	}); 

	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		//console.info($("#shadow").is(":hidden"));
		if (e && e.keyCode == 27) { // 按 Esc 要做的事情
			if(!$("#shadow").is(":hidden")){
				$("#preview").remove(); 
				onlightSwitcher();
			}
		}
		if (e && e.keyCode == 113) { // 按 F2 要做的事情
		}
		if (e && e.keyCode == 13) {// enter 要做的事情 
		}
	};
};
*/
