package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.User;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/28 14:51
 */
public interface UserService {

    //查询哪个企业的哪个部门
    PageInfo findAll(String companyId, int page, int size);

    //查询该企业所有部门
    List<User> findAll(String companyId);

    //添加部门
    void save(User user);

    //编辑
    User findById(String id);

    void update(User user);

    void delete(String id);

    //通过email查询用户
    User findByEmail(String email);
}
