package net.qiyuesuo.rpc.exception;

/**
 * 远程调用异常，远程调用过程中抛出的异常，服务端上未真正调用，可尝试再次调用
 * @author zhouree
 *
 */
public class RpcInvokeException extends RpcException {

	private static final long serialVersionUID = 1L;

}
