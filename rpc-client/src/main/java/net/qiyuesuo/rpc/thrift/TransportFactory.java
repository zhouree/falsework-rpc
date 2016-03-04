package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.transport.TTransport;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.exception.RpcServerException;
import net.qiyuesuo.rpc.exception.RpcTransportException;

public interface TransportFactory {
	/**
	 * 获取TTransport
	 * @param address
	 * @return
	 * @throws RpcServerException
	 */
	TTransport open(ServerNode node) throws RpcTransportException;

	/**
	 * 销毁TransportFactory对象
	 * @throws Exception
	 */
	void destroy() throws Exception;

	/**
	 * 释放TTransport
	 */
	void release(ServerNode node,TTransport transport);

}
