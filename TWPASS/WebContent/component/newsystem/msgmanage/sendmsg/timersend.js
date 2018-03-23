$.use('jqtree', function($) {

	$("#ok1").click(function() {
		var parm = $("#year").val()+"-"+$("#month").val()+"-"+$("#day").val()+" ";
		parm+=$("#hour").val()+":"+$("#minutes").val();
		window.parent.timerWin.closeAndSubmit(parm);
		});
	var getLStr = function(hour) {
		if (hour >= 0 && hour < 6)
			return "凌晨";
		if (hour >= 6 && hour < 12)
			return "上午";
		if (hour >= 12 && hour < 14)
			return "中午";
		if (hour >= 14 && hour < 18)
			return "下午";
		if (hour >= 18 && hour <= 23)
			return "晚上";
	}
	var chargDay = function(year, month) {
		var dayNum = new Date(year, month, 0).getDate();
		$("#day>option:gt(27)").remove("#day>option");
		for ( var i = 29; i <= dayNum; i++) {
			$("#day").append(
					"<option value=\"" + (i) + "\">" + (i) + "</option>")
		}
	}
	$("select").change(function() {
		if (this.id == "month" || this.id == "year") {
			chargDay($("#year").val(), $("#month").val());
		}
		var s = "";
		$("select").each(function(i) {
			if (i > 0) {
				if (this.id == "hour") {
					s += getLStr(this.value);
				}
				s += $(this).val() + $(this).attr("dd");

			}

		});
		$("#info").html("本消息将在 " + s + "投递给对方!");
	});
	var init = function() {
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		var hour=date.getHours();
		var minutes = date.getMinutes();
		for ( var i = 0; i < 4; i++) {
			$("#year").append(
					"<option value=\"" + (year + i) + "\">" + (year + i)
							+ "</option>")
		}
		chargDay(year, month)
		$("#month").val(month);
		$("#day").val(day);
		$("#hour").val(hour);
		$("#minutes").val((parseInt(minutes / 5) + 1) * 5);
		$("#day").change();
	}
	init();

});