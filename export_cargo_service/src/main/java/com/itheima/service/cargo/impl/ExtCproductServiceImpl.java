package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.service.cargo.ExtCProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author fanbo
 * @date 2020/7/16 22:30
 */
@Service
public class ExtCproductServiceImpl implements ExtCProductService {

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    @Override
    public void save(ExtCproduct extCproduct) {
        //附件金额
        double amount = 0.0d;
        if (extCproduct.getCnumber()!=null&&extCproduct.getPrice()!=null){
            amount = extCproduct.getCnumber()*extCproduct.getPrice();
        }

        extCproduct.setAmount(amount);
        extCproduct.setId(UUID.randomUUID().toString());
        extCproductDao.insertSelective(extCproduct);

        //通过contractProduct.contractId取合同实体
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setExtNum(contract.getExtNum()+extCproduct.getCnumber());
        contract.setTotalAmount(contract.getTotalAmount()+amount);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //附件金额
        double amount = 0.0d;
        if (extCproduct.getCnumber()!=null&&extCproduct.getPrice()!=null){
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        extCproduct.setAmount(amount);

        //通过contractProduct.contractId取合同实体
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        ExtCproduct old = extCproductDao.selectByPrimaryKey(extCproduct.getId());

        contract.setTotalAmount(contract.getTotalAmount()-old.getAmount()+amount);
        contract.setExtNum(contract.getExtNum()-old.getCnumber()+extCproduct.getCnumber());
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //通过id查询附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        double extAmount = extCproduct.getAmount();
        int extNum = extCproduct.getCnumber();

        //合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //合同金额
        contract.setTotalAmount(contract.getTotalAmount()-extAmount);
        //合同数量
        contract.setExtNum(contract.getExtNum()-extNum);
        extCproductDao.deleteByPrimaryKey(id);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ExtCproduct findById(String id) {
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        return extCproduct;
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
