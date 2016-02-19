/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误处理类
 *
 * @author lcl 2015/1/31.
 * @version 1.0.0
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value = "/404")
    public String err404() {
        return "/error/404";
    }

    @RequestMapping(value = "/500")
    public String err400() {
        return "/error/500";
    }

    @RequestMapping(value = "/csrf")
    public String errCsrf() {
        return "/error/csrf";
    }

    @RequestMapping(value = "/tip")
    public String errTip(ModelMap modelMap, HttpServletRequest request) {

        modelMap.put("message", request.getParameter("message"));
        return "/error/error_tip";
    }
}
