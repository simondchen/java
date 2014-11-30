package com.simon.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.simon.msg.LoginMsg;
import com.simon.msg.LoginResMsg;
import com.simon.net.Client;

public class LoginModel {

	public int login(int flag,String name,String pwd){
		LoginMsg lmsg=new LoginMsg(flag,name,pwd);
		Socket s=null;
		ObjectInputStream ois=null;
		ObjectOutputStream oos=null;
		int status=0;
		try{
			s=new Socket("127.0.0.1",Client.port);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(lmsg);
			ois=new ObjectInputStream(s.getInputStream());
			LoginResMsg resmsg=(LoginResMsg) ois.readObject();
			ois.close();
			oos.close();
			s.close();
		    status=resmsg.flag;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(ois!=null){
					ois.close();
				}
				if(oos!=null){
					oos.close();
				}
				if(s!=null){
					s.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}
}
