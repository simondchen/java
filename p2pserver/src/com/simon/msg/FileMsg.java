package com.simon.msg;

import java.io.Serializable;
public class FileMsg implements Serializable{
	public String filename;
	public FileMsg(String filename){
		this.filename=filename;
	}
}
