package com.Controller;

import com.bean.Result;
import com.bean.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.util.Password;
import util.UserError;

import javax.jws.Oneway;
import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @RequestMapping("/login")
    public Object login(@RequestBody User user, HttpSession session){
        System.out.println(user);
        Result result = new Result();
        UserError ue = service.login(user);
        System.out.println(ue);
        switch (ue) {
            case USERNAME_NULL:
                result.setSuccess(false);
                result.setMsg("用户名不能为空");
                break;
            case PASSWORD_NULL:
                result.setSuccess(false);
                result.setMsg("密码不能为空");
                break;
            case USERNAME_OR_PASSWORD_ERROR:
                result.setSuccess(false);
                result.setMsg("用户名或密码错误");
                break;
            case SUCCESS:
                result.setSuccess(true);
                user = service.findByName(user.getName());
                result.setValue(user);
                session.setAttribute("user",result.getValue());
                result.setMsg("登录成功");
                break;
        }


        return result;
    }


    @RequestMapping("/register")
    public Object register(@RequestBody User user) {
        Result result = new Result();
        UserError ue = service.register(user);
        switch (ue) {
            case USERNAME_NULL:
                result.setSuccess(false);
                result.setMsg("用户名不能为空");
                break;
            case NICKNAME_NULL:
                result.setSuccess(false);
                result.setMsg("昵称不能为空");
                break;
            case PASSWORD_NULL:
                result.setSuccess(false);
                result.setMsg("密码不能为空");
                break;
            case USERNAME_REPEAT:
                result.setSuccess(false);
                result.setMsg("用户名重复");
                break;
            case NICKNAME_REPEAT:
                result.setSuccess(false);
                result.setMsg("昵称重复");
                break;
            case REPEATPASSWORD_ERROR:
                result.setSuccess(false);
                result.setMsg("二次密码错误");
                break;
            case SUCCESS:
                result.setSuccess(true);
                result.setMsg("注册成功");
                break;
        }
        return result;
    }
    @RequestMapping("/changePassword")
    public Object changePassword(String password,String newPassword,HttpSession session){
        Result result = new Result();
        User user = (User)session.getAttribute("user");;
        UserError ue = service.changePassword(user,password,newPassword);
            switch (ue) {
                case NOLOGIN:
                    result.setSuccess(false);
                    result.setMsg("没有权限，请登录后操作");
                    System.out.println("没有权限");
                    break;
                case OLDPASSWORD_ERROR:
                    result.setSuccess(false);
                    result.setMsg("原密码错误");
                    System.out.println("原密码错误");
                    break;
                case SUCCESS:
                    result.setSuccess(true);
                    result.setMsg("修改成功，请重新登录");
                    System.out.println("成功");
                    break;
            }
        return result;
    }
    @RequestMapping("/logout")
    public Object logout(HttpSession session){
        Result result = new Result();
        session.removeAttribute("user");
        if (session == null) {
            result.setSuccess(true);
            result.setMsg("登出成功");
        }else {
            result.setSuccess(false);
            result.setMsg("登出失败");
        }
        return result;
    }

    @RequestMapping("/repeat")
    public Object repeat(String username){
        Result result = new Result();
        User user = service.findByName(username);
        if (user != null){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        return result;
    }

}
