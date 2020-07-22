package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.common.utils.UploadUtil;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.service.cargo.FactoryService;
import com.itheima.web.controller.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author fanbo
 * @date 2020/7/16 10:48
 */

@Controller
@RequestMapping(value = "/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private FactoryService factoryService;

    @Reference
    private ContractProductService contractProductService;

    @RequestMapping(value = "/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria = factoryExample.createCriteria();
        //andCtypeEqualTo 和数据库中的字段名字有关
        criteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //放到request域
        request.setAttribute("factoryList",factoryList);

        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria criteria1 = contractProductExample.createCriteria();
        criteria1.andContractIdEqualTo(contractId);
        PageInfo pageInfo = contractProductService.findAll(contractProductExample, page, size);
        request.setAttribute("page",pageInfo);
        request.setAttribute("contractId",contractId);
        return "cargo/product/product-list";
    }

    @RequestMapping(value = "/edit")
    public String edit(String contractId, ContractProduct contractProduct, MultipartFile productPhoto) throws IOException {
        contractProduct.setCompanyId(companyId);
        contractProduct.setCompanyName(companyName);

        //货物图片上传
        if (!productPhoto.isEmpty()){
            String upload = new UploadUtil().upload(productPhoto.getBytes());
            contractProduct.setProductImage(upload);
        }

        if (StringUtil.isEmpty(contractProduct.getId())){
            contractProductService.save(contractProduct);
        }else {
            contractProductService.update(contractProduct);
        }

        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;

    }

    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id){
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria = factoryExample.createCriteria();
        criteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //货物id
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct",contractProduct);
        return "cargo/product/product-update";
    }

    @RequestMapping(value = "/delete")
    public String delete(String id, String contractId){
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }

    @RequestMapping(value = "toImport")
    public String toImport (String contractId){
         request.setAttribute("contractId",contractId);
         return "cargo/product/product-import";
    }

    @RequestMapping(value = "/import")
    public String importExcel(String contractId,MultipartFile file) throws IOException {

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        //页
        Sheet sheet = workbook.getSheetAt(0);

        Object[] strings  = new Object[10];
        //循环excel表数据
        for (int i = 1; i<=sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            for (int j = 1; j < 10; j++) {
                Cell cell = row.getCell(j);
                strings[j] = getCellValue(cell);
            }

            ContractProduct contractProduct = new ContractProduct(strings,companyId,companyName);
            contractProduct.setContractId(contractId);
            contractProductService.save(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }


    public Object getCellValue(Cell cell){
        Object object = new Object();

        switch (cell.getCellType()){
            case STRING:
                object = cell.getStringCellValue();
                break;
            case BOOLEAN:
                object = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    object = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                }else{
                    object = cell.getNumericCellValue();
                }
                break;
            case FORMULA:
                break;
        }

        return object;
    }
}
