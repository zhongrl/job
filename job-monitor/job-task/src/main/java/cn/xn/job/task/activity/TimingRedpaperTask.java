package cn.xn.job.task.activity;

import cn.xn.freamwork.util.SpringContextHolder;
import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.task.base.BaseJob;
import com.xiaoniuapp.activity.srfx.service.redpaper.service.TimingRedpaperService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 项目名称：job-task
 * 
 * 类名称：TimingRedpaperTask
 * 
 * 类描述： 定时红包塞钱任务
 * 
 * 创建人： zhou'sheng
 * 
 * 创建时间：2015年11月6日 下午4:52:53
 * 
 * Copyright (c) 深圳市XXX有限公司-版权所有
 */
public class TimingRedpaperTask extends BaseJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);

		try {
            before(scheduleJob);
            TimingRedpaperService timingRedpaperService = SpringContextHolder.getBean("timingRedpaperService");
            timingRedpaperService.createTimingRedpaper();
		} catch (Exception e) {
			error(scheduleJob, e);
		}
        after(scheduleJob);
	}

}
