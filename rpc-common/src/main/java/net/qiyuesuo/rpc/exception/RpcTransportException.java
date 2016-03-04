package net.qiyuesuo.rpc.exception;


/**
 * 传输层异常，在建立连接阶段抛出，一般是因为服务不可用，宕机或网络故障，可尝试其他节点重新调用
 * @author zhouree
 *
 */
public class RpcTransportException extends RpcException{
	
	private static final long serialVersionUID = 1L;

	public RpcTransportException() {
		super();
	}

	public RpcTransportException(String msg, Throwable e) {
		super(msg, e);
	}

	public RpcTransportException(String msg) {
		super(msg);
	}

	public RpcTransportException(Throwable e) {
		super(e);
	}

}
