package cn.xn.freamwork.support.conversation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 *
 * @version 1.0.0
 */
@Target ( ElementType.METHOD)
@Retention ( RetentionPolicy.RUNTIME)
public @interface ConversationMapping {
	String STATE_DEFAULT="default";
	String STATE_BEGIN="begin";
	String STATE_END="end";

	String flowId() default "default";

	String state() default STATE_DEFAULT;

}
