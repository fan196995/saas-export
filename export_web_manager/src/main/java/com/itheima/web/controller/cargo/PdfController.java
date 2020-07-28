package com.itheima.web.controller.cargo;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.utils.BeanMapUtils;
import com.itheima.domain.cargo.Export;
import com.itheima.domain.cargo.ExportProduct;
import com.itheima.domain.cargo.ExportProductExample;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.service.cargo.ExportProductService;
import com.itheima.service.cargo.ExportService;
import com.itheima.web.controller.BaseController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Controller
@RequestMapping(value = "/cargo/export")
public class PdfController extends BaseController {


    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    @Reference
    private ContractProductService contractProductService;

    @RequestMapping(value = "/exportPdf")
    public void exportPdf(String id) throws Exception {
        //通过id查询export主表内容，报运单信息，返回实体类
        Export export = exportService.findById(id);

        export.setCustomerContract(export.getCustomerContract());

        //TODO  报运单中厂家的信息

        //将实体类转化为map
/*        Map map = new HashMap();
        map.put("inputDate",export.getInputDate());
        map.put("contractIds",export.getContractIds());*/
        Map<String, Object> exportMap = BeanMapUtils.beanToMap(export);

        //通过id查询exportProduct报运单商品信息，返回list
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);

        List<ExportProduct> list = exportProductService.findAll(exportProductExample);

        // [{productNo: 123, packingUnit: 1}, {productNo: 1234, packingUnit: 12}]

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

//        获取.jasper的路径
        String path = session.getServletContext().getRealPath("/")+"/jasper/export.jasper";

        //String sourceFileName: 文件的路径,
        //Map<String, Object> params：文件里需要通过map填充的内容
        //JRDataSource dataSource：JR数据源，列表
//        通过JasperFillManager管理器读取.jasper的文件 创建jrprint
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, exportMap, dataSource);

//        通过JasperExportManager管理器输出pdf文件
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }


    /*@RequestMapping(value = "/exportPdf005")
    public void exportPdf005() throws Exception {
        String path = session.getServletContext().getRealPath("/")+"/jasper/test005.jasper";

        List list = new ArrayList();

        for (int i = 0; i < 5; i++) {
            Map map = new HashMap();
            map.put("username", "菜10"+i);
            map.put("value", new Random().nextInt(1000));
            list.add(map);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //new JREmptyDataSource() --> list
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, new HashMap<String, Object>(), dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }


//    list模版
    @RequestMapping(value = "/exportPdf004")
    public void exportPdf004() throws Exception {
        String path = session.getServletContext().getRealPath("/")+"/jasper/test004.jasper";

        Map map = new HashMap();

        map.put("username", "菜10");
        map.put("deptname", "开发部");
        map.put("age", 12);
        map.put("salary", 2000.0d);

        List list = new ArrayList();

        for (int i = 0; i < 5; i++) {
            list.add(map);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //new JREmptyDataSource() --> list
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, new HashMap<String, Object>(), dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }


//    map模板
    @RequestMapping(value = "/exportPdf003")
    public void exportPdf003() throws Exception {
        String path = session.getServletContext().getRealPath("/")+"/jasper/test003.jasper";

        Map map = new HashMap();
        map.put("username", "菜10");
        map.put("deptname", "开发部");
        map.put("age", 12);
        map.put("salary", 2000.0d);

        //new JREmptyDataSource() --> list
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, map, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }


    @RequestMapping(value = "/exportPdf002")
    public void exportPdf002() throws Exception {
        String path = session.getServletContext().getRealPath("/")+"/jasper/test002.jasper";
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, new HashMap<String, Object>(), new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @RequestMapping(value = "/exportPdf001")
    public void exportPdf001() throws Exception {
        //1、获取.jasper的路径
        String path = session.getServletContext().getRealPath("/")+"/jasper/test001.jasper";

        //2、通过JasperFillManager管理器读取.jasper的文件 创建jrprint

        JasperPrint jasperPrint = JasperFillManager.fillReport(path, new HashMap<String, Object>(), new JREmptyDataSource());

        //3、通过JasperExportManager管理器输出pdf文件
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }*/
}
