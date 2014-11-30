package com.simon.view;

import javax.swing.*;

import com.simon.net.Client;
import com.simon.net.FileTransThread;
import com.simon.util.Ip;
import com.simon.model.MetaModel;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class UploadDialog extends JDialog implements ActionListener{
	
	//顶端布局
	JPanel jp1;
	JLabel jl;
	JComboBox jcb;
	//底端布局
	JPanel jp2;
	JButton jb2;
	//中间布局
	JPanel jp3,jp4;
	JLabel jl1,jl2,jl3,jl4;
	JTextField jtf1,jtf2,jtf3,jtf4;
	public UploadDialog(Frame owner,String title,boolean modal){
		super(owner,title,modal);
		//顶端布局
		File file=new File("file");
		if(!file.isDirectory()){
			System.out.println("file应该是一个目录");
		}
		String[] filename=file.list();
		//jtf1设置内容
		jtf1=new JTextField(10);
		jcb=new JComboBox(filename);
		String sfile=jcb.getSelectedItem().toString();
		File file1=new File("file/"+sfile);
		long length=file1.length();
		String len="";
		if(length<1024){
			len=length+"B";
		}else if(length>=1024){
			len=(float)length/1024+"K";
		}else if(length>1024 && length<1024*1024){
			len=(float)length/(1024*1024)+"M";
		}else{
			len=(float)length/(1024*1024*1024)+"G";
		}
		System.out.println(len);
		jtf1.setText(len+"");
		jcb.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					String filename=(String)jcb.getSelectedItem();
					File file=new File("file/"+filename);
					long length=file.length();
					String len="";
					if(length<1024){
						len=length+"B";
					}else if(length>=1024){
						len=(float)length/1024+"K";
					}else if(length>1024 && length<1024*1024){
						len=(float)length/(1024*1024)+"M";
					}else{
						len=(float)length/(1024*1024*1024)+"G";
					}
					System.out.println(len);
					jtf1.setText(len+"");
				}
			}
			
		});
		JPanel jp1=new JPanel();
		jl=new JLabel("选择要上传的文件");
		jp1.add(jl);
		jp1.add(jcb);
		//底端布局
		jp2=new JPanel();
		jb2=new JButton("提交");
		jb2.addActionListener(this);
		jp2.add(jb2);
		//中间布局
		jp3=new JPanel();
		jp4=new JPanel();
		jl1=new JLabel("文件大小");
		jl2=new JLabel("用户名");
		jl3=new JLabel("ip地址");
		jl4=new JLabel("端口号");
		jtf2=new JTextField(10);
		jtf3=new JTextField(10);
		jtf4=new JTextField(10);
		jtf2.setText(Client.username);
		//获取ip地址
		String ip=Ip.getIpAddress();
		if(ip==null){
			System.out.println("获取ip失败");
		}else{
			jtf3.setText(ip);
		}
		jtf4.setText(FileTransThread.port+"");
		jp3.setLayout(new GridLayout(4,1));
		jp4.setLayout(new GridLayout(4,1));
		jp3.add(jl1);
		jp3.add(jl2);
		jp3.add(jl3);
		jp3.add(jl4);
		jp4.add(jtf1);
		jp4.add(jtf2);
		jp4.add(jtf3);
		jp4.add(jtf4);
		//设置JFrame
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
		this.add(jp3,BorderLayout.WEST);
		this.add(jp4,BorderLayout.CENTER);
		this.setSize(400,300);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb2){
			//上传
			System.out.println("上传");
			String filename=(String)jcb.getSelectedItem();
			String size=jtf1.getText();
			String username=jtf2.getText();
			String ip=jtf3.getText();
			String port=jtf4.getText();
			String sql="insert into MetaInfo (file,size,username,ip,port) values(?,?,?,?,?)";
			List<String> parms=new ArrayList<String>();
			parms.add(filename);
			parms.add(size);
			parms.add(username);
			parms.add(ip);
			parms.add(port);
			MetaModel metamodel=new MetaModel();
			metamodel.updateModel(sql,parms);
			this.dispose();
		}
	}
}