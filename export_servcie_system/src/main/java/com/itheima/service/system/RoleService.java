package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Role;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/30 20:45
 */
public interface RoleService {

    //分页查询所有
    PageInfo findAll(String companyId,int page, int size);

    //不分页
    List<Role> findAll(String companyId);

    //根据id进行查询
    Role findById(String id);

    //保存
    int save(Role role);

    //更新
    int update(Role role);

    //删除
    int delete(String id);

    //通过用户id查询该用户下的角色信息
    List<Role> findByUserId(String id);

    //用户角色保存并跳转页面
    void changeRole(String userid, String[] roleIds);
}
