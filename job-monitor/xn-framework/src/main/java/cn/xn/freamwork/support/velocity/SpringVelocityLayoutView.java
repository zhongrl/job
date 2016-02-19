/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.support.velocity;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.context.ChainedContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Vecity模板引擎视图定义
 *
 * @author lcl 2015/07/01
 * @version 1.0.0
 */
public class SpringVelocityLayoutView extends org.springframework.web.servlet.view.velocity.VelocityLayoutView {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChainedContext chainedContext = (ChainedContext) super.createVelocityContext(model, request, response);
        ApplicationContext applicationContext = getApplicationContext();
        Map<String,VelocityAccessBean> map = applicationContext.getParent().getBeansOfType(VelocityAccessBean.class);
        Map<String,Object> toolMap = chainedContext.getToolbox();
        for (Map.Entry<String, VelocityAccessBean> stringObjectEntry : map.entrySet()) {
            toolMap.put(stringObjectEntry.getKey(),stringObjectEntry.getValue());
        }
        chainedContext.setToolbox(toolMap);
        logger.warn("name:{}", chainedContext.getToolbox());
        return chainedContext;
    }


}
