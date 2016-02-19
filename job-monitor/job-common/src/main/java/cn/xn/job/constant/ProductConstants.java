package cn.xn.job.constant;

public class ProductConstants {

	//交易类型
	public static final String RECHARGE         = "0";//充值
	public static final String WITHDRAWCASH     = "1";//提现
	public static final String INVEST           = "2";//投资
	public static final String payments         = "3";//回款
	public static final String FAILWITHDRAWCASH = "4";//提现失败回充
	public static final String SUCCESSWITHDRAWCASH = "5";//提现成功



    /**======================================
     * 产品状态，1：待审核（数据初始化），2：审核通过（上架），
     * 3 募集完成 4. 还款中 5（已到期）下架，
     * 6 已还款 ，7 已删除，8驳回
     *======================================*/
    public static final Integer PRODUCT_STATUS_1 = new Integer(1);// 待审核（数据初始化）
    public static final Integer PRODUCT_STATUS_2 = new Integer(2);// 审核通过（上架）
    public static final Integer PRODUCT_STATUS_3 = new Integer(3);// 募集完成
    public static final Integer PRODUCT_STATUS_4 = new Integer(4);// 还款中
    public static final Integer PRODUCT_STATUS_5 = new Integer(5);// （已到期）下架
    public static final Integer PRODUCT_STATUS_6 = new Integer(6);// 已还款
    public static final Integer PRODUCT_STATUS_7 = new Integer(7);// 已删除
    public static final Integer PRODUCT_STATUS_8 = new Integer(8);// 驳回



    /** 产品起息规则 **/
    public static final String PRODUCT_INTEREST_WAY_1 = "1";// 购买当日
    public static final String PRODUCT_INTEREST_WAY_2 = "2";// 购买次日
    public static final String PRODUCT_INTEREST_WAY_3 = "3";//募集完成当日
    public static final String PRODUCT_INTEREST_WAY_4 = "4";//募集完成次日
    public static final String PRODUCT_INTEREST_WAY_5 = "5";// 指定日期

    /** 是否浮动利率 **/
    public static final Integer PRODOCUT_IS_FLOW_1 = new Integer(1);// 固定利率
    public static final Integer PRODOCUT_IS_FLOW_2 = new Integer(2);// 浮动利率


    /** 期限类型，1/天数，2/自然月 **/
    public static final Integer DEAD_LINE_TYPE_1 = new Integer(1);
    public static final Integer DEAD_LINE_TYPE_2 = new Integer(2);

    /** 产品类型，1：天添牛(101/双十一特供标, 102/资管一号, 103/稳赢牛, 104/天添牛降息, 105/月息宝)
     * 2：指数牛
     * 3：活期宝
     * 4: 惠房宝
     * 5: 信托一号(502/天添牛信托2号)
     * 6: BS2P类产品 (601/自然生长, 602/0元购6S)
     **/
    public static final Integer PRODUCT_TYPE_1 = 1;
    public static final Integer PRODUCT_TYPE_105 = 105;
    public static final Integer PRODUCT_TYPE_2 = 2;
    public static final Integer PRODUCT_TYPE_3 = 3;
    public static final Integer PRODUCT_TYPE_4 = 4;




    /** 产品位置 **/
    public static final Integer PRODUCT_IS_SHOW_1 = new Integer(1);// 显示在列表
    public static final Integer PRODUCT_IS_SHOW_2 = new Integer(2);// 显示在首页和列表
    public static final Integer PRODUCT_IS_SHOW_3 = new Integer(3);// 显示在首页
    public static final Integer PRODUCT_IS_SHOW_4 = new Integer(4);// 隐藏

}
