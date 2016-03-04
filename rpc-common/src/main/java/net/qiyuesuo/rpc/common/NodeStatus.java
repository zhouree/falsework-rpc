package net.qiyuesuo.rpc.common;

/**
 * 节点状态
 * @author zhouree
 *
 */
public class NodeStatus {
	
	public static final NodeStatus UNKNOWN = new NodeStatus("UNKNOWN");
	public static final NodeStatus UP = new NodeStatus("UP");
	public static final NodeStatus DOWN = new NodeStatus("DOWN");
	public static final NodeStatus OUT_OF_SERVICE = new NodeStatus("OUT_OF_SERVICE");

	private final String code;

	private final String description;

	public NodeStatus(String code) {
		this(code, "");
	}

	public NodeStatus(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return this.code;
	}
	public String getDescription() {
		return this.description;
	}

	@Override
	public String toString() {
		return this.code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		NodeStatus other = (NodeStatus) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}

}
