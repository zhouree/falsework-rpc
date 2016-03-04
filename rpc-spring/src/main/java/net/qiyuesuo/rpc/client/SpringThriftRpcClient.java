package net.qiyuesuo.rpc.client;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import net.qiyuesuo.rpc.thrift.ThriftRpcClient;

public class SpringThriftRpcClient extends ThriftRpcClient implements DisposableBean, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

}
