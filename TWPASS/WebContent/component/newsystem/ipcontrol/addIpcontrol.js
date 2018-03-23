var request = talkweb.ControlBus.getRequest();
var controltype = request['controltype'];
var id = request['id'];
var opt = request['opt'];
var notes = (controltype == '2' ? '是否绑定：' : '是否拒绝：');
talkweb.Bus.ready({
    items: [{
        classPath: "Components.Form",
        updateItems: false,
        order: 2,
        options: {
            id: "ipcontrolForm",
            saveInstance: true,
            name: "ipcontrolForm",
            container: "addFormDiv",
            action: "ipcontrol_addIpControl.action",
            dataSource: "ipcontrol_getIpControlByIpControlId.action?id=" + id,
            errorFile: "errorConfig.json",
            submitJson:false,//设置form表单数据提交格式为非json格式
            haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            columns: 2,
            afterLoad: function(){
                if (opt === 'modify') {
                    var ipform = talkweb.ControlBus.getInstance("ipcontrolForm");
                    ipform.setOptions({
                        resetToBlank: false
                    })
                }
            },
            submitCallback: function(result){
//                if (result == '') {
//                    alert("添加规则失败！");
//                }
//                else {
//                    alert(result.msg);
//                }
            	alert(result.msg);
                parent.talkweb.ControlBus.getInstance("datagrid").refreshData();
                parent.popupObj.close();
            },
            items: [{
                classPath: "BaseControl.Hidden",
                width: '0%',
                options: {
                    name: "ipControl.controlid",
                    value: ""
                }
            }, {
                classPath: "BaseControl.Hidden",
                width: '0%',
                options: {
                    name: "ipControl.createtime",
                    value: ""
                }
            }, {
                classPath: "BaseControl.Hidden",
                width: '0%',
                options: {
                    name: "ipControl.createuser",
                    value: ""
                }
            }, {
                classPath: "BaseControl.Hidden",
                width: '0%',
                options: {
                    name: "ipControl.controltype",
                    id: "controltype",
                    saveInstance: true,
                    value: ""
                }
            }, {
                classPath: "BaseControl.Text",
                width: '45%',
                options: {
                    id: "userid",
                    name: "ipControl.userid",
                    notes: "登录账号：",
                    validateItems: {
                        isNumber: true,
                        requires: true
                    }
                }
            }, {
                classPath: "BaseControl.Text",
                width: '55%',
                options: {
                    id: "username",
                    name: "ipControl.username",
                    notes: "姓名：",
                    validateItems: {
                        requires: true
                    }
                }
            }, {
                classPath: "BaseControl.Text",
                width: '45%',
                options: {
                    id: "ipaddress",
                    name: "ipControl.ipaddress",
                    notes: "ip地址：",
                    validateItems: {
                        isIp: true,
                        requires: true
                    }
                }
            }, {
                classPath: "BaseControl.Text",
                width: '55%',
                options: {
                    id: "maskcode",
                    name: "ipControl.maskcode",
                    notes: "子网掩码：",
                    validateItems: {
                        isIp: true,
                        requires: true
                    }
                }
            }, {
                classPath: "Components.Datepicker",
                width: '45%',
                options: {
                    notes: "开始时间：",
                    name: "ipControl.startdate",
                    id: "startdate",
                    saveInstance: true,
                    displayFormat: "yyyy-mm-dd",
                    validateItems: {
                        requires: true
                    }
                }
            }, {
                classPath: "Components.Datepicker",
                width: '55%',
                options: {
                    notes: "截止时间：",
                    id: "enddate",
                    saveInstance: true,
                    name: "ipControl.enddate",
                    displayFormat: "yyyy-mm-dd",
                    validateItems: {
                        requires: true
                    }
                }
            }, {
                classPath: "Components.RadioGroup",
                width: '100%',
                options: {
                    name: "ipControl.controlvalue",
                    id: "controlvalue",
                    saveInstance: true,
                    value: "1",
                    notes: notes,
                    items: [{
                        text: "是",
                        value: "1"
                    }, {
                        text: "否",
                        value: "0"
                    }]
                }
            }, {
                classPath: "BaseControl.TextArea",
                width: '100%',
                options: {
                    name: "ipControl.memo",
                    width: "520px",
                    height: "80px",
                    notes: "备注："
                }
            }]
        }
    }, {
        classPath: "BaseControl.Button",
        width: "10%",
        options: {
            value: "保存",
            className: "btn_save",
            container: "operationDiv",
            click: saveIpControl//点击提交按钮执行的动作
        }
    }, {
        classPath: "BaseControl.Button",
        options: {
            value: "重置",
            container: "operationDiv",
            className: "btn_reset",
            click: function(){
                talkweb.ControlBus.getInstance("ipcontrolForm").resetClick();
            }
        }
    }, {
        classPath: "BaseControl.Button",
        width: "10%",
        options: {
            value: "关闭",
            className: "btn_close",
            container: "operationDiv",
            click: closeDialog//点击重置按钮执行的动作
        }
    }]
})

/**
 * 关闭对话框
 */
function closeDialog(){
    parent.popupObj.close();
}

/**
 * 保存操作
 */
function saveIpControl(){
    talkweb.ControlBus.getInstance("controltype").setValue(controltype);
    //var startdate = talkweb.ControlBus.getInstance("startdate");
    //var enddate = talkweb.ControlBus.getInstance("enddate");
    //startdate.setValue(formDate(startdate.getValue()));
    //enddate.setValue(formDate(enddate.getValue()));
    var form = talkweb.ControlBus.getInstance("ipcontrolForm");
    if (opt == 'modify') {
    	form.setOptions({
    		action:"ipcontrol_modifyIpControl.action"
    	});
    	form.submit();
//        $.ajax({
//            type: "POST",
//            url: "ipcontrol_modifyIpControl.action",
//            data: "data={" + form.getValues() + "}",
//            dataType: "json",
//            success: function(data){
//                alert(data.msg);
//                parent.talkweb.ControlBus.getInstance("datagrid").refreshData(true);
//                parent.popupObj.close();
//            }
//        });
    }
    else {
        form.submit();
    }
}

/**
 *
 * @param {Object} date
 */
function formDate(date){
    if ('' != date && 'undefined' != date) {
        date = date.trim();
        var dates = date.split("-")
        if (dates[1] < 10) {
            dates[1] = '0' + date[1];
        }
        if (dates[2] < 10) {
            dates[2] = '0' + date[2];
        }
        return dates[0] + "-" + dates[1] + "-" + dates[2];
    }
}
