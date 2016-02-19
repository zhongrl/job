/**
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
package cn.xn.job.task.activity;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoniuapp.activity.service.box.service.QuizBoxInterfaceService;

import cn.xn.freamwork.util.LogLib;
import cn.xn.freamwork.util.SpringContextHolder;

/**
 * 
 * 项目名称：job-task
 * 
 * 类名称：sendAwardUnCommit
 * 
 * 类描述：派发本期活动结束点击竞猜宝箱，未提交竞猜金额用户的1元奖励
 * 
 * 创建人： wangpengbo
 * 
 * 创建时间：2015年11月18日
 * 
 */
public class SendAwardUnCommit implements Job{
	
	private static final Logger log = LoggerFactory.getLogger(SendAwardUnCommit.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		 LogLib.logInfo("", "派发本期活动结束点击竞猜宝箱，未提交竞猜金额用户的1元奖励开始...");
		try {
			QuizBoxInterfaceService quizBoxInterfaceService = SpringContextHolder.getBean("quizBoxInterfaceService");
			quizBoxInterfaceService.sendAwardNotSubmit();
			LogLib.logInfo("", "派发本期活动结束点击竞猜宝箱，未提交竞猜金额用户的1元奖励成功...");
		} catch (Exception e) {
			LogLib.logInfo("", "派发本期活动结束点击竞猜宝箱，未提交竞猜金额用户的1元奖励异常...");
			log.error("派发本期活动结束点击竞猜宝箱，未提交竞猜金额用户的1元奖励异常...", e);
		}
		
	}

}
