package cn.xn.job.task.activity;

import cn.xn.freamwork.util.LogLib;
import cn.xn.freamwork.util.SpringContextHolder;
import com.xiaoniuapp.activity.service.base.BaseResponse;
import com.xiaoniuapp.activity.service.redpaper.service.RedpaperService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 *
 * 项目名称：activity-service
 *
 * 类名称：RedpaperOverdueApp
 *
 * 类描述： 红包过期推送消息
 *
 * 创建人： zhou'sheng
 *
 * 创建时间：2015年5月23日 上午11:25:42
 *
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class RedpaperOverdueApp implements Job {
    private Logger log = LoggerFactory.getLogger(RedpaperOverdueApp.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        LogLib.logInfo("", "红包过期推送消息任务开始....");

        try {
            RedpaperService redpaperService = SpringContextHolder.getBean("redpaperService");
            BaseResponse<Boolean> response = redpaperService.overdueForAppMessage(new Date(), 2);
        } catch (Exception e) {
            log.error("红包过期推送消息任务异常 err={}", e.toString());
        }

        LogLib.logInfo("", "红包过期推送消息任务结束....");

    }
}
