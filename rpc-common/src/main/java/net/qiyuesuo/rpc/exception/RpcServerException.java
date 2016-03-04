package net.qiyuesuo.rpc.exception;

/**
 * 服务端服务异常，出现该异常可能是参数错误，或者服务本身出错
 * @author zhouree
 *
 */
public class RpcServerException extends RpcException {

	private static final long serialVersionUID = 1L;

	public RpcServerException(String msg, Throwable e) {
		super(msg, e);
	}

	public RpcServerException(String msg) {
		super(msg);
	}

	public RpcServerException(Throwable e) {
		super(e);
	}

}
