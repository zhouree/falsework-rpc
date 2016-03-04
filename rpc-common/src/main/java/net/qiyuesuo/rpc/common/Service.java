package net.qiyuesuo.rpc.common;

public interface Service {
	
	/**
	 * 服务接口名称,接口的完整类名如：com.demo.user.UserService
	 */
	public String getServiceName() ;

	/**
	 * 服务接口的版本号，默认1.0.0
	 */
	public String getVersion();

	public String getGroup();

	public String getNamespace();

	public Object getHandler();

	public Class<?> getServiceClass();

	public String getServicePath();
	
}
