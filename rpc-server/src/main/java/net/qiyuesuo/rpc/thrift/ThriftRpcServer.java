package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.TProcessor;

import net.qiyuesuo.rpc.common.NodeStatus;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.common.ServiceServer;
import net.qiyuesuo.rpc.exception.RpcServerException;
import net.qiyuesuo.rpc.registry.EmptyServerRegistry;
import net.qiyuesuo.rpc.registry.Registry;
import net.qiyuesuo.rpc.server.AbstractRpcServer;
import net.qiyuesuo.rpc.server.RunningStatus;


/**
 * 基于Thrift的RPCServer
 * @author zhouree
 *
 */
public class ThriftRpcServer extends AbstractRpcServer {
	
	private TServerThread serverThread;
	private RunningStatus status =  RunningStatus.stopped;
	
	private Registry registry = new EmptyServerRegistry();
	
	private ThriftServerConfig serverConfig;
	
	/**
	 * 默认配置创建RPC服务
	 */
	public ThriftRpcServer() {
		this(new ThriftServerConfig());
	}
	
	/**
	 * 创建RPC服务
	 * @param serverConfig
	 */
	public ThriftRpcServer(ThriftServerConfig serverConfig) {
		super(serverConfig);
		this.serverConfig = serverConfig;
		
	}
	
	@Override
	public synchronized void init() throws Exception {
		logger.info("Init Server...");
		if(getServiceScanner()!=null){
			setServices(getServiceScanner().scanServices());
		}
		status= RunningStatus.initialized;
	}
	
	@Override
	public synchronized void start() throws Exception {
		startCheck();
		logger.info("Starting Server...");
		status = RunningStatus.starting;
		
		// 处理Processor, 注册service
		TProcessorBuilder builder = new TProcessorBuilder(getServices());
		TProcessor tProcessor = builder.buildMultiplexedProcessor();
		serverThread = new TServerThread(tProcessor, serverConfig);
		serverThread.start();
		status = RunningStatus.running;
		logger.info("Server started.");
		setStatus(NodeStatus.UP);
		
		//向注册中心注册
		registry.register(this);
	}
	
	private void startCheck() throws Exception {
		// 参数和状态检测
		if(status !=RunningStatus.initialized){
			logger.warn("Invalid server status,server nit init,invoke init() before start.");
			throw new RpcServerException("Invalid server status!");
		}
		
		if (getServices() == null || getServices().isEmpty()) {
			throw new NullPointerException("service is null or empty!");
		}
	}

	@Override
	public synchronized void stop() {
		if(status !=RunningStatus.running){
			logger.warn("Invalid server status,server is not in running,stop aborted.");
			logger.info("Server not stop,current status is:{}",status);
			return;
		}
		serverThread.shutdown();
		status = RunningStatus.stopped;
		logger.info("Server stop success.");
		setStatus(NodeStatus.DOWN);
	}

	public void setServerConfig(ThriftServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	public void expose(Object... services) throws Exception {
		for (Object serviceImpl : services) {
			Service service = new ServiceServer(serviceImpl);
			getServices().add(service);
		}
		status = RunningStatus.initialized;
	}

	/**
	 * 获取ServiceScanner,默认为null
	 * @return
	 */
	protected ServiceScanner getServiceScanner() {
		return null;
	}

	@Override
	public boolean isAvailable() {
		return status==RunningStatus.running;
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

}
