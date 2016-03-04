package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.TProcessor;

import net.qiyuesuo.rpc.common.Service;

public interface ServiceRegister {
	
	public void registerService(TProcessor processor, Service service) throws Exception;

}
