var cqt_prefix = "http://192.168.43.49:8080/batchproject/dynamic/";
//var cqt_prefix = "http://192.168.146.176:7002/batchproject/dynamic/";
var time;
var myDate = new Date();
var year=myDate.getFullYear().toString();
var month=(myDate.getMonth()+1).toString(); 
var date=myDate.getDate().toString();
if(month.length<2)
	month='0'+month;
if(date.length<2)
	date='0'+date;

