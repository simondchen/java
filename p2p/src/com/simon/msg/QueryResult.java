package com.simon.msg;

import java.io.Serializable;
import java.util.Vector;
public class QueryResult implements Serializable{
	public Vector rowdata;
	//columnNames 由前段来决定，所以服务端只需要传送rowdata即可
	//public Vector columnNames;
	public QueryResult(){
		rowdata=new Vector();
	}
}
