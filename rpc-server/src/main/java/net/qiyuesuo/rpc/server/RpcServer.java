package net.qiyuesuo.rpc.server;

import net.qiyuesuo.rpc.common.Node;

/**
 * RPC server接口
 * @author zhouree
 *
 */
public interface RpcServer extends Node{
	
	/**
	 * 初始化服务
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * 启动服务（启动服务需要使用单独的线程启动）</br>
	 * start()的实现必须判断服务当前状态，重复调用不会产生影响。
	 * @throws Exception
	 */
	void start() throws Exception;
	
	/**
	 * 停止服务，释放资源
	 * @throws Exception
	 */
	void stop() throws Exception;
	
}
