package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.domain.company.Company;
import com.itheima.domain.system.Dept;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/28 14:51
 */
public interface DeptService {

    //查询哪个企业的哪个部门
    PageInfo findAll(String companyId,int page, int size);

    //查询该企业所有部门
    List<Dept> findAll(String companyId);

    //添加部门
    void save(Dept dept);

    //编辑
    Dept findById(String id);

    void update(Dept dept);

    void delete(String id);

}
