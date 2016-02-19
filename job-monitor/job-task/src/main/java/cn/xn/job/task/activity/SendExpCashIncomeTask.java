/**
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
package cn.xn.job.task.activity;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xn.freamwork.util.LogLib;
import cn.xn.freamwork.util.SpringContextHolder;

import com.xiaoniuapp.activity.service.redpaper.service.IExperienceCashService;

/**
 * 
 * 项目名称：job-task
 * 
 * 类名称：SendExpCashIncomeTask
 * 
 * 类描述：体验金投资到期收益派发
 * 
 * 创建人： wangpengbo
 * 
 * 创建时间：2015年11月18日
 * 
 */
public class SendExpCashIncomeTask implements Job{
	
	private Logger log = LoggerFactory.getLogger(SendExpCashIncomeTask.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
        LogLib.logInfo("", "体验金投资收益派发任务开始....");

        try {
            IExperienceCashService experienceCashService = SpringContextHolder.getBean("expCashProviderService");
            experienceCashService.sendIncome();
            LogLib.logInfo("", "体验金投资收益派发任务调用成功");
        } catch (Exception e) {
            LogLib.logInfo("", "体验金投资收益派发任务调用失败");
            this.log.error("体验金投资收益派发任务调用失败", e);
        }
		
	}

}
