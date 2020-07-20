package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.RoleDao;
import com.itheima.domain.system.Role;
import com.itheima.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author fanbo
 * @date 2020/6/30 20:44
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        PageHelper.startPage(page,size);
        List<Role> roleList = roleDao.findAll(companyId);
        PageInfo pageInfo = new PageInfo(roleList);
        return pageInfo;
    }

    @Override
    public List<Role> findAll(String companyId) {
        List<Role> roleList = roleDao.findAll(companyId);
        return roleList;
    }

    @Override
    public Role findById(String id) {
        Role byId = roleDao.findById(id);
        return byId;
    }

    @Override
    public int save(Role role) {
        role.setId(UUID.randomUUID().toString());
        int save = roleDao.save(role);
        return save;
    }

    @Override
    public int update(Role role) {
        int update = roleDao.update(role);
        return update;
    }

    @Override
    public int delete(String id) {
        int delete = roleDao.delete(id);
        return delete;
    }

    @Override
    public List<Role> findByUserId(String id) {
        List<Role> roleDaoByUserId = roleDao.findByUserId(id);
        return roleDaoByUserId;
    }

    @Override
    public void changeRole(String userid, String[] roleIds) {
        //通过userid进行删除
        roleDao.deleteByUserId(userid);
        //循环roleIds，写入用户角色中间表
        for (String roleId : roleIds) {
            roleDao.insertUserRole(userid,roleId);
        }
    }
}
