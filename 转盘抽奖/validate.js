/**
 * Created by huguantao on 15/10/12.
 */

$().ready(function() {
    $("#validate").validate({
        rules: {
            name: {
                required: true,
                minlength: 4,
                maxlength: 12
            },
            phone: {
                required: true,
                minlength: 12
            },
            password: {
                required: true,
                minlength: 6,
                maxlength: 12
            },
            confirm_password: {
                required: true,
                minlength: 6,
                equalTo: "#password"
            }
        },
        messages: {
            name: {
                required: "请输入密码",
                minlength: "请使用4-12位英文字母、数字和下划线，首字符必须 为字母或数字"
            },
            phone: {
                required: "电话号码",
                minlength: "请输入正确的电话号码"
            },
            password: {
                required: "请输入密码",
                minlength: "请使用6-12位英文字母、数字和下划线，首字符必须 为字母或数字"
            },
            confirm_password: {
                required: "请输入确认密码",
                minlength: "确认密码请使用6-12位英文字母、数字和下划线，首字符必须 为字母或数字",
                equalTo: "两次输入密码不一致不一致"
            }
        }
    });
});