package net.qiyuesuo.rpc.server;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import net.qiyuesuo.rpc.RpcUtils;
import net.qiyuesuo.rpc.annotation.RpcService;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.common.ServiceServer;
import net.qiyuesuo.rpc.config.GlobalConfigProperties;
import net.qiyuesuo.rpc.thrift.ServiceScanner;


/**
 * 基于 RpcService注解扫描需要暴露的Service
 * @author zhouree
 */
public class RpcServiceScanner implements ServiceScanner, ApplicationContextAware {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ApplicationContext applicationContext;
	private Class<? extends Annotation> annotationClass = RpcService.class;
	
	private GlobalConfigProperties properties;

	public RpcServiceScanner(GlobalConfigProperties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public List<Service> scanServices() {
		List<Service> list = new ArrayList();
		String[] serviceNames = applicationContext.getBeanNamesForAnnotation(annotationClass);
		String namespce = properties.getNamespace();
		String globalGroup = properties.getGroup();
		for (String name : serviceNames) {
			Object bean = applicationContext.getBean(name);
			logger.info("find annotated bean:{}",bean);
			try {
				RpcService annotation = (RpcService) applicationContext.findAnnotationOnBean(name, annotationClass);
				String group = RpcUtils.determineGroup(globalGroup, annotation.group());
				Service service = new ServiceServer(namespce,group,bean);
				list.add(service);
			} catch (Exception e) {
				logger.error("error while scanService :{}",bean,e);
			}
		}
		return list;
	}
}
