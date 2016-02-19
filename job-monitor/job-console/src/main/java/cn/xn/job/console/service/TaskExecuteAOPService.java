/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author chunlin.li https://github.com/springlin2012
 *
 */
package cn.xn.job.console.service;

import cn.xn.freamwork.util.LogLib;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 功能描述: 定时任务AOP
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2015/09/05.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
//@Aspect
//@Component("taskExecuteAOPService")
public class TaskExecuteAOPService {


    //@Before("execution(* cn.xn.job.task.quartz.JobFactory.execute(..))")
    public void beginTask(JoinPoint joinPoint) {
        LogLib.logInfo("", "========== Begin Task.天添牛计息JOB执行 ==============");

    }



    //@After("execution(* cn.xn.job.task..*.*(..))")
    public void afterTask() {
        LogLib.logInfo("", "========== End Task.天添牛计息JOB执行 ==============");



    }


    //@AfterReturning("execution(* cn.xn.job.task..*.*(..))")
    public void afterReturningTask() {
        LogLib.logInfo("", "========== SUCCESS End Task.天添牛计息JOB执行 ==============");

    }


}
