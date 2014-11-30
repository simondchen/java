package com.simon.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ip {
	public static String getIpAddress(){
		try {
			InetAddress address=InetAddress.getLocalHost();
			String ip=address.getHostAddress();
			return ip;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
