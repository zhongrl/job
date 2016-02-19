package cn.xn.job.console.controller;

import cn.xn.freamwork.controller.BaseController;
import cn.xn.freamwork.util.MD5util;
import cn.xn.job.console.model.UserInfo;
import cn.xn.job.console.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础控制类
 * @author lcl 2014/07/30
 * @version 1.0.0
 */
public class WebBaseController extends BaseController
{
    final Logger logger = LoggerFactory.getLogger(WebBaseController.class);

    @Autowired
    private IUserService userService;

    /**
     * 用户支付密码校验
     * @param payPwd
    * @return
            */
    public boolean checkPayPwd(String userName, String payPwd) {

        UserInfo userInfo = userService.findInfoAllByUser(userName);
        if (null != userInfo) {
            if (MD5util.md5(payPwd).equals(userInfo.getPaymentPwd()))
                return true;
        }

        return false;
    }


}
