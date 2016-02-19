package cn.xn.freamwork.dto;

/**
 * 接口调用返回状态基础类
 *
 * @author lcl 2014/07/09
 */
public class BaseResponseDTO extends BaseDTO
{

    private static final long serialVersionUID = 4948378706511355585L;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 响应码
     */
    private String code;

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

    public String getCode()
    {
        return code;
    }

    public void setCode(final String code)
    {
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
