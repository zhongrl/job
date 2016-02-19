/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author chunlin.li https://github.com/springlin2012
 *
 */
package cn.xn.job.service.product;

import cn.xn.job.service.BaseTest;
import cn.xn.job.task.quartz.DemoTask;
import org.junit.Test;
import org.quartz.impl.JobExecutionContextImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能描述:
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2016/01/06.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class DemoTaskTest extends BaseTest {

    @Autowired
    private DemoTask demoTask;


    @Test
    public void test() {
        try {
            demoTask.execute(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
