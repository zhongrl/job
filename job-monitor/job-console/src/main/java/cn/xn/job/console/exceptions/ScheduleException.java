package cn.xn.job.console.exceptions;

import cn.xn.freamwork.exception.ServiceRtException;

/**
 * 功能描述: 定时任务异常
 * <p/>
 * 创建人: chunlin.li
 * <p/>
 * 创建时间: 2015/08/08.
 * <p/>
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class ScheduleException extends ServiceRtException {

    /** serialVersionUID */
    private static final long serialVersionUID = -1921648378954132894L;

    /**
     * Constructor
     */
    public ScheduleException() {
        super();
    }

    /**
     * Instantiates a new ScheduleException.
     *
     * @param e the e
     */
    public ScheduleException(Throwable e) {
        super(e);
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public ScheduleException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param code the code
     * @param message the message
     */
    public ScheduleException(String code, String message) {
        super(code, message);
    }
}
