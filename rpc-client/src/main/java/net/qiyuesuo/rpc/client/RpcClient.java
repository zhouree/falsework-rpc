package net.qiyuesuo.rpc.client;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;

/**
 * Rpc客户端定义
 * @author zhouree
 *
 */
public interface RpcClient<T> {
	
	/**
	 * 初始化客户端
	 */
	void init() throws Exception;
	
	T openClient(Service service,ServerNode node) throws Exception;
	
	void closeClient(ServerNode node,T client) throws Exception;
	
	/**
	 * 关闭客户端，释放资源
	 */
	void destroy();
	
}
