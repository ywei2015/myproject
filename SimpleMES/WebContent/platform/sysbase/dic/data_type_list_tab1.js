

jQuery(function($) {
	//SF.FormBody.onload(formbodyConfig);
	//$("#btn_FormBody_cancel").hide();
	//tab1 初始化。
  	var structId=$.fn.geturl("rightIframe","structId");
	var keyId=$.fn.geturl("rightIframe","keyId");
	$.post("../../../dynamic/ObjStructure/dic_ref_detail",{id:keyId,structId:structId},function(result){
		 $.fn.setForm($("#Formbody"),result); 
		 $("#btnIcon").change();
		 //iconShow();
	});

	$("#btn-save").bind("click",function(){
		 var objs =$("#Formbody"); //SF.getSearchParams();//var objs = SF.getFormValues(); //SF.getSearchParams();
			//保存之前做校验
	  	 var flag=false;
	  	 $.ajax({
			        type: "POST",
			        dataType: "json",
			        url: "../../../dynamic/Dic/validCodeAndName",
			        async: false,//同步  
			        data:$("#Formbody").serialize(),
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
				if(flag){
					return;
				}
     	
     	var result=forceAjaxSynchro("../../../dynamic/ObjStructure/saveDic",objs,function(){
    	});
		BootstrapDialog.show({
            title:constant.TIP,
            message: result.responseJSON.message,
            buttons: [{
                label: constant.CONFIRM,
                action: function(dialog) {
                	dialog.close();
                	return false;
                }
            }]
        });
     	 return false;
	});
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
        var activeTab = $(e.target).text();
        //alert(activeTab);
        // 获取前一个激活的标签页的名称
        var previousTab = $(e.relatedTarget).text();
        $(".active-tab span").html(activeTab);
        $(".previous-tab span").html(previousTab);
    });
    $('#myTab li a').click(function(){
    	setTimeout(function(){
    		initFormCss();  
    	}, 200);
	}); 
  
	}); 
  