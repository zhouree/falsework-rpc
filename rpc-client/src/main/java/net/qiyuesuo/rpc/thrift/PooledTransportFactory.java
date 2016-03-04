package net.qiyuesuo.rpc.thrift;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.ServerNode;

/**
 * 池化传输层TTransport
 * 
 * @author zhouree
 */
public class PooledTransportFactory extends BaseKeyedPooledObjectFactory<ServerNode, TTransport> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public TTransport create(ServerNode node) throws Exception {
		logger.info("create new transport.");
		return TransportBuilder.framedTransport(node.getHost(), node.getPort());
	}

	@Override
	public PooledObject<TTransport> wrap(TTransport transport) {
		return new DefaultPooledObject<TTransport>(transport);
	}

	@Override
	public void destroyObject(ServerNode key, PooledObject<TTransport> p) throws Exception {
		TTransport transport = p.getObject();
		transport.close();
	}

	/**
	 * TODO 添加心跳检测
	 * 在服务端口异常关闭的情况下，<code>transport.isOpen()</code>
     * 仍然返回true,需要添加心跳检测，将关闭的链接移除。
	 */
	@Override
	public boolean validateObject(ServerNode key, PooledObject<TTransport> p) {
		TTransport transport = p.getObject();
		return transport.isOpen();
	}

}
