package net.qiyuesuo.rpc.server;

import net.qiyuesuo.rpc.thrift.ServiceScanner;
import net.qiyuesuo.rpc.thrift.ThriftRpcServer;
import net.qiyuesuo.rpc.thrift.ThriftServerConfig;


/**
 * 通过Spring容器管理ThriftRpcServer生命周期
 * @author zhouree
 *
 */
public class SpringThriftRpcServer extends ThriftRpcServer implements SpringRpcServer {
	
	private ServiceScanner serviceScanner;
	
	public SpringThriftRpcServer(ThriftServerConfig serverConfig) {
		super(serverConfig);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

	@Override
	public void destroy() throws Exception {
		this.stop();
	}

	@Override
	public ServiceScanner getServiceScanner() {
		return serviceScanner;
	}

	public void setServiceScanner(ServiceScanner serviceScanner) {
		this.serviceScanner = serviceScanner;
	}

}
