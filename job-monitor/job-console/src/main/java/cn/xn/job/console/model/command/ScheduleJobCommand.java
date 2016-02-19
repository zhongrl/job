/*
 * Copyright (c) 2015 xiaoniu, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.job.console.model.command;
import java.io.Serializable;

/**
 * 定时任务参数映射类
 *
 * Created by moker.li on 2015/07/04.
 * @version 1.0
 *
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class ScheduleJobCommand implements Serializable {

    /**
     * 每天时间
     */
    private Integer type2Hour;
    private Integer type2Minute;

    /**
     * 按月时间
     */
    private Integer type3Minute;
    private Integer type3Second;

    public Integer getType2Hour() {
        return type2Hour;
    }

    public void setType2Hour(Integer type2Hour) {
        this.type2Hour = type2Hour;
    }

    public Integer getType2Minute() {
        return type2Minute;
    }

    public void setType2Minute(Integer type2Minute) {
        this.type2Minute = type2Minute;
    }

    public Integer getType3Minute() {
        return type3Minute;
    }

    public void setType3Minute(Integer type3Minute) {
        this.type3Minute = type3Minute;
    }

    public Integer getType3Second() {
        return type3Second;
    }

    public void setType3Second(Integer type3Second) {
        this.type3Second = type3Second;
    }

}
