package com.anabol.userstore.service.impl;

import com.anabol.userstore.dao.UserDao;
import com.anabol.userstore.entity.User;
import com.anabol.userstore.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService{
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
