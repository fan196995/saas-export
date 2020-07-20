package com.itheima.dao.system;

import com.itheima.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/30 20:31
 */
public interface RoleDao {

    //根据id查询
    Role findById(String id);

    //查询全部用户
    List<Role> findAll(String companyId);

    //根据id删除
    int delete(String id);

    //保存
    int save(Role role);

    //更新   乐观锁返回int
    int update(Role role);

    //通过用户查询角色信息
    List<Role>findByUserId(String id);

    void deleteByUserId(String userid);

    void insertUserRole(@Param("userid") String userid,@Param("roleId") String roleId);
}
