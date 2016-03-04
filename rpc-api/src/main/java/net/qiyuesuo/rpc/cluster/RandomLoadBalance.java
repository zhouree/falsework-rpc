package net.qiyuesuo.rpc.cluster;

import java.util.List;
import java.util.Random;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;

public class RandomLoadBalance implements LoadBalance {

	public static final String STRATAGE = "random";

	private final Random random = new Random();

	@Override
	public ServerNode select(Service service, List<ServerNode> serverNodes) {
		if(serverNodes ==null || serverNodes.isEmpty()){
			return null;
		}
		int totalWeight = 0; // 总权重
		for (ServerNode node : serverNodes) {
			int weight = node.getWeight();
			totalWeight += weight; // 累计总权重
		}
		// 按总权重数随机
		int offset = random.nextInt(totalWeight);
		int index = 0;
		// 确定随机值落在的区间
		do {
			offset -= serverNodes.get(index).getWeight();
			index++;
		} while (offset >= 0);
		
		return serverNodes.get(--index);
	}

}
