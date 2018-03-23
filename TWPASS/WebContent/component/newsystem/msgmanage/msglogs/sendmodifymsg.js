function getBit(str){//获得字符长度,一个中文占2位
	return str.replace(/\n/g,'..').replace(/[^\x00-\xff]/g, '..').length;
}
var request = talkweb.ControlBus.getRequest();
var dialog = "";
$(function(){
	// 提交前设置发送时间,发送方式,发送时间名称,如为周期发送还要设置发送频率和频率名称
	var setHiddenj = function() {
		if ($("#sendFun").val() == 2) {
			$("#viewObj").val("$" + $("#viewObj").val());
		}
		$("#starttime").val("");
		// 设置通知方式
		var a = 0;
		$("input[type='checkbox']:checked").each(function(i) {
			a += parseInt($(this).val());
		});
		$("#notifytype").val(a);

		// 设置信息发送时间
		var flg = $("input[type='radio']:checked").val();
		if (flg == 1) {// 定时发送
			$("#starttime").val(
				$("#year").val() + "-" + $("#month").val()
				+ "-" + $("#day").val() + " "
				+ $("#hour").val() + ":"
				+ $("#minutes").val());
				//$("#sendtype").val("1")
		}
		if (flg == 2) {// 周期发送
			var a = $("#hour1").val() + ":" + $("#minutes1").val();

			var frequency = $("#frequency").val();

			switch (frequency) {
				case '1':// 每天,不需要扩展时间显示
					a="2011-01-01 "+a
					break;
				case '2':// 每周
					var week=$("#ext_weekNum").val();
					var date=new Date();
					var step=week-date.getDay()
					var newdate=new Date(date.getFullYear(),date.getMonth(),date.getDate()+step);
					var m=newdate.getMonth()+1
					var d=newdate.getDate();
					a=newdate.getFullYear()+"-"+(m<10?"0"+m:m)+"-"+(d<10?"0"+d:d)+" "+a
					break;
				case '3':// 每月
					a="2011-01-"+$("#ext_day").val()+" "+a
					break;
				case '4':// 每年
					a="2011-"+$("#ext_month").val()+"-"+$("#ext_day").val()+" "+a
					break;
			}
						
			$("#starttime").val(a);
						//$("#sendtype").val("2")
		}
	}
	
	// 事件
	// 周期发送频率变化
	// <!-- 周期年4,月3,日1,周2 -->
	$("#frequency").change(function() {
		var frequency = $(this).val();
		switch (frequency) {
			case '1':// 每天,不需要扩展时间显示
				$("#extPan").html("");
				break;
			case '2':// 每周
				$("#extPan").html($("#weekObj").val());
				break;
			case '3':// 每月
				$("#extPan").html($("#dayObj").val());
				break;
			case '4':// 每年
				$("#extPan").html($("#monthObj").val() + $("#dayObj").val());
				break;
		}
	});

	// 单选按钮事件
	$("input[type='radio']").click(
		function() {
			$("input[type='radio']").each(
				function(i) {
					if (this.checked) {
						$("#SPAN_" + $(this).val()+ " select").attr("disabled", false);
					} else {
						$("#SPAN_" + $(this).val()+ " select").attr("disabled", true);
					}
				});
		}
	);
				
	$("#msgType").change(
		function() {
			if ($(this).val() == '')
				return;
        $("#template").empty();
        //  $("#content").val("");
		$.post("msg_getSendMsgTemplate.action",
			{"MSGTYPE" : $(this).val()},
			function(result) {
				$("#template").html(result);
                $("#template").val($("#templateidHid").val());
			});
		}
	);
	// 模版选择事件
	$("#template").change(
		function() {
			if ($("#template").val() == "") {
				return;
			}
			$.post("$domain/system/msgmanage/sendmsg/msg_getMsgTemplateInfo.action",
				{"TEMPLATEID" : $("#template").val()},
				function(result) {
					$("#content").val(eval("("+ result+ ")").content);
				}
			);
		}
	);
	
	/** 按钮点击事件*********** */
	//选择用户组	
	$("#selOne").unbind("click").click(
		function(){
			$("#sendFun").val("2");
			$("#viewObj").val("");
			$("#sendObj").val("");
			showall();
    });
	// 表单提交
	$("#submintBt").click(function() {
		var form = $("#msgform");
		var tishi="";
		var flag = true;
		if($("#msgTitle").val() ===""){
			tishi +='消息主题不能为空！';
			flag = false;
		}
		if(getBit($("#msgTitle").val()) > 100){
			tishi += '消息主题不能超过50个字!';
			flag = false;
		}
		if($("#sendObj").val() ===""){
			tishi +='发送对象不能为空！';
			flag = false;
		}
		if($("#msgType").val() ===""){
			tishi +='消息类型不能为空！';
			flag = false;
		}
		var contlen=getBit($.trim($("#content").val()));
        if (contlen == 0) {
            tishi +="消息内容不能为空!!";
            flag = false;
        }
        if (contlen > 4000) {
           tishi +="消息内容不能超过2000个字!";
           flag = false;
        }
        if(flag){
        	try{
        	if(confirm('您确定要立即发送消息吗?')){
        		setHiddenj();
        		var data = "{";
        		data +="'sendFun':'" + $("#sendFun").val() +"',";
        		data +="'notifytype':'" + $("#notifytype").val() +"',";
        		data +="'starttime':'" + $("#starttime").val() +"',";
        		data +="'servertime':'" + $("#servertime").val() +"',";
        		data +="'title':'" + $("#msgTitle").val() +"',";
        		data +="'receiver':'" + $("#sendObj").val() +"',";
        		data +="'msgtype':'" + $("#msgType").val() +"',";
        		data +="'templateid':'" + $("#template").val() +"',";
        		data +="'sendtype':'" + $('input:radio[name="sendtype"]:checked').val() +"',";
        		data +="'content':'" + $("#content").val() +"'}";
        		$("#data").val(data);
				$.post("msg_sendMsg.action?data=" + data+"&content="+$("#content").val(),{},
					function(msg){
					    var ob = eval("("+msg+")");
						alert(ob.msg);
						window.parent.dialog.close();
					}
				);
				
			}
			}catch(err){
				window.parent.dialog.close();
			}
		} else {
			alert(tishi);
		}
	});
	
    $("#closeBtn").click(function() {
          window.parent.dialog.close();
//        parent.win.closeAndFresh();
	});

				// 根据年月确定该月天数
	var chargDay = function(year, month) {
		var dayNum = new Date(year, month, 0).getDate();
		$("#day>option:gt(27)").remove("#day>option");
		for ( var i = 29; i <= dayNum; i++) {
			$("#day").append("<option value=\"" + (i) + "\">" + (i)+ "</option>")
		}
	}
	
	// 周期发送时间初始化
	var init = function() {		
		$(":radio").get(0).checked=true;
		$("#SPAN_1>select").attr("disabled", true);
		$("#SPAN_2>select").attr("disabled", true);
		$("#msgform")[0].reset();	    
        $("#month").change(function(){
            chargDay($("#year").val(), $(this).val());
        });
        $("#year").change(function(){
        	chargDay($(this).val(),$("#month").val());
        });
                    
        $.post("msg_getServerTime.action",{},function(msg){
        	var date=msg.split("-");
            var year = parseInt(date[0]);
            var month = date[1];
            var day = date[2];
            var hour =date[3];
            var minutes =date[4];
                    
            for ( var i = 0; i < 4; i++) {
                $("#year").append(
                      "<option value=\"" + (year + i) + "\">"
                      + (year + i) + "</option>")
            }
            chargDay(year, month)
            $("#month").val(month);
            $("#day").val(day);
            $("#hour").val(hour);
            var m = (parseInt(minutes / 5) + 1) * 5;
            $("#minutes").val((m > 59 ? (hour + 1) : m));
                      
		});
					// 初始化通知方式
		$.post("msg_getNotifyType.action",{}, function(result) {$("#sendTypeTd").html(result)});
		$("#sendFun").val("1");// 通过选择用户发送
		$("#selOne").val("选择接收人").unbind("click").click(function() {
			$('#dialogDiv1').dialog('open');
		});
		// 初始化消息类型
        $.post("msg_initMesType.action", {}, function(result) {$("#msgType").html(result)});
        // 初始化发送周期
        $.post("msg_initMsgFre.action", {}, function(result) {$("#frequency").html(result)});
        //获取修改对象
        $.post("msg_getMsgData.action?logId="+request["logId"],{}, function(result) {
			var msginfo = eval("(" + result+")");
			$("#viewObj").val(msginfo.viewObj);
			$("#sendTypesHid").val(msginfo.sendTypesHid);
			$("#templateidHid").val(msginfo.templateidHid);
			$("#notifytypename").val(msginfo.notifytypename);
			$("#notifytype").val(msginfo.notifytype);
			$("#sendObj").val(msginfo.receiver);
			$("#statusname").html(msginfo.statusname);
			$("#msgTitle").val(msginfo.title);
			$("#msgType").val(msginfo.msgtype);
			$("#template").val(msginfo.templateid);
			$("#content").val(msginfo.content);
			$("#verifyid").val(msginfo.verifyid);
			$("#msgid").val(msginfo.msgid);
			$("#frequencyname").val(msginfo.frequencyname);
			$("#frequencyTime").val(msginfo.frequencyTime);
			$("#starttime").val(msginfo.starttime);
			
			var msginfo=$("#content").val();
            $("#msgType").change();
            $("#content").val(msginfo);
            var sendtypes= $("#sendTypesHid").val();
            $.each($(":checkbox"),function(i,item){
            	if(sendtypes.indexOf($(this).val())!=-1){
                	this.checked=true;
             	}
            }); 
            $("#SPAN_" + $(":radio:checked").val()+ " select").attr("disabled", false);
            //周期控件值设置
            $("#frequency").change();
            var a=$("#frequencyTime").val();
            if(a!=""){
            	var obj=eval("({"+a+"})");
                if(obj){
                   	$.each(obj,function(i,item){
                     	$("#"+i).val(item);
                    })
                    }
            }
            $("#frequencyTime").val("");
            var viewObj= $("#viewObj").val();
            var sendObj= $("#sendObj").val();
            if($("#sendObj").val().indexOf("$")!=-1){
            	$("#all").click();
                $("#viewObj").val(viewObj)
                $("#sendObj").val(sendObj);
            }
		});
	}
	init();
	
});