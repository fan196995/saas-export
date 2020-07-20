package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.common.utils.UploadUtil;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.ExtCProductService;
import com.itheima.service.cargo.FactoryService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author fanbo
 * @date 2020/7/16 23:22
 */
@Controller
@RequestMapping(value = "/cargo/extCproduct")
public class ExtcProductController extends BaseController {

    @Reference
    private FactoryService factoryService;

    @Reference
    private ExtCProductService extCProductService;

    @RequestMapping(value = "/list")
    public String list(String contractId, String contractProductId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){

        //1、通过factoryService查询所有的厂家，条件必须货物
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //2、factoryList放入request域当中
        request.setAttribute("factoryList", factoryList);

        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo pageInfo = extCProductService.findAll(extCproductExample, page, size);
        request.setAttribute("page", pageInfo);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        return "cargo/extc/extc-list";
    }


    @RequestMapping(value = "/edit")
    public String edit(String contractId, String contractProductId, ExtCproduct extCproduct, MultipartFile productPhoto) throws IOException {

        extCproduct.setCompanyId(companyId);
        extCproduct.setCompanyName(companyName);

        //附件图片上传
        if (!productPhoto.isEmpty()){
            String upload = new UploadUtil().upload(productPhoto.getBytes());
            extCproduct.setProductImage(upload);
        }

        if (StringUtil.isEmpty(extCproduct.getId())){
            extCproduct.setContractId(contractId);
            extCproduct.setContractProductId(contractProductId);
            extCProductService.save(extCproduct);
        }else {
            extCProductService.update(extCproduct);
        }

        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }


    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId){

        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //2、factoryList放入request域当中
        request.setAttribute("factoryList", factoryList);


        ExtCproduct extCproduct = extCProductService.findById(id);
        request.setAttribute("extCproduct", extCproduct);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        return "cargo/extc/extc-update";
    }


    @RequestMapping(value = "/delete")
    public String delete(String id, String contractId, String contractProductId){
        extCProductService.delete(id);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }

}
