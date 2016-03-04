package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

public class ThriftUtils {
	
	/**
	 * 反射获取TServiceClient对象
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	public static TServiceClient reflectServiceClient(String serviceName ,TTransport transport) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// 定义协议, 与服务端对应
		TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(new TBinaryProtocol(transport), serviceName);
		// 加载Client类
		String clientName = serviceName + "Thrift$Client";
		Class<TServiceClient> clientClass = (Class<TServiceClient>) classLoader.loadClass(clientName);
		// 获取客户端
		return clientClass.getConstructor(TProtocol.class).newInstance(multiProtocol);
	}
	

}
