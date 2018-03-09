
var formbodyConfig1 = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "维修单申请",
    berth : "detailForm1",
    items : [
        { 
        	type:"combox",   
			title:"报修类型",
			dataIndex: 'repairType',
			fieldText: 'name',
			fieldValue: 'pid',
			disabled: true,
			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
			      //,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
			    url: "../../../../dynamic/dicView/listDic?view=v_equ_repairapplytype",
				root: "dataset.data"
        },
        { 
        	type:"combox",   
			title:"设备名称",
			dataIndex: 'equId',
			fieldText: 'name',
			fieldValue: 'pid',
			disabled: true,
			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
			    //  ,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
			url: "../../../../dynamic/Repair/findEqu",
			root: "dataset.data"
            
        }
        ,{ 
        	type:"textinput",
            title:"报修申请人",
            disabled: true,
            dataIndex:'applyUsername'
        }
        ,{
			type:"datetime",
		    title:"报修申请时间",
		    dataIndex:'createTime',
		    disabled: true,
		    config:{
		        format: 'yyyy-mm-dd',
		    	minView:'month',
				initialDate: new Date()
			}
		}
        ,{
			type:"datetime",
		    title:"发生时间",
		    dataIndex:'occurTime',
		    disabled: true,
		    config:{
		    	format: 'yyyy-mm-dd hh:ii:ss',
		        //showSecond: true,
		    	autoclose:true,
		    	minView: 0 ,
		    	minuteStep:1,
				initialDate: new Date()
			}
		}
//		,{ 
//        	type:"combox",
//            title:"指定维修人",
//            dataIndex: 'repairUserid',
//			fieldText: 'name',
//			fieldValue: 'pid',
//			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
//			      //,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
//			    url: "../../../../dynamic/dicView/listDic?view=v_sec_user",
//				root: "dataset.data"
//        }
		,{ 
        	type:"textarea",
            title:"异常描述",
            dataIndex:'abnormalDes',
            disabled: true,
            rowspan: 5,
            colspan: 13
        }
//        ,{ 
//        	type:"textarea",
//            title:"备注",
//            dataIndex:'remark',  
//            rowspan: 5
//        }
    ],
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){}
};

var formbodyConfig2 = {
		name  : "Formbody",   
	    type  : "FormBody", 
	    title : "设备维修记录",
	    berth : "detailForm2",
	    items : [
	    	{
				type:"combox",   
				title:"故障类型",
				dataIndex: 'abnormalType',
				disabled: true,
				fieldText:"name",
				fieldValue:"pid",
				//data:[{'code':'1','name':'机械'},{'code':'0','name':'电器'}],
				url: "../../../../dynamic/dicView/listDic?view=v_equ_faulttype",
				root: "dataset.data"
			},{
				type:"datetime",
			    title:"维修开始时间",
			    dataIndex:'repairSt',
			    disabled: true,
			    config:{
			    	format: 'yyyy-mm-dd hh:ii:ss',
			        //showSecond: true,
			    	autoclose:true,
			    	minView: 0 ,
			    	minuteStep:1,
					initialDate: new Date()
				}
			},{
				type:"datetime",
			    title:"维修结束时间",
			    dataIndex:'repairEt',
			    disabled: true,
			    config:{
			    	format: 'yyyy-mm-dd hh:ii:ss',
			        //showSecond: true,
			    	autoclose:true,
			    	minView: 0 ,
			    	minuteStep:1,
					initialDate: new Date()
				}
			}
//			,{
//				type:"datetime",
//			    title:"维修日期",
//			    dataIndex:'endssTime',
//			    config:{
//			        format: 'yyyy-mm-dd',
//			    	minView:'month',
//					initialDate: new Date()
//				}
//			}
			,{ 
				type:"combox",
	            title:"维修人",
	            dataIndex: 'repairUserid',
	            disabled: true,
				fieldText: 'name',
				fieldValue: 'pid',
				url: "../../../../dynamic/dicView/listDic?view=v_sec_user",
			    root: "dataset.data"
	        },{ 
	        	type:"textarea",
	            title:"维修情况描述",
	            disabled: true,
	            dataIndex:'repairProdes',  
	            rowspan: 4
	        }
	    ],
	    layoutConfig : {
	        columns : 2
	    },
	    onsubmit: function(){}
	};

var formbodyConfig3 = {
		name  : "Formbody",   
	    type  : "FormBody", 
	    title : "设备维修验证",
	    berth : "detailForm3",
	    items : [
	        {
				type:"combox",   
				title:"验证结果",
				disabled: true,
				dataIndex: 'chekcResult',
				fieldText:"name",
				fieldValue:"code",
				data:[{'code':'1','name':'已解决'},{'code':'0','name':'未解决'}],
				//url:"../../../DataDict?type=Workshop",
				root: "dataset"
			},{ 
	        	type:"textinput",
	            title:"结果描述",
	            disabled: true,
	            dataIndex:'invalidDes'
	        },{ 
	        	type:"combox",
	            title:"验证人",
	            dataIndex: 'checkUserid',
	            disabled: true,
				fieldText: 'name',
				fieldValue: 'pid',
				url: "../../../../dynamic/dicView/listDic?view=v_sec_user",
			    root: "dataset.data"
	        },{
				type:"datetime",
			    title:"验证时间",
			    dataIndex:'checkEt',
			    disabled: true,
			    config:{
			    	format: 'yyyy-mm-dd hh:ii:ss',
			        //showSecond: true,
			    	autoclose:true,
			    	minView: 0 ,
			    	minuteStep:1,
					initialDate: new Date()
				}
			}
	    ],
	    layoutConfig : {
	        columns : 2
	    },
	    onsubmit: function(){}
	};

$(document).ready(function() {   
	//formbodyConfig["url"] = MesWebApiUrl + "/sec/user/getuserpage/"+f_pid;
	//formbodyConfig["dataroot"] = 'dataset';
    SF.FormBody.onload(formbodyConfig1);
    SF.FormBody.onload(formbodyConfig2);
    SF.FormBody.onload(formbodyConfig3);
    $(":button").css("display","none");
	if(window.dialogArguments==undefined) return;
//	var mode = window.dialogArguments.mode;
	var row = window.dialogArguments.data.row;
		$.ajaxSettings.async = false; 
		$.post("../../../../dynamic/Repair/findRepairById?id="+row.pid,{},function(data){
			console.log(row);
			SF.BindFormData(data);
			if(data.createTime!=null){
				$("#createTime").val(formatDate(data.createTime));
			}
			if(data.occurTime!=null){
				$("#occurTime").val(formatDate(data.occurTime));
			}
			if(data.repairSt!=null){
				$("#repairSt").val(formatDate(data.repairSt));
			}
			if(data.repairEt!=null){
				$("#repairEt").val(formatDate(data.repairEt));
			}
			if(data.checkEt!=null){
				$("#checkEt").val(formatDate(data.checkEt));
			}
			$("#showName1").text("设备名称: "+data.equName);
			$("#showName2").text("发生时间: "+$("#occurTime").val());
			
			var statusName;
			if(data.status==0){
        		return "草稿"
        	}else if(data.status==10){
				statusName="已申报待修";
			}else if(data.status==20){
				statusName="维修中";
			}else if(data.status==29){
				statusName="修完待验证";
			}else if(data.status==30){
				statusName="验证中";
			}else if(data.status==40){
				statusName="维修单已关闭";
			}
			$("#showName3").text("状态: "+statusName);
			console.log(SF.getFormValues());//获得表单的value
		});
})