talkweb.Bus.ready({
    items: [{
        classPath: "Components.Form",
        updateItems: false,
        order: 2,
        options: {
            id: "ruleForm",
            saveInstance: true,
            name: "ruleForm",
            container: "formDiv",
            dataSource: "ipcontrol_initRuleForm.action",
            action: "ipcontrol_modifyRuleValue.action",
            haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            columns: 1,
            requestType: "post",
            submitCallback: function(result){
            },
            afterLoad: onload,
            items: [{
                classPath: "BaseControl.CheckBox",
                width: "3%",
                options: {
                    id: "ONSCHOOL",
                    name: "ONSCHOOL",
                    saveInstance: true
                }
            }, {
                classPath: "BaseControl.Span",
                width: "97%",
                options: {
                    value: "不允许不在校学生登录"
                }
            }, {
                classPath: "BaseControl.CheckBox",
                width: "3%",
                options: {
                    id: "SCHOOLSTATUS",
                    saveInstance: true,
                    name: "SCHOOLSTATUS"
                }
            }, {
                classPath: "BaseControl.Span",
                width: "97%",
                options: {
                    value: "不允许无学籍学生登录"
                }
            }, {
                classPath: "BaseControl.CheckBox",
                width: "3%",
                options: {
                    id: "REGISTER",
                    saveInstance: true,
                    name: "REGISTER"
                }
            }, {
                classPath: "BaseControl.Span",
                width: "97%",
                options: {
                    value: "不允许未注册学生登录"
                }
            },{
                classPath: "BaseControl.CheckBox",
                width: "3%",
                options: {
                    id: "ONJOB",
                    saveInstance: true,
                    name: "ONJOB"
                }
            },{
                classPath: "BaseControl.Span",
                width: "97%",
                options: {
                    value: "不允许不在职的教职工登录"
                }
            }, {
                classPath: "BaseControl.CheckBox",
                width: "3%",
                options: {
                    id: "CERTIFICATE",
                    saveInstance: true,
                    name: "CERTIFICATE"
                }
            },{
                classPath: "BaseControl.Span",
                width: "97%",
                options: {
                    value: "不允许无数字证书的学生登录"
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
            click: save//点击提交按钮执行的动作
        }
    }]
})


function onload(){
    var controls = ["ONSCHOOL", "SCHOOLSTATUS", "REGISTER", "ONJOB", "CERTIFICATE"];
    for (var i = 0; i < controls.length; i++) {
        fastDev("#" + controls[i]).attr('checked', fastDev("#" + controls[i]).attr("value") == '0' ? true : false);
    }
}

/**
 * 执行保存操作
 */
function save(){
    var form = talkweb.ControlBus.getInstance("ruleForm");
    var controls = ["ONSCHOOL", "SCHOOLSTATUS", "REGISTER", "ONJOB", "CERTIFICATE"];
    var reqData = buildReqData(controls);
    $.ajax({
        type: "POST",
        url: "ipcontrol_modifyRuleValue.action",
        data: "data={" + reqData + "}",
        dataType: "json",
        success: function(data){
            alert("访问控制信息修改成功！");
        }
    });
}

/**
 * 拼接请求数据
 * @param {Object} controls
 */
function buildReqData(controls){
    var reqData = "";
    for (var i in controls) {
        if (fastDev("#" + controls[i]).attr("checked")) {
            reqData += (controls[i] + ":'0',");
        }
    }
    return reqData;
}
