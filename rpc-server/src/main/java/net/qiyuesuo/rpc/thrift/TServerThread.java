package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TServerThread extends Thread {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final TServer server;
	
	public TServerThread(TProcessor processor, ThriftServerConfig serverConfig) throws Exception {
		//TServerTransport  serverTransport = new TServerSocket(serverConfig.getPort());
		TNonblockingServerSocket  serverTransport = new TNonblockingServerSocket(serverConfig.getPort());
		
		TProtocolFactory protocolFactory = new TBinaryProtocol.Factory(true, true);  
		TTransportFactory transportFactory = new TFramedTransport.Factory();
		
		TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);  
		tArgs.processor(processor);
		tArgs.transportFactory(transportFactory);
		tArgs.protocolFactory(protocolFactory);
		
		//tArgs.maxWorkerThreads(1000);
		//tArgs.minWorkerThreads(10);
		tArgs.selectorThreads(serverConfig.getThrift().getSelectorThreads());
		tArgs.workerThreads(serverConfig.getThrift().getWorkerThreads());
		
		//server = new TThreadPoolServer(tArgs);
		server = new TThreadedSelectorServer(tArgs);
		
		logger.info("Processor:{}",processor.getClass().getName());
		logger.info("Protocol:{}",TBinaryProtocol.class.getName());
		logger.info("Transport:{}",TFramedTransport.class.getName());
		logger.info("TServer:{}",server.getClass().getName());
	}

	public String getTServerInfo(){
		StringBuilder builder = new StringBuilder();
		builder.append("Protocol:"+TBinaryProtocol.class.getName());
		builder.append("Transport:"+TFramedTransport.class.getName());
		builder.append("TServer:"+server.getClass().getName());
		return builder.toString();
	}
	//启动服务
	@Override
	public void run(){
		server.serve();
	}
	
	public void shutdown(){
		server.stop();
	}
	
}

