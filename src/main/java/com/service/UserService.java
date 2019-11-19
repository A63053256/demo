package com.service;

import com.bean.User;
import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.SHA256Util;
import util.UserError;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDao dao;

    public List<User> userList(){
        return dao.findAll();
    }
    public void deleteUser(String id) {
        dao.delete(id);
    }
    public User findByName(String name) {
        return dao.findByName(name);
    }
    public UserError register(User user) {

        if (user.getName() == null || user.getName().trim().length() == 0) {
            return UserError.USERNAME_NULL;
        }
        if (user.getNickName() == null || user.getNickName().trim().length() == 0) {
            return UserError.NICKNAME_NULL;
        }
        if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
            return UserError.PASSWORD_NULL;
        }
        if (!(user.getRepassword().equals(user.getPassword()))) {
            return UserError.REPEATPASSWORD_ERROR;
        }
        String id = UUID.randomUUID().toString();
        id = id.replace("-","");
        user.setId(id);
        user.setPassword(SHA256Util.sha256(user.getPassword()));
        User u = dao.findByName(user.getName());
        User u1 = dao.findByNickName(user.getNickName());
        if (u != null) {
            return UserError.USERNAME_REPEAT;
        }else if (u1 != null){
            return UserError.NICKNAME_REPEAT;
        }
        System.out.println(user);
        dao.add(user);
        return UserError.SUCCESS;
    }

    public UserError login(User user) {
        if (user.getName() == null || user.getName().trim().length() == 0) {
            return UserError.USERNAME_NULL;
        }
        if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
            return UserError.PASSWORD_NULL;
        }
        User u = dao.findByName(user.getName());
        if (u == null || !u.getPassword().equals(SHA256Util.sha256(user.getPassword()))) {
            return UserError.USERNAME_OR_PASSWORD_ERROR;
        }
        return UserError.SUCCESS;
    }


    public UserError changePassword(User user,String password,String newPassword){
        password = SHA256Util.sha256(password);
        if (!(user.getPassword().equals(password))){
            return UserError.OLDPASSWORD_ERROR;
        }
        if (user == null ){
            return UserError.NOLOGIN;
        }
        user.setPassword(SHA256Util.sha256(newPassword));
        dao.update(user);
        return UserError.SUCCESS;
    }
}
