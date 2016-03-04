package net.qiyuesuo.rpc.config;

import java.io.Serializable;

import net.qiyuesuo.rpc.common.Constants;

public class GlobalConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 命名空间 */
	private String namespace = Constants.DEFAULT_NAME_SPACE;
	
	/**服务组*/
	private String group = Constants.DEFAULT_GROUP;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
