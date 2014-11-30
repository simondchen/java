package com.simon.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;

import com.simon.msg.LoginMsg;
import com.simon.msg.LoginResMsg;
import com.simon.msg.Msg;
import com.simon.msg.QueryResult;
import com.simon.msg.ResMsg;
import com.simon.db.DBManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Server {
	public static int port=9000;
	public static Map<String,String> online=null; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//开启线程
		Thread t=new Thread(new OnlineTestThread());
		t.setDaemon(true);
		t.start();
		online=new HashMap<String,String>();
		ServerSocket ss=null;
		Socket cs=null;
		ObjectInputStream ois=null;
		ObjectOutputStream oos=null;
		try {
			ss=new ServerSocket(port);
			System.out.println("服务器已经开启，等待连接");
			while(true){
				cs=ss.accept();
				System.out.println("request comes");
				DBManager mgr=new DBManager();
				ois=new ObjectInputStream(cs.getInputStream());
				Msg msg=(Msg)ois.readObject();
				switch(msg.flag){
					case 0:
						//query操作
						System.out.println(msg.sql);
						QueryResult result=mgr.query(msg.sql,msg.parms);
						//要在result中添加是否在线的判断 
						ResMsg resmsg=new ResMsg(0,result);
						oos=new ObjectOutputStream(cs.getOutputStream());
						oos.writeObject(resmsg);
						//要注意关闭打开的资源
						oos.close();
						break;
					case 1:
						//update操作
						System.out.println(msg.sql);
						mgr.update(msg.sql,msg. parms);
						ResMsg resupdatemsg=new ResMsg(1,null);
						oos=new ObjectOutputStream(cs.getOutputStream());
						oos.writeObject(resupdatemsg);
						oos.close();
						break;
					case 2:
						//登陆请求
						String logname=((LoginMsg)msg).username;
						String logpwd=((LoginMsg)msg).password;
						int flag=mgr.loginQuery(logname,logpwd);
						LoginResMsg lresmsg=new LoginResMsg(flag);
						oos=new ObjectOutputStream(cs.getOutputStream());
						oos.writeObject(lresmsg);
						oos.close();
						if(flag==0){
							//用户登陆成功
							String ip=cs.getInetAddress().toString();
							System.out.println(ip);
							ip=ip.substring(1);
							online.put(logname,ip);
							//刷新ip地址
							String sql="update MetaInfo set ip=? where username=?";
							List<String> parms=new ArrayList<String>();
							parms.add(ip);
							parms.add(logname);
							mgr=new DBManager();
							mgr.update(sql,parms);
						}
						break;
					case 3:
						//注册请求
						String signame=((LoginMsg)msg).username;
						String sigpwd=((LoginMsg)msg).password;
						int sigflag=mgr.sigUpdate(signame,sigpwd);
						LoginResMsg sigresmsg=new LoginResMsg(sigflag);
						oos=new ObjectOutputStream(cs.getOutputStream());
						oos.writeObject(sigresmsg);
						oos.close();
						break;
					default:
						System.out.println("程序不应该执行到这里");
						break;
				}
				//关闭资源
				if(oos!=null){
					oos.close();
				}
				ois.close();
				cs.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(cs!=null){
					cs.close();
				}
				if(ss!=null){
					ss.close();
				}
				if(ois!=null){
					ois.close();
				}
				if(oos!=null){
					oos.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}