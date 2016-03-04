package net.qiyuesuo.rpc.zookeeper;

import java.io.Closeable;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import net.qiyuesuo.rpc.config.ZookeeperConfig;

public class ZkClientFactory implements Closeable{
	
	private ZookeeperConfig config;
	
	private boolean singleton = true;
	
	private CuratorFramework zkClient;
	
	public ZkClientFactory(ZookeeperConfig config) {
		super();
		this.config = config;
	}

	public CuratorFramework getZkClient() throws Exception {
		if (singleton) {
			if (zkClient == null) {
				zkClient = create();
				zkClient.start();
			}
			return zkClient;
		}
		return create();
	}

	public CuratorFramework create() throws Exception {
		String zkHosts = config.getHosts();
		int sessionTimeout = config.getSessionTimeout();
		int connectionTimeout = config.getConnectionTimeout();
		int maxRetries = config.getRetry();
		return create(zkHosts, sessionTimeout, connectionTimeout,maxRetries);
	}

	public static CuratorFramework create(String zkHosts, int sessionTimeout, int connectionTimeout,int maxRetries) {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		return builder.connectString(zkHosts).sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(30000)
				.canBeReadOnly(true).retryPolicy(new ExponentialBackoffRetry(1000, maxRetries))
				.defaultData(null).build();
	}

	@Override
	public void close() {
		if (zkClient != null) {
			zkClient.close();
		}
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

}
