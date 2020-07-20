package com.itheima.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.dao.company.CompanyDao;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author fanbo
 * @date 2020/6/28 14:54
 */
//这里一定是dubbo的service注解
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    //查询所有企业
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    //保存企业信息
    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }

    @Override
    public Company findById(String id) {
        Company company =companyDao.findById(id);
        return company;
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageResult findByPage(int page, int size) {
        long total = companyDao.findCount();  //总数量
        List rows =companyDao.findPage((page-1)*size,size);
        PageResult pageResult = new PageResult(total, rows, page, size);
        return pageResult;
    }

    @Override
    public PageInfo findByHelper(int page, int size) {
        PageHelper.startPage(page, size);
        List<Company> companyList = companyDao.findAll();
        PageInfo pageInfo = new PageInfo(companyList);
        return pageInfo;
    }
}
