/*MesWebApi接口前缀地址*/
var MesWebApiUrl = "http://192.168.12.241/MesWebApi/api";

/* 查询条件操作符枚举 ConditionOperator */
if(typeof ConditionOperator == "undefined"){
	var ConditionOperator = {
		Like : 0, //Like
		In : 1, //In
		NotIn : 2, //Not in
		Equal : 3, //等于
		Greater : 4, //大于
		GreaterAndEqual : 5, //大于等于
		Less : 6, //小于
		LessAndEqual : 7, //小于等于
		NotEqual : 8, //不等于
		ChildIn : 9, //子查询
		Between : 10 //between '值1' and '值2' 
	}
};

/*设置一个cookie
 expiresMinutes为0时不设定过期时间，即当浏览器关闭时cookie自动消失
 */
function setCookie(name,value,expiresMinutes){ 
	var cookieString=name+"="+escape(value);  
	if(expiresHours>0){ //判断是否设置过期时间 
		var date=new Date(); 
		date.setTime(date.getTime+expiresMinutes*60*1000); 
		cookieString=cookieString+"; expires="+date.toGMTString(); 
	}
	document.cookie=cookieString; 
}  

/*读取cookie*/
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

/*删除cookies*/
function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

