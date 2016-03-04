package net.qiyuesuo.rpc.annotation;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import net.qiyuesuo.rpc.RpcUtils;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.common.ServiceClient;
import net.qiyuesuo.rpc.config.GlobalConfigProperties;
import net.qiyuesuo.rpc.proxy.RpcClientProxyFactory;

public class RpcInjectAnnotationPostProcessor implements  BeanPostProcessor,BeanFactoryAware{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final Class<? extends Annotation> autowiredAnnotationType = RpcInject.class;
	
	private BeanFactory beanFactory;
	private RpcClientProxyFactory rpcClient;
	private GlobalConfigProperties globalProperties;
	
	private ConcurrentHashMap<String, Object> injectMap = new ConcurrentHashMap();
	
	public RpcInjectAnnotationPostProcessor(RpcClientProxyFactory rpcClient,GlobalConfigProperties globalProperties) throws Exception {
		this.rpcClient = rpcClient;
		this.globalProperties = globalProperties;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		List<RpcInjectedElement> injectedElements = buildAutowiringMetadata(bean.getClass());
		try {
			for (RpcInjectedElement element : injectedElements) {
				Class<?> serviceClass = element.getServiceClass();
				RpcInject annotation = element.getAnnotation();
				Object object = getProxyObject(serviceClass, annotation);
				element.injectRpc(bean,object);
			}
		}
		catch (Throwable ex) {
			logger.warn("Error creating bean with name '{}': Injection of  dependencies failed", beanName,ex);
		}
		
		return bean;
	}
	
	/**
	 * 循环查找所有父类中的autowiredAnnotationType注解
	 * @param clazz
	 * @return
	 */
	private List<RpcInjectedElement> buildAutowiringMetadata(final Class<?> clazz) {
		List<RpcInjectedElement> elements = new LinkedList();
		Class<?> targetClass = clazz;

		do {
			final LinkedList<RpcInjectedElement> currElements = new LinkedList<RpcInjectedElement>();

			ReflectionUtils.doWithLocalFields(targetClass, new ReflectionUtils.FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(field, autowiredAnnotationType);
					if (attributes != null) {
						if (Modifier.isStatic(field.getModifiers())) {
							if (logger.isWarnEnabled()) {
								logger.warn("Autowired annotation is not supported on static fields: " + field);
							}
							return;
						}
						currElements.add(new RpcInjectedElement(field));
					}
				}
			});

			ReflectionUtils.doWithLocalMethods(targetClass, new ReflectionUtils.MethodCallback() {
				@Override
				public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
					Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
					if (!BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
						return;
					}
					AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(bridgedMethod, autowiredAnnotationType);
					if (attributes != null && method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
						if (Modifier.isStatic(method.getModifiers())) {
							if (logger.isWarnEnabled()) {
								logger.warn("Autowired annotation is not supported on static methods: " + method);
							}
							return;
						}
						if (method.getParameterTypes().length == 0) {
							if (logger.isWarnEnabled()) {
								logger.warn("Autowired annotation should be used on methods with parameters: " + method);
							}
						}
						PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
						currElements.add(new RpcInjectedElement(method, pd));
					}
				}
			});

			elements.addAll(0, currElements);
			targetClass = targetClass.getSuperclass();
		}
		while (targetClass != null && targetClass != Object.class);

		return elements;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
			throw new IllegalArgumentException(
					"AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory");
		}
		this.beanFactory = beanFactory;
	}
	
	private Object getProxyObject(Class<?> serviceClass, RpcInject annotation) throws Exception {
		String cacheKey = serviceClass.getName();
		beanFactory.containsBean(cacheKey);
		Object object = injectMap.get(cacheKey);

		if (object != null) {
			logger.info("return a cached instance for {}", serviceClass);
		} else {
			String namespace = globalProperties.getNamespace();
			String group = RpcUtils.determineGroup(globalProperties.getGroup(), annotation.group());
			String version = annotation.version();

			Service service = new ServiceClient(group, namespace, version, serviceClass);
			object = rpcClient.proxy(service);
			logger.info("put a instance to cache {}", serviceClass);
			injectMap.put(cacheKey, object);
		}
		return object;

	}
}
