package net.qiyuesuo.rpc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import net.qiyuesuo.rpc.zookeeper.ZkClientFactoryBean;

@Configuration
@EnableConfigurationProperties(ZkConfigProperties.class)
public class ZkAutoConfiguration {

	 @Autowired
	private ZkConfigProperties properties;

	@Bean
	@ConditionalOnMissingBean
	@Order(Ordered.LOWEST_PRECEDENCE-1)
	public ZkClientFactoryBean zkClientFactory() throws Exception {
		return new ZkClientFactoryBean(properties);
	}
	
}
