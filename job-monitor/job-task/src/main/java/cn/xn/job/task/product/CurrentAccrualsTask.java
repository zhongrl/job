/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.job.task.product;

import cn.xn.freamwork.util.SpringContextHolder;
import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.task.base.BaseJob;
import com.xiaoniuapp.product.service.task.ICurrentAccrualsTask;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 功能描述: 活期每日收益计算
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2016/01/04.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class CurrentAccrualsTask extends BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);

        before(scheduleJob);
        try {
            ICurrentAccrualsTask currentAccrualsTask = SpringContextHolder.getBean("currentAccrualsTask");
            currentAccrualsTask.dataHandle();
        } catch (Exception e) {
            error(scheduleJob, e);
        }

        after(scheduleJob);
    }
}
