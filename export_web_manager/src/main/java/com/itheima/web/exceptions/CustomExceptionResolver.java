package com.itheima.web.exceptions;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 异常处理类
 * @author fanbo
 * @date 2020/6/28 17:04
 */

public class CustomExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof UnauthorizedException){
            return new ModelAndView("forward:/unauthorized.jsp");
        }
        ModelAndView modelAndView = new ModelAndView();
        // /WEB-INF/pages/error.jsp
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMsg","统一异常处理的页面");
        return modelAndView;
    }
}
