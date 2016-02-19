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
import com.xiaoniuapp.product.service.task.IProductAmountsLimitTask;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 功能描述: 新手标额度调整
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2016/01/08.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class NewUserMarkAmountLimitTask extends BaseJob{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);

        before(scheduleJob);
        try {
            IProductAmountsLimitTask productAmountsLimit = SpringContextHolder.getBean("productAmountsLimitTask");
            productAmountsLimit.newUserMarkAmountsLimit();
        } catch (Exception e) {
            error(scheduleJob, e);
        }
        after(scheduleJob);
    }

}
