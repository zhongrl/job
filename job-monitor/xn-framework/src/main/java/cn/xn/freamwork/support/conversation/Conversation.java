package cn.xn.freamwork.support.conversation;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 会话
 *
 */
public class Conversation {


	//共享空间
	private String flowId;

	private HttpServletRequest request;

	public Conversation(HttpServletRequest request, String flowId) {
		this.request = request;
		this.flowId = (flowId == null ? ConversationUtil.FLOW_ID_DEFAULT : flowId);
	}

	public String getFlowId() {
		return flowId;
	}

	private Map<String, Object> getConversationMap() {
		return ConversationUtil.getConversationMap(request, flowId);
	}


	public Conversation put(String key, Object value) {
		 getConversationMap().put(key, value);
		return this;
	}

	@SuppressWarnings ( "unchecked" )
	public <T> T get(String key) {
		Object obj=getConversationMap().get(key);
		if(obj==null)
			return null;

		return (T) obj;
	}

}
