package net.qiyuesuo.rpc.cluster;

import java.util.List;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;

public interface LoadBalance {

	ServerNode select(Service service,List<ServerNode> serverNodes);

}
