package net.qiyuesuo.rpc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import net.qiyuesuo.rpc.config.ClientAutoConfiguration;
import net.qiyuesuo.rpc.server.SpringRpcServerStarter;

/**
 * Spring boot 方式启动服务</br>
 * {@link SpringRpcServerStarter}
 * 
 * @author zhouree
 */
@SpringBootApplication(exclude={ClientAutoConfiguration.class})
@ComponentScan(excludeFilters={@Filter(type=FilterType.ASSIGNABLE_TYPE,value={ClientApplication.class})})
public class ServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
		context.registerShutdownHook();
		context.start();
	}
	
}
