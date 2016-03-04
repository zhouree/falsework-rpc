package net.qiyuesuo.rpc.proxy;

import java.lang.reflect.Method;

import net.qiyuesuo.rpc.common.Invocation;
import net.qiyuesuo.rpc.common.Invoker;
import net.qiyuesuo.rpc.common.Service;

public class RpcInvocation implements Invocation {

	private Method method;
	private Object[] args;
	
	private Service service;
	
	private transient Invoker<?> invoker;
	
	public RpcInvocation(Service service, Method method, Object[] args) {
		super();
		this.service = service;
		this.method = method;
		this.args = args;
	}

	@Override
	public Object getTarget() {
		return null;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Invoker<?> getInvoker() {
		return invoker;
	}

	public void setInvoker(Invoker<?> invoker) {
		this.invoker = invoker;
	}

	@Override
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
