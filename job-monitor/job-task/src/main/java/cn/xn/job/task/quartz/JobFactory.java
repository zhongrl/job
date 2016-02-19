package cn.xn.job.task.quartz;

import cn.xn.job.service.console.model.ScheduleJobDO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述: 定时任务工厂实现
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2015/08/08.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
//@DisallowConcurrentExecution
public class JobFactory implements Job {

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(JobFactory.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.info("JobFactory execute");

        try {
            //Thread.sleep(10000);
            ScheduleJobDO scheduleJob = (ScheduleJobDO) context.getMergedJobDataMap().get(
                    ScheduleJobDO.JOB_PARAM_KEY);

            System.out.println("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
