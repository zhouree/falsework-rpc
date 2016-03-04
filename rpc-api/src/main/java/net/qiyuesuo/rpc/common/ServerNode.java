package net.qiyuesuo.rpc.common;

import java.util.ArrayList;
import java.util.List;

import net.qiyuesuo.rpc.config.ServerConfig;
import net.qiyuesuo.rpc.utils.ServerUtils;

/**
 * 服务节点
 * @author zhouree
 */
public class ServerNode implements Node {

	private String host;
	private int port;
	
	private NodeStatus status = NodeStatus.UNKNOWN;
	
	private int weight = Constants.DEFAULT_WEIGHT;
	
	/**
	 * 暴露的服务
	 */
	private List<Service> services = new ArrayList();
	
	/**
	 * 通过配置文件初始化ServerNode对象，Server启动时加载
	 * @param config
	 */
	public ServerNode(ServerConfig config) {
		this.host = ServerUtils.detectServerIp();
		this.port = config.getPort();
		this.weight = config.getWeight();
	}
	
	public ServerNode(String host,int port){
		this.host = host;
		this.port = port;
	}
	
	@Override
	public NodeStatus getStatus() {
		return status;
	}

	public void setStatus(NodeStatus status) {
		this.status = status;
	}

	public int getWeight() {
		return weight > 0 ? weight : 1;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public boolean isAvailable() {
		return status == NodeStatus.UP;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String getAddress() {
		return getHost() + ":" + getPort();
	}
	
	@Override
	public String toString() {
		return "ServerNode [address=" + getAddress() + ", status=" + status + ", weight=" + weight + "]";
	}

	@Override
	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	public String getNodePath() {
		return Constants.ZK_SEPARATOR + getAddress();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ServerNode other = (ServerNode) obj;
		if (host == null) {
			if (other.host != null) {
				return false;
			}
		} else if (!host.equals(other.host)) {
			return false;
		}
		if (port != other.port) {
			return false;
		}
		return true;
	}
}
