package net.qiyuesuo.rpc.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.qiyuesuo.rpc.registry.Registry;
import net.qiyuesuo.rpc.registry.ZkServerRegistry;
import net.qiyuesuo.rpc.server.RpcServer;
import net.qiyuesuo.rpc.server.RpcServiceScanner;
import net.qiyuesuo.rpc.server.SpringRpcServerStarter;
import net.qiyuesuo.rpc.server.SpringThriftRpcServer;
import net.qiyuesuo.rpc.thrift.ServiceScanner;

@Configuration
@ConditionalOnClass({ SpringRpcServerStarter.class })
@EnableConfigurationProperties({ServerConfigProperties.class,GlobalConfigProperties.class})
@AutoConfigureAfter(ZkAutoConfiguration.class)
public class ServerAutoConfiguration {

	@Autowired
	private ServerConfigProperties serverProperties;
	@Autowired
	private GlobalConfigProperties globalProperties;
	
	@Autowired
	private CuratorFramework zkClient;

	@Bean
	@ConditionalOnMissingBean
	public ServiceScanner serviceScanner() {
		return new RpcServiceScanner(globalProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public ZkServerRegistry serverRegistry() {
		return new ZkServerRegistry(zkClient);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnClass(SpringThriftRpcServer.class)
	@ConditionalOnBean({ ServiceScanner.class })
	public RpcServer rpcServer(ServiceScanner serviceScanner,Registry registry) {
		SpringThriftRpcServer rpcServer = new SpringThriftRpcServer(serverProperties);
		rpcServer.setServiceScanner(serviceScanner);
		rpcServer.setRegistry(registry);
		return rpcServer;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(RpcServer.class)
	public SpringRpcServerStarter rpcServerStarter(RpcServer rpcServer) {
		return new SpringRpcServerStarter(rpcServer);
	}

}
