/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author chunlin.li https://github.com/springlin2012
 *
 */
package cn.xn.job.task.product;

import cn.xn.freamwork.util.SpringContextHolder;
import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.task.base.BaseJob;
import com.alibaba.fastjson.JSONObject;
import com.xiaoniuapp.product.service.task.IProductAmountsLimitTask;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 功能描述: 产品初始额度更新任务
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2016/01/19.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class ProductAmountUpdateTask extends BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);

        before(scheduleJob);
        try {
            String params = scheduleJob.getParams();
            JSONObject json = JSONObject.parseObject(params);
            Long amount= json.getLong("amount");

            IProductAmountsLimitTask productAmountsLimit = SpringContextHolder.getBean("productAmountsLimitTask");
            productAmountsLimit.updatedProductAmount(amount);
        } catch (Exception e) {
            error(scheduleJob, e);
        }
        after(scheduleJob);
    }
}