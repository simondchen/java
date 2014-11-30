package com.simon.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.simon.model.FileModel;
import com.simon.model.MetaModel;
import com.simon.net.Client;
import com.simon.view.UploadDialog;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
public class MainFrame extends JFrame implements ActionListener{
	
	//component
	//顶端布局
	JPanel jp1;
	JLabel jl1;
	JTextField jtf;
	JButton jb1;
	JButton jbself;
	//中间布局
	JScrollPane jsp;
	JTable jt;
	//底端布局
	JPanel jp2;
	JButton jb2,jb3,jb4;
	public MainFrame(){
		//顶端布局
		jp1=new JPanel();
		jl1=new JLabel("请输入查询文件名");
		jtf=new JTextField(10);
		jb1=new JButton("查询");
		jbself=new JButton("查询自己");
		jb1.addActionListener(this);
		jbself.addActionListener(this);
		jp1.add(jl1);
		jp1.add(jtf);
		jp1.add(jb1);
		jp1.add(jbself);
		//底端布局
		jp2=new JPanel();
		jb2=new JButton("上传");
		//注意这里只能删除自己上传的元数据，所以最好显示自己所上传的数据是新建一个面板
		jb3=new JButton("删除");
		jb3.setEnabled(false);
		jb4=new JButton("下载");
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		//中间布局
		jt=new JTable();
		DefaultTableCellRenderer tcr=new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		jt.setDefaultRenderer(Object.class,tcr);
		jsp=new JScrollPane(jt);
		this.add(jsp);
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
		this.setSize(600,450);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("欢迎您,"+Client.username);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1){
			System.out.println("查询");
			jb3.setEnabled(false);
			//获取查询文本,然后查询要封装成一个表模型,从而将界面与业务逻辑分离开来
			//String sql=jtf.getText();
			String file=jtf.getText();
			if(file.equals("")){
				JOptionPane.showMessageDialog(null,"请输入查询文件名");
				return;
			}
			String sql="select * from MetaInfo where file='"+file+"'";
			if(file.equals("*")){
				sql="select * from MetaInfo";
			}
			System.out.println(sql);
			List<String> parms=new ArrayList<String>();
			MetaModel metamodel=new MetaModel();
			metamodel.queryModel(sql, parms);
			jt.setModel(metamodel);
		}
		if(e.getSource()==jbself){
			System.out.println("查询自己");
			jb3.setEnabled(true);
			String sql="select * from MetaInfo where username='"+Client.username+"'";
			List<String> parms=new ArrayList<String>();
			MetaModel metamodel=new MetaModel();
			metamodel.queryModel(sql,parms);
			jt.setModel(metamodel);
		}
		if(e.getSource()==jb2){
			System.out.println("上传");
			UploadDialog ud=new UploadDialog(this,"上传",true);
		}
		if(e.getSource()==jb3){
			System.out.println("删除");
			int row=jt.getSelectedRow();
			if(row==-1){
				JOptionPane.showMessageDialog(this,"请选择一行");
				return;
			}
			String filename=(String)jt.getValueAt(row,0);
			System.out.println(filename);
			 String sql="delete from MetaInfo where file=? and username=?";
			 List<String> parms=new ArrayList<String>();
			 parms.add(filename);
			 parms.add(Client.username);
			 MetaModel metamodel=new MetaModel();
			 metamodel.updateModel(sql,parms);
		}
		if(e.getSource()==jb4){
			System.out.println("下载");
			int row=jt.getSelectedRow();
			if(row==-1){
				JOptionPane.showMessageDialog(this,"请选择要下载的文件");
				return;
			}
			if(jt.getValueAt(row,2).equals(Client.username)){
				JOptionPane.showMessageDialog(this,"不要选择自己的上传的文件下载");
				return;
			}
			String ip=(String)jt.getValueAt(row,3);
			String port=(String)jt.getValueAt(row,4);
			String filename=(String)jt.getValueAt(row,0);
			FileModel filemodel=new FileModel(ip,port,filename);
			if(filemodel.transFile()==0){
				JOptionPane.showMessageDialog(this,"文件传输成功");
			}else{
				JOptionPane.showMessageDialog(this,"文件传输失败");
			}
		}
	}
}
