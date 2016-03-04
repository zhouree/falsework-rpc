package net.qiyuesuo.rpc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import net.qiyuesuo.rpc.config.ServerAutoConfiguration;

/**
 * Spring boot 方式启动客户端
 * 
 * @author zhouree
 */
@SpringBootApplication(exclude={ServerAutoConfiguration.class})
@ComponentScan(excludeFilters={@Filter(type=FilterType.ASSIGNABLE_TYPE,value={ServerApplication.class})})
public class ClientApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
		context.registerShutdownHook();
		context.start();

		DemoService demoService = context.getBean(DemoService.class);
		demoService.sayHello();
		//demoService.performanceTest();
	}

}
