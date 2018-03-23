var fm=fastDev.getInstance('objectDetails');
var columns=null;
var request=fastDev.Browser.getRequest();
var  cTabletypeId=null;
var  cBasedataId=null;
var  formData=null;



function addOne(jobObject){
	var url="jobObjects_addObject.action";
	fastDev.post(url,{
		success:function(data){
			fastDev.tips(data);
		},
		data:jobObject
	});
}

function doSave(grid,win){

	var items=new Object();
	items["cBasedataId"]=cBasedataId;
	items["cTabletypeId"]=cTabletypeId;
	items['cOrgid']=fastDev.getInstance("cOrgid").getValue();
	
	var type=request['type'];
    if(items['cOrgid']==null||items['cOrgid']==''){
    	if(type!='search'){
    		fastDev.alert("所属组织不能为空！");
        	return;
    	}
    }
    
	
	for(var i=0;i<columns.length;i++){
		var id="tbIntegrateInfo."+columns[i].name;
		var value=document.getElementById(id).value;
		//alert(id+","+value);
		items[id]=value;
	}
	//alert(fastDev.Util.JsonUtil.parseString(items));
	
	var type=request['type'];
	if(type=='search'){
		grid.refreshData(items);
		return;
	}
	
	var url="jobObjects_updateObject.action";
	if(type=='add'){
		url="jobObjects_addObject.action";
	}
	///alert(url);
	
	fastDev.post(url,{
		success:function(data){
			fastDev.tips(data);
			grid.refreshData();
			win.close();
		},
		data:items
	});
}

function resetForm(){
	document.getElementById("objectDetails").reset();
	/*	if(request['type']=='add'){		
		return;
	}
	if(formData==null||formData==''){
		return;
	}
	for(var key in formData){
		
		if(key=='cOrgid'){
			//alert(formData[key]+"****");
			fastDev.getInstance("tbIntegrateInfo."+key).setValue(formData[key]);
			continue;
		}
		var el=document.getElementById("tbIntegrateInfo."+key);
		if(el!=null){
			el.value=formData[key];
		}
	}*/
	
}




fastDev(function() {
	    
	    cBasedataId= request['cBasedataId'];
		cTabletypeId=request['cTabletypeId'];
		var type=request['type'];
		if(type!='search'){
			document.getElementById('orgCell').innerHTML="<font color='red'>*</font>所属组织";
		}
		//alert(cBasedataId+","+cTabletypeId+","+type);
		var url="jobObjects_getDynamicColums.action";
		
		var td="";
		fastDev.post(url,{
			success:function(data){
				if(data==null ||data==undefined ||data==''){
					return;
				}
				columns=data;
				writeTable();
				if(type!='add'&&type!='search'){
					fastDev.post("jobObjects_getObjectInfo.action",{
						success:function(data){
							var dat=data.data[0];
							formData=dat;
							//alert(fastDev.Util.JsonUtil.parseString(dat));
							for(var key in dat){
								
								if(key=='cOrgid'){
									if(key=='cOrgid'){
										getOrgname(dat[key]);
										fastDev.getInstance("cOrgid").setValue(dat[key]);
										continue;
									}
									fastDev.getInstance(key).setValue(dat[key]);
									continue;
								}
								var el=document.getElementById("tbIntegrateInfo."+key);
								if(el!=null){
									el.value=dat[key];
								}
							}
						},
						data:{"cBasedataId":cBasedataId,
							  "cTabletypeId":cTabletypeId
							  }
					});
				}
			},
			data:{"cTabletypeId":cTabletypeId}
		});
		
});



function writeTable(){
	var table=document.getElementById("tb");
	var tr,td1,td2,textNode,input,div;
	var j=0;
	for(var i=0;i<columns.length;i++){
		if(i%2==0){
			tr=document.createElement("tr");
		}
		    td1=document.createElement('td');
		    td1.setAttribute("class", "ui-form-table-dt");
		    textNode=document.createTextNode(columns[i].text);
		    td1.appendChild(textNode);
		    td2=document.createElement('td');
		    td2.setAttribute("class", "ui-form-table-dd");
		    div=document.createElement("div");
		    div.setAttribute("class", "ui-form-wrap ui-input");
		    
		    
		    input=document.createElement('input');
		    input.setAttribute('id', "tbIntegrateInfo."+columns[i].name);
		    input.setAttribute('type', 'text');
		    input.setAttribute("class", "ui-form-field ui-form-input");
		    div.appendChild(input);
		    td2.appendChild(div);
		    tr.appendChild(td1);
		    tr.appendChild(td2);
		    
		j++;
		if(j==2){
			
			table.appendChild(tr);
			j=0;
		}
	}
	if(j!=0){
		td2.setAttribute("colspan", 3);
		table.appendChild(tr);
	}
	
}


function getOrgname(orgid){
	fastDev.post("jobObjects_getOrgname.action",{
		success:function(data){
			data=fastDev.Util.JsonUtil.parseString(data);
			if(data=='{}'){
				data="";
			}
			fastDev.getInstance("orgname").setValue(data);
		},
		data:{
			"cOrgid":orgid
		}
	});
}

function setOrgid(value){
	//alert(value);
	var orgid=fastDev.getInstance('cOrgid');
	orgid.setValue(value);
	
	document.getElementById("orgnameDiv").style.display="block";
	var orgname=fastDev.getInstance("org").getText();
	
	fastDev.getInstance('orgname').setValue(orgname);
	document.getElementById("orgDiv").style.display="none";
}

function editOrgid(){
	var orgid=fastDev.getInstance('cOrgid').getValue();
	var type=request['type'];

	if(orgid==null || orgid==''){
		if(request['type']!='add'&&type!='search'){
			//alert(request['type']);
			fastDev.tips("正在加载中，请稍等！");
			return;
		}
	}
	fastDev.getInstance('org').setValue(orgid);
	document.getElementById("orgnameDiv").style.display="none";
	document.getElementById("orgDiv").style.display="block";
	
}


