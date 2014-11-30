package com.simon.db;
import java.sql.*;
public class DbTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection conn;
			PreparedStatement stmt;
			ResultSet res;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/simon","root","109445");
			stmt=conn.prepareStatement("select * from MyClass");
			res=stmt.executeQuery();
			while(res.next()){
				String name=res.getString("name");
				String a=res.getString(1);
				System.out.println(name);
				System.out.println(a);
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
