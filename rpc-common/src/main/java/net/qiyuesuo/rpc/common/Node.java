package net.qiyuesuo.rpc.common;

import java.util.List;

/**
 * Node接口，一个定义一个服务节点
 * @author zhouree
 *
 */
public interface Node {
	
    String getAddress();
    
    NodeStatus getStatus();
    
    boolean isAvailable();
    
    /**
     * 获取暴露的服务
     * @return
     */
    List<Service> getServices();

}
