package com.itheima.dao.system;

import com.itheima.domain.system.Dept;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/29 11:54
 */
public interface DeptDao {

    List<Dept> findAll(String companyId);

    Dept findById(String id);

    void save(Dept dept);

    void update(Dept dept);

    void delete(String id);
}
