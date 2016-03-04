package net.qiyuesuo.rpc.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.qiyuesuo.rpc.annotation.RpcInjectAnnotationPostProcessor;
import net.qiyuesuo.rpc.client.RpcClient;
import net.qiyuesuo.rpc.client.SpringThriftRpcClient;
import net.qiyuesuo.rpc.cluster.LoadBalance;
import net.qiyuesuo.rpc.cluster.RandomLoadBalance;
import net.qiyuesuo.rpc.common.Invoker;
import net.qiyuesuo.rpc.discovery.ServiceDiscovery;
import net.qiyuesuo.rpc.discovery.ZookeeperServiceDiscovery;
import net.qiyuesuo.rpc.proxy.RpcClientProxyFactory;
import net.qiyuesuo.rpc.thrift.PoolableTransportFactory;
import net.qiyuesuo.rpc.thrift.TFramedTransportFactory;
import net.qiyuesuo.rpc.thrift.ThriftRpcClientProxyFactory;
import net.qiyuesuo.rpc.thrift.ThriftRpcInvoker;
import net.qiyuesuo.rpc.thrift.TransportFactory;

@Configuration
@EnableConfigurationProperties({ClientConfigProperties.class,GlobalConfigProperties.class})
@AutoConfigureAfter(ZkAutoConfiguration.class)
public class ClientAutoConfiguration {
	
	@Autowired
	private GlobalConfigProperties globalProperties;
	
	@Bean
	@ConditionalOnMissingBean
	public ServiceDiscovery serviceDiscovery(CuratorFramework zkClient) throws Exception {
		return new ZookeeperServiceDiscovery(zkClient);
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(ServiceDiscovery.class)
	public TransportFactory poolableTransportFactory() {
		return new PoolableTransportFactory();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public TransportFactory framedTransportFactory() throws Exception {
		return new TFramedTransportFactory();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RpcClient thriftRpcClient(TransportFactory transportFactory) {
		SpringThriftRpcClient client = new SpringThriftRpcClient();
		client.setTransportFactory(transportFactory);
		return client;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public LoadBalance loadBalance(){
		return new RandomLoadBalance();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public Invoker invoker(RpcClient rpcClient,LoadBalance loadBalance,ServiceDiscovery serviceDiscovery){
		return new ThriftRpcInvoker(rpcClient, loadBalance, serviceDiscovery);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RpcClientProxyFactory clientProxyFactory(Invoker invoker) {
		return new ThriftRpcClientProxyFactory(invoker);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(RpcClientProxyFactory.class)
	public RpcInjectAnnotationPostProcessor rpcInjectPostProcessor(RpcClientProxyFactory factory) throws Exception {
		return new RpcInjectAnnotationPostProcessor(factory,globalProperties);
	}
}
