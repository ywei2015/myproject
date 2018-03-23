function afterInitRender() {
	showResourceModes("DJDT");
	var map = document.getElementById("map");
	map.style.width = (window.innerWidth - 200) * 0.99 + 'px';
	map.style.height = (window.innerHeight) * 0.99 + 'px';
	map.src = "../../mobile/map/map.html?cObjectTypeid=-1";
}

function onNodeClick(event, id, value) {
	document.getElementById("map").src = "../../mobile/map/map.html?cObjectTypeid=" + id;
}
   