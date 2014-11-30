package com.simon.model;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.simon.msg.FileMsg;

public class FileModel {
	public String ip;
	public int port;
	public String filename;
	public FileModel(String ip,String port,String filename){
		this.ip=ip;
		this.port=Integer.parseInt(port);
		this.filename=filename;
	}
	
	public int transFile(){
		Socket s=null;
		ObjectOutputStream oos=null;
		FileOutputStream fos=null;
		DataInputStream dis=null;
		byte[] inputbyte=null;
		try{
			s=new Socket(ip,port);
			FileMsg fmsg=new FileMsg(filename);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(fmsg);
			File file=new File("download/"+filename);
			if(!file.exists()){
				file.createNewFile();
			}
			fos=new FileOutputStream(new File("download/"+filename));
			dis=new DataInputStream(s.getInputStream());
			inputbyte=new byte[1024];
			int length=0;
			System.out.println("开始接收数据");
			while((length=dis.read(inputbyte,0,inputbyte.length))>0){
				fos.write(inputbyte,0,length);
				fos.flush();
			}
			System.out.println("完成接收");
			oos.close();
			fos.close();
			dis.close();
			s.close();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}finally{
				try {
					if(oos!=null){
						oos.close();
					}
					if(fos!=null){
						fos.close();
					}
					if(dis!=null){
						dis.close();
					}
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
