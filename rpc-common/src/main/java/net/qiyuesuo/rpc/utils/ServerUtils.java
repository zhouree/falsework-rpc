package net.qiyuesuo.rpc.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUtils {
	
	private static Logger logger = LoggerFactory.getLogger(ServerUtils.class);

	public static String detectServerIp() {
		String serverIp = null;
		// 一个主机有多个网络接口
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = netInterfaces.nextElement();
				// 每个网络接口,都会有多个"网络地址",比如一定会有lookback地址,会有siteLocal地址等.以及IPV4或者IPV6 .
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = addresses.nextElement();
					if (address instanceof Inet6Address) {
						continue;
					}
					if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
						serverIp = address.getHostAddress();
						logger.info("detected server ip :" + serverIp);
						continue;
					}
				}
			}
		} catch (SocketException e) {
			logger.error(e.getMessage(),e);
		}
		return serverIp;
	}
	
}
