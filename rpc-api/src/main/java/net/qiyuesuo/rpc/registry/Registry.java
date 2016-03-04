package net.qiyuesuo.rpc.registry;

import net.qiyuesuo.rpc.common.ServerNode;

/**
 * 服务注册器，用于服务端向注册中心注册，客户端向注册中心订阅
 * @author zhouree
 */
public interface Registry {

	
	/**
	 * 注册服务
	 * @param node
	 */
	void register(ServerNode node);

	/**
	 * 服务注销
	 */
	void unregister();
	
}
