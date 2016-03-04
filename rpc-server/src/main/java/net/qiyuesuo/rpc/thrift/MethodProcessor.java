package net.qiyuesuo.rpc.thrift;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TBase;
import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TProcessor;

public class MethodProcessor<I> extends TBaseProcessor<I> implements TProcessor {

	public MethodProcessor(I iface) {
		super(iface, getProcessMap());
	}

	protected static <I> Map<String, ProcessFunction<I, ? extends TBase>> getProcessMap() {
		Map<String, ProcessFunction<I, ? extends TBase>> processMap = new HashMap();
		MethodHandler<I,TBase> hello = new MethodHandler("hello");
		processMap.put("hello", hello);
		return processMap;
	}

}
