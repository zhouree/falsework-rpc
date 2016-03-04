package net.qiyuesuo.rpc.thrift;

import java.util.List;

import net.qiyuesuo.rpc.common.Service;

/**
 * Thrift RPC Service 扫描接口
 * 用来扫描发现需要暴露的RPC服务类
 * @author zhouree
 *
 */
public interface ServiceScanner {
	
	/**
	 * 返回需要暴露成RPC服务的Service列表</br>
	 * 若服务列表为空，则返回空对象
	 * @return
	 * @throws Exception 
	 */
	public List<Service> scanServices();

}
