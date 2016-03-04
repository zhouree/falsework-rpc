package net.qiyuesuo.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.qiyuesuo.rpc.config.ZookeeperConfig;

@ConfigurationProperties(prefix = ZkConfigProperties.PREFIX)
public class ZkConfigProperties extends ZookeeperConfig{

	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "zookeeper";
}
