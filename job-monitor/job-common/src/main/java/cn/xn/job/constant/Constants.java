/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.job.constant;


/**
 * 公共常量类
 *
 * Created by moker.li on 2015/07/06.
 * @version 1.0
 */
public class Constants {

    public static class Page {
        public static final int DEFAULT_PAGE = 1;
        public static final int DEFAULT_ROWS = 10;
    }

    /** 产品流水 **/
    public static class ProductBalanceConstant {
        public static final Integer PRODUCT_BALANCE_RECORD_TRAD_TYPE_1 = new Integer(1);// 投资
        public static final Integer PRODUCT_BALANCE_RECORD_TRAD_TYPE_2 = new Integer(2);// 回款
    }

    /** 幂等常量 **/
    public static class IdempotentConstant {
        public static final Integer IDEMPOTENT_STATUS_1 = new Integer(1);// 等待
        public static final Integer IDEMPOTENT_STATUS_2 = new Integer(2);// 处理中
        public static final Integer IDEMPOTENT_STATUS_3 = new Integer(3);// 失败
        public static final Integer IDEMPOTENT_STATUS_4 = new Integer(4);// 完成
    }


    /**======================================
     * 0/停止, 1/运行中
     *======================================*/
    public final static String SCHEDULE_JOB_STATE_RUN      =   "1";
    public final static String SCHEDULE_JOB_STATE_STOP     =   "0";
    

    /**
     * 接口状态返回
     */
    public static class ResultState {
        public static final Integer SUCCESS_CODE_0 = new Integer(0);// 成功
        public static final String SUCCESS_CODE_0_MSG = "成功";

        /**
         * Dubbo接口调用异常
         */
        public static final Integer ERROR_CODE_99002 = new Integer(99002);
        public static final String ERROR_CODE_99002_MSG = "SOA 接口调用异常";

        /**
         * 99003/参数异常编码
         */
        public static final Integer ERROR_CODE_99003 = new Integer(99003);

        /**
         * 系统处理异常编码
         */
        public static final Integer ERROR_CODE_99005 = new Integer(99005);

    }

    /**
     * 操作说明
     */
    public static class OperationState {
        public static final String BUY_PRODUCT_INSERT_INVESTRECORD_NAME = "投资";

        public static final String INVESTSUCCESS_REMARK = "购买成功";// 投资成功描述

        public static final String INVESTSUCCESS = "2";// 投资成功

        public static final String INVESTRECORD_TYPE = "TZ";// 投资记录

    }


    /**
     * 会员奖励常量
     */
    public static class MemberAward {

        /**
         * 奖励派发状态: 0/未派发,1/已派发
         */
        public static final Integer STATE_0 = new Integer(0);
        public static final Integer STATE_1 = new Integer(1);

    }


    /**
     * APP消息推送
     */
    public static final int MESSAGE_APP_TYPE_1 = 1;//活期宝赎回
    public static final int MESSAGE_APP_TYPE_2 = 2;//活期宝收益率提升
    public static final int MESSAGE_APP_TYPE_3 = 3;//定期产品到期回款


}
