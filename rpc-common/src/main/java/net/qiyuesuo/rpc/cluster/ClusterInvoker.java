package net.qiyuesuo.rpc.cluster;

import net.qiyuesuo.rpc.common.Invoker;

public interface ClusterInvoker<T> extends Invoker<T> {
	
	LoadBalance getLoadBalance();
	
}
