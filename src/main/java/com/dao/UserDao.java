package com.dao;

import com.bean.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    void delete(String id);
    void update(User user);
    User findById(String id);
    User findByName(String name);
    User findByNickName(String name);
    List<User> findAll();
}
