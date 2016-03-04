package net.qiyuesuo.rpc.proxy;

import net.qiyuesuo.rpc.common.Service;

public interface RpcClientProxyFactory {
	
	/**
	 * 使用默认参数构建Service对象做代理 {@link #proxy(Service)}
	 * @param serviceClass 
	 * @return 
	 * @throws Exception
	 */
	<T> T proxy(Class<?> serviceClass) throws Exception;
	
	/**
	 * 生成客户端代理服务
	 * @param service 要代理的服务
	 * @return 
	 * @throws Exception
	 */
	<T> T proxy(Service service) throws Exception;
	
}
