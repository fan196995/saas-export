package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.domain.cargo.*;
import com.itheima.domain.vo.ExportProductVo;
import com.itheima.domain.vo.ExportResult;
import com.itheima.domain.vo.ExportVo;
import com.itheima.service.cargo.ContractService;
import com.itheima.service.cargo.ExportProductService;
import com.itheima.service.cargo.ExportService;
import com.itheima.web.controller.BaseController;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanbo
 * @date 2020/7/23 17:14
 */

@Controller
@RequestMapping(value = "/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;


    @RequestMapping(value = "/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = contractService.findAll(contractExample, page, size);
        request.setAttribute("page",pageInfo);
        return "cargo/export/export-contractList";
    }


    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page",pageInfo);
        return "cargo/export/export-list";
    }

    //合同的id数组
    @RequestMapping(value = "/toExport")
    public String toExport(String id){
        request.setAttribute("id",id);
        return "cargo/export/export-toExport";
    }

    //保存报运单
    @RequestMapping(value = "/edit")
    public String edit(Export export,String contractIds){
        export.setContractIds(contractIds);
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);
        if (StringUtil.isEmpty(export.getId())){
            exportService.save(export);
        }else{
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }


    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id){
        Export export = exportService.findById(id);
        request.setAttribute("export",export);

        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);

        //查询报运单商品
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        request.setAttribute("eps",exportProductList);
        return "cargo/export/export-update";
    }

    @RequestMapping(value = "/submit")
    public String submit(String id){
        Export export = exportService.findById(id);
        export.setState(1);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(value = "/cancel")
    public String cancel(String id){
        Export export = exportService.findById(id);
        export.setState(0);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(value = "/delete")
    public String delete(String id){
        exportService.delete(id);
        return "redirect:/cargo/export/list.do";
    }


    //海关
    @RequestMapping(value = "/exportE")
    public String exportE(String id){
        Export export = exportService.findById(id);
        //海关数据实体
        ExportVo exportVo = new ExportVo();
        BeanUtils.copyProperties(export,exportVo);
        exportVo.setExportId(id);

        //查询报运商品
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);

        List<ExportProductVo> exportVos = new ArrayList<ExportProductVo>();

        //构造给海关数据
        for (ExportProduct exportProduct : exportProductList) {
            ExportProductVo exportProductVo = new ExportProductVo();
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            exportProductVo.setExportId(id);
            exportProductVo.setExportProductId(exportProduct.getId());
            exportVos.add(exportProductVo);
        }

        //合并海关商品和主数据
        exportVo.setProducts(exportVos);

        //调海关接口 jax-rs 数据传给海关
        WebClient wc  = WebClient.create("http://localhost:8099/ws/export/user");
        wc.post(exportVo);

        //id传给海关
        wc = WebClient.create("http://localhost:8099/ws/export/user/"+id);
        ExportResult exportResult =  wc.get(ExportResult.class);

        System.out.println("========================调用海关接口："+exportResult.toString());

        //通过海关返回更新税金
        exportService.updateE(exportResult);

        return "redirect:/cargo/export/list.do";
    }

}
