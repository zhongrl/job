package cn.xn.job.task.activity;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.xn.freamwork.util.LogLib;
import cn.xn.freamwork.util.SpringContextHolder;
import com.xiaoniuapp.activity.service.base.BaseResponse;
import com.xiaoniuapp.activity.service.redpaper.service.RedpaperService;

/**
 * 
 * 项目名称：job-task
 * 
 * 类名称：RedpaperOverdue
 * 
 * 类描述： 红包过期任务
 * 
 * 创建人： zhou'sheng
 * 
 * 创建时间：2015年8月17日 上午9:51:55
 * 
 * Copyright (c) 深圳市XXX有限公司-版权所有
 */
public class RedpaperOverdueTask implements Job {
	private Logger log = LoggerFactory.getLogger(RedpaperOverdueTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LogLib.logInfo("", "红包过期任务开始....");
		try {
			RedpaperService redpaperService = SpringContextHolder.getBean("redpaperService");

			BaseResponse<Boolean> response = redpaperService.redpaperOverdue();
			
			LogLib.logInfo("", "红包过期任务调用成功");
		} catch (Exception e) {
			LogLib.logInfo("", "红包过期任务调用失败");
			this.log.error("红包过期任务调用失败", e);
		}
		
	}

}
