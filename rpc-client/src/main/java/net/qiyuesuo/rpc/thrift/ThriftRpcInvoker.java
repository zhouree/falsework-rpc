package net.qiyuesuo.rpc.thrift;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.client.RpcClient;
import net.qiyuesuo.rpc.cluster.ClusterInvoker;
import net.qiyuesuo.rpc.cluster.LoadBalance;
import net.qiyuesuo.rpc.common.Invocation;
import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.discovery.ServiceDiscovery;
import net.qiyuesuo.rpc.exception.RpcServerException;
import net.qiyuesuo.rpc.exception.RpcTransportException;

public class ThriftRpcInvoker implements ClusterInvoker{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private RpcClient<TServiceClient> rpcClient;
	
	private LoadBalance loadBalance;
	
	private ServiceDiscovery serviceDiscovery;
	
	public ThriftRpcInvoker(RpcClient<TServiceClient> rpcClient, LoadBalance loadBalance, ServiceDiscovery serviceDiscovery) {
		super();
		this.rpcClient = rpcClient;
		this.loadBalance = loadBalance;
		this.serviceDiscovery = serviceDiscovery;
	}
	
	@Override
	public Object invoke(Invocation invocation) throws Exception {
		Service service = invocation.getService();
		
		List<ServerNode> servers = serviceDiscovery.discoverServices(service);
		
		ServerNode node = loadBalance.select(service,servers);
		TServiceClient serviceClient = null;
		//TODO 多次尝试，直到遍历全部可用serverNode
		try {
			//每次调用将获取一个TServiceClient对象，建立一个transport链接,使用完毕需要释放资源
			serviceClient = rpcClient.openClient(service, node);
		}catch(RpcTransportException e){
			//若不能建立连接，重新选择节点
			servers = serviceDiscovery.refresh(service);
			node = loadBalance.select(service,servers);
		}
		Method method = invocation.getMethod();
		try {
			return method.invoke(serviceClient, invocation.getArgs());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//TODO 服务调用失败，转移到其他服务器
			throw new RpcServerException(e);
		} finally {
			rpcClient.closeClient(node,serviceClient);
		}
	}
	
	
	@Override
	public LoadBalance getLoadBalance() {
		return loadBalance;
	}

	public ServiceDiscovery getServiceDiscovery() {
		return serviceDiscovery;
	}

	public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}

}
