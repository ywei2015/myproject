$(function() {
	var jsonStr;
	document.getElementById("username").innerHTML = getCookie("opername");
	document.getElementById("pusername").innerHTML = getCookie("opername");
	if (isBlank(getCookie("operator")) || isBlank(getCookie("opername"))) {
		window.location.href = "login.html";
	} else {
		$.ajax({
			type : 'POST',
			url : "../../dynamic/Security/getMenuByUserId",
			dataType : "json",
			async : false,
			success : function(data) {
				$.each(data, function(index, dataset) {
					if (dataset.menutree.id == '9999') {
						temp = dataset.menutree.children;
						temp1 = JSON.stringify(temp);
						temp2 = temp1.substring(0,
								JSON.stringify(temp).length - 1);
						jsonStr = temp2.substr(1);
					} else {
						temp = dataset.menutree;
						jsonStr = JSON.stringify(temp);
					}
				})
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// alert("网络异常，请重试！");
			}
		})
	}
	var temp = "{id : \"10010\",text : \"系统菜单\",isHeader : true},";
	addTabs(({
		id : '90008',
		title : '欢迎页',
		close : false,
		url : '/platform/Admin/dashboard.html'
	}));
	App.fixIframeCotent();
	//jsonStr = jsonStr.replace(new RegExp('"icon":null', 'g'), 'icon:"fa fa-circle-o"');
	//console.info(jsonStr);
	var menus = eval('[' + temp + jsonStr + ']');// {id : "10010",text:"我的工作台",isHeader : true} 
	$('.sidebar-menu').sidebarMenu({
		data : menus,
		param : {
			strUser : getCookie("opername")
		}
	});

});

function logonOut() {
	BootstrapDialog
			.show({
				message : "是否退出系统？",
				buttons : [
						{
							label : "确定",
							cssClass : "btn-primary",
							icon : "glyphicon glyphicon-ban-circle",// 通过bootstrap的样式添加图标按钮
							action : function(dialog) { // 给当前按钮添加点击事件
								$
										.post(
												"../../dynamic/Security/logonOut",
												{},
												function(result) {
													if (result.status == 200) {
														deleteCookie(
																"operator", "/");
														deleteCookie(
																"opername", "/");
														window.parent.location.href = "/platform/Admin/login.html";
													} else {
														layer
																.alert("网络异常，请重试！");
														return false;
													}
													// dialog.close();
												}, "json");
							}
						}, {
							label : "取消",
							cssClass : "btn-primary",
							icon : "glyphicon glyphicon-ban-circle",
							action : function(dialog) { // 给当前按钮添加点击事件
								dialog.close();
							}
						} ]
			});
}
// 获取cookie
function getCookie(name) {
	var name = name;
	// 读cookie属性，这将返回文档的所有cookie
	var allcookies = document.cookie;
	// 查找名为name的cookie的开始位置
	name += "=";
	var pos = allcookies.indexOf(name);
	// 如果找到了具有该名字的cookie，那么提取并使用它的值
	if (pos != -1) { // 如果pos值为-1则说明搜索"version="失败
		var start = pos + name.length; // cookie值开始的位置
		var end = allcookies.indexOf(";", start); // 从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
		if (end == -1)
			end = allcookies.length; // 如果end值为-1说明cookie列表里只有一个cookie
		var value = allcookies.substring(start, end); // 提取cookie的值
		return (value); // 对它解码
	} else
		return null; // 搜索失败，返回空字符串
}
// 清除cookie
function deleteCookie(name, path) {
	var name = (name);
	var expires = new Date(0);
	path = path == "" ? "" : ";path=" + path;
	document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}
function isBlank(str) {
	if (str == null || str == undefined || str == '') {
		return true;
	} else {
		return false;
	}
}