package com.itheima.service.system.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.ModuleDao;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    //分页查询所有
    public PageInfo findAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<Module> list = moduleDao.findAll();
        return new PageInfo(list);
    }

    //根据id进行查询
    public Module findById(String id)
    {
        return moduleDao.findById(id);
    }
    //保存
    public int save(Module module) {
        module.setId(UUID.randomUUID().toString());
        return moduleDao.save(module);
    }

    //更新
    public int update(Module module) {
        return moduleDao.update(module);
    }

    //删除
    public int delete(String id) {
        return moduleDao.delete(id);
    }

    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    //通过角色查找模块信息
    public List<Module> findModuleByRoleId(String roleId) {
        return moduleDao.findModuleByRoleId(roleId);
    }

    public void insertRoleModule(String roleid, String moduleIds) {
        //1、删除该角色下的所有模块
        moduleDao.deleteByRoleId(roleid);

        //2、moduleIds转化成数组 以逗号分隔
        String [] modules = moduleIds.split(",");

        //3、循环数组，通过roleid, moduleid写入中间表
        for (String moduleId : modules) {
            moduleDao.insertRoleModule(roleid,moduleId);
        }
    }



    /**
     * pe_user  degree
     * 0-saas管理员
     * 1-企业管理员
     * 其他-企业用户
     *
     *
     * ss_module  belong
     * 0：sass系统内部菜单
     * 1：租用企业菜单
     * @param user
     * @return
     */
    //查找用户对应的模块
    public List<Module> findModuleByUser(User user) {
        if (user.getDegree()==0){
            //saas管理员
            return moduleDao.findByDegree(user.getDegree());
        }else if (user.getDegree()==1){
            //企业管理员
            return moduleDao.findByDegree(user.getDegree());
        }else {
            return moduleDao.findModuleByUserId(user.getId());
        }
    }
}
