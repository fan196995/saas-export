package com.itheima.web.controller.company;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.common.entity.PageResult;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/28 15:23
 */

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;


    @RequestMapping(value = "/list")
    @RequiresPermissions(value = "企业管理")  //shiro授权的监听器 注解形式
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
/*        PageResult pageResult = companyService.findByPage(page, size);
        request.setAttribute("page", pageResult);*/

//        使用pagehelper插件
        PageInfo pageInfo= companyService.findByHelper(page, size);
//        System.out.println("pagehelper分页："+pageInfo.getList().size());
        request.setAttribute("page", pageInfo);
        return "company/company-list";
    }


    @RequestMapping(value = "/save")
    public String save(Date date){
        System.out.println(date);
        return "success";
    }

    @RequestMapping(value = "/toAdd",name = "新建企业")
    public String toAdd(){
        return "company/company-add";
    }


    @RequestMapping(value = "/edit",name = "保存企业信息")
    public String edit(Company company){
//        System.out.println(company);
        //判断id是否为空 ,为空则添加
        if (StringUtil.isEmpty(company.getId())){
            companyService.save(company);
        }else {
            companyService.update(company);
        }
        return "redirect: /company/list.do";
    }

    @RequestMapping(value = "/toUpdate",name = "编辑企业信息")
    public String toUpdate(String id){
//        System.out.println("id:"+id);
        Company company =companyService.findById(id);
        request.setAttribute("company",company);
        return "company/company-update";
    }

    @RequestMapping(value = "/delete", name = "删除一条企业信息")
    public String delete(String id) {
//        System.out.println("我接收到了一个id：" + id);
        companyService.delete(id);
        return "redirect: /company/list.do";
    }

}
