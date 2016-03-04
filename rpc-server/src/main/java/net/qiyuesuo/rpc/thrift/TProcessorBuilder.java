package net.qiyuesuo.rpc.thrift;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.Service;

public class TProcessorBuilder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<Service> services;

	public TProcessorBuilder() {
		super();
	}
	
	public TProcessorBuilder(List<Service> services) {
		super();
		this.services = services;
	}

	/**
	 * 创建TMultiplexedProcessor
	 * @return
	 * @throws Exception 
	 */
	public TProcessor buildMultiplexedProcessor() throws Exception {
		if(services ==null || services.isEmpty()){
			throw new IllegalArgumentException("to build a MultiplexedProcessor services should not empty.");
		}
		TMultiplexedProcessor multiProcessor = new TMultiplexedProcessor();
		for (Service service : services) {
			TProcessor processor = buildTProcessor(service);
			// 注册多接口服务
			multiProcessor.registerProcessor(service.getServiceName(), processor);
			logger.info("Register Service:{}", service.getServiceName());
		}
		return multiProcessor;
	}
	
	/**
	 * 构建TProcessor对象
	 * 
	 * @param serviceName
	 * @param handler
	 * @return
	 */
	public TProcessor buildTProcessor(Service service) throws Exception {
		Class<?> serviceInterface = service.getServiceClass();
		if (!validateIface(serviceInterface)){
			throw new IllegalClassFormatException("serviceInterface " + serviceInterface + "should implements Iface");
		}
		String serviceName = service.getServiceName();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		//反射通过service 获取Thrift TProcessor
		String processorName = serviceName + "Thrift$Processor";
		String ifaceName = serviceName + "Thrift$Iface";
		Class<?> processorClass = classLoader.loadClass(processorName);
		Class<?> ifaceClass = classLoader.loadClass(ifaceName);
		Constructor<?> constructor = processorClass.getConstructor(ifaceClass);
		TProcessor processor = (TProcessor) constructor.newInstance(service.getHandler());
		if (processor == null) {
			throw new IllegalClassFormatException("service-class should implements Iface");
		}
		return processor;
	}
	
	// 暴露的接口必须实现Thrift Iface接口
	private boolean validateIface(Class<?> serviceInterface) {
		Class<?>[] ifaces = serviceInterface.getInterfaces();
		if (ifaces.length == 0) {
			logger.warn("serviceInterface " + serviceInterface + "should implements Iface");
			return false;
		}
		for (Class<?> iface : ifaces) {
			String ifaceName = iface.getSimpleName();
			if (ifaceName.equals("Iface")) {
				return true;
			}
		}
		logger.warn("serviceInterface " + serviceInterface + "should implements Iface");
		return false;
	}

}
