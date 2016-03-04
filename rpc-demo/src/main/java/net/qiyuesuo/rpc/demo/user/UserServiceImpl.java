package net.qiyuesuo.rpc.demo.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import net.qiyuesuo.rpc.annotation.RpcService;

@Service
@RpcService
public class UserServiceImpl implements UserService {
	
	private List<User> users;

	public UserServiceImpl() {
		User lisa = new User(101, "Lisa");
		User jacky = new User(102, "Jacky");
		User tim = new User(103, "Tim");
		User rick = new User(104, "Rick");
		User toyota = new User(105, "Toyota");
		User yonghao = new User(106, "Yonghao");
		users = new ArrayList<User>();
		users.add(lisa);
		users.add(jacky);
		users.add(tim);
		users.add(rick);
		users.add(toyota);
		users.add(yonghao);
	}

	@Override
	public User get(long id) throws TException {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> getAll() throws TException {
		return users;
	}

}
