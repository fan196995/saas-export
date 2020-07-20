package com.itheima.dao.company;

import com.itheima.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/28 11:41
 */
public interface CompanyDao {

    //查询所有企业
    List<Company> findAll();

    void save(Company company);

    Company findById(String id);

    void update(Company company);

    void delete(String id);

    long findCount();

    List findPage(@Param("index") int index, @Param("size") int size);
}
