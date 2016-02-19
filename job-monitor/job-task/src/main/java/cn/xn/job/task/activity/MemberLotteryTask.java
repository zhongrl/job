/**
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
package cn.xn.job.task.activity;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoniuapp.activity.member.service.IMemberLotteryService;

import cn.xn.freamwork.util.LogLib;
import cn.xn.freamwork.util.SpringContextHolder;

/**
 * 
 * 项目名称：job-task
 * 
 * 类名称：MemberLotteryTask
 * 
 * 类描述： 会员抽活期宝利率叠加活动收益统计发放 任务
 * 
 * 创建人： Liuzhudong
 * 
 * 创建时间：2015年8月29日
 * 
 */
public class MemberLotteryTask  implements Job{

  private Logger log = LoggerFactory.getLogger(MemberLotteryTask.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    LogLib.logInfo("", "会员抽活期宝利率叠加活动收益统计发放 任务开始....");
    try {
      IMemberLotteryService memberLotteryService = SpringContextHolder.getBean("memberLotteryService");

      memberLotteryService.executeJob();
      
      LogLib.logInfo("", "会员抽活期宝利率叠加活动收益统计发放 任务调用成功");
    } catch (Exception e) {
      LogLib.logInfo("", "会员抽活期宝利率叠加活动收益统计发放 任务调用失败");
      this.log.error("会员抽活期宝利率叠加活动收益统计发放 任务调用失败", e);
    }
    
  }

}
