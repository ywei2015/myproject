$(document).ready(function() {
	$("#ok1").click(function() {
			var time=$("#hour").val()+":"+$("#minutes").val();
			window.parent.loopWin.closeAndSubmit($("#loopType").val(), time);
		});
	//下拉列表CHANGE事件,拼装提示信息
	$("select").change(function() {
		var s = "";
		$("select").each(function(i) {
			if (i > 0) {
				if (this.id == "hour") {
					s += getLStr(this.value);
				}
				s += $(this).val() + $(this).attr("dd");
			}
			if(i==0){
				s+=$("#loopType>option:selected").text();
			}
		});
		$("#info").html("本消息将在 " + s + "投递给对方!");
	});
	$("#hour").change();//调用change事件,初始化提示消息
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