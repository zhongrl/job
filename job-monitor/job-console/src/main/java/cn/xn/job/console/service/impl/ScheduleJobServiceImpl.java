package cn.xn.job.console.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import cn.xn.job.console.mapper.ScheduleJobDOMapper;
import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.service.console.model.ScheduleJobDOCriteria;
import cn.xn.job.console.service.ScheduleJobService;
import cn.xn.job.console.utils.ScheduleUtils;
import cn.xn.job.constant.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 定时任务服务类
 *
 * Created by moker.li
 *
 */
@SuppressWarnings("all")
@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    /** 调度工厂 **/
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void initScheduleJob() {
        List<ScheduleJobDO> scheduleJobList = scheduleJobDOMapper.selectByExample(new ScheduleJobDOCriteria());
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }

        for (ScheduleJobDO scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler,
                    scheduleJob.getJobName(), scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Autowired
    private ScheduleJobDOMapper scheduleJobDOMapper;

    public Integer insert(ScheduleJobDO scheduleJob) {
        //creawte schedule job
        scheduleJob.setScheduleJobId(System.currentTimeMillis());
        CronTrigger trigger = ScheduleUtils.createScheduleJob(scheduler, scheduleJob);


        if (StringUtils.isEmpty(trigger))
            return null;

        if (StringUtils.isEmpty(scheduleJob.getCronExpression())) {
            scheduleJob.setCronExpression(trigger.getCronExpression());
        }

        //初始化任务执行时间
        Date date = trigger.getNextFireTime();
        scheduleJob.setExecTime(date);
        scheduleJob.setNextExecTime(date);

        return scheduleJobDOMapper.insert(scheduleJob);
    }


    public void update(ScheduleJobDO scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        scheduleJobDOMapper.updateByPrimaryKey(scheduleJob);
    }


    public void delUpdate(ScheduleJobDO scheduleJob) {
        //先删除JOB任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(),
                scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        scheduleJobDOMapper.updateByScheduleId(scheduleJob);
    }

    public void delete(Long scheduleJobId) {
        ScheduleJobDO scheduleJob = this.get(scheduleJobId);
        if (scheduleJob == null)
            return;

        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(),
                scheduleJob.getJobGroup());

        //删除数据
        scheduleJobDOMapper.deleteByPrimaryKey(scheduleJob.getId());
    }


    public void runOnce(Long scheduleJobId) {
        ScheduleJobDO scheduleJob = this.get(scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }


    /**
     * 暂停任务
     * @param scheduleJobId the schedule job id
     */
    public void pauseJob(Long scheduleJobId) throws Exception {
        ScheduleJobDO scheduleJob = this.get(scheduleJobId);
        if (scheduleJob == null)
            return;

        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //数据更新状态
        updateScheduleState(scheduleJob.getId(), Constants.SCHEDULE_JOB_STATE_STOP);
    }


    /**
     * 恢复任务
     * @param scheduleJobId the schedule job id
     */
    public void resumeJob(Long scheduleJobId) throws Exception {
        ScheduleJobDO scheduleJob = get(scheduleJobId);
        if (scheduleJob == null)
            return;

        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //数据更新状态
        updateScheduleState(scheduleJob.getId(), Constants.SCHEDULE_JOB_STATE_RUN);
    }



    private boolean updateScheduleState(Long id, String status) throws Exception {
        ScheduleJobDO scheduleJobNew = new ScheduleJobDO();
        scheduleJobNew.setId(id);
        scheduleJobNew.setStatus(status);

        return scheduleJobDOMapper.updateStateByPrimaryKey(scheduleJobNew)>0 ? true:false;
    }

    /**
     * 根据ID获取任务
     * @param scheduleJobId
     * @return
     */
    public ScheduleJobDO get(Long scheduleJobId) {
        ScheduleJobDOCriteria example = new ScheduleJobDOCriteria();
        example.createCriteria().andScheduleJobIdEqualTo(scheduleJobId);

        List<ScheduleJobDO> jobs = scheduleJobDOMapper.selectByExample(example);
        return (jobs != null && !jobs.isEmpty()) ? jobs.get(0):null;
    }


    public List<ScheduleJobDO> queryList(ScheduleJobDO scheduleJob) {
        ScheduleJobDOCriteria example = new ScheduleJobDOCriteria();
        example.setOrderByClause("GMT_CREATE desc");
        List<ScheduleJobDO> scheduleJobs = scheduleJobDOMapper.selectByExample(example);

        //List<ScheduleJobDO> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class, scheduleJobs);
        /*try {
            //quartz 中获取任务状态及表达式
            for (ScheduleJobDO vo : scheduleJobs) {

                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                vo.setJobTrigger(trigger.getKey().getName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }*/

        return scheduleJobs;
    }


    public List<ScheduleJobDO> queryExecutingJobList() {
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();//获取执行中的定时任务列表
            List<ScheduleJobDO> jobList = new ArrayList<ScheduleJobDO>(executingJobs.size());

            ScheduleJobDO job = null;

            for (JobExecutionContext executingJob : executingJobs) {
                job = new ScheduleJobDO();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();

                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setJobTrigger(trigger.getKey()+"");
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                //执行开始时间

                jobList.add(job);
            }
            return jobList;
        } catch (SchedulerException e) {
            return null;
        }

    }

    @Override
    public List<ScheduleJobDO> queryQuartzJobList() throws Exception {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJobDO> jobList = new ArrayList<ScheduleJobDO>();
        for (JobKey jobKey : jobKeys) {

            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                //{ NONE/没有, NORMAL/正常, PAUSED/已暂停, COMPLETE/执行完成, ERROR/异常, BLOCKED/锁住 }
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                //如果不为待执行任务
                if (triggerState != Trigger.TriggerState.NORMAL)
                    continue;

                ScheduleJobDO job = new ScheduleJobDO();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setJobTrigger(trigger.getKey()+"");
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }

        return jobList;
    }

    @Override
    public List<ScheduleJobDO> queryJobListByExample(ScheduleJobDOCriteria example) throws Exception {
        return scheduleJobDOMapper.selectByExample(example);
    }

    @Override
    public boolean existsJobName(String jobName) throws Exception {
        ScheduleJobDOCriteria example = new ScheduleJobDOCriteria();
        example.createCriteria().andJobNameEqualTo(jobName);

        List<ScheduleJobDO> result = queryJobListByExample(example);
        return (result != null && CollectionUtils.isNotEmpty(result)) ? true:false;
    }
}
