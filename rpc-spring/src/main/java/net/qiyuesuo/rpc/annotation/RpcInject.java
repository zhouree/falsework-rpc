package net.qiyuesuo.rpc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;

import net.qiyuesuo.rpc.common.Constants;

/**
 * 用于RPC客户端Service注入</br>
 * 使用方法同Spring注解 {@link Autowired}
 * 
 * @author zhouree
 */
@Target({ ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcInject {

	boolean required() default true;

	/**
	 * 服务的group
	 * @return
	 */
	String group() default Constants.DEFAULT_GROUP;

	String version() default Constants.DEFAULT_VERSION;

}
