/**
 * 
 */
package net.qiyuesuo.rpc.demo.thrift;

import net.qiyuesuo.rpc.demo.hello.HelloService;
import net.qiyuesuo.rpc.demo.hello.HelloServiceImpl;
import net.qiyuesuo.rpc.thrift.ThriftRpcServer;

/**
 * Thrift RPC Server服务端，不使用Spring
 * 
 * @author zhouree
 */
public class Server {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		HelloService hello = new HelloServiceImpl();
		ThriftRpcServer server = new ThriftRpcServer();
		server.expose(hello);
		server.start();
	}

}
