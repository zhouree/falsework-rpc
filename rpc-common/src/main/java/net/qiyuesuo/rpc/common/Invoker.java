package net.qiyuesuo.rpc.common;

/**
 * 
 * @author zhouree
 *
 * @param <T>
 */
public interface Invoker<T> {

    Object invoke(Invocation invocation) throws Exception;

}
