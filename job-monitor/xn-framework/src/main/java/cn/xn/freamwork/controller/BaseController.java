package cn.xn.freamwork.controller;

import cn.xn.freamwork.model.QueryPage;
import cn.xn.freamwork.model.command.SearchCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础控制类
 * @author lcl 2014/07/30
 * @version 1.0.0
 */
public class BaseController
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String RESULT_KEY_ERROR = "error";
    private static final String R_KEY_CODE = "code";
    private static final String R_KEY_MSG  = "msg";

    /**
     * 返回值KEY
     */
    private static final String RESULT_DATA_KEY         = "rows";
    private static final String RESULT_DATA_PAGE        = "currentPage";
    private static final String RESULT_DATA_TOTAL       = "total";
    private static final String RESULT_DATA_PAGENUM     = "pageNum";

    private static final String RESULT_DATA_TRADESTART      = "tradeStart";
    private static final String RESULT_DATA_TRADEEND        = "tradeEnd";

    /**
     * 分页参数返回
     * @param modelMap
     * @param data
     * @param currentPage
     * @param total
     * @return
     */
    protected ModelMap resultPageModel(ModelMap modelMap, Object data
                            , Integer currentPage, Integer total) {

        modelMap.put(RESULT_DATA_KEY, data);
        modelMap.put(RESULT_DATA_PAGE, currentPage);
        modelMap.put(RESULT_DATA_TOTAL, total);
        return modelMap;
    }

    protected ModelMap resultPageModel(ModelMap modelMap, QueryPage qp) {
        if (null == qp)
            return modelMap;

        modelMap.put(RESULT_DATA_KEY, qp.getData());
        modelMap.put(RESULT_DATA_PAGE, qp.getCurrentPage());
        modelMap.put(RESULT_DATA_TOTAL, qp.getTotal());
        modelMap.put(RESULT_DATA_PAGENUM, qp.getPageNum());
        return modelMap;
    }

    protected ModelMap resultPageModel(ModelMap modelMap, QueryPage qp, String dataKeyName) {
        if (null == qp)
            return modelMap;

        modelMap.put(dataKeyName, qp.getData());
        modelMap.put(RESULT_DATA_PAGE, qp.getCurrentPage() == null ? 1:qp.getCurrentPage());
        modelMap.put(RESULT_DATA_TOTAL, qp.getTotal() == null ? 1:qp.getTotal());
        modelMap.put(RESULT_DATA_PAGENUM, qp.getPageNum() == null ? 1:qp.getPageNum());
        return modelMap;
    }

    protected ModelMap resultPageModel(ModelMap modelMap, QueryPage qp, SearchCommand common) {

        this.resultPageModel(modelMap, qp);
        modelMap.put(RESULT_DATA_TRADESTART, common.getTradeStart());
        modelMap.put(RESULT_DATA_TRADEEND, common.getTradeEnd());
        return modelMap;
    }

    protected ModelMap resultPageModel(ModelMap modelMap, Object data) {

        return this.resultPageModel(modelMap, data, 1, 10);
    }

    /**
     * 判断是否有错，把有错的放到model里面
     * @param command
     * @param result
     * @param modelMap
     * @return
     */
    protected boolean hasError(Object command, BindingResult result, ModelMap modelMap){

        if (logger.isInfoEnabled())
            logger.info("command:{}", command);

        if (result.hasErrors()) {
            modelMap.put("result", result);
            modelMap.put("command", command);
            return true;
        }

        return false;
    }

    /**
     * 放入错误消息
     * @param modelMap
     * @param validateMsg
     * @return
     */
    protected ModelMap putErrorMsg(ModelMap modelMap, String validateMsg){
        Map<String, Object> vailMsg = new HashMap<String, Object>();
        vailMsg.put(this.R_KEY_MSG, validateMsg);

        modelMap.put(this.RESULT_KEY_ERROR, vailMsg);
        return modelMap;
    }


}
