package com.simon.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.simon.msg.FileMsg;
public class FileTransThread implements Runnable{
	public static int port=9999;
	/*
	 * flag 表示线程的状态
	 * 0---继续执行线程
	 * 1---关闭线程
	 * 终止线程的方法，现在先采用最原始的设置标志位的方式，之后可以考虑采用更高级的方式，如interrupt?
	 */
	public int flag=0;
	public void run(){
		boolean bool=false;
		ObjectInputStream ois=null;
		DataOutputStream dos=null;
		FileInputStream fis=null;
		Socket cs=null;
		byte[] sendbyte=null;
		ServerSocket ss=null;
		try {
			System.out.println("thread runs");
			ss=new ServerSocket(port);
			//默认的文件存储路径为工作目录下的/file目录中
			while(flag==0){
				cs=ss.accept();
				System.out.println("request comes");
				ois=new ObjectInputStream(cs.getInputStream());
				FileMsg fmsg=(FileMsg)ois.readObject();
				//ois.close();
				String filename=fmsg.filename;
				//如果是心跳包的话,则退出此次循环
				if(filename.equals("heartbeat")){
					ois.close();
					cs.close();
					continue;
				}
				System.out.println(filename);
				File file=new File("file/"+filename);
				long l=file.length();
				dos=new DataOutputStream(cs.getOutputStream());
				fis=new FileInputStream(file);
				sendbyte=new byte[1024];
				double sumL=0;
				int length=0;
				while((length=fis.read(sendbyte,0,sendbyte.length))>0){
					sumL+=length;
					System.out.println("已传输:"+(sumL/l)*100+"%");
					dos.write(sendbyte,0,length);
					dos.flush();
				}
				if(sumL==l){
					bool=true;
				}
				dos.close();
				fis.close();
				cs.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("文件传输异常");
			bool=false;
			e.printStackTrace();
		}finally{
			System.out.println("continue退出,finally会执行吗");
				try {
					if(ois!=null){
						ois.close();
					}
					if(dos!=null){
						dos.close();
					}
					if(fis!=null){
						fis.close();
					}
					if(cs!=null){
						cs.close();
					}
					if(ss!=null){
						ss.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println(bool?"成功":"失败");
	}
}
