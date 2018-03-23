function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#jobTab').attr('height', (height-4)+'px');
}