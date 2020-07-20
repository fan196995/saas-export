package com.itheima.web.controller;


import com.github.pagehelper.util.StringUtil;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

/*	@RequestMapping(value = "/login",name = "登录")
	public String login(String email,String password) {
        //传入参数
        if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)){
            request.setAttribute("error","请输入用户名或密码");
            return "forward:login.jsp";
        }
        //通过email查询用户
        User user =userService.findByEmail(email);

        //查到了 user!=null，数据库密码 == 输入的密码
        if (user!=null && user.getPassword().equals(password)){

            //查找用户对应的模块
            List<Module> moduleList = moduleService.findModuleByUser(user);
            session.setAttribute("modules",moduleList);

            //写入session，跳转到home/main
            session.setAttribute("loginUser",user);
            return "home/main";
        }else {
            //未查询到
            request.setAttribute("error","用户名或密码不正确！");
            return "forward:login.jsp";
        }
	}*/

    //通过shiro进行登录
    @RequestMapping(value = "/login",name = "登录")
    public String login(String email,String password) {
        try {
            Subject subject = SecurityUtils.getSubject();
            //创建token
            UsernamePasswordToken upToken = new UsernamePasswordToken(email,password);
            subject.login(upToken);//两个方法：1、身份认证；2、密码比较

//        通过subject取到安全数据(user对象存入安全数据)
            User user = (User) subject.getPrincipal();
            if (user != null) {
                //放入session
                session.setAttribute("loginUser",user);
                //查找用户对应的模块
                List<Module> moduleList = moduleService.findModuleByUser(user);
                session.setAttribute("modules",moduleList);
                return "home/main";
            }else {
                request.setAttribute("error", "用户不存在！");
                return "forward:login.jsp";
            }
        } catch (AuthenticationException e) {
            //未查询到
            request.setAttribute("error","用户名或密码不正确！");
            return "forward:login.jsp";
        }
    }

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
