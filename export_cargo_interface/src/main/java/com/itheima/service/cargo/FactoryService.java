package com.itheima.service.cargo;



import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;

import java.util.List;

public interface FactoryService {

    //查询所有工厂信息
    List<Factory> findAll(FactoryExample example);
}
