package cn.xn.freamwork.support.conversation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 添加自定义注解扫描处理的请求处理器
 *
 * @version 1.0.0
 */

public class ConversationRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	private final Map<String, String> flowIndexUrlMap = new LinkedHashMap<String, String>();

	@Override
	protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
//		if(!StringUtils.isEmpty(lookupPath)){
//			lookupPath=lookupPath.toLowerCase();
//		}
		return super.lookupHandlerMethod(lookupPath, request);
	}


	@Override
	protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
		Annotation annotation = method.getAnnotation(ConversationMapping.class);
		if (annotation != null) {
			ConversationMapping conversationMapping = (ConversationMapping) annotation;
			if (ConversationMapping.STATE_BEGIN.equals(conversationMapping.state())) {

				RequestMapping controllerRequestMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);

				String parentUrl = "";
				if (controllerRequestMapping != null) {
					parentUrl = controllerRequestMapping.value()[0];
				}

				//添加访问链接
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                //将请求映射路径放入 map
				flowIndexUrlMap.put(conversationMapping.flowId(), parentUrl + requestMapping.value()[0]);
			}
		}

		super.registerHandlerMethod(handler, method, mapping);
	}


	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		HandlerInterceptor[] handlerInterceptors = getAdaptedInterceptors();

		for (HandlerInterceptor handlerInterceptor : handlerInterceptors) {
			if (handlerInterceptor instanceof KeyUrlMapping) {
				((KeyUrlMapping) handlerInterceptor).setKeyUrl(flowIndexUrlMap);
			}
		}

		logger.info("flowIndexUrlMap:" + flowIndexUrlMap);
	}

}
