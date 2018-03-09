/**
 * 把字符串中的&替换为#,=替换为:
 * @param str
 * @return
 */
function changeConditionString(str) {
	var retStr;
	retStr = str.replaceAll("&", ",");
	retStr = retStr.replaceAll("=", ":");
	return retStr;
}
/**
 * 把字符串中的,替换为:
 * @param str
 * @return
 */
function changeConditionString1(str) {
	var retStr;
	retStr = str.replaceAll(",", ":");
	return retStr;
}
/**
 * 把日期字符串中的-替换为空
 * @param str
 * @return
 */
function changeDateString(str) {
	var retStr;
	retStr = str.replaceAll("-", "");
	return retStr;
}
/**
 * 取当前年(四位yyyy)
 * @return
 */
function getCurrentYear() {
	var retStr;
	var today = new Date();
	retStr = today.getFullYear();
	return retStr;
}
/**
 * 取当前月
 * @return
 */
function getCurrentMonth() {
	var retStr;
	var today = new Date();
	retStr = today.getMonth() + 1;
	if (retStr < 10) {
		retStr = '0' + retStr;
	}

	return retStr;
}
/**
 * 取当前日期
 * @return
 */
function getCurrentDay() {
	var retStr;
	var today = new Date();
	retStr = DateToStr(today);
	return retStr;
}

/**
 * 取当前日（例如：今天是2010-10-08，则结果返回08）
 * @return
 * @by feiying add 
 */
function getCurrentOnlyDay() {
	var retStr;
	var d;
	var today = new Date();
	d = today.getDate();
	if (d <= 9) {
		d = "0" + d;
	}
	retStr = d;
	return retStr;
}
/**
 * 取当前日期前后几天的日期
 * 如：5表示当前日期后五天,-5表示当前日期前五天
 * @return
 */
function getBeforeOrAfterDay(NumDay) {
	var retStr;
	var today = new Date();
	today = new Date(Date.parse(today) + (86400000 * parseInt(NumDay)));
	retStr = DateToStr(today);
	return retStr;
}
/**
 * 取当前月的第一天
 * @return
 */
function getMonthFirstDay() {
	var retStr;
	var Nowdate = new Date();
	var MonthFirstDay = new Date(Nowdate.getFullYear(), Nowdate.getMonth(), 1);
	retStr = DateToStr(MonthFirstDay);
	return retStr;
}
function getMonthFirstDay1() {
	var retStr;
	var Nowdate = new Date();
	var MonthFirstDay = new Date(Nowdate.getFullYear(), Nowdate.getMonth(), 1);
	retStr = DateToStr(MonthFirstDay);
	return retStr;
}
/**
 * 取某年月的最后一天
 * @param sYear
 * @param sMonth
 * @return
 */
function getMonthLastDay(sYear, sMonth) {
	var retStr;
	var MonthNextFirstDay = new Date(sYear, sMonth, 1);
	var MonthLastDay = new Date(MonthNextFirstDay - 86400000);
	retStr = DateToStr(MonthLastDay);
	return retStr;
}
/**
 * 日期转化为字符串格式： yyyy-mm-dd
 * @param dt
 * @return
 */
function DateToStr(dt) {
	var str = "";
	if (dt.getFullYear) {
		var y, m, d;
		y = dt.getFullYear();
		m = dt.getMonth() + 1; //1-12
		if (m <= 9)
			m = "0" + m;
		d = dt.getDate();
		if (d <= 9)
			d = "0" + d;
		str = "" + y + "-" + m + "-" + d;
	}
	return str;
}
/**
 * 日期转化为字符串格式： yyyymmdd
 * 月份不加1
 * @param dt
 * @return
 */
function DateToStrNew(dt) {
	var str = "";
	var y, m, d;
	y = dt.getFullYear();
	m = dt.getMonth(); //1-12
	// alert("m is "+m);
	if (m <= 9)
		m = "0" + m;
	d = dt.getDate();
	if (d <= 9)
		d = "0" + d;
	str = "" + y + m + d;
	return str;
}
/**
 * strDate 的格式是 yyyy-mm-dd, 如果格式不正确返回当前日期
 * @param strDate
 * @return
 */
function StrToDate(strDate) {
	var splitArray;
	var str = "";
	str = strDate;
	splitArray = str.split("-")
	if (splitArray.length != 3)
		return new Date();
	for (var i = 0; i < splitArray.length; i++) {
		if (isNaN(splitArray))
			return new Date();
	}
	return new Date(splitArray[0], splitArray[1] - 1, splitArray[2]);
}

/**
 * 取得特定月份的第一天
 * @param year
 * @param month
 * @return
 */
function getSpecialMonthFirstDay(year, month) {
	var firstDayOfMonth = new Date(year, month - 1, 1);
	return DateToStr(firstDayOfMonth);
}

/**
 * 
 * @param dt
 * @return 返回形如20090909的日期
 */
function DateToStr2(dt) {
	var str = "";
	if (dt.getFullYear) {
		var y, m, d;
		y = dt.getFullYear();
		m = dt.getMonth() + 1; //1-12
		if (m <= 9)
			m = "0" + m;
		d = dt.getDate();
		if (d <= 9)
			d = "0" + d;
		str = "" + y + "" + m + "" + d;
	}
	return str;
}

/**
 * 
 * @param sYear
 * @param sMonth
 * @return 返回形如20090909的日期
 */
function getMonthLastDay2(sYear, sMonth) {
	var retStr;
	var MonthNextFirstDay = new Date(sYear, sMonth, 1);
	var MonthLastDay = new Date(MonthNextFirstDay - 86400000);
	retStr = DateToStr2(MonthLastDay);
	return retStr;
}
String.prototype.left=function(len){
	this.substring(0,len);
}
String.prototype.right=function(len){
	this.substring(len-1);
}
//grid列展示时间格式化
function timeFormat(src) {
	if (src == '' || src == undefined)
		return '';
	var dest = src.substring(0,4); 
	src = src.substring(4);
	dest += "-" + src.substring(0,2); 
	src = src.substring(2);
	dest += "-" + src.substring(0,2); 
	src = src.substring(2);
	dest += " " + src.substring(0,2);
	src = src.substring( 2);
	dest += ":" + src.substring(0,2);
	src = src.substring(2);
	dest += ":" + src;
	return dest;
}

function dateFormat(src){
	if(src=='' || src==undefined)
		return'';
    var dest = src.left(4); // year
    src = src.right(src.length - 4);
    dest += "-" + src.left(2); // month
    src = src.right(src.length - 2); 
    dest += "-" + src.left(2); // day
    return dest;
}
