package cn.xn.freamwork.support.conversation;

import org.apache.commons.collections.MapUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 流程会话工具类
 *
 * @version 1.0.0
 */
public class ConversationUtil {
	public final static String FLOW_ID_DEFAULT = "default";
	public static final String CONVERSATION_ATTR_FLAG = "_hytz_conversation";


	/**
	 * @param request
	 * @param flowId
	 * @return
	 */
	public static Map<String, Object> getConversationMap(HttpServletRequest request, String flowId) {
		Map<String, Map<String, Object>> flowIdConversationMap =(Map<String, Map<String, Object>>) request.getSession().getAttribute(CONVERSATION_ATTR_FLAG);
		if(MapUtils.isEmpty(flowIdConversationMap)){
			return null;
		}

		return flowIdConversationMap.get(flowId);
	}


	/**
	 * 删除会话属性
	 *
	 * @param request
	 */
	public static void removeConversation(HttpServletRequest request,String flowId) {
		Map<String, Map<String, Object>> flowIdConversationMap =(Map<String, Map<String, Object>>) request.getSession().getAttribute(CONVERSATION_ATTR_FLAG);
		if(MapUtils.isEmpty(flowIdConversationMap)){
			return;
		}

		flowIdConversationMap.remove(flowId);
	}




	@SuppressWarnings ( "unchecked" )
	public static void createConversationMap(HttpServletRequest request, String flowId) {

		Object objMap=request.getSession().getAttribute(CONVERSATION_ATTR_FLAG);
		if(objMap==null){
			objMap=new LinkedHashMap<String, Map<String, Object>>(1);
			request.getSession().setAttribute(CONVERSATION_ATTR_FLAG,objMap);
		}
		LinkedHashMap linkedHashMap= (LinkedHashMap) objMap;
        //如果会话已经存在，不用再去创建
        if (linkedHashMap.get(flowId) == null){
            linkedHashMap.put(flowId,new HashMap<String,Object>());
        }
	}

}
