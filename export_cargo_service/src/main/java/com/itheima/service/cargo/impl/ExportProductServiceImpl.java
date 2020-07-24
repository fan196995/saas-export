package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.cargo.ExportProductDao;
import com.itheima.domain.cargo.ExportProduct;
import com.itheima.domain.cargo.ExportProductExample;
import com.itheima.service.cargo.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/7/23 17:58
 */
@Service
public class ExportProductServiceImpl implements ExportProductService {

    @Autowired
    private ExportProductDao exportProductDao;

    @Override
    public ExportProduct findById(String id) {
        return null;
    }

    @Override
    public void save(ExportProduct exportProduct) {

    }

    @Override
    public void update(ExportProduct exportProduct) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        return exportProductDao.selectByExample(example);
    }
}
