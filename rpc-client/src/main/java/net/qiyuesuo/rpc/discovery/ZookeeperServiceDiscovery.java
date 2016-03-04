package net.qiyuesuo.rpc.discovery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.Constants;
import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;


/**
 * 基于 Zookeeper的服务发现实现
 * @author zhouree
 *
 */
public class ZookeeperServiceDiscovery implements ServiceDiscovery {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private CuratorFramework zkClient;

	private final ConcurrentHashMap<Service, List<ServerNode>> serverNodeCache = new ConcurrentHashMap();

	public ZookeeperServiceDiscovery(CuratorFramework zkClient) {
		super();
		this.zkClient = zkClient;
	}

	@Override
	public List<ServerNode> discoverServices(Service service) throws Exception {
		List<ServerNode> cachedNodes = serverNodeCache.get(service);
		if (cachedNodes == null) {
			logger.info("No ServerNode found in cache for {}",service);
			cachedNodes = initCache(service);
		}
		return cachedNodes;
	}

	private List<ServerNode> initCache(Service service) throws Exception {
		logger.info("Init ServerNode from zookeeper...");
		List<ServerNode> current = new ArrayList();
		List<String> childrenList = null;
		String path = service.getServicePath();

		childrenList = zkClient.getChildren().forPath(path);

		if (childrenList == null || childrenList.isEmpty()) {
			logger.error("no service found in zookeeper!");
		}

		for (String children : childrenList) {
			current.add(buildNode(service,children));
		}
		serverNodeCache.put(service, current);
		buildCacheListener(service);
		return current;
	}

	private void buildCacheListener(Service service) throws Exception {
		String servicePath = service.getServicePath();
		//使用PathChildrenCache监控Zookeeper的Node和Path的状态 
		PathChildrenCache cachedPath = new PathChildrenCache(zkClient, servicePath, true);
		cachedPath.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) {
				logger.info("PathChildrenCacheEvent {}",event);
				PathChildrenCacheEvent.Type eventType = event.getType();
				switch (eventType) {
				case CONNECTION_SUSPENDED:
				case CONNECTION_LOST:
					logger.error("Connection error,waiting...");
					return;
				default:
				}
				try {
					logger.info("server node changed,rebuild cache.");
					cachedPath.rebuild();
					rebuild(service, cachedPath);
				} catch (Exception e) {
					logger.error("CachedPath rebuild error!", e);
				}
			}
		});
		cachedPath.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
	}

	private void rebuild(Service service, PathChildrenCache cachedPath) {
		List<ServerNode> current = new ArrayList();
		List<ChildData> children = cachedPath.getCurrentData();
		if (children == null || children.isEmpty()) {
			logger.error("server cluster is empty!");
			return;
		}
		for (ChildData data : children) {
			String path = data.getPath();
			String address = path.substring(path.lastIndexOf(Constants.ZK_SEPARATOR) + 1);
			current.add(buildNode(service, address));
		}
		serverNodeCache.put(service, current);
	}

	private ServerNode buildNode(Service service, String address) {
		String[] hostname = address.split(":");
		String ip = hostname[0];
		Integer port = Integer.valueOf(hostname[1]);
		ServerNode node = new ServerNode(ip, port);

		if (hostname.length == 3) {
			int weight = Integer.valueOf(hostname[2]);
			node.setWeight(weight);
		}
		return node;
	}

	@Override
	public List<ServerNode> refresh(Service service) {
		return null;
	}

}
