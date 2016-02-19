package cn.xn.job.task.product;

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
import com.xiaoniuapp.product.common.emums.transfer.TransferProductStatus;
import com.xiaoniuapp.product.common.emums.transfer.TransferRecordStatus;
import com.xiaoniuapp.product.service.transfer.ITransferProductAdminService;
import com.xiaoniuapp.product.service.transfer.vo.request.TransferProductAdminQuery;
import com.xiaoniuapp.product.service.transfer.vo.request.TransferRecordAdminQuery;
import com.xiaoniuapp.product.service.transfer.vo.result.TransferProductVo;
import com.xiaoniuapp.product.service.transfer.vo.result.TransferRecordVo;

/**
 * @Description 转让投资到期款处理
 * @author lifeng
 * @date 2016年1月11日 下午7:03:04
 * @copyright www.xiaoniuapp.com Inc. All rights reserved.
 */
public class TransferExpiredRefundTask extends BaseJob {

	private Logger logger = LoggerFactory.getLogger(TransferExpiredRefundTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			if (isConcurrentExecute(context)) {
				logger.info("转让产品[到期处理]定时任务正在执行......");
				return;
			}
		} catch (SchedulerException e) {
			logger.error("转让产品[到期处理]定时任务并发执行判断异常", e);
			return;
		}
		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
		ScheduleJobDO scheduleJob = (ScheduleJobDO) mergedJobDataMap.get(ScheduleJobDO.JOB_PARAM_KEY);
		before(scheduleJob);

		try {
			ITransferProductAdminService iTransferProductAdminService = (ITransferProductAdminService) SpringContextHolder
					.getBean("iTransferProductAdminService");
			transferProductExpired(iTransferProductAdminService);
			transferInvestRefund(iTransferProductAdminService);
		} catch (Exception e) {
			error(scheduleJob, e);
		}

		after(scheduleJob);
	}

	/**
	 * 转让产品到期处理
	 * 
	 * @param iTransferProductAdminService
	 */
	private void transferProductExpired(ITransferProductAdminService iTransferProductAdminService) {
		try {
			DataPage<TransferProductVo> page = new DataPage<TransferProductVo>();
			TransferProductAdminQuery query = new TransferProductAdminQuery();
			query.setStatus(TransferProductStatus.SELLOUT.getIndex());
			PageResult<TransferProductVo> result = iTransferProductAdminService.queryTransferProduct(query, page);
			logger.info("转让产品[到期处理]数据查询.result={}", result);
			if (result.getPage().getTotalCount() <= 0) {
				return;
			}
			while (result.getPage().getTotalCount() > 0) {
				for (TransferProductVo vo : result.getPage().getList()) {
					try {
						BaseResult baseResult = iTransferProductAdminService.handleTransferProductExpire(vo.getFid());
						logger.info("转让产品[到期处理].result={},TransferProductVo={}", baseResult, vo);
					} catch (Exception e) {
						logger.info("转让产品[到期处理]异常.TransferProductVo={}", vo, e);
					}
				}
				if (result.getPage().hasNext()) {
					result = iTransferProductAdminService.queryTransferProduct(query, page);
					logger.info("转让产品[到期处理]数据查询.result={}", result);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			logger.error("转让产品[到期处理]异常.", e);
		}
	}

	/**
	 * 转让投资回款处理
	 * 
	 * @param iTransferProductAdminService
	 */
	private void transferInvestRefund(ITransferProductAdminService iTransferProductAdminService) {
		try {
			DataPage<TransferRecordVo> page = new DataPage<TransferRecordVo>();
			TransferRecordAdminQuery query = new TransferRecordAdminQuery();
			query.setStatus(TransferRecordStatus.WORKING.getIndex());
			PageResult<TransferRecordVo> result = iTransferProductAdminService.queryTransferRecord(query, page);
			logger.info("转让产品[回款处理]待回款记录.result={}", result);
			if (result.getPage().getTotalCount() <= 0) {
				return;
			}
			while (result.getPage().getTotalCount() > 0) {
				for (TransferRecordVo vo : result.getPage().getList()) {
					BaseResult baseResult = iTransferProductAdminService.handleTransferInvestRefund(vo.getFid());
					logger.info("转让产品[回款处理],result={},vo={}", baseResult, vo);
				}
				if (result.getPage().hasNext()) {
					result = iTransferProductAdminService.queryTransferRecord(query, page);
					logger.info("转让产品[回款处理]待回款记录.result={}", result);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			logger.error("转让产品[回款处理]异常.", e);
		}

	}

}
