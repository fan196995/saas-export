package com.itheima.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ContractProduct;
import com.itheima.domain.cargo.ContractProductExample;

//合同对应的货物service接口
public interface ContractProductService {

    //分页查询货物列表
    PageInfo findAll(ContractProductExample example, int page, int size);

    //保存合同信息
    void save(ContractProduct contractProduct);

    //通过id进行查询
    ContractProduct findById(String id);

    //更新合同信息
    void update(ContractProduct contractProduct);

    //通过id进行删除
    void delete(String id);

}
