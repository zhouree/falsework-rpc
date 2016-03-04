package net.qiyuesuo.rpc.common;

import java.io.Serializable;

public abstract class AbstractService implements Serializable, Service {

	private static final long serialVersionUID = 1L;

	private String group;
	private String namespace;

	private Class<?> serviceClass;

	private String version;

	/**
	 * 服务接口名称,接口的完整类名如：com.demo.user.UserService
	 */
	@Override
	public String getServiceName() {
		return serviceClass.getName();
	}

	/**
	 * 服务接口的版本号，默认1.0.0
	 */
	@Override
	public String getVersion() {
		return version == null ? Constants.DEFAULT_VERSION : version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public Class<?> getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(Class<?> serviceClass) {
		this.serviceClass = serviceClass;
	}

	@Override
	public String getServicePath() {
		return Constants.ZK_SEPARATOR + getNamespace() + Constants.ZK_SEPARATOR + getGroup() + Constants.ZK_SEPARATOR
				+ getServiceName() + Constants.ZK_SEPARATOR + getVersion();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((serviceClass == null) ? 0 : serviceClass.getName().hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		AbstractService other = (AbstractService) obj;
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (namespace == null) {
			if (other.namespace != null) {
				return false;
			}
		} else if (!namespace.equals(other.namespace)) {
			return false;
		}
		if (serviceClass == null) {
			if (other.serviceClass != null) {
				return false;
			}
		} else if (!serviceClass.getName().equals(other.serviceClass.getName())) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Service [namespace=" + namespace + ", group=" + group + ", serviceClass=" 
				+ serviceClass.getName() + ", version=" + version + "]";
	}

}
