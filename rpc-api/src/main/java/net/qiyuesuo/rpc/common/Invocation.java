package net.qiyuesuo.rpc.common;

import java.lang.reflect.Method;

public interface Invocation {
	
	Object getTarget();
	
	Method getMethod();
	
	Object[] getArgs();

	Service getService();

}
