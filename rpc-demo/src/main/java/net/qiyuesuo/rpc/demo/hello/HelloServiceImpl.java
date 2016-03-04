package net.qiyuesuo.rpc.demo.hello;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import net.qiyuesuo.rpc.annotation.RpcService;

@Service
@RpcService
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String username) throws TException {
		return "Hi," + username + " ,Welcome to the thrift's world !";
	}

}
