package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.client.RpcClient;
import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.exception.ReflectException;
import net.qiyuesuo.rpc.exception.RpcTransportException;

public class ThriftRpcClient implements RpcClient<TServiceClient> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private TransportFactory transportFactory;
	
	public ThriftRpcClient() {
		logger.info("Using TFramedTransportFactory");
		this.transportFactory = new TFramedTransportFactory();
	}
	
	public ThriftRpcClient(TransportFactory transportFactory) {
		super();
		this.transportFactory = transportFactory;
	}

	@Override
	public void init() throws Exception {
		if(this.transportFactory == null){
			logger.info("transportFactory is null,will init by TFramedTransportFactory");
			this.transportFactory = new TFramedTransportFactory();
		}
	}

	@Override
	public void destroy() {
		try {
			transportFactory.destroy();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 打开Transport,获取TServiceClient对象
	 */
	@Override
	public TServiceClient openClient(Service service,ServerNode node) throws RpcTransportException{
		TTransport transport = transportFactory.open(node);
		String serviceName = service.getServiceName();
		TServiceClient serviceClient = null;
		try {
			serviceClient = ThriftUtils.reflectServiceClient(serviceName,transport);
		} catch (Exception e) {
			throw new ReflectException(e);
		}
		return serviceClient;
	}
	
	/**
	 * 释放TServiceClient对象资源
	 */
	@Override
	public void closeClient(ServerNode node, TServiceClient serviceClient) {
		//释放TTransport 
		TTransport transport = serviceClient.getOutputProtocol().getTransport();
		transportFactory.release(node,transport);
	}
	
	public TransportFactory getTransportFactory() {
		return transportFactory;
	}

	public void setTransportFactory(TransportFactory transportFactory) {
		this.transportFactory = transportFactory;
	}

}
