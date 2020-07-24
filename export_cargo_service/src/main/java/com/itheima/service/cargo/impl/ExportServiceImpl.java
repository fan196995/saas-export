package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.*;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ExportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author fanbo
 * @date 2020/7/23 17:58
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;  //报运单dao

    @Autowired
    private ContractDao contractDao;   //合同dao

    @Autowired
    private ContractProductDao contractProductDao;  //合同的货物对应的dao

    @Autowired
    private ExportProductDao exportProductDao;  //报运单的商品对应的dao

    @Autowired
    private ExtCproductDao extCproductDao;  //合同的附件dao

    @Autowired
    private ExtEproductDao extEproductDao;  //报运单的附件dao

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Export export) {
        //保存报运单
        export.setId(UUID.randomUUID().toString());
        //报运单初始状态
        export.setState(0);

        //获得合同ids数组
        String[] contractIds = export.getContractIds().split(",");

        //货物数量
        int proNum = 0;
        //附件数量
        int extNum = 0;

        //合同号
        String contractNos = "";

        //循环查询合同，合同号
        for (String contractId : contractIds) {
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            //存合同号
            contractNos += contract.getContractNo() + " ";
            //0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务
            contract.setState(2);
            //更新
            contractDao.updateByPrimaryKeySelective(contract);
        }

            //货物列表
            ContractProductExample contractProductExample = new ContractProductExample();
            ContractProductExample.Criteria criteria = contractProductExample.createCriteria();
            criteria.andContractIdIn(Arrays.asList(contractIds));

            List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);

            //key:货物id  value：报运商品id          货物id------商品id
            Map<String, String> map = new HashMap<String, String>();

            //循环货物列表
            for (ContractProduct contractProduct : contractProductList) {
                ExportProduct exportProduct = new ExportProduct();
                //货物内容写入报运商品

/*              exportProduct.setCnumber(contractProduct.getCnumber());
                exportProduct.setPrice(contractProduct.getPrice());*/
                //copyProperties 将第一个对象中同名属性copy到第二个对象中
                BeanUtils.copyProperties(contractProduct,exportProduct);

                exportProduct.setExportId(export.getId());
                exportProduct.setId(UUID.randomUUID().toString());
                exportProductDao.insertSelective(exportProduct);

                map.put(contractProduct.getId(),exportProduct.getId());
                proNum  = proNum + contractProduct.getCnumber();
            }

            //根据合同id 获取附件
            ExtCproductExample extCproductExample = new ExtCproductExample();
            ExtCproductExample.Criteria exampleCriteria = extCproductExample.createCriteria();
            exampleCriteria.andContractIdIn(Arrays.asList(contractIds));
            List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);

            //循环附件列表
            for (ExtCproduct extCproduct : extCproductList) {
                ExtEproduct extEproduct = new ExtEproduct();
                //合同附件写入报运附件
                BeanUtils.copyProperties(extCproduct,extEproduct);
                extEproduct.setExportId(export.getId());
                extEproduct.setId(UUID.randomUUID().toString());

                //商品id
                String productId = map.get(extCproduct.getContractProductId());//对应商品id
                extEproduct.setExportProductId(productId);

                extEproductDao.insertSelective(extEproduct);
                extNum = extNum + extCproduct.getCnumber();
            }

        //商品数量
        export.setProNum(proNum);
        //附件数量
        export.setExtNum(extNum);
        export.setCustomerContract(contractNos);
        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Export> list = exportDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
