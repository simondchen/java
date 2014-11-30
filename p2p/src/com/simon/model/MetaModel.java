package com.simon.model;

import javax.swing.table.AbstractTableModel;

import com.simon.msg.Msg;
import com.simon.msg.ResMsg;
import com.simon.net.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
public class MetaModel extends AbstractTableModel{

	public Vector rowdata=null;
	public Vector columnNames=null;
	
	public MetaModel(){
		columnNames=new Vector();
		columnNames.add("文件名");
		columnNames.add("文件大小");
		columnNames.add("用户名");
		columnNames.add("ip地址");
		columnNames.add("端口");
		columnNames.add("是否在线");
		//columnNames.add("是否在线");
	}
	
	public void updateModel(String sql,List<String> parms){
		Socket s=null;
		ObjectOutputStream oos=null;
		ObjectInputStream ois=null;
		try{
			s=new Socket("127.0.0.1",Client.port);
			Msg msg=new Msg(1,sql,parms);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
			ois=new ObjectInputStream(s.getInputStream());
			ResMsg resmsg=(ResMsg)ois.readObject();
			System.out.println("received msg:status="+resmsg.flag);
			oos.close();
			ois.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				try {
					if(oos!=null){
						oos.close();
					}
					if(ois!=null){
						ois.close();
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
	
	public void queryModel(String sql,List<String> parms){
		Socket s=null;
		ObjectOutputStream oos=null;
		ObjectInputStream ois=null;
		try{
			s=new Socket("127.0.0.1",Client.port);
			Msg msg=new Msg(0,sql,parms);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
			ois=new ObjectInputStream(s.getInputStream());
			ResMsg resmsg=(ResMsg)ois.readObject();
			System.out.println("received msg:status="+resmsg.flag);
			Vector rowdata=resmsg.result.rowdata;
			System.out.println(rowdata);
			this.rowdata=rowdata;
			oos.close();
			ois.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				try {
					if(oos!=null){
						oos.close();
					}
					if(ois!=null){
						ois.close();
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
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowdata.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return (String)((Vector)rowdata.get(rowIndex)).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)columnNames.get(column);
	}

}
