package cn.xn.job.console.service;

import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.service.console.model.ScheduleJobDOCriteria;

import java.util.List;

/**
 * 功能描述: 定时任务服务类
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2015/08/08.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public interface ScheduleJobService {

    /**
     * 初始化定时任务
     */
    public void initScheduleJob();

    /**
     * 新增
     * 
     * @param scheduleJobVo
     * @return
     */
    public Integer insert(ScheduleJobDO scheduleJobVo) throws Exception;

    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     * 
     * @param scheduleJobVo
     */
    public void update(ScheduleJobDO scheduleJobVo) throws Exception;

    /**
     * 删除重新创建方式
     * 
     * @param scheduleJobVo
     */
    public void delUpdate(ScheduleJobDO scheduleJobVo) throws Exception;

    /**
     * 删除
     * 
     * @param scheduleJobId
     */
    public void delete(Long scheduleJobId) throws Exception;

    /**
     * 运行一次任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void runOnce(Long scheduleJobId) throws Exception;

    /**
     * 暂停任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void pauseJob(Long scheduleJobId) throws Exception;

    /**
     * 恢复任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void resumeJob(Long scheduleJobId) throws Exception;

    /**
     * 获取任务对象
     * 
     * @param scheduleJobId
     * @return
     */
    public ScheduleJobDO get(Long scheduleJobId) throws Exception;

    /**
     * 查询任务列表
     * 
     * @param scheduleJob
     * @return
     */
    public List<ScheduleJobDO> queryList(ScheduleJobDO scheduleJob) throws Exception;

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
    public List<ScheduleJobDO> queryExecutingJobList() throws Exception;


    /**
     * 获取Quartz中的所有定时任务
     * @return
     * @throws Exception
     */
    public List<ScheduleJobDO> queryQuartzJobList() throws Exception;


    /**
     * 根据条件获取任务列表
     * @return
     * @throws Exception
     */
    public List<ScheduleJobDO> queryJobListByExample(ScheduleJobDOCriteria example) throws Exception;

    /**
     * 是否存在该JOB
     * @return
     * @throws Exception
     */
    public boolean existsJobName(String jobName) throws Exception;
}
