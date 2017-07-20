var title= ["已扫","名称","最近录入时间","操作"];
var theTable=document.getElementById("table");
var userId=getQueryString('userId');
var f_doc_type=getQueryString('f_doc_type');//test:
var f_mat_batch=getQueryString('f_mat_batch');
var data_p={
		'f_doc_type':f_doc_type,
		'remark5':1,
		'f_mat_batch':f_mat_batch,
		'userId':userId
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
var picishu=0;
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
	//if(f_mat_batch!=null&&f_mat_batch!=""){
	$.ajax({
		type : "post",
		url:cqt_prefix+'storage/getBatDepotIoDetailByFZ',
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
						data_td1=rowdata.remark;//数量
						td.innerHTML=data_td1;
						r.appendChild(td);
						var shu=parseInt(data_td1);
						picishu=shu+picishu;
						
						td=document.createElement('td');
						td.style.color='blue';
						data_td1=rowdata.matname;//名称
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						var data_td2=rowdata.lastmodifiedtime;//开始
						data_td1=data_td2.substring(4,6)+'-'+data_td2.substring(6,8)+' '+data_td2.substring(8,10)+':'+data_td2.substring(10,12);
						td.innerHTML=data_td1;
						r.appendChild(td);
						
						td=document.createElement('td');
						var matcodeid=rowdata.matcode;
						data_td="<a href='javascript:' onclick='tiaozhuan("+matcodeid+")'>查看 </a>";
						//data_td="<a href='BatchStorageOut.html?userId='"+userId+"'>查看 </a>";
						td.innerHTML=data_td;
						r.appendChild(td);
					}
					b.appendChild(r);
				}
				var tishi=document.getElementById("cailiao");
				 tishi.innerHTML=picishu;
			}
			
		}
			 
		}
	});
	//}
	theTable.appendChild(b);
}
var Id;
function deleteRow(i){
	Id=i;
	var tishi=picihao;
	$("#sure").text(picihao[i]);
}

function tiaozhuan(matcode){
	window.location.href="BatchStorageDetail.html?userId="+userId+"&matcode="+matcode;
}

function verifySubmit(){
	    var aa=Id;
    	$.ajax({
    		type : "post",
    		url: cqt_prefix+'storage/deleteBatDepotIoDetail',
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