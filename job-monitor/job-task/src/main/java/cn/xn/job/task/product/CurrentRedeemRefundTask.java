package cn.xn.job.task.product;

import java.util.Date;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xn.freamwork.util.SpringContextHolder;
import cn.xn.job.service.console.model.ScheduleJobDO;
import cn.xn.job.task.base.BaseJob;

import com.xiaoniuapp.common.page.DataPage;
import com.xiaoniuapp.common.result.BaseResult;
import com.xiaoniuapp.common.result.ListResult;
import com.xiaoniuapp.common.result.PageResult;
import com.xiaoniuapp.common.utils.DateUtils;
import com.xiaoniuapp.product.common.emums.redeem.RedeemRequestStatus;
import com.xiaoniuapp.product.service.redeem.ICurrentRedeemAdminService;
import com.xiaoniuapp.product.service.redeem.vo.result.CurrentRedeemRequestVo;

/**
 * @Description 活期宝赎回回款定时任务
 * @author lifeng
 * @date 2016年1月12日 下午7:10:13
 * @copyright www.xiaoniuapp.com Inc. All rights reserved.
 */
public class CurrentRedeemRefundTask extends BaseJob {

	private Logger logger = LoggerFactory.getLogger(CurrentRedeemRefundTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			if (isConcurrentExecute(context)) {
				logger.info("活期宝赎回申请[回款处理]定时任务正在执行......");
				return;
			}
		} catch (SchedulerException e) {
			logger.error("活期宝赎回申请[回款处理]定时任务并发执行判断异常", e);
			return;
		}

		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
		ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);
		before(scheduleJob);

		try {
			ICurrentRedeemAdminService iCurrentRedeemAdminService = (ICurrentRedeemAdminService) SpringContextHolder
					.getBean("iCurrentRedeemAdminService");
			resetRequestRefundWorkingToWait(iCurrentRedeemAdminService);
			DataPage<CurrentRedeemRequestVo> page = new DataPage<CurrentRedeemRequestVo>();
			page.setPageIndex(1);
			page.setPageSize(100);
			Date endTime = DateUtils.getTheDayZero();
			PageResult<CurrentRedeemRequestVo> result = iCurrentRedeemAdminService.queryCurrentRedeemRequest(
					RedeemRequestStatus.WAIT.getIndex(), null, endTime, page);
			logger.info("活期宝赎回申请[回款处理],查询待回款申请.result={}", result);
			if (result.getPage().getTotalCount() <= 0) {
				return;
			}
			while (result.getPage().getTotalCount() > 0) {
				List<CurrentRedeemRequestVo> currentRedeemRequestVos = result.getPage().getList();
				for (CurrentRedeemRequestVo vo : currentRedeemRequestVos) {
					ListResult<String> listResult = iCurrentRedeemAdminService.findCurrentRedeemLogId(vo.getFid());
					if (listResult.getList().isEmpty()) {
						iCurrentRedeemAdminService.updateCurrentRedeemRequestStatus(vo.getFid(),
								RedeemRequestStatus.FAILURE.getIndex(), null, "投资记录赎回流水不存在");
						continue;
					}
					int retryTimes = vo.getRetryTimes() + 1;
					iCurrentRedeemAdminService.updateCurrentRedeemRequestStatus(vo.getFid(),
							RedeemRequestStatus.WORKING.getIndex(), retryTimes, null);
					int failure = 0;
					for (String redeemLogId : listResult.getList()) {
						BaseResult baseResult = iCurrentRedeemAdminService.handleCurrentRedeemRefund(redeemLogId);
						logger.info("活期宝赎回申请[赎回流水]回款.result={},redeemLogId={}", baseResult, redeemLogId);
						if (baseResult.isFailure()) {
							failure++;
						}
					}
					if (failure == 0) {
						iCurrentRedeemAdminService.updateCurrentRedeemRequestStatus(vo.getFid(),
								RedeemRequestStatus.FINISH.getIndex(), retryTimes, "回款成功");
						continue;
					}
					if (retryTimes > 2) {
						iCurrentRedeemAdminService.updateCurrentRedeemRequestStatus(vo.getFid(),
								RedeemRequestStatus.FAILURE.getIndex(), retryTimes, "赎回流水回款失败记录数=" + failure);
					}
				}
				if (result.getPage().hasNext()) {
					result = iCurrentRedeemAdminService.queryCurrentRedeemRequest(RedeemRequestStatus.WAIT.getIndex(),
							null, endTime, page);
					logger.info("活期宝赎回申请[回款处理],查询待回款申请.result={}", result);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			error(scheduleJob, e);
		}
		after(scheduleJob);
	}

	/**
	 * 回款中状态的赎回申请重置为待回款
	 */
	private void resetRequestRefundWorkingToWait(ICurrentRedeemAdminService iCurrentRedeemAdminService) {
		DataPage<CurrentRedeemRequestVo> page = new DataPage<CurrentRedeemRequestVo>();
		page.setPageIndex(1);
		page.setPageSize(100);
		PageResult<CurrentRedeemRequestVo> result = iCurrentRedeemAdminService.queryCurrentRedeemRequest(
				RedeemRequestStatus.WORKING.getIndex(), null, null, page);
		logger.info("活期宝赎回申请[重置回款状态]回款中改为待回款,查询回款中数据.result={}", result);
		if (result.getPage().getTotalCount() <= 0) {
			return;
		}
		while (result.getPage().getTotalCount() > 0) {
			for (CurrentRedeemRequestVo vo : result.getPage().getList()) {
				logger.info("活期宝赎回申请[重置回款状态]回款中改为待回款.CurrentRedeemRequestVo={}", vo);
				iCurrentRedeemAdminService.updateCurrentRedeemRequestStatus(vo.getFid(),
						RedeemRequestStatus.WAIT.getIndex(), null, null);
			}
			if (result.getPage().hasNext()) {
				result = iCurrentRedeemAdminService.queryCurrentRedeemRequest(RedeemRequestStatus.WORKING.getIndex(),
						null, null, page);
				logger.info("活期宝赎回申请[重置回款状态]回款中改为待回款,查询回款中数据.result={}", result);
			} else {
				break;
			}
		}
	}
}
