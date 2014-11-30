package com.simon.db;
import java.sql.*;
import java.util.List;

import com.simon.msg.QueryResult;
import com.simon.net.Server;

import java.util.Vector;
public class DBManager {
	public static String driver="com.mysql.jdbc.Driver";
	public static String url="jdbc:mysql://localhost:3306/p2p";
	public static String user="root";
	public static String password="109445";
	public static Connection conn;  //the only connection
	public DBManager() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		conn=DriverManager.getConnection(url, user, password);
	}
	
	public ResultSet querySet(String sql,List<String> parms) throws SQLException{
		PreparedStatement stmt;
		ResultSet set;
		int i=1;
		stmt=conn.prepareStatement(sql);
		for(String parm:parms){
			stmt.setString(i++,parm);
		}
		set=stmt.executeQuery();
		return set;
	}
	
	public int sigUpdate(String name,String pwd) throws SQLException{
		String namesql="select * from User where username=?";
		PreparedStatement stmt=conn.prepareStatement(namesql);
		stmt.setString(1,name);
		ResultSet rs=stmt.executeQuery();
		if(rs.first()){
			//用户名已经注册过
			stmt.close();
			rs.close();
			conn.close();
			return 4;
		}
		String insertsql="insert into User values(?,?)";
		stmt=conn.prepareStatement(insertsql);
		stmt.setString(1,name);
		stmt.setString(2,pwd);
		stmt.executeUpdate();
		stmt.close();
		rs.close();
		conn.close();
		return 3;
	}
	
	//这样会在数据库层添加了业务逻辑,这样不太好
	public int loginQuery(String name,String pwd) throws SQLException{
		String namesql="select * from User where username=?";
		PreparedStatement stmt=conn.prepareStatement(namesql);
		stmt.setString(1,name);
		ResultSet rs=stmt.executeQuery();
		//注意这里rs.first()是否是这样使用的,要小心
		if(!rs.first()){
			//用户名不存在
			stmt.close();
			rs.close();
			conn.close();
			return 1;
		}
		String verifysql="select * from User where username=? and password=?";
		stmt=conn.prepareStatement(verifysql);
		stmt.setString(1,name);
		stmt.setString(2,pwd);
		rs=stmt.executeQuery();
		int ret= rs.first()?0:2;
		stmt.close();
		rs.close();
		conn.close();
		return ret;
	}
	
	public QueryResult query(String sql,List<String> parms) throws SQLException{
		PreparedStatement stmt;
		ResultSet rs;
		QueryResult result=new QueryResult();
		int i=1;
		stmt=conn.prepareStatement(sql);
		for(String parm:parms){
			stmt.setString(i++,parm);
		}
		rs=stmt.executeQuery();
		while(rs.next()){
			Vector hang=new Vector();
			//这里有没有更好的方法获取到数目，先这样写吧
			hang.add(rs.getString(2));
			hang.add(rs.getString(3));
			String username=rs.getString(4);
			hang.add(username);
			hang.add(rs.getString(5));
			hang.add(rs.getString(6));
			if(Server.online.get(username)==null){
				hang.add("不在线");
			}else{
				hang.add("在线");
			}
			result.rowdata.add(hang);
		}
		stmt.close();
		rs.close();
		conn.close();
		return result;
	}
	
	public void update(String sql,List<String> parms) throws SQLException{
		PreparedStatement stmt;
		int i=1;
		stmt=conn.prepareStatement(sql);
		for(String parm:parms){
			stmt.setString(i++,parm);
		}
		stmt.executeUpdate();
	}
}