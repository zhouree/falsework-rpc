package net.qiyuesuo.rpc.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import net.qiyuesuo.rpc.server.RpcServer;

public interface SpringRpcServer extends RpcServer, InitializingBean, DisposableBean {
	
}
