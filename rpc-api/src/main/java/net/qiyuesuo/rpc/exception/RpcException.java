package net.qiyuesuo.rpc.exception;

/**
 *  RPC服务端异常
 */
public class RpcException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public RpcException(){
		super();
	}
	
	public RpcException(String msg){
		super(msg);
	}
	
	public RpcException(Throwable e){
		super(e);
	}
	
	public RpcException(String msg,Throwable e){
		super(msg,e);
	}
}
