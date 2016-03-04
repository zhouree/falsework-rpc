package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.TProcessor;

public interface ProcessorFactory{

	public TProcessor buildProcessor();
	
}
