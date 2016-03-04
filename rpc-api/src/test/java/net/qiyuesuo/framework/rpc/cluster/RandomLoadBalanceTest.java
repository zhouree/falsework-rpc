package net.qiyuesuo.framework.rpc.cluster;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.rpc.cluster.RandomLoadBalance;
import net.qiyuesuo.rpc.common.ServerNode;

public class RandomLoadBalanceTest {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testSelect() {
		RandomLoadBalance loadBanance = new RandomLoadBalance();
		List<ServerNode> nodes = new ArrayList();
		ServerNode node1 = loadBanance.select(null, nodes);
		Assert.assertNull(node1);
		
		nodes.add(new ServerNode("localhost", 8000));
		nodes.add(new ServerNode("localhost", 8001));
		ServerNode node = new ServerNode("localhost", 8002);
		node.setWeight(2);
		nodes.add(node);
		
		for(int i=0;i<20;i++){
			ServerNode node2 = loadBanance.select(null, nodes);
			logger.info(node2.toString());
		}
		
	}

}
