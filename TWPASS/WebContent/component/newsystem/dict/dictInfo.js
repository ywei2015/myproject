var request = fastDev.Browser.getRequest();//获取请求对象
var dictid = request['dictid'];
var dicttypeid = request['dicttypeid'];
function initTemplate(data){
	var source = document.getElementById('tpl').innerHTML;
	template = fastDev.create("Template", {
		htmlList : source,
		params : data
	});
	var dom = template.createStaticHtml();
	fastDev("body").append(dom);
}


fastDev(function(){
	fastDev.create("Proxy", {
		url : "dict_getDictEntry.action",
		urlParam : {
			dictid : dictid,
			dicttypeid : dicttypeid
		}
	}).load(initTemplate);
});