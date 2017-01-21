var title= ["序号","名称","批次号","开始","结束","数量","单位","操作"];
var theTable=document.getElementById("table");
var userId=getQueryString('userId');
var f_workorder_code=getQueryString('f_workorder_code'); //test:
var f_machine=getQueryString('f_machine');//test:
var data_p={
		'f_workorder_code':f_workorder_code,
		'f_machine':f_machine
};
var img = new Image(); 
function loadImage() { 
	img.src = "../js/image/shanch.png"; 
	img.onload = function(){ //图片下载完毕时异步调用callback函数。 
		initTable(data_p);
	}; 
} 
loadImage();
var billarray=[];
var picihao=[];
function initTable(dataj){
	theTable.innerHTML="";
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
	if(userId==null){
	$.ajax({
		type : "post",
		url: cqt_prefix+'rollbatch/getBatWorkOrderInput',
		data:dataj,
		success : function(data) {
			//var str=eval('(' + data + ')');   //解析json
			var str=data.dataset;
			 if(str.totalRecords>0){
			var length=str.batworkorderinput.length;
			var color_type='no';
			//设置表格
			if(length>0){
				var tishi_type=document.getElementById("type_tishi");
				tishi_type.style.display="block";
				for(var i=0;i<length;i++){
					var rowdata=str.batworkorderinput[i];//对象
					var r =document.createElement('tr');
					if(i%2==0) r.style.backgroundColor='white';
					if("0"==rowdata.isremark1){
						r.style.color='red';
						color_type='yes';
					}
					if(rowdata!=null||rowdata!=undefined){
						var td;
						var data_td1;
						
						td=document.createElement('td');
						data_td1=1+i;//序号
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						if("0"==rowdata.remark4){
							td.style.color='red';
						}else{
							td.style.color='blue';
						}
						data_td1=rowdata.matname;//名称
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						td.id="picihao";
						data_td1=rowdata.matbatch;//批次号
						picihao[i]=data_td1;
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
						var shanchu="<div ><img height='32' width='30' src='../js/image/shanch.png'></img></div>";
						data_td="<a  onclick='deleteRow("+i+")' href='#popupDialog' data-rel='popup'  data-position-to='window'" +
						">"+shanchu+"</a>";
						td.innerHTML=data_td;
						r.appendChild(td);
					}
					b.appendChild(r);
				}
				if("w"==rowdata.remark5){
					r.style.color='yellow';
				}
				if("0"==rowdata.remark4){
					r.style.color='red';
				}
				
			}
			
		}
		}
	});
	}
	theTable.appendChild(b);
}
var Id;
function deleteRow(i){
	Id=i;
	var tishi=picihao;
	$("#sure").text(picihao[i]);
}


function verifySubmit(){
	    var aa=Id;
    	$.ajax({
    		type : "post",
    		url: cqt_prefix+'rollbatch/deleteBatWorkOrderInput',
    		data:{'f_pid':billarray[Id],'userId':userId},
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