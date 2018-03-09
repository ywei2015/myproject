//定义全局提示

var constant = {
	LOWEST_CHOICE_ONE:"请选择一条记录！",
	CONFIRM_DELETE:'您确定要删除吗！',
	CONRIRM_DELETE_ROLE:'删除角色会清空该角色已分配人员，确认要删除所选择的数据？',
	ONLY_CHOICE_ONE:"请选择一行数据",
	EXCEED_ONE_DATA:"超过一行数据请重新选择",
	CONFIRM_DEL:'是否进行操作！',
	DATE_FORMAT_EIGHT:"yyyy-MM-dd",
	SUCCESS_MSG:"操作成功！",
	CONFIRM:"确认",
	TIP:"提示",
	CANCEL:"取消"
};
//JSON.stringify(constant);
function forceAjax(url,obj){
	$.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: obj.serialize(),
        success: function (result) {
        	
           	if(result.status==200){
           		layer.alert(result.message);
           	}
        	
        },
        error: function(data) {
         }
    });
    
}
function forceAjax(url,obj,func){
	$.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: obj.serialize(),
        success: function (result) {
           	if(result.status==200){
           		alert(result.message);
           		func();
           	}
        },
        error: function(data) {
         }
    });
    
}
//同步请求
function forceAjaxSynchro(url,obj,func){
	var data=null;
	if($.fn.isNotBlank(obj)){
		data=obj.serialize();
		console.info(data);
	}
	var result=$.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        async: false,//同步  
        data:data ,
        success: function (result) {
           	if(result.status==200){
           	}
        },
        error: function(data) {
         }
    });
	return result;
    
}
function isBlank(str){
	if (str == null || str == undefined  || str == '') { 
				return true;
		}else{
			return false;
		}
}
	(function($){ 
	//参考文献 ：http://www.cnblogs.com/cangowu/p/5124042.html
	/**
	 * 将josn对象赋值给form
	 * @param {dom} 指定的选择器
	 * @param {obj} 需要给form赋值的json对象
	 * @method serializeJson
	 * */
		$.fn.setForm=function( obj,jsonValue){
		  
		   
		    $.each(jsonValue,function(name,ival){
		        var $oinput = obj.find("input[name='"+name+"']");
				var select =obj.find("select[name='"+name+"']");
				var textarea =obj.find("textarea[name='"+name+"']");
		        if($oinput.attr("type")=="checkbox"){
		            if(ival !== null){
		                var checkboxObj = $("[name='+name+']");
		                var checkArray = ival.split(";");
		                for(var i=0;i<checkboxObj.length;i++){
		                    for(var j=0;j<checkArray.length;j++){
		                        if(checkboxObj[i].value == checkArray[j]){
		                            checkboxObj[i].click();
		                        }
		                    }
		                }
		            }
		        }
		        else if($oinput.attr("type")=="radio"){
		            $oinput.each(function(){
		                var radioObj = $("[name='+name+']");
		                for(var i=0;i<radioObj.length;i++){
		                    if(radioObj[i].value == ival){
		                        radioObj[i].click();
		                    }
		                }
		            });
		        }
		        else if($oinput.attr("type")=="text"){
		           	$oinput.val(ival);
		        }
		         else if($oinput.attr("type")=="hidden"){
		           	$oinput.val(ival);
		        }
		           else if($oinput.attr("type")=="number"){
		           	$oinput.val(ival);
		        }
		        else if($oinput.attr("type")=="textarea"){
		            obj.find("[name='+name+']").html(ival);
		        }else if(select.length!=0){
		        	// $(".selector").find("option[text='pxx']").attr("selected",true);
		        	select.find("option").prop("selected",false);
		        	select.find("option[value='"+ival+"']").prop("selected",true);
		        }
		        else if(textarea.length!=0){
		        	// $(".selector").find("option[text='pxx']").attr("selected",true);
		        	
		        	textarea.val(ival);
		        }
		        else{
		            obj.find("[name='+name+']").val(ival);
		        }
		    })
	};
	$.fn.isNotBlank=function(str){
		if (str != null && str != undefined  && str != '') { 
					return true;
			}else{
				return false;
			}
	}
	/**
	 * 判断一个参数是否为空，为空为true
	 * @param {Object} str
	 */
	$.fn.isBlank=function(str){
		if (str == null || str == undefined  || str == '') { 
					return true;
			}else{
				return false;
			}
	}
	//获取连iframe链接上的参数值，id为iframe，name为变量名
	$.fn.geturl=function(id,name) {
            var reg = new RegExp("[^\?&]?" + encodeURI(name) + "=[^&]+");
            var arr = window.parent.document.getElementById(id).contentWindow.location.search.match(reg);
            if (arr != null) {
                return decodeURI(arr[0].substring(arr[0].search("=") + 1));
            }
            return "";
        }
	/**
	 * 删除数组指定元素
	 * @param arr
	 * @param val
	 * @returns
	 */
	namespace('tw.array.removeByValue');
	$.tw.array.removeByValue=function(arr, val) {
		  for(var i=0; i<arr.length; i++) {
		    if(arr[i] == val) {
		      arr.splice(i, 1);
		      break;
		    }
		  }
		}
	/**
	 * layer扩展
	 */
	namespace('zw.layer.setWindow');
	$.zw.layer.setWindow=function(obj,func){
		/*	var target=obj.parent('.layui-layer-content').next('.layui-layer-setwin').find('.layui-layer-max');
			if(target.length!=1){
				layer.alert('$.layer.setWindow方法操作错误');
				return false;
			}
			target.bind("mouseup",function(){
				 t=window.setInterval(func,150);
			});
		*/
	}

        
})($);
 
	/**
	 * js命名空间,调用时，必须以$符号开头
	 * @param namespaceString
	 */
	function namespace(namespaceString){  
	    var temp = [];//声明了一个空的数组  
	    var array = namespaceString.split(".");  
	    for(var i=0;i<array.length;i++){  
	        temp.push(array[i]);  
	        /**  
	         * 把多个json对象添加了window上  
	         */  
	        eval("window."+temp.join(".")+"={}"); 
	        eval("$."+temp.join(".")+"={}");  
	    }  
	}
	/**
	 * 采用正则表达式获取地址栏参数
	 * @param name
	 * @returns
	 */
	function getQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
	/**
	 * 日期转换 yyyy-mm-dd hh:ii:ss
	 * @param nows
	 * @returns
	 */
	function formatDate(nows) { 
		var now=new Date(nows); 
		var year=now.getFullYear(); 
		var month=now.getMonth()+1;
		if(month>0 && month<10){
			month='0'+month;
		}
		var date=now.getDate(); 
		if(date>=0 && date<10){
			date='0'+date;
		}
		var hour=now.getHours(); 
		if(hour>=0 && hour<10){
			hour='0'+hour;
		}
		var minute=now.getMinutes(); 
		if(minute>=0 && minute<10){
			minute='0'+minute;
		}
		var second=now.getSeconds(); 
		if(second>=0 && second<10){
			second='0'+second;
		}
		return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;  
		} 
	
	/*********************依赖bootstrap 工具方法*****************************/
	//字段校验
	function validField(url,data){
		//保存之前做校验
	   	 var flag=false;
	   	 $.ajax({
			        type: "POST",
			        dataType: "json",
			        url: url,
			        async: false,//同步  
			        data:data,
			        success: function (result) {
			           	if(result.status!=200){
			           		BootstrapDialog.show({
		        	            title: constant.TIP,
		        	            message:result.message,
		        	            buttons: [{
		        	                label: constant.CONFIRM,
		        	                action: function(dialog) {
		        	                	dialog.close();
		        	                	return false;
		        	                }
		        	            }]
		        	        });
			           		flag=true;
			           	}
			           
			        },
			    });
		return flag;
	}
	
	