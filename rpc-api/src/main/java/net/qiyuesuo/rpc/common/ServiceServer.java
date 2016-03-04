package net.qiyuesuo.rpc.common;

import java.io.Serializable;
import java.lang.instrument.IllegalClassFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.annotation.RpcService;

public class ServiceServer extends AbstractService{
	
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Object handler;

	public ServiceServer(Object serviceImpl) throws Exception {
		this(Constants.DEFAULT_NAME_SPACE, Constants.DEFAULT_GROUP, serviceImpl);
	}

	/**
	 * 用于server端暴露服务的时候创建ServiceInfo对象
	 * @param group
	 * @param namespace
	 * @param serviceImpl 要暴露的服务实现类
	 * @throws Exception
	 */
	public ServiceServer(String namespace,String group, Object serviceImpl) throws Exception {
		super();
		setGroup(group);
		setNamespace(namespace);
		
		this.handler = serviceImpl;
		
		Class<?>[] interfaces = serviceImpl.getClass().getInterfaces();
		// 必须实现接口
		if (interfaces.length == 0) {
			throw new IllegalClassFormatException("service should have at least one interface");
		}
		
		// 默认暴露第一个接口
		Class<?> serviceClass = interfaces[0];
		logger.info("Find serviceInterface [{}] for {}", serviceClass, serviceImpl);
		
		// 检查RpcService注解
		RpcService rpcAnnotation = serviceImpl.getClass().getDeclaredAnnotation(RpcService.class);
		if (rpcAnnotation == null) {
			throw new IllegalClassFormatException("service should  Annotated with " + RpcService.class);
		}
		
		//获取bean注解上的version信息
		setVersion(rpcAnnotation.version());
		
		// 检查注解中的serviceClass
		Class<?> annotatedServiceInterface = rpcAnnotation.serviceInterface();
		if (annotatedServiceInterface.isInterface() && annotatedServiceInterface != Serializable.class) {
			logger.info("Find annotated serviceInterface [{}] for {}", annotatedServiceInterface, serviceImpl);
			serviceClass = annotatedServiceInterface;
		}
		setServiceClass(serviceClass);
	}


	@Override
	public Object getHandler() {
		return handler;
	}

	public void setHandler(Object handler) {
		this.handler = handler;
	}

}
