package cn.xn.freamwork.support.conversation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * 参数解决器
 *
 * @version 1.0.0
 */

public class ConversationHandlerMethodArgumentResolver  implements HandlerMethodArgumentResolver {
	private Logger logger = LoggerFactory.getLogger(ConversationHandlerMethodArgumentResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Conversation.class.isAssignableFrom(parameter.getParameterType());  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Object resolveArgument(MethodParameter param, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if (!Conversation.class.isAssignableFrom(param.getParameterType())) {
			return WebArgumentResolver.UNRESOLVED;
		}

		Annotation annotation = param.getMethod().getAnnotation(ConversationMapping.class);
		if (annotation == null) {
			logger.warn("did you forget add Annotation @ConversationMapping on method[{}]?", param.getMethod().getName());
			return null;
		}

		return getCurrentConversationMap(webRequest.getNativeRequest(HttpServletRequest.class), (ConversationMapping) annotation);
	}


	public static Conversation getCurrentConversationMap(HttpServletRequest request,ConversationMapping conversationMapping){
		return new Conversation(request, conversationMapping.flowId());
	}
}
