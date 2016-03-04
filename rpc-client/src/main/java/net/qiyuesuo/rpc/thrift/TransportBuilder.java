package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import net.qiyuesuo.rpc.exception.RpcTransportException;

/**
 * 构建传输层TTransport对象
 * 
 * @author zhouree
 */
public class TransportBuilder {

	public static TTransport framedTransport(String host, int port) throws RpcTransportException {
		if (host == null) {
			throw new RpcTransportException("No provider available for remote service");
		}
		
		TSocket tsocket = new TSocket(host, port);
		TTransport transport = new TFramedTransport(tsocket);
		try {
			transport.open();
		} catch (TTransportException e) {
			throw new RpcTransportException(e);
		}
		return transport;
	}

	public static TTransport socketTransport(String host, int port) throws RpcTransportException {
		TSocket transport = new TSocket(host, port);
		try {
			transport.open();
		} catch (TTransportException e) {
			throw new RpcTransportException(e);
		}
		return transport;
	}
}
