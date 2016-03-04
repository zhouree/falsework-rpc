package net.qiyuesuo.rpc.annotation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.util.ReflectionUtils;

/**
 * @author zhouree
 */
public class RpcInjectedElement extends InjectionMetadata.InjectedElement {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Class<?> serviceClass;
	private RpcInject annotation;

	public RpcInjectedElement(Field field) {
		super(field, null);
		this.serviceClass = field.getType();
		this.annotation = field.getAnnotation(RpcInject.class);
	}

	public RpcInjectedElement(Method method, PropertyDescriptor pd) {
		super(method, pd);
	}

	/**
	 * 处理RpcInject注解
	 * 
	 * @param target
	 * @throws Exception 
	 */
	public void injectRpc(Object bean, Object object) throws Exception {
		
		if (this.isField) {
			Field field = (Field) this.member;
			ReflectionUtils.makeAccessible(field);
			field.set(bean, object);
		} else {
			Method method = (Method) this.member;
			// TODO 暂时不支持method上的注解
			logger.warn("暂时不支持method上的注解{}", method);
		}
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}

	public RpcInject getAnnotation() {
		return annotation;
	}
}
