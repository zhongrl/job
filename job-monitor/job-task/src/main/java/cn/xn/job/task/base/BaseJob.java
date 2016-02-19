/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author chunlin.li https://github.com/springlin2012
 *
 */
package cn.xn.job.task.base;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xn.freamwork.util.DateUtils;
import cn.xn.job.service.console.model.ScheduleJobDO;

/**
 * 功能描述: 定时任务基类
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2016/01/06.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public abstract class BaseJob implements Job {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void before(ScheduleJobDO scheduleJob) {
		logger.info("========== Begin Task info.[jobName:{}, date:{}] ==============", scheduleJob.getJobName(),
				DateUtils.format(new Date()));
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
		ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);

		try {
			this.before(scheduleJob);
			this.execute(context);
		} catch (Exception e) {
			this.error(scheduleJob, e);
		}

		this.after(scheduleJob);
	}

	public void after(ScheduleJobDO scheduleJob) {
		logger.info("========== End Task.[jobName:{}, date:{}] ==============", scheduleJob.getJobName(),
				DateUtils.format(new Date()));
	}

	public void error(ScheduleJobDO scheduleJob, Exception e) {
		logger.error("[jobName:{}], execute job err={}", scheduleJob.getJobName(), e.toString());
	}

	/**
	 * 定时任务并发执行判断
	 * 
	 * @param context
	 * @return true : 并发执行
	 * @throws SchedulerException
	 */
	protected synchronized boolean isConcurrentExecute(JobExecutionContext context) throws SchedulerException {
		int concurrent = 0;
		String currentJobName = context.getJobInstance().getClass().getName();
		List<JobExecutionContext> jobExecutionContexts = context.getScheduler().getCurrentlyExecutingJobs();
		for (JobExecutionContext jobExecutionContext : jobExecutionContexts) {
			String jobName = jobExecutionContext.getJobInstance().getClass().getName();
			if (currentJobName.equals(jobName)) {
				concurrent++;
			}
		}
		if (concurrent > 1) {
			return true;
		}
		return false;
	}

}
