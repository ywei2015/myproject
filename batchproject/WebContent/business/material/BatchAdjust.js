var title= ["编码","名称","小件批次号","原大件批次号","新大件批次号","操作"];
var theTable=document.getElementById("table");
var f_master_batch=getQueryString('f_master_batch'); //OldDEF
var data_p={
		'f_master_batch':f_master_batch
};
initTable(data_p);
var billarray=[];
function initTable(dataj){
	theTable.innerHTML="";
	$.ajax({
		type : "post",
		url: cqt_prefix+'sizepieces/getBatBatAdjustDetail',
		data:dataj,
		success : function(data) {
			//var str=eval('(' + data + ')');   //解析json
			var str=data.dataset;
			var length=str.batbatadjustdetail.length;
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
					var rowdata=str.batbatadjustdetail[i];//对象
					var r =document.createElement('tr');
					if(i%2==0) r.style.backgroundColor='white';
					if(rowdata!=null||rowdata!=undefined){
						var td;
						var data_td1;
						
						td=document.createElement('td');
						data_td1=rowdata.matcode;//名称
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						td.style.color='blue';
						data_td1=rowdata.matname;//名称
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						data_td1=rowdata.slavebatch;//小件批次号
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						data_td1=rowdata.oldmasterbatch;
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						data_td1=rowdata.newmasterbatch;
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						var pid=rowdata.pid;
						billarray[i]=pid;
						//data_td="<a href='' onclick='deleteRow("+rowdata.billpid+")'>删除</a>";
						data_td="<a onclick='deleteRow("+i+")' href='#popupDialog' data-rel='popup' data-role='button' data-position-to='window'" +
								" class='ui-btn-right' data-transition='pop'>删除</a>";
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
    		url: cqt_prefix+'sizepieces/deleteBatBatAdjustDetail',
    		data:{'f_pid':billarray[Id]},
    		success : function(data) {
    			var falg=data.dataset.response.code;
    			if(falg==0)
    				alert("删除失败!");
    			if(falg==1)
    				initTable(data_p);
    		}
    	});  
  
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
}