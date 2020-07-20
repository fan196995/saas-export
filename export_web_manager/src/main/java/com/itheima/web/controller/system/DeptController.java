package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.domain.company.Company;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/29 13:22
 */
@Controller
@RequestMapping(value = "/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/list",name = "分页查询部门列表")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PageInfo pageInfo = deptService.findAll(companyId, page, size);
        request.setAttribute("page", pageInfo);
        return "system/dept/dept-list";
    }

    @RequestMapping(value = "/toAdd",name = "跳转添加页面")
    public String toAdd(){
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);
        return "system/dept/dept-add";
    }

    @RequestMapping(value = "/edit",name = "添加部门转到list")
    public String edit(Dept dept){
        //企业对应的部门，应为其设置企业ID和企业名字
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        //判断id是否为空 ,为空则添加
        if (StringUtil.isEmpty(dept.getId())){
            deptService.save(dept);
        }else {
            deptService.update(dept);
        }
        return "redirect: /system/dept/list.do";
    }

    @RequestMapping(value = "/toUpdate",name = "编辑部门")
    public String findById(String id){
        Dept dept = deptService.findById(id);
        request.setAttribute("dept",dept);

        List<Dept> list= deptService.findAll(companyId);
        request.setAttribute("deptList",list);

        return "system/dept/dept-update";
    }

    @RequestMapping(value = "/delete",name = "删除部门")
    public String delete(String id){
        deptService.delete(id);
        return "redirect: /system/dept/list.do";
    }

}
