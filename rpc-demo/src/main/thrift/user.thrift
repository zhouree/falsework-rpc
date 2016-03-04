namespace java net.qiyuesuo.rpc.demo

struct User{
	1:i64 id,
	2:string name
}

service UserServiceThrift{
	User get(1:i64 id)
	
	list<User> getAll()
}