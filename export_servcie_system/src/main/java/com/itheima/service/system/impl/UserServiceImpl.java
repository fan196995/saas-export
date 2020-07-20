package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.Encrypt;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.User;
import com.itheima.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author fanbo
 * @date 2020/6/29 13:15
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        PageHelper.startPage(page,size);
        List<User> list = userDao.findAll(companyId);
        PageInfo pageInfo =new PageInfo(list);
        return pageInfo;
    }

    @Override
    public List<User> findAll(String companyId) {
        List<User> userList = userDao.findAll(companyId);
        return userList;
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(Encrypt.md5(user.getPassword(),user.getEmail()));
        userDao.save(user);
    }

    @Override
    public User findById(String id) {
        User byId = userDao.findById(id);
        return byId;
    }

    @Override
    public void update(User user) {
        user.setPassword(Encrypt.md5(user.getPassword(),user.getEmail()));
        userDao.update(user);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
