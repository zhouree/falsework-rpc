package net.qiyuesuo.rpc.discovery;

import java.util.List;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;

public interface ServiceDiscovery {
	
	List<ServerNode> discoverServices(Service service) throws Exception;

	List<ServerNode> refresh(Service service);

}
