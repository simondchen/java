package com.simon.msg;

import java.io.Serializable;
public class ResMsg implements Serializable{
	/*
	 * 0---query
	 * 1---update
	 */
	public int flag;
	public QueryResult result;
	public ResMsg(int flag,QueryResult result){
		this.flag=flag;
		this.result=result;
	}
}
