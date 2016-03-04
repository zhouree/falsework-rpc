package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.transport.TTransport;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.exception.RpcTransportException;

public class TFramedTransportFactory implements TransportFactory {
	
	@Override
	public TTransport open(ServerNode node) throws RpcTransportException {
		return TransportBuilder.framedTransport(node.getHost(), node.getPort());
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void release(ServerNode node,TTransport transport) {
		transport.close();
	}

}
