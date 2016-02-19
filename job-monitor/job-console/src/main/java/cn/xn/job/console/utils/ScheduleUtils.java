package cn.xn.job.console.utils;

import cn.xn.job.console.exceptions.ScheduleException;
import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.task.base.BaseJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 定时任务辅助类
 * 
 */
public class ScheduleUtils {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleUtils.class);

    /**
     * 获取触发器key
     * 
     * @param jobName
     * @param jobGroup
     * @return
     */
    public static TriggerKey getTriggerKey(String jobName, String jobGroup) {

        return TriggerKey.triggerKey(jobName, jobGroup);
    }

    /**
     * 获取表达式触发器
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @return cron trigger
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            return (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            LOG.error("获取定时任务CronTrigger出现异常", e);
            throw new ScheduleException("获取定时任务CronTrigger出现异常");
        }
    }

    /**
     * 创建任务
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static CronTrigger createScheduleJob(Scheduler scheduler, ScheduleJobDO scheduleJob) {

        return createScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
            scheduleJob.getCronExpression(), false, scheduleJob);
    }

    /**
     * 创建定时任务
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param isSync the is sync
     * @param param the param
     */
    public static CronTrigger createScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression, boolean isSync, ScheduleJobDO param) {

        ScheduleJobDO scheduleJob = (ScheduleJobDO) param;
        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class) Class.forName(scheduleJob.getJobClass());
        } catch (ClassNotFoundException e) {
            LOG.error("======>>> createScheduleJob Class not found! JobName: ["+ scheduleJob.getJobName() +"], err:"+ e);
            return null;
        }

        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleJobDO.JOB_PARAM_KEY, param);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = null;
            if (!StringUtils.isEmpty(cronExpression)) {
                scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            } else {
                if (null != param.getDayOfMonth()) {
                    scheduleBuilder = CronScheduleBuilder.monthlyOnDayAndHourAndMinute(param.getDayOfMonth(),
                            param.getHour(), param.getMinute());
                } else if (null != param.getMinute() && null != param.getHour()) {
                    scheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute(param.getHour(), param.getMinute());
                } else {
                    int minute = param.getMinute().intValue();
                    int second = param.getSecond().intValue();

                    String newCronExpression = "";
                    if (minute > 0 && second > 0) {
                        newCronExpression = String.format("%d %d * * * ?", second, minute);
                    } else {
                        if (minute > 0) {
                            newCronExpression = String.format("0 */%d * * * ?", minute);
                        } else if (second > 0) {
                            //秒
                            newCronExpression = String.format("*/%d * * * * ?", second);
                        }
                    }

                    scheduleBuilder = CronScheduleBuilder.cronSchedule(newCronExpression);
                }

            }

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);

            return trigger;
        } catch (SchedulerException e) {
            LOG.error("创建定时任务失败", e);
            throw new ScheduleException("创建定时任务失败");
        }
    }

    /**
     * 运行一次任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void runOnce(Scheduler scheduler, String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("运行一次定时任务失败", e);
            throw new ScheduleException("运行一次定时任务失败");
        }
    }

    /**
     * 暂停任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("暂停定时任务失败", e);
            throw new ScheduleException("暂停定时任务失败");
        }
    }

    /**
     * 获取jobKey
     *
     * @param jobName the job name
     * @param jobGroup the job group
     * @return the job key
     */
    public static JobKey getJobKey(String jobName, String jobGroup) {

        return JobKey.jobKey(jobName, jobGroup);
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("暂停定时任务失败", e);
            throw new ScheduleException("暂停定时任务失败");
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJobDO scheduleJob) {
        updateScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
            scheduleJob.getCronExpression(), false, scheduleJob);
    }

    /**
     * 更新定时任务
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param isSync the is sync
     * @param param the param
     */
    public static void updateScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression, boolean isSync, Object param) {

        //同步或异步
//        Class<? extends Job> jobClass = isSync ? JobSyncFactory.class : JobFactory.class;

        try {
//            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(jobName, jobGroup));

//            jobDetail = jobDetail.getJobBuilder().ofType(jobClass).build();

            //更新参数 实际测试中发现无法更新
//            JobDataMap jobDataMap = jobDetail.getJobDataMap();
//            jobDataMap.put(ScheduleJobVo.JOB_PARAM_KEY, param);
//            jobDetail.getJobBuilder().usingJobData(jobDataMap);

            TriggerKey triggerKey = ScheduleUtils.getTriggerKey(jobName, jobGroup);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
                .build();

            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            LOG.error("更新定时任务失败", e);
            throw new ScheduleException("更新定时任务失败");
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            scheduler.deleteJob(getJobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            LOG.error("删除定时任务失败", e);
            throw new ScheduleException("删除定时任务失败");
        }
    }
}
