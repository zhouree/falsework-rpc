package net.qiyuesuo.rpc.common;

public class Constants {
	
	public static final int DEFAULT_WEIGHT = 1;
	//默认版本号
	public static final String DEFAULT_VERSION = "1.0.0";
	//默认group
	public static final String DEFAULT_GROUP = "default";
	//默认命名空间
	public static final String DEFAULT_NAME_SPACE = "servers";
	
	//ZK全局设置
    public static final String ZK_SEPARATOR = "/";
    public static final String ZK_NAMESPACE_CLIENTS = "clients";
    public static final String ZK_NAMESPACE_CONFIGS = "configs";
    public static final String ZK_NAMESPACE_STATISTICS = "statistics";

    /** zookeeper中使用时间戳作目录的格式 */
    public static final String ZK_TIME_NODE_FORMAT = "yyyyMMddHHmmss";

    /** zookeeper中总计节点名称 */
    public static final String ZK_NAMESPACE_TOTAL = "total";

    /** zookeeper中详细节点名称 */
    public static final String ZK_NAMESPACE_DETAIL = "detail";

}
