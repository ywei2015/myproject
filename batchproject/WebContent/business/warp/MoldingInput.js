var title= ["序号","名称","批次号","开始","结束","数量","单位","操作"];
var theTable=document.getElementById("table");
var f_workorder_code=getQueryString('f_workorder_code'); //test:
var f_machine=getQueryString('f_machine');//test:
var data_p={
		'f_workorder_code':f_workorder_code,
		'f_machine':f_machine
};
initTable(data_p);
var billarray=[];
function initTable(dataj){
	theTable.innerHTML="";
	$.ajax({
		type : "post",
		url: cqt_prefix+'rollbatch/getBatWorkOrderInput',
		data:dataj,
		success : function(data) {
			//var str=eval('(' + data + ')');   //解析json
			var str=data.dataset;
			var length=str.batworkorderinput.length;
			var b = document.createElement('tbody');
			var title_r=document.createElement('tr');
			//设置表头
			for( var e in title){
				var title_table=title[e];
				var td=document.createElement('th');
				td.innerHTML=''+title_table;
				title_r.appendChild(td);
			}
			b.appendChild(title_r);
			//设置表格
			if(length>0){
				for(var i=0;i<length;i++){
					var rowdata=str.batworkorderinput[i];//对象
					var r =document.createElement('tr');
					if(i%2==0) r.style.backgroundColor='white';
					if(rowdata!=null||rowdata!=undefined){
						var td;
						var data_td1;
						
						td=document.createElement('td');
						data_td1=1+i;//序号
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						td.style.color='blue';
						data_td1=rowdata.matname;//名称
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						data_td1=rowdata.matbatch;//批次号
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						var data_td2=rowdata.starttime;//开始
						data_td1=data_td2.substring(0,4)+'-'+data_td2.substring(4,6)+'-'+data_td2.substring(6,8)
						+':'+data_td2.substring(8,10);
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						var data_td2=rowdata.endtime;//结束
						data_td1=data_td2.substring(0,4)+'-'+data_td2.substring(4,6)+'-'+data_td2.substring(6,8)
						+':'+data_td2.substring(8,10);
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						data_td1=rowdata.quantity;//数量
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						data_td1=rowdata.unit;//单位
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						var pid=rowdata.pid;
						billarray[i]=pid;
						data_td="<a  data-role='button' onclick='deleteRow("+i+")' href='#popupDialog' data-rel='popup'  data-position-to='window'" +
						">删除</a>";
						td.innerHTML=data_td;
						r.appendChild(td);
					}
					b.appendChild(r);
				}
				
				theTable.appendChild(b);
			}
			
		}
	
	});

}
var Id
function deleteRow(i){
	Id=i;
}


function verifySubmit(){
	    var aa=Id;
    	$.ajax({
    		type : "post",
    		url: cqt_prefix+'rollbatch/deleteBatWorkOrderInput',
    		data:{'f_pid':billarray[Id]},
    		success : function(data) {
    			var falg=data.dataset.response.code;
			if(falg==0)
				$("#tishi").text("删除失败！");
			if(falg==1){
				$("#tishi").text("删除成功！");
				$("#ok").bind('click',function(){
					 initTable(data_p);
					 $("#ok").unbind("click");
				   });
			    
			}
			}
    	});  
  
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
}