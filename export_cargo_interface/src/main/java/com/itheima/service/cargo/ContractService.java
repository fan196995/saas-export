package com.itheima.service.cargo;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;

public interface ContractService {

    //分页查询
    PageInfo findAll(ContractExample example, int page, int size);

    //保存合同信息
    void save(Contract contract);

    //通过id进行查询
    Contract findById(String id);

    //更新合同信息
    void update(Contract contract);

    //通过id进行删除
    void delete(String id);
}
