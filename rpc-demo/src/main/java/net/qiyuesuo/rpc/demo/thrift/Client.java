package net.qiyuesuo.rpc.demo.thrift;

import net.qiyuesuo.rpc.client.RpcClient;
import net.qiyuesuo.rpc.cluster.LoadBalance;
import net.qiyuesuo.rpc.cluster.RandomLoadBalance;
import net.qiyuesuo.rpc.common.Invoker;
import net.qiyuesuo.rpc.demo.hello.HelloService;
import net.qiyuesuo.rpc.discovery.FixedAddressServiceDiscovery;
import net.qiyuesuo.rpc.discovery.ServiceDiscovery;
import net.qiyuesuo.rpc.proxy.RpcClientProxyFactory;
import net.qiyuesuo.rpc.thrift.ThriftRpcClient;
import net.qiyuesuo.rpc.thrift.ThriftRpcClientProxyFactory;
import net.qiyuesuo.rpc.thrift.ThriftRpcInvoker;

/**
 * Thrift RPC 客户端,不使用Spring
 * 
 * @author zhouree
 */
public class Client {
	
	public static void main(String[] args) throws Exception {
		ServiceDiscovery serviceDiscovery = new FixedAddressServiceDiscovery("localhost:9000");
		LoadBalance loadBalance = new RandomLoadBalance();
		RpcClient client = new ThriftRpcClient();
		Invoker invoker = new ThriftRpcInvoker(client, loadBalance, serviceDiscovery);
		
		RpcClientProxyFactory proxyFactory = new ThriftRpcClientProxyFactory(invoker);
		
		// 动态生成代理类
		HelloService hello = proxyFactory.proxy(HelloService.class);
		System.out.println(hello.hello("Ricky"));
		
		client.destroy();
	}

}
