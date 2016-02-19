/**
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
package cn.xn.job.task.activity;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoniuapp.activity.service.base.BaseResponse;
import com.xiaoniuapp.activity.service.box.service.QuizBoxInterfaceService;

import cn.xn.freamwork.util.LogLib;
import cn.xn.freamwork.util.SpringContextHolder;

/**
 * 
 * 项目名称：job-task
 * 
 * 类名称：CreateBoxTask
 * 
 * 类描述：生成宝箱任务
 * 
 * 创建人： wangpengbo
 * 
 * 创建时间：2015年11月18日
 * 
 */
public class CreateBoxTask implements Job {
	
	public static final Logger log = LoggerFactory.getLogger(CreateBoxTask.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		LogLib.logInfo("", "生成宝箱任务开始....");
		
		try {
			QuizBoxInterfaceService quizBoxInterfaceService = SpringContextHolder.getBean("quizBoxInterfaceService");
			BaseResponse<Boolean> response = quizBoxInterfaceService.createBoxService();
			if(response.getCode()!=0){
				LogLib.logInfo("", "生成宝箱任务失败");
			}
			LogLib.logInfo("", "生成宝箱任务成功....");
		} catch (Exception e) {
			LogLib.logInfo("", "生成宝箱任务服务失败");
			log.error("生成宝箱任务服务失败", e);
		}
	}
}
