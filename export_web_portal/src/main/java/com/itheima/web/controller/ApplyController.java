package com.itheima.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fanbo
 * @date 2020/7/10 20:13
 */
@Controller
public class ApplyController {

    @Reference
    private CompanyService companyService;

    @RequestMapping(value = "/apply")
    //@ResponseBody返回值为1,如果不用@ResponseBody返回值为网页
    public @ResponseBody String apply(Company company){
        try {
            //调用接口 save方法保存企业
            company.setState(0);
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            return "2";
        }
    }
}
