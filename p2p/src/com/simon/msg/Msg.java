package com.simon.msg;
import java.util.List;
import java.io.Serializable;
public class Msg implements Serializable{
	/*
	 * flag indicates the type of message
	 * 0---query
	 * 1---update
	 */
	public int flag;
	public String sql;
	public List<String> parms;
	public Msg(int flag){
		this.flag=flag;
	}
	public Msg(int flag,String sql,List<String> parms){
		this.flag=flag;
		this.sql=sql;
		this.parms=parms;
	}
}
