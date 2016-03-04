package net.qiyuesuo.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = GlobalConfigProperties.PREFIX)
public class GlobalConfigProperties extends GlobalConfig{
	
	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "rpc.global";

}
