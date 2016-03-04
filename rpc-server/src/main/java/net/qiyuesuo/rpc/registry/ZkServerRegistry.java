package net.qiyuesuo.rpc.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.exception.RpcServerException;

/**
 * 基于ZooKeeper的注册机制
 * @author zhouree
 *
 */
public class ZkServerRegistry implements Registry {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private CuratorFramework zkClient;

	public ZkServerRegistry(CuratorFramework zkClient) {
		super();
		this.zkClient = zkClient;
	}

	@Override
	public void register(ServerNode node) {
		logger.info("register to ZooKeeper {}",node.toString());
		if(zkClient.getState() == CuratorFrameworkState.LATENT){
			zkClient.start();
		}
        for(Service service:node.getServices()){
        	// 创建服务节点
        	String servicePath = service.getServicePath();
        	createPersistentNode(servicePath);
	        StringBuilder pathBuilder = new StringBuilder(servicePath);
	        String nodePath = node.getNodePath();
	        pathBuilder.append(nodePath);
	        try {
	            if (zkClient.checkExists().forPath(pathBuilder.toString()) != null) {
	            	zkClient.delete().forPath(pathBuilder.toString());
	            }
	            logger.info("create zookeeper node: {}",pathBuilder.toString());
	            zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(pathBuilder.toString());
	        } catch (Exception e) {
	            logger.error("Create node error in the path : {}", pathBuilder.toString(), e);
	        }
        }

	}

	@Override
	public void unregister() {
		zkClient.close();
	}
	
	private void createPersistentNode(String nodePath) throws RpcServerException {
        try {
            if (zkClient.checkExists().forPath(nodePath) == null) {
            	logger.info("create zookeeper node: {}",nodePath);
                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(nodePath);
            }
        } catch (Exception e) {
            logger.error("Zookeeper error in the path : {}", nodePath, e);
            throw new RpcServerException(e.getMessage(), e);
        }
    }
}
