package cn.xn.job.console.event;

import javax.annotation.PostConstruct;

import cn.xn.job.console.service.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 功能描述: 定时任务初始化
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2015/08/08.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@Component
public class ScheduleJobInit {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInit.class);

    /** 定时任务service */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {

        if (LOG.isInfoEnabled()) {
            LOG.info("init");
        }

        scheduleJobService.initScheduleJob();

        if (LOG.isInfoEnabled()) {
            LOG.info("end");
        }
    }

}
