namespace java net.qiyuesuo.rpc.demo
 
service HelloServiceThrift{
	string hello(1: string name);
}