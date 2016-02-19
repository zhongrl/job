package cn.xn.freamwork.support.conversation;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 流程会谈 拦截器
 *
 * @version 1.0.0
 */

public class ConversationResolverHandlerInterceptor extends HandlerInterceptorAdapter implements KeyUrlMapping
{

	private boolean exposeConversationAttributes;

    private Map<String,String> keyUrlMap;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ConversationMapping conversationMapping = getConversationMapping(handler);
		if (conversationMapping == null) {
			return true;
		}

		if(ConversationMapping.STATE_BEGIN.equals(conversationMapping.state())){
			ConversationUtil.createConversationMap(request, conversationMapping.flowId());
		}

		Map<String, Object> map= ConversationUtil.getConversationMap(request,conversationMapping.flowId());
		if(map==null){
            if(!response.isCommitted()){
			    response.sendRedirect(keyUrlMap.get(conversationMapping.flowId()));
            }
			return false;
		}

		//共享属性
	   if(exposeConversationAttributes){
		   for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
			   request.setAttribute(stringObjectEntry.getKey(), stringObjectEntry.getValue());
		   }
	   }

	    return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		ConversationMapping conversationMapping = getConversationMapping(handler);
		if (conversationMapping == null) {
			return;
		}

		if(ConversationMapping.STATE_END.equals(conversationMapping.state())){
			ConversationUtil.removeConversation(request, conversationMapping.flowId());
		}

		super.postHandle(request, response, handler, modelAndView);
	}


	private ConversationMapping getConversationMapping(Object handler) {
		if (notHandlerMethod(handler)) {
			return null;
		}
		return ((HandlerMethod) handler).getMethodAnnotation(ConversationMapping.class);
	}


	private boolean notHandlerMethod(Object handler) {
		return handler == null || !(handler instanceof HandlerMethod);
	}

	private void buildRedirectView(ConversationMapping conversationMapping, String flowId, ModelAndView modelAndView) {
		if (conversationMapping == null) {
			throw new IllegalArgumentException("no flowId for mapping:"+flowId );
		}

		String redirectUrl = keyUrlMap.get(flowId);
		if (redirectUrl != null) {
			modelAndView.setView(new RedirectView(redirectUrl));
			modelAndView.getModelMap().clear();
		}

	}


	public void setExposeConversationAttributes(boolean exposeConversationAttributes) {
		this.exposeConversationAttributes = exposeConversationAttributes;
	}


	@Override
	public void setKeyUrl(Map<String, String> keyUrlMap) {
		this.keyUrlMap=keyUrlMap;
	}
}




