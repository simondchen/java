package com.simon.msg;

import java.io.Serializable;
public class LoginResMsg implements Serializable{
	/*
	 * 0---登陆成功
	 * 1---用户名不存在
	 * 2---密码错误
	 * 3---注册成功
	 * 4---用户名已注册
	 */
	public int flag;
	public LoginResMsg(int flag){
		this.flag=flag;
	}
}
