package net.qiyuesuo.rpc.thrift;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;

/**
 * thrift 方法处理类
 * 
 * @author zhouree
 * @param <I>
 * @param <T>
 */
public class MethodHandler<I, T extends TBase> extends ProcessFunction<I, T> {

	//private final String methodName;
	//private final boolean oneway = false;

	//private final boolean invokeAsynchronously;

	public MethodHandler(String methodName) {
		super(methodName);
	}

	@Override
	protected boolean isOneway() {
		return false;
	}

	@Override
	public TBase getResult(I iface, T args) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getEmptyArgsInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
