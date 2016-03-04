package net.qiyuesuo.rpc.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ClientConfigProperties.PREFIX)
public class ClientConfigProperties implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "rpc.client";
}
