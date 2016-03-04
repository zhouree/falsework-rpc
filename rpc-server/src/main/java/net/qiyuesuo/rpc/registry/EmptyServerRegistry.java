package net.qiyuesuo.rpc.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.registry.Registry;

public class EmptyServerRegistry implements Registry {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void register(ServerNode node) {
		logger.info("register:{}",node);
	}

	@Override
	public void unregister() {
		logger.info("unregister.");
	}

}
