package com.itheima.service.company;

import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.domain.company.Company;

import java.util.List;

/**
 * service接口
 * @author fanbo
 * @date 2020/6/28 14:51
 */
public interface CompanyService {

    //查询所有企业
    List<Company> findAll();

    //保存企业信息
    void save(Company company);

    Company findById(String id);

    void update(Company company);

    void delete(String id);

    //传统分页
    PageResult findByPage(int page, int size);

    //分页插件
    PageInfo findByHelper(int page, int size);
}
