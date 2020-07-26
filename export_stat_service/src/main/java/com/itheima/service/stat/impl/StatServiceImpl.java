package com.itheima.service.stat.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.stat.StatDao;
import com.itheima.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author fanbo
 * @date 2020/7/25 23:05
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatDao statDao;

    @Override
    public List<Map> getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }

    @Override
    public List<Map> getSellData(String companyId) {
        return statDao.getSellData(companyId);
    }

    @Override
    public List<Map> getOnlineData(String companyId) {
        return statDao.getOnlineData(companyId);
    }
}
