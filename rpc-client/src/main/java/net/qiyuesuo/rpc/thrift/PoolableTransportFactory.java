package net.qiyuesuo.rpc.thrift;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.exception.RpcTransportException;

public class PoolableTransportFactory implements TransportFactory {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private GenericKeyedObjectPool<ServerNode,TTransport> transportPool;
	
	public PoolableTransportFactory(){
		PooledTransportFactory factory = new PooledTransportFactory();
		transportPool= new GenericKeyedObjectPool(factory);
	}
	
	@Override
	public TTransport open(ServerNode node) throws RpcTransportException {
		try {
			return transportPool.borrowObject(node);
		} catch (Exception e) {
			throw new RpcTransportException(e);
		}
	}

	@Override
	public void release(ServerNode node,TTransport transport) {
		transportPool.returnObject(node, transport);
	}
	
	@Override
	public void destroy() throws Exception {
		transportPool.close();
	}

}
