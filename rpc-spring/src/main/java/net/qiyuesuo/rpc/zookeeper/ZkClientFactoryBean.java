package net.qiyuesuo.rpc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import net.qiyuesuo.rpc.config.ZookeeperConfig;
import net.qiyuesuo.rpc.zookeeper.ZkClientFactory;

public class ZkClientFactoryBean extends ZkClientFactory implements FactoryBean<CuratorFramework>,DisposableBean{

	public ZkClientFactoryBean(ZookeeperConfig config) {
		super(config);
	}

	@Override
	public CuratorFramework getObject() throws Exception {
		return getZkClient();
	}

	@Override
	public Class<?> getObjectType() {
		return CuratorFramework.class;
	}

	@Override
	public void destroy() throws Exception {
		super.close();
	}
}
