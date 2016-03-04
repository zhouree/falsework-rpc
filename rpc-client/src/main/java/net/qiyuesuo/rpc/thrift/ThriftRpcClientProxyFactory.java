package net.qiyuesuo.rpc.thrift;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.Invoker;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.common.ServiceClient;
import net.qiyuesuo.rpc.proxy.RpcClientProxyFactory;
import net.qiyuesuo.rpc.proxy.RpcInvocation;

/**
 * ThriftRpc 客户端服务代理类
 * 
 * @author zhouree
 */
public class ThriftRpcClientProxyFactory implements RpcClientProxyFactory{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Invoker invoker;

	public ThriftRpcClientProxyFactory(Invoker invoker) {
		this.invoker = invoker;
	}

	/**
	 * 使用默认参数构建Service对象做代理
	 */
	@Override
	public <T> T proxy(Class<?> serviceClass) throws Exception {
		Service service = new ServiceClient(serviceClass);
		return proxy(service);
	}
	
	/**
	 * 
	 */
	@Override
	public <T> T proxy(Service service) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Class<?> serviceClass = service.getServiceClass();
		logger.info("proxy client service:{}",service);
		
		T proxyClient = (T) Proxy.newProxyInstance(classLoader, new Class[] { serviceClass }, new InvocationHandler(){

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				RpcInvocation invocation = new RpcInvocation(service, method, args);
				return invoker.invoke(invocation);
			}
			
		});
		return proxyClient;
	}
	
}
