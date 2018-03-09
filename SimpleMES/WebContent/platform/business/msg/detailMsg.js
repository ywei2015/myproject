
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "消息详情",
    berth : "detailForm",
    items : [
      /*  { 
        	type:"combox",
            title:"任务类型",
        	dataIndex: 'wbTasktype',
			dataText: 'wbTasktype',
			fieldText:"name",
			fieldValue:"id",
			url:"../../../../dynamic/dicView/listDic?view=v_equ_tasktype",
			root: "dataset.data"
        },*/{ 
        	type:"textinput",
            title:"标题",
            dataIndex:'title'
        },/*
        { 
        	type:"textinput",
            title:"内容",
            dataIndex:'content'
        },  { 
        	type:"textinput",
            title:"消息类型",
            dataIndex:'msgType'
        }, {
			type:"combox",   
			title:"通知方式",
			dataIndex: 'noticeType',
			dataText: 'noticeType',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':'1','name':'是'},{'code':'0','name':'否'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset"
		},*/{ 
        	type:"textinput",
            title:"来源",
            dataIndex:'from'
        },{ 
        	type:"textinput",
            title:"计划下达时间",
            dataIndex:'planTime'
        },/*{ 
        	type:"textinput",
            title:"预期时间",
            dataIndex:'expiryTime'
        },*/{ 
        	type:"textinput",
            title:"发送者",
            dataIndex:'sender'
        },
     /*   { 
        	type:"textinput",
            title:"接收者",
            dataIndex:'receiver'
        },{ 
        	type:"textinput",
            title:"实际发送时间",
            dataIndex:'factSendTime'
        },{ 
        	type:"textinput",
            title:"实际收到时间",
            dataIndex:'factGetTime'
        },{ 
        	type:"textinput",
            title:"接收设备号",
            dataIndex:'deviceSN'
        },{ 
        	type:"textinput",
            title:"发送次数",
            dataIndex:'sendCount'
        },
        { 
        	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        },{ 
        	type:"textinput",
            title:"创建者",
            dataIndex:'creator'
        }{ 
        	type:"textinput",
            title:"创建时间",
            dataIndex:'createTime'
        },{ 
        	type:"textinput",
            title:"修改人",
            dataIndex:'lastModifier'
        },
        { 
        	type:"textinput",
            title:"修改时间",
            dataIndex:'lastModifiedTime'
        },{ 
        	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        }
		,{ 
        	type:"textarea",
            title:"备注",
            dataIndex:'remark',  
            rowspan:4
        }*/{ 
        	type:"textarea",
            title:"内容",
            dataIndex:'content',  
            rowspan:4
        }
    ],
    hidefields : ['pid'] ,
    layoutConfig : {
        columns : 2
    }
};


$(document).ready(function() {   
	SF.FormBody.onload(formbodyConfig);
	var mode = window.dialogArguments.mode;
	var data = window.dialogArguments.data;
	SF.BindFormData(data);
	$("#btn_FormBody_commit").hide();
})