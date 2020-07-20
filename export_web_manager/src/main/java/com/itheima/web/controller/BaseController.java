package com.itheima.web.controller;

import com.itheima.domain.system.User;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    protected String companyId;
    protected String companyName;
    protected User loginUser;

    /**
     * ModelAttribute  每一个controller方法访问时，会先执行此注释下的方法
     * @param
     * @return
     */
    @ModelAttribute
    public void init(HttpServletRequest request, HttpSession session, HttpServletResponse response){
        this.request = request;
        this.session = session;
        this.response = response;

        User user = (User) session.getAttribute("loginUser");

        if (user!=null){
            this.loginUser = user;
            this.companyId = user.getCompanyId();
            this.companyName = user.getCompanyName();
        }
/*        //测试数据
        this.companyId = "1";
        this.companyName = "传智播客教育股份有限公司";*/
    }
}
