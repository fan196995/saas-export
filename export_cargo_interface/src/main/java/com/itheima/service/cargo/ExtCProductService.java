package com.itheima.service.cargo;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;

//附件的service接口
public interface ExtCProductService {

    //分页查询附件列表
    PageInfo findAll(ExtCproductExample example, int page, int size);

    //保存附件信息
    void save(ExtCproduct extCproduct);

    //通过id进行查询
    ExtCproduct findById(String id);

    //更新合同信息
    void update(ExtCproduct extCproduct);

    //通过id进行删除
    void delete(String id);
}
