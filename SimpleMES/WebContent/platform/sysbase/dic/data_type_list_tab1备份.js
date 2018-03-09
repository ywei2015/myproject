 var formbodyConfig = {
 	name  : "Formbody",   
     type  : "FormBody", //id
     title : "基础数据维护",
     berth : "detailForm",
     items : [
         { 
         	type:"textinput",
             title:"编号",
             dataIndex:'code'
         },
         { 
         	type:"textinput",
             title:"名称",
             dataIndex:'name'
         },
         { 
         	type:"textinput",
             title:"全名称",
             dataIndex:'fullName'
         },
         { 
         	type:"numberinput",
             title:"排序值",
             dataIndex:'num1'
         },
         { 
          	type:"numberinput",
              title:"关系排序",
              dataIndex:'trdOrder'
          }, { 
           	type:"textinput",
            title:"网页地址",
            dataIndex:'value2'
        },
        { 
           	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        },
        
        {
			type:"combox",   
			title:"是否有效",
			dataIndex: 'sysFlag',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'有效'},{'code':0,'name':'无效'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
		}
     ],
     layoutConfig : {
         columns : 2
     },
     hidefields:['typeRefDicId','objEntityRefId','parentId','type','id','typeChild'],
     onsubmit: function(){ 
    	 var objs =$("#Formbody"); //SF.getSearchParams();//var objs = SF.getFormValues(); //SF.getSearchParams();
     	 forceAjax("../../../dynamic/ObjStructure/saveDic",objs,function(result){
     		alert(result.message);
     		return false;
		});	
     	 return false;
     }
 };


jQuery(function($) {
	SF.FormBody.onload(formbodyConfig);
	$("#btn_FormBody_cancel").hide();
	//tab1 初始化。
  	var structId=$.fn.geturl("rightIframe","structId");
	var keyId=$.fn.geturl("rightIframe","keyId");
	$.post("../../../dynamic/ObjStructure/dic_ref_detail",{id:keyId,structId:structId},function(result){
		SF.BindFormData(result);
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
    		initFormCss()
    	},200);
	});
  
	}); 
  