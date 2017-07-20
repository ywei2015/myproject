var title= ["编码","名称","批次号","数量","单位"];
var theTable=document.getElementById("table");
var userId=getQueryString('userId');
var f_bill_no=getQueryString('f_bill_no'); //test:
var f_doc_type=getQueryString('f_doc_type');//test:
var data_p={
		'f_bill_no':f_bill_no,
		'f_doc_type':f_doc_type,
		'remark5':1
};

initTable(data_p);

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
	if(f_bill_no!=null&&f_bill_no!=""){
	$.ajax({
		type : "post",
		url:cqt_prefix+'storage/getBatDepotValidate',
		data:dataj,
		success : function(data) {
			//var str=eval('(' + data + ')');   //解析json
			var str=data.dataset;
			 if(str.totalRecords>0){
			var length=str.batdepotiodetail.length;
			
			//设置表格
			if(length>0){
				
				for(var i=0;i<length;i++){
					var rowdata=str.batdepotiodetail[i];//对象
					var r =document.createElement('tr');
					if(i%2==0) r.style.backgroundColor='white';
					if(rowdata!=null||rowdata!=undefined){
						var td;
						var data_td1;
						td=document.createElement('td');
						data_td1=rowdata.matcode;//编码
						td.innerHTML=data_td1;
						r.appendChild(td);
						td=document.createElement('td');
						td.style.color='blue';
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
						data_td1=rowdata.quantity;//数量
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						data_td1=rowdata.unit;//单位
						td.innerHTML=data_td1;
						r.appendChild(td);
					}
					b.appendChild(r);
				}
				
				
			}
			
		}
		}
	});
	}
	theTable.appendChild(b);
}


function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
}