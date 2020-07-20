package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author fanbo
 * @date 2020/7/16 11:37
 */
@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public void save(ContractProduct contractProduct) {
        //货物金额
        double amount = 0.0d;
        if (contractProduct.getCnumber()!=null&&contractProduct.getPrice()!=null){
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }

        contractProduct.setId(UUID.randomUUID().toString());
        //设置货物金额
        contractProduct.setAmount(amount);
        contractProductDao.insertSelective(contractProduct);

        //通过contractProduct.contractId取合同实体
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()+amount);

        contract.setProNum(contract.getProNum()+contractProduct.getCnumber());
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ContractProduct contractProduct) {
        //货物金额
        double amount = 0.0d;
        if (contractProduct.getCnumber()!=null&&contractProduct.getPrice()!=null){
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        //设置货物金额
        contractProduct.setAmount(amount);

        //通过contractProduct.contractId取合同实体
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());

//        查询以前的货物
        ContractProduct old = contractProductDao.selectByPrimaryKey(contractProduct.getId());

        contract.setTotalAmount(contract.getTotalAmount()-old.getAmount()+amount);
        contract.setProNum(contract.getProNum()-old.getCnumber()+contractProduct.getCnumber());

        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //考虑附件
        //获取货物下的所有附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);

        double extAmount = 0.0d;
        int extNum = 0;

//        循环货物下的所有附件调用附件的删除方法
        for (ExtCproduct extCproduct : extCproductList) {
            extAmount += extCproduct.getAmount();
            extNum += extCproduct.getCnumber();
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }

        //删除的同时删除货物的金额
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        double proAmount = contractProduct.getAmount();
        int proNum = contractProduct.getCnumber();

        //合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());

        //合同金额
        contract.setTotalAmount(contract.getTotalAmount()-proAmount-extAmount);
        //合同货物数量
        contract.setProNum(contract.getProNum()-proNum);
        //合同附件数量
        contract.setExtNum(contract.getExtNum() - extNum);
        contractProductDao.deleteByPrimaryKey(id);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ContractProduct findById(String id) {
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        return contractProduct;
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
