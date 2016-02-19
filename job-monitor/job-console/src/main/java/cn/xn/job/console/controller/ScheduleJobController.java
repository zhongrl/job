package cn.xn.job.console.controller;

import cn.xn.freamwork.util.DateUtils;
import cn.xn.freamwork.util.ResponseUtils;
import cn.xn.freamwork.util.ResultUtils;
import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.console.model.command.ScheduleJobCommand;
import cn.xn.job.console.service.ScheduleJobService;
import cn.xn.job.constant.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: 定时任务操作类
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2015/08/08.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@Controller
@RequestMapping("/job")
public class ScheduleJobController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** job service */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 任务页面
     *
     * @return
     */
    @RequestMapping(value = "input-schedule-job", method = RequestMethod.GET)
    public Map<String, Object> inputScheduleJob(ScheduleJobDO scheduleJobDO, ModelMap modelMap) throws Exception {

        if (scheduleJobDO.getScheduleJobId() != null) {
            ScheduleJobDO scheduleJob = scheduleJobService.get(scheduleJobDO.getScheduleJobId());
            scheduleJob.setKeywords(scheduleJobDO.getKeywords());
            modelMap.put("scheduleJobDO", scheduleJob);
            return ResultUtils.successResult(scheduleJob);
        }

        return ResultUtils.successResult();
    }

    /**
     * 删除任务
     *
     * @return
     */
    @RequestMapping(value = "delete-schedule-job", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteScheduleJob(Long scheduleJobId) throws Exception {

        scheduleJobService.delete(scheduleJobId);

        return ResultUtils.successResult();
    }

    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "run-once-schedule-job", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> runOnceScheduleJob(Long scheduleJobId) throws Exception {

        scheduleJobService.runOnce(scheduleJobId);

        return ResultUtils.successResult();
    }

    /**
     * 暂停
     *
     * @return
     */
    @RequestMapping(value = "pause-schedule-job", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pauseScheduleJob(Long scheduleJobId) throws Exception {

        scheduleJobService.pauseJob(scheduleJobId);
        return ResultUtils.successResult();
    }

    /**
     * 恢复
     *
     * @return
     */
    @RequestMapping(value = "resume-schedule-job", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> resumeScheduleJob(Long scheduleJobId) throws Exception {

        scheduleJobService.resumeJob(scheduleJobId);
        return ResultUtils.successResult();
    }

    /**
     * 保存任务
     * @param command
     * @return
     */
    @RequestMapping(value = "save-schedule-job", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveScheduleJob(HttpServletRequest reqeust,
                                               ScheduleJobDO scheduleJobDO,
                                               ScheduleJobCommand command) throws Exception {

        scheduleJobDO.setStatus(Constants.SCHEDULE_JOB_STATE_RUN);
        //填充定时任务时间
        if (null != command.getType2Hour())
            scheduleJobDO.setHour(command.getType2Hour());

        if (null != command.getType2Minute())
            scheduleJobDO.setMinute(command.getType2Minute());

        if (null != command.getType3Minute())
            scheduleJobDO.setMinute(command.getType3Minute());

        //操作
        if (scheduleJobDO.getScheduleJobId() == null) {
            if(null == scheduleJobService.insert(scheduleJobDO))
                return ResultUtils.failResult();
        } else if (StringUtils.equalsIgnoreCase(scheduleJobDO.getKeywords(), "delUpdate")){
            //直接拿keywords存一下
            scheduleJobService.delUpdate(scheduleJobDO);
        }else {
            scheduleJobService.update(scheduleJobDO);
        }

        return ResultUtils.successResult();
    }


    @RequestMapping(value = "/validate/jobClass")
    @ResponseBody
    public Map<String, Object> validateJobClass(@RequestParam(value = "jobClass") String jobClass) throws Exception {

        try {
            if (Class.forName(jobClass) == null)
                return ResultUtils.errResult("未找到定时任务执行类!");
        } catch (Exception e) {
            logger.info("not found class. "+ jobClass);
            return ResultUtils.errResult("未找到定时任务执行类!");
        }

        return ResultUtils.successResult();
    }


    @RequestMapping(value = "/validate/jobName")
    @ResponseBody
    public Map<String, Object> validateJobName(
            @RequestParam(value = "jobName", required = true) String jobName) throws Exception {

         if (scheduleJobService.existsJobName(jobName))
             return ResultUtils.successResult();

        return ResultUtils.errResult();
    }


    /**
     * 任务列表页
     * @return
     */
    @RequestMapping(value = "/qeury/showPage", method = RequestMethod.GET)
    public String listScheduleJobShow(HttpServletRequest request) throws Exception {

        return "page/list-schedule-job";
    }



    /**
     * 任务列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qeury/scheduleJobs")
    @ResponseBody
    public Map<String, Object> listScheduleJob(HttpServletRequest request) throws Exception {

        //数据库定时任务列表
        List<ScheduleJobDO> scheduleJobList = scheduleJobService.queryList(null);
        for (ScheduleJobDO job : scheduleJobList) {
            job.setGmtCreateStr(DateUtils.format(job.getGmtCreate(), DateUtils.FORMAT_TIME));

            String jobExecTimeStr = "";
            if (null == job.getDayOfMonth() && null == job.getHour()
                    && null == job.getMinute() && null == job.getSecond()) {
                jobExecTimeStr = job.getCronExpression();
            } else {
                Integer dayOfMonth = job.getDayOfMonth();
                jobExecTimeStr = (null == dayOfMonth || dayOfMonth.intValue() <= 0 ? "":"每月 "+ job.getDayOfMonth() + "/日 ");
                Integer hour = job.getHour();
                jobExecTimeStr += (null == hour ? "": "每天 "+job.getHour() +"/点 ");

                jobExecTimeStr += (StringUtils.isNotEmpty(jobExecTimeStr) ? "" : "每小时 ") +
                        (job.getMinute() == null ? "" : job.getMinute() + "/分 ");

                jobExecTimeStr += (StringUtils.isNotEmpty(jobExecTimeStr) ? "":"每分钟 ") +
                        (job.getSecond() == null ? "":job.getSecond() + "/秒");
            }
            job.setJobExecTime(jobExecTimeStr);

            job.setStatus(job.getStatus().equals("1") ? "正常" : "停止");
        }

        return ResponseUtils.easyuiDataResult(null, scheduleJobList, scheduleJobList.size());
    }



    /**
     * 任务列表页
     * @return
     */
    @RequestMapping(value = "/page/monitor", method = RequestMethod.GET)
    public String waitingJobPage(HttpServletRequest request) throws Exception {

        return "page/monitor-schedule-job";
    }

    /**
     * 待执行任务列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qeury/waitingJobs")
    @ResponseBody
    public Map<String, Object> waitingJobLists(HttpServletRequest request) throws Exception {

        //待执行任务列表
        List<ScheduleJobDO> waitingJobList = scheduleJobService.queryQuartzJobList();

        return ResponseUtils.easyuiDataResult(null, waitingJobList, CollectionUtils.isEmpty(waitingJobList) ? 0:waitingJobList.size());
    }

    /**
     * 当前运行任务列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qeury/executingJobs")
    @ResponseBody
    public Map<String, Object> executingJobList(HttpServletRequest request) throws Exception {

        //当前运行任务列表
        List<ScheduleJobDO> executingJobList = scheduleJobService.queryExecutingJobList();

        return ResponseUtils.easyuiDataResult(null, executingJobList, CollectionUtils.isEmpty(executingJobList) ? 0:executingJobList.size());
    }


}
