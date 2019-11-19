/**
 * 页面初始化后，绑定函数。
 */
$(function(){
	//注册
	$("#regist_button").click(function(){
		register();
	});
	
	//登录
	$("#login").click(function(){
		login();
	});
	
	//登出
	$("#logout").click(function(){
		logout();
	});
	//判断用户名重复
	$("#regist_username").blur(function(){
	    repeat();
    });


	//修改密码
	$("#changePassword").click(function(){
		changepwd();
	})
	
});
//显示错误提示框
function clearError(e) {
	$(e).popover('hide');
}
//判断用户名重复
function repeat() {
    var name = $('#regist_username').val().trim();
    $.ajax({
        url:'user/repeat.do',
        method:'post',
        data:{username:name},
        success:function (result) {
            if (result.success == true){
                $('#regist_username').attr("data-content","用户名重复");
                $('#regist_username').popover('show');
            }
        }
    })
}
//注册
function register() {
    var name = $('#regist_username').val().trim();
    var nickName = $('#nickname').val().trim();
    var password = $('#regist_password').val().trim();
    var final_password = $('#final_password').val().trim();
    if (name == null || name.length == 0){
        $('#regist_username').attr("data-content","用户名不能为空");
        $('#regist_username').popover('show');
        return;
    }
    if (nickName == null || nickName.length == 0){
        $('#nickname').attr("data-content","昵称不能为空");
        $('#nickname').popover('show');
        return;
    }
    if (password == null || password.length == 0){
        $('#regist_password').attr("data-content","密码不能为空");
        $('#regist_password').popover('show');
        return;
    }
    if (password.length < 6){
        $('#regist_password').attr("data-content","密码不能小于6位");
        $('#regist_password').popover('show');
        return;
    }

    if (final_password == null || final_password.length == 0){
        $('#final_password').attr("data-content","二次密码不能为空");
        $('#final_password').popover('show');
        return;
    }
    var data = {name:name,nickName:nickName,password:password,repassword:final_password};
    $.ajax({
        url:'user/register.do',
        method:'post',
        data:JSON.stringify(data),
        contentType: 'application/json',//如果添加contentType那么传到后台的就必须是json字符串
        dataType: "json", //json格式，如果后台返回的数据为json格式的数据，那么前台会收到Object。
        success:function (result) {
            if (result.success == true){
                swal({
                    title:result.msg,
                    type: "success",
                    confirmButtonColor: "#16e50c",
                    confirmButtonText:"确定",
                    closeOnConfirm: false
                });
                $("#zc").attr("class","sig sig_out");
                $("#dl").attr("class","log log_in");
            }else {
                swal({
                    title:result.msg,
                    type: "warning",
                    confirmButtonColor: "#e53027",
                    confirmButtonText:"确定",
                    closeOnConfirm: false
                });
                $('#error').html(result.msg);
            }
        }
	})

}

//登陆
function login() {
	var name = $('#name').val().trim();
	var password = $('#password').val().trim();
	var data = {name:name,password:password};
	if (name == null || name.length == 0){
        $('#name').attr("data-content","用户名不能为空");
        $('#name').popover('show');
		return;
	}
    if (password == null || password.length == 0){
        $('#password').attr("data-content","密码不能为空");
        $('#password').popover('show');
        return;
    }
    $.ajax({
		url:'user/login.do',
		method:'post',
        data:JSON.stringify(data),
        contentType: 'application/json',//如果添加contentType那么传到后台的就必须是json字符串
        dataType: "json", //json格式，如果后台返回的数据为json格式的数据，那么前台会收到Object。
		success:function (result) {
			if (result.success == true){
				addCookie('userId',result.value.id);
				addCookie('nickname',result.value.nickName);
				swal({
					title:result.msg,
					type: "success",
					confirmButtonColor: "#6be557",
					confirmButtonText:"确定",
					closeOnConfirm: false
				},
					function() {
                        location.href = "edit.html";
                    });
			}else {
                swal({
                    title:result.msg,
                    type: "warning",
                    confirmButtonColor: "#e53027",
                    confirmButtonText:"确定",
                    closeOnConfirm: false
                });
            }
        }
	})
}

/**
 * 退出登录
 */
function logout() {
    delCookie("userId")
    delCookie("nickname")
    $.ajax({
        url: 'user/logout.do',
        method: 'get',
        success: function (result) {
            if (result.success == true) {
                alert(result.msg);
                        location.href = "login.html";
            }
        }
    });
}
/**
 * 修改密码
 */
function changepwd(){
    var  lastPassword = $('#last_password').val().trim();
    var  newPassword = $('#new_password').val().trim();
    var  finalPassword = $('#final_password').val().trim();
    var data = { password:lastPassword,newPassword:newPassword}
    if (lastPassword == null || lastPassword.length == 0){
        $('#last_password').attr("data-content","密码不能为空");
        $('#last_password').popover('show');
        return;
    }
    if (newPassword == null || newPassword.length == 0){
        $('#new_password').attr("data-content","新密码不能为空");
        $('#new_password').popover('show');
        return;
    }
    if (newPassword.length< 6 ){
        $('#new_password').attr("data-content","新密码长度不能小于6位");
        $('#new_password').popover('show');
        return;
    }

    if (finalPassword == null || finalPassword.length == 0){
        $('#final_password').attr("data-content","二次密码不能为空");
        $('#final_password').popover('show');
        return;
    }
    if (!(finalPassword == newPassword)){
        $('#final_password').attr("data-content","两次密码不一致");
        $('#final_password').popover('show');
        return;
    }
    $.ajax({
        url:'user/changePassword.do',
        method:'post',
        data:{password:lastPassword,newPassword:newPassword},
        success:function (result) {
            if (result.success == true){
                swal({
                        title:result.msg,
                        type: "success",
                        confirmButtonColor: "#6be557",
                        confirmButtonText:"确定",
                        closeOnConfirm: false
                    },
                    function() {
                        location.href = "login.html";
                    });
            }else {
                swal({
                    title:result.msg,
                    type: "warning",
                    confirmButtonColor: "#e53027",
                    confirmButtonText:"确定",
                    closeOnConfirm: false
                });
            }
        }
    })
	//logout();
}


