package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractService;
import com.itheima.service.cargo.ExportProductService;
import com.itheima.service.cargo.ExportService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
