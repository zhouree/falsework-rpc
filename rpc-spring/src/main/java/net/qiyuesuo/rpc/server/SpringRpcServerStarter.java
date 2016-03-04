package net.qiyuesuo.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import net.qiyuesuo.rpc.server.RpcServer;

/**
 * RpcServer Starter,将该类注册成Service后RPC服务将随Spring一起启动
 * 
 * @author zhouree
 */
public class SpringRpcServerStarter implements InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private RpcServer rpcServer;

	public SpringRpcServerStarter(RpcServer rpcServer) {
		super();
		this.rpcServer = rpcServer;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			synchronized (rpcServer) {
				rpcServer.start();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
