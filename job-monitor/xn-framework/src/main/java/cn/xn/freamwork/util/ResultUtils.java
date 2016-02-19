package cn.xn.freamwork.util;

import cn.xn.freamwork.model.QueryPage;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回JSON格式,数据模型
 * @author lcl 2014/07/11
 * @version 1.0.0
 */
public class ResultUtils
{
    /** ================== 返回KEY ================== */
    public final static String R_KEY_CODE       = "code";
    public final static String R_KEY_SUCCESS    = "success";
    public final static String R_KEY_DATA       = "data";

    public final static String R_KEY_MSG        = "message";

    /** ================== 返回状态编码 ================== */
    public final static Integer R_SUCCESS_CODE  = 200;
    public final static boolean R_SUCCESS_MS    = true;

    public final static Integer R_FAIL_CODE     = 500;
    public final static boolean R_FAIL_MS       = false;

    public final static Integer R_ERR_CODE      = 300;
    public final static boolean R_ERR_MS        = false;

    //已登录成功状态
    public final static Integer R_LOGIN_SUCCESS_CODE  = 100;
    public final static boolean R_LOGIN_SUCCESS_MS    = true;

    /** ================== 错误消息返回 ================== */
    public final static String R_ERR_MSG_PARAMS_ERR   = "params disqualification!";

    /**
     * 模拟row数据模型
     * @param modelMap
     * @param data
     * @return
     */
    public static Map<String, Object> successResultRow(Map<String, Object> modelMap, Object data){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put(R_KEY_CODE, R_SUCCESS_CODE);
        modelMap.put(R_KEY_SUCCESS, R_SUCCESS_MS);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("rows", data);

        modelMap.put(R_KEY_DATA, dataMap);
        return modelMap;
    }


    /**
     * 分页返回
     * @param modelMap
     * @param qm
     * @return
     */
    public static Map<String, Object> successResultPage(Map<String, Object> modelMap, QueryPage qm) {

        return successResultPage(modelMap, qm.getRows(), qm.getCurrentPage(), qm.getTotal());
    }


    /**
     * 自定义分页返回
     * @param modelMap
     * @param data
     * @param currentPage
     * @param total
     * @return
     */
    public static Map<String, Object> successResultPage(Map<String, Object> modelMap,
                                                        Object data, Integer currentPage, Integer total){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put(R_KEY_CODE, R_SUCCESS_CODE);
        modelMap.put(R_KEY_SUCCESS, R_SUCCESS_MS);
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("rows", data);
        pageMap.put("currentPage", currentPage);
        pageMap.put("total", total);

        modelMap.put(R_KEY_DATA, pageMap);
        return modelMap;
    }

    /**
     * 成功消息返回
     *
     * @param modelMap
     * @param data
     * @return
     */
    public static Map<String, Object> successResult(Map<String, Object> modelMap, Object data){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put(R_KEY_CODE, R_SUCCESS_CODE);
        modelMap.put(R_KEY_SUCCESS, R_SUCCESS_MS);
        if (null != data)
            modelMap.put(R_KEY_DATA, data);
        else
            modelMap.put(R_KEY_DATA, "null");

        return modelMap;
    }

    public static Map<String, Object> successResult(){
        return successResult(null, null);
    }

    public static Map<String, Object> successResult(Object data){
        return successResult(null, data);
    }

    /**
     * 已登录消息返回
     *
     * @param modelMap
     * @param msg
     * @return
     */
    public static Map<String, Object> loginSuccessResult(Map<String, Object> modelMap, String msg){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(msg)) {
            msg = "存在异常, 请检查!";
        }

        modelMap.put(R_KEY_MSG, msg);
        modelMap.put(R_KEY_CODE, R_LOGIN_SUCCESS_CODE);
        modelMap.put(R_KEY_SUCCESS, R_LOGIN_SUCCESS_MS);
        return modelMap;
    }


    public static Map<String, Object> failResult(){
        return failResult(null);
    }

    /**
     * 处理错误返回
     * @param modelMap
     * @return
     */
    public static Map<String, Object> failResult(Map<String, Object> modelMap){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put(R_KEY_CODE, R_FAIL_CODE);
        modelMap.put(R_KEY_SUCCESS, R_FAIL_MS);
        return modelMap;
    }

    /**
     * 处理失败返回
     * @param modelMap
     * @param msg
     * @return
     */
    public static Map<String, Object> errResult(Map<String, Object> modelMap, String msg){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(msg)) {
            msg = "请求参数存在异常, 请检查!";
        }

        modelMap.put(R_KEY_MSG, msg);
        modelMap.put(R_KEY_CODE, R_ERR_CODE);
        modelMap.put(R_KEY_SUCCESS, R_ERR_MS);
        return modelMap;
    }

    public static Map<String, Object> errResult(){
        return errResult(null, "失败!");
    }

    public static Map<String, Object> errResult(String message){
        return errResult(null, message);
    }


    public static Map<String, Object> customResult(Map<String, Object> modelMap, Integer code, String message, Object data){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put(R_KEY_CODE, code);
        modelMap.put(R_KEY_SUCCESS, message);
        modelMap.put(R_KEY_DATA, data);
        return modelMap;
    }

    public static String toString(Object val) {
        if (null == val)
            return "";

        String str = val+"";
        if (str.equals("null"))
            return "";

        return str;
    }

}
