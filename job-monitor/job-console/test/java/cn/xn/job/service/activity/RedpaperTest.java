/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author chunlin.li https://github.com/springlin2012
 *
 */
package cn.xn.job.service.activity;

import cn.xn.job.service.BaseTest;
import com.xiaoniuapp.activity.srfx.service.redpaper.service.TimingRedpaperService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能描述:
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2016/01/14.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class RedpaperTest extends BaseTest {

    @Autowired
    private TimingRedpaperService timingRedpaperService;


    @Test
    public void redaper() {

        timingRedpaperService.createTimingRedpaper();
    }


}
