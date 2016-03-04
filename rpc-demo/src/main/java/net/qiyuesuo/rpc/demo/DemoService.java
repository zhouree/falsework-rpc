package net.qiyuesuo.rpc.demo;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import net.qiyuesuo.rpc.annotation.RpcInject;
import net.qiyuesuo.rpc.demo.hello.HelloService;
import net.qiyuesuo.rpc.demo.user.UserService;

@Service
public class DemoService {
	
	@RpcInject
	private HelloService helloService;
	
	@RpcInject
	private UserService userService;
	
	private long t0, t1;
	
	public void sayHello() throws Exception{
		System.out.println(helloService.hello("Ricky"));
	}
	
	public void performanceTest(){
		try {
			System.out.println("Start...");
			singleThreadTest();
			mutiThreadTest(4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void singleThreadTest() throws Exception{
		System.out.println("=========== 单线程调用10000次 ===============");
		t0 = System.currentTimeMillis();
		for(int i=0;i<10000;i++){
			userService.get(101);
		}
		t1 = System.currentTimeMillis();
		System.out.println("单线程调用10000次耗时：" + (t1 - t0)+"毫秒");
	}
	
	protected void mutiThreadTest(int threadNum) throws Exception{
		System.out.println("========== "+threadNum+"线程调用，每线程10000次请求  =========");
		HelloTask task = new HelloTask();
		t0 = System.currentTimeMillis();
		t1 = System.currentTimeMillis();
		for(int i=0;i<threadNum;i++){
			Thread t = new Thread(task);
			t.start();
		}
	}
	
	class HelloTask implements Runnable{
		@Override
		public void run() {
			try {
				for(int i=0;i<10000;i++){
					userService.get(101);
				}
				long t = System.currentTimeMillis();
				t1 = t1 > t ? t1 : t;
				String name = Thread.currentThread().getName();
				System.out.println(name + "耗时：" + (t1 - t0) + "毫秒");
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}
}
