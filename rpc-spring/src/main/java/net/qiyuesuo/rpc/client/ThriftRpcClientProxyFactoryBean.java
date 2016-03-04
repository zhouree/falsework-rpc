package net.qiyuesuo.rpc.client;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;

import net.qiyuesuo.rpc.common.Constants;
import net.qiyuesuo.rpc.common.Invoker;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.common.ServiceClient;
import net.qiyuesuo.rpc.thrift.ThriftRpcClientProxyFactory;

/**
 * 客户端代理工厂，用于XML配置方式获取客户端
 * <pre>
 * {@code
 * <bean id="helloService" class="net.qiyuesuo.framework.rpc.client.ThriftRpcClientProxyFactoryBean">
 *     <property name="thriftRpcClient" ref="thriftRpcClient" />
 *     <property name="serviceName" value="net.qiyuesuo.framework.rpc.demo.HelloService" />
 * </bean>
 * } 
 * </pre>
 */
public class ThriftRpcClientProxyFactoryBean extends ThriftRpcClientProxyFactory implements FactoryBean<Object>, InitializingBean{


	private Object proxyClient;
	private Class<?> serviceClass;
	
	/**
	 * 客户端代理类名
	 */
	private String serviceName;
	private String group = Constants.DEFAULT_GROUP;
	private String namespace = Constants.DEFAULT_NAME_SPACE;
	private String version = Constants.DEFAULT_VERSION;

	public ThriftRpcClientProxyFactoryBean(Invoker invoker) {
		super(invoker);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		// 加载接口类
		serviceClass = classLoader.loadClass(serviceName);
		Service service = new ServiceClient(group,namespace,version,serviceClass);
		proxyClient = proxy(service);
	}
	
	@Override
	public Object getObject() throws Exception {
		return proxyClient;
	}

	@Override
	public Class<?> getObjectType() {
		return serviceClass;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
