  $(document).ready(function () {          
      $("#search").bind("click", function(){
    	  self.location='workorderinput.html?batch='+item.matbatch;
      });  
  });  
  

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
}