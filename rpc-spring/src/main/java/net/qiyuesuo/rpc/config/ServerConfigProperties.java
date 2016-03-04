package net.qiyuesuo.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.qiyuesuo.rpc.thrift.ThriftServerConfig;

@ConfigurationProperties(prefix = ServerConfigProperties.PREFIX)
public class ServerConfigProperties extends ThriftServerConfig{

	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "rpc.server";
}
