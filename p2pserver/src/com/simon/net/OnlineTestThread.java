package com.simon.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

import com.simon.msg.FileMsg;
public class OnlineTestThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("线程开启");
		Socket s=null;
		ObjectOutputStream oos=null;
		while(true){
			try {
				//每一秒发送一个心跳包来检测在线状态
				Thread.sleep(1000);
				Thread.yield();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//注意,这里迭代的过程中修改容器会导致modCount和exceptedCount值不一致,从而会抛出ConcurrentModificationException异常
			Iterator<String> it=Server.online.keySet().iterator();
		//	for(String username : Server.online.keySet()){
			while(it.hasNext()){
				String username=it.next();
				String ip=Server.online.get(username);
				//查询用户名对应的文件下载端口号,最好是所有客户端的文件下载端口号固定用一个,但是如果在一台
				//机器上实验,则要加上这一步!这里先假设simon---9999,alex---10000
				int port=username.equals("simon")?9999:10000;
				//int port=9999;
				try {
					//System.out.println(ip);
					s=new Socket("127.0.1.1",port);
					oos=new ObjectOutputStream(s.getOutputStream());
					FileMsg filemsg=new FileMsg("heartbeat");
					oos.writeObject(filemsg);
				} catch (Exception e){
					//出现异常,说明客户端的文件下载服务未开启,也即相当于用户不在线
					System.out.println("连接失败");
					//Server.online.remove(username);
					it.remove();
					e.printStackTrace();
				}finally{
					try {
							if(s!=null){
								s.close();
							}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}