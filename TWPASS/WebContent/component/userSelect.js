function selectuser() {
	var select = fastDev.getInstance("userselect");
	var value = select.getValue();
	if(value=="" || value==null){
		fastDev.alert('请选择一个账户','信息提示','warning');
	} else {
		fastDev.create('Proxy', {
			action: "portal_userSelectLongin.action"
		}).save({
			userid: value
		}, function(result) {
			if(result.res == '1') {
				top.location.href = 'index.jsp';
			} else {
				fastDev.alert('账号错误', '信息提示', 'error');
			}
		});
	}
	
}