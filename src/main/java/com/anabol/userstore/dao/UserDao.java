package com.anabol.userstore.dao;

import com.anabol.userstore.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(int id);

    void insert(User user);

    void deleteById(int id);

    void update(User user);
}
