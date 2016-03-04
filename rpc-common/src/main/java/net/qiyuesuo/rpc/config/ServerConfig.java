package net.qiyuesuo.rpc.config;

import java.io.Serializable;

public class ServerConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 服务注册本机端口,默认9000 */
	private Integer port = 9000;
	
	/** 权重,默认为1 */
	private int weight = 1;

	/** 是否开启监控 */
	private boolean monitor = true;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public boolean isMonitor() {
		return monitor;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}

