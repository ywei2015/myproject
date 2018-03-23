fastDev(function(){
	setCurrentTime();
});

function setCurrentTime(){
	var date=new Date();
	var str=date.getFullYear()+"-";
	var a=[];
	a[0]=(date.getMonth()+1);
	a[1]=date.getDay();
	a[2]=date.getHours();
	a[3]=date.getMinutes();
	a[4]=date.getSeconds();
	for(var i=0;i<5;i++){
		if(a[i]<10){
			a[i]="0"+a[i];
		}
	}
	str=str+a[0]+"-"+a[1]+"  "+a[2]+":"+a[3]+":"+a[4];
	fastDev.getInstance('cHandleTime').setValue(str);
}

function getTextBoxString(textBox){
	var a=[];
	var b=[" type="," readOnly="," width="," id="," saveInstance="];
	a[0]=textBox.type;
	a[1]=textBox.readOnly;
	a[2]=textBox.width;
	a[3]=textBox.id;
	a[4]=textBox.saveInstance;

	var el="<div itype='TextBox' ";
	for(var i=0;i<5;i++){
		if(a[i]!=null&&a[i]!=undefined&&a[i]!=''){
			el+=b[i]+"'"+a[i]+"' ";
		}
	}
	el+="></div>";
	return el;
}

function writeTable(){
	document.write("<table class='ui-form-table'>");
	
	document.write("</table>");
}