package cn.xn.job.common.model;

import java.io.Serializable;

/**
 * 接口调用返回状态基础类
 *
 * @author lcl 2014/07/09
 */
public class BaseResponse implements Serializable
{

    private static final long serialVersionUID = -9114785921799883576L;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * @return the state
     */
    public Integer getState()
    {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(final Integer state)
    {
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(final String msg)
    {
        this.msg = msg;
    }
}
