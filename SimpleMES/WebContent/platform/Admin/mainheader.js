

/*    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
      <i class="fa fa-bell-o"></i>
      <span class="label label-warning">10</span>
    </a>
    <ul class="dropdown-menu">
      <li class="header">You have 10 notifications</li>
      <li>
        <!-- inner menu: contains the actual data -->
        <ul class="menu">
          <li>
            <a href="#">
              <i class="fa fa-users text-aqua"></i> 5 new members joined today
            </a>
          </li>
          <li>
            <a href="#">
              <i class="fa fa-users text-red"></i> 5 new members joined
            </a>
          </li>
        </ul>
      </li>
      <li class="footer"><a href="#">View all</a></li>
      </ul>
				url:"./notifypage/msg.html?id=23455",
      */


function setNotifications(msgdata){   
	var notifyheader = '<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell-o fa-lg"></i><span class="label label-warning">'
				+ msgdata.total + '</span></a>' +
				'<ul class="dropdown-menu"><li class="header">您有 '+msgdata.total+' 条未读消息。</li>';
	var notifylist = '';
	for(var i=0;i<msgdata.rows.length;i++){//fa fa-info-circle
		notifylist +=  '<li><a onclick=detail("'+msgdata.rows[i].pid+'")><i class="fa fa-comment-o text-aqua"></i>'+msgdata.rows[i].title+'</a></li>';
	}
	var notifyfooter = '<li class="footer"><a href="javaScript:void(0)" onclick="addTab()">查看所有消息</a></li></ul>'; 
	var contenthtml = notifyheader + notifylist + notifyfooter 
	$("#ames_sysNotifyMenu").html(contenthtml);
}
function detail(pid){
	$.post("../../../dynamic/message/updateStatus",{pid:pid},function(result){
		//location.reload();
		var win = SF.showModalDlg("../business/msg/detailMsg.html?id="+pid,"update",result,800,400,function(){}); 
	});
}
function addTab(){
	addTabs(({
		id : 'msg',
		title : '消息',
		close : true,
		url : '../business/msg/message.html'
	}));
	
}
function setHelpMenu(){   
	var sheader = '<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-phone fa-lg"></i><span class="label label-warning"></span></a>' + 
				'<ul class="dropdown-menu"><li class="header">欢迎您来咨询,联系方式如下：</li>';
	var slist = '<li><i class="fa fa-handshake-o text-aqua"></i>  Tel：0731-88799888</li>'; 
	 slist += '<li><i class="fa fa-handshake-o text-aqua"></i>  FAX：0731-88799866</li>'; 
	var sfooter = '<li class="footer"><a href="http://www.talkweb.com.cn/" target="_blank">拓维信息欢迎您！</a></li></ul>'; 
	var contenthtml = sheader + slist + sfooter 
	$("#ames_sysHelpMenu").html(contenthtml);
}

//var rmcount = 1;
function refreshMsgCount(){ 
	//console.info("------"+ (rmcount++) );
	$.post("../../dynamic/message/list",{status:10,limit:1000},function(data){
		setNotifications(data);
		setHelpMenu();
	}); 
}

$(function() {
	refreshMsgCount();
	setInterval(refreshMsgCount,5000); 
});

