package cn.xn.freamwork.util;

import cn.xn.freamwork.dto.BaseResponseDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回状态工具类
 * @author lcl 2014/07/23
 * @version 1.0.0
 */
public class ResponseUtils
{

    /** ================== 返回状态KEY ================== */
    public final static String R_KEY_STATE = "resultState";
    public final static String R_KEY_CODE  = "resultCode";
    public final static String R_KEY_MS    = "resultMs";
    public final static String R_KEY_DATA  = "resultData";

    /** ================== 返回状态编码 ================== */
    public final static Integer R_SUCCESS_STATE     = 1;
    public final static String  R_SUCCESS_CODE      = "200";
    public final static String  R_SUCCESS_MS        = "接口调用正常返回";

    public final static Integer R_FAIL_STATE        = -1;
    public final static String  R_FAIL_CODE         = "500";
    public final static String  R_FAIL_MS           = "接口调用异常";


    public final static Integer R_PARAMS_ERROR_STATE    = 0;
    public final static String  R_PARAMS_ERROR_CODE     = "300";
    public final static String  R_PARAMS_ERRORL_MS      = "调用接口参数异常";



    /**
     * =============== 返回单个实体时,附加返回状态 ==================
     */
    public static BaseResponseDTO success(BaseResponseDTO res) {
        if (null == res)
            res = new BaseResponseDTO();

        res.setState(R_SUCCESS_STATE);
        res.setCode(R_SUCCESS_CODE);
        res.setMsg(R_SUCCESS_MS);
        return res;
    }

    public static BaseResponseDTO errorParams(BaseResponseDTO res) {
        if (null == res)
            res = new BaseResponseDTO();

        res.setState(R_PARAMS_ERROR_STATE);
        res.setCode(R_PARAMS_ERROR_CODE);
        res.setMsg(R_PARAMS_ERRORL_MS);
        return res;
    }

    public static BaseResponseDTO fail(BaseResponseDTO res) {
        if (null == res)
            res = new BaseResponseDTO();

        res.setState(R_FAIL_STATE);
        res.setCode(R_FAIL_CODE);
        res.setMsg(R_FAIL_MS);
        return res;
    }


    public static BaseResponseDTO customResponse(BaseResponseDTO res, Integer state, String code, String ms) {
        if (null == res)
            res = new BaseResponseDTO();

        res.setState(state);
        res.setCode(code);
        res.setMsg(ms);
        return res;
    }

    /**
     * =============== 多对象返回,附加返回状态 ==================
     */
    public static Map<String, Object> successResult(Map<String, Object> modelMap, Object data) {
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put(R_KEY_STATE, R_SUCCESS_STATE);
        modelMap.put(R_KEY_CODE, R_SUCCESS_CODE);
        modelMap.put(R_KEY_MS, R_SUCCESS_MS);
        modelMap.put(R_KEY_DATA, data);
        return modelMap;
    }

    public static Map<String, Object> failResult(Map<String, Object> modelMap) {
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put(R_KEY_STATE, R_FAIL_STATE);
        modelMap.put(R_KEY_CODE, R_SUCCESS_CODE);
        modelMap.put(R_KEY_MS, R_SUCCESS_MS);
        modelMap.put(R_KEY_DATA, null);
        return modelMap;
    }


    /**
     * 调用接口是否正常返回
     * @param result
     * @return
     */
    public static boolean isExec(Map<String, Object> result) {

        return ((Integer) result.get(R_KEY_STATE) > 0
                && result.get(R_KEY_DATA) != null) ? true:false;
    }


    /**
     * easyui 数据格式返回
     * @param modelMap
     * @param data
     * @return
     */
    public static Map<String, Object> easyuiDataResult(Map<String, Object> modelMap, Object data, int total) {
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put("total", total);
        modelMap.put("rows", data);
        return modelMap;
    }

}
