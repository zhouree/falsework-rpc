package net.qiyuesuo.rpc.discovery;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.qiyuesuo.rpc.common.ServerNode;
import net.qiyuesuo.rpc.common.Service;
import net.qiyuesuo.rpc.discovery.ServiceDiscovery;

public class FixedAddressServiceDiscovery implements ServiceDiscovery{
	
	private final List<ServerNode> container = new CopyOnWriteArrayList<ServerNode>();

	public FixedAddressServiceDiscovery(String serverAddress) throws Exception{
		String[] hostnames = serverAddress.split(",");//"ip:port:weight,ip:port"
        for (String hostname : hostnames) {
            String[] address = hostname.split(":");
            String host = address[0];
            int port = Integer.valueOf(address[1]);
            
            ServerNode node = new ServerNode(host,port);
            if(address.length == 3){
            	int weight = Integer.valueOf(address[2]);
            	node.setWeight(weight);
            }
            container.add(node);
        }
	}

	@Override
	public List<ServerNode> discoverServices(Service info) {
		return container;
	}

	@Override
	public List<ServerNode> refresh(Service service) {
		return container;
	}

}
