package cn.xn.job.task.product;

import java.util.Calendar;
import java.util.Date;

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
import com.xiaoniuapp.common.result.PageResult;
import com.xiaoniuapp.common.utils.DateUtils;
import com.xiaoniuapp.product.common.emums.invest.InvestStatus;
import com.xiaoniuapp.product.service.buy.IInvestOrderAdminService;
import com.xiaoniuapp.product.service.invest.vo.result.InvestRecordVo;

/**
 * @Description 未完成支付投资订单系统补单定时任务
 * @author lifeng
 * @date 2016年1月16日 上午11:02:45
 * @copyright www.xiaoniuapp.com Inc. All rights reserved.
 */
public class InvestOrderRepayTask extends BaseJob {

	private Logger logger = LoggerFactory.getLogger(InvestOrderRepayTask.class);

	/**
	 * 未完成支付投资订单系统补单定时任务
	 * <p>
	 * 处理当前系统前3分钟的订单
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			if (isConcurrentExecute(context)) {
				logger.info("未完成支付投资订单[系统补单]定时任务正在执行");
				return;
			}
		} catch (SchedulerException e) {
			logger.error("未完成支付投资订单[系统补单]定时任务并发执行判断异常", e);
			return;
		}
		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
		ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);
		before(scheduleJob);
		try {
			IInvestOrderAdminService iInvestOrderAdminService = (IInvestOrderAdminService) SpringContextHolder
					.getBean("iInvestOrderAdminService");
			DataPage<InvestRecordVo> page = new DataPage<InvestRecordVo>();
			page.setPageIndex(1);
			page.setPageSize(100);
			PageResult<InvestRecordVo> result = iInvestOrderAdminService.queryInvestRecord(
					InvestStatus.NOPAYMENT.getIndex(), null, DateUtils.add(new Date(), Calendar.MINUTE, -3), page);
			logger.info("未完成支付投资订单[系统补单]需要补单记录数={}", result);
			if (result.getPage().getTotalCount() <= 0) {
				return;
			}
			while (result.getPage().getTotalCount() > 0) {
				for (InvestRecordVo vo : result.getPage().getList()) {
					BaseResult baseResult = iInvestOrderAdminService.handleNoPaymentInvest(vo.getFid());
					logger.info("未完成支付投资订单[系统补单]result={},InvestRecordVo={}", baseResult, vo);
				}
				if (result.getPage().hasNext()) {
					result = iInvestOrderAdminService.queryInvestRecord(InvestStatus.NOPAYMENT.getIndex(), null,
							DateUtils.add(new Date(), Calendar.MINUTE, -3), page);
					logger.info("未完成支付投资订单[系统补单]需要补单记录数={}", result);
				} else {
					logger.info("未完成支付投资订单[系统补单]本次任务完成");
					break;
				}
			}
		} catch (Exception e) {
			logger.error("未完成支付投资订单[系统补单]定时任务异常", e);
		}
		after(scheduleJob);

	}

}
