package net.qiyuesuo.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.config.ServerConfig;

public abstract class AbstractRpcServer extends ServerNode implements RpcServer {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public AbstractRpcServer(ServerConfig config) {
		super(config);
	}
	
}
