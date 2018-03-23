function QRCodePrint(infocode)
{
	
	var TSCObj;
	try {
		TSCObj = new ActiveXObject("TSCActiveX.TSCLIB");
		alert("已检测到TSCActiveX插件。");
	}
	catch (e) {
		alert("未检测到TSCActiveX插件，请安装。");
		return false;
	}
	//TSCObj.ActiveXabout();
	TSCObj.ActiveXopenport ("Bar Code Printer T-4403E #2");
	TSCObj.ActiveXsendcommand ("SET TEAR ON");
	TSCObj.ActiveXclearbuffer();
	TSCObj.ActiveXsendcommand ("DENSITY 12");
	TSCObj.ActiveXsendcommand ("SIZE 40 mm,30 mm");
	TSCObj.ActiveXsendcommand ("GAP 3 mm,0 mm"); 
	TSCObj.ActiveXsendcommand ("REFERENCE 5 mm,2 mm"); 
	TSCObj.ActiveXsendcommand ("QRCODE 100,50,L,12,M,0,M2,S7,\"A"+infocode+"\""); 
	TSCObj.ActiveXprinterfont("40", "10", "3", "0", "1", "1", "NO:"+infocode);
	TSCObj.ActiveXprintlabel ("1","1");
	TSCObj.ActiveXcloseport();
}