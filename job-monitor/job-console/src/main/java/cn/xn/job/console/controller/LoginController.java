/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.job.console.controller;

import cn.xn.freamwork.common.ShiroConstants;
import cn.xn.freamwork.exception.WebException;
import cn.xn.freamwork.support.shiro.ShiroPermission;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录控制
 *
 * @author lcl 2015/7/18.
 * @version 1.0.0
 */
@Controller
@RequestMapping
public class LoginController extends WebBaseController{

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() throws WebException {
        if(StringUtils.isNotEmpty(ShiroPermission.getAttribute(ShiroConstants.KEY_SHIRO_UID)))
            return "redirect:/";

        return "/login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req,
                        HttpServletResponse response,
                        ModelMap model) throws WebException {

        //是否登录
        if(StringUtils.isNotEmpty(ShiroPermission.getAttribute(ShiroConstants.KEY_SHIRO_UID)))
            return "redirect:/";

        Object failure = req.getAttribute("shiroLoginFailure");
        String exceptionClassName = (null != failure) ? String.valueOf(failure):"";
        String error = null;

        if(StringUtils.isNotEmpty(exceptionClassName)) {

            if (UnknownAccountException.class.getName().equals(exceptionClassName)
                    || AuthenticationException.class.getName().equals(exceptionClassName)
                    || exceptionClassName.indexOf(IncorrectCredentialsException.class.getName()+":") >= 0
                    || exceptionClassName.indexOf(ExcessiveAttemptsException.class.getName()) >= 0) {

                error = "用户名/密码错误";
            } else {
                String[] errMsg = exceptionClassName.split(":");
                error = (null != errMsg ? errMsg[errMsg.length-1]:"");
            }

        }

        this.putErrorMsg(model, error);
        return "/login";
    }


    @RequestMapping(value = "/logout")
    public String logout() throws Exception {

        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }

}
