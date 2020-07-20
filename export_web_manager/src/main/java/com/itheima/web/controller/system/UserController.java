package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.Role;
import com.itheima.domain.system.User;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.RoleService;
import com.itheima.service.system.UserService;
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
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list",name = "分页查询部门列表")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PageInfo pageInfo = userService.findAll(companyId, page, size);
        request.setAttribute("page", pageInfo);
        return "system/user/user-list";
    }

    @RequestMapping(value = "/toAdd",name = "跳转添加页面")
    public String toAdd(){
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);
        return "system/user/user-add";
    }

    @RequestMapping(value = "/edit",name = "添加部门转到list")
    public String edit(User user){
        //企业对应的部门，应为其设置企业ID和企业名字
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        //判断id是否为空 ,为空则添加
        if (StringUtil.isEmpty(user.getId())){
            userService.save(user);
        }else {
            userService.update(user);
        }
        return "redirect: /system/user/list.do";
    }

    @RequestMapping(value = "/toUpdate",name = "编辑部门")
    public String findById(String id){
        User user = userService.findById(id);
        request.setAttribute("user",user);

        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);

        return "system/user/user-update";
    }

    @RequestMapping(value = "/delete",name = "删除部门")
    public String delete(String id){
        userService.delete(id);
        return "redirect: /system/user/list.do";
    }

    /**
     *
     * @param id  user.id
     * @return
     */
    @RequestMapping(value = "/roleList",name = "跳转用户管理分配角色界面")
    public String roleList (String id){
        User user = userService.findById(id);
        request.setAttribute("user",user);

        List<Role> roleList = roleService.findAll(companyId);
        request.setAttribute("roleList",roleList);

        //通过用户id查询该用户下的角色信息
        List<Role> userRoleList =roleService.findByUserId(id);

        //循环list构建userRoleStr
        String userRoleStr = "";
        for (Role role : userRoleList) {
            userRoleStr = userRoleStr + role.getId() + ",";
        }
        request.setAttribute("userRoleStr",userRoleStr);

        return "system/user/user-role";
    }

    @RequestMapping(value = "/changeRole",name = "用户角色保存并跳转页面")
    public String changeRole(String userid, String[] roleIds){
        roleService.changeRole(userid,roleIds);

        return "redirect: /system/user/list.do";
    }

}
