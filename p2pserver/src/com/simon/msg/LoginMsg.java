package com.simon.msg;

import java.io.Serializable;
public class LoginMsg extends Msg implements Serializable{
	public String username;
	public String password;
	public LoginMsg(int flag,String username,String password){
		super(flag);
		this.username=username;
		this.password=password;
	}
}
