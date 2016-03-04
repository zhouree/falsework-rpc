package net.qiyuesuo.rpc.common;

public class ServiceClient extends AbstractService{

	private static final long serialVersionUID = 1L;

	public ServiceClient(Class<?> serviceClass) throws Exception {
		this(Constants.DEFAULT_GROUP,Constants.DEFAULT_NAME_SPACE,Constants.DEFAULT_VERSION,serviceClass);
	}
	
	/**
	 * 用于客户端代理时创建Service对象
	 * @param group
	 * @param namespace
	 * @param version
	 * @param serviceClass 要代理的接口类
	 * @throws Exception
	 */
	public ServiceClient(String group, String namespace,String version,Class<?> serviceClass) throws Exception {
		super();
		setGroup(group);
		setNamespace(namespace);
		setServiceClass(serviceClass);
		setVersion(version);
	}

	@Override
	public Object getHandler() {
		return null;
	}

}
