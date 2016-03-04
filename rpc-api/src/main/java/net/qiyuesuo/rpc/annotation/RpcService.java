package net.qiyuesuo.rpc.annotation;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.qiyuesuo.rpc.common.Constants;


/**
 * 该注解用来暴露一个RPC服务，用于服务的实现类上
 * @author zhouree
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcService {
	
	/**
	 * 设置要暴露的服务接口</br>
	 * 非必须，默认为实现类的第一个接口</>
	 * 若有多个接口务必将要暴露的接口放在第一个，或者指定serviceInterface的值
	 */
	Class<?> serviceInterface() default Serializable.class;
	
	String group() default Constants.DEFAULT_GROUP;
	
	String version() default Constants.DEFAULT_VERSION;
	
}
