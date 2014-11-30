package com.simon.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.simon.model.LoginModel;
import com.simon.msg.LoginMsg;
import com.simon.net.Client;

import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener{

	//中间
	JPanel jp1;
	JPanel injp1,injp2;
	JLabel jl1,jl2;
	JTextField jtf;
	JPasswordField jpf;
	//底部
	JPanel jp2;
	JButton jb1,jb2;
	
	public LoginFrame(){
		//中间
		jp1=new JPanel();
		injp1=new JPanel();
		injp2=new JPanel();
		jl1=new JLabel("用户名");
		jl2=new JLabel("密码");
		jtf=new JTextField(10);
		jpf=new JPasswordField(10);
		//jp1.setLayout(null);
		injp1.add(jl1);
		injp1.add(jtf);
		injp2.add(jl2);
		injp2.add(jpf);
		jp1.add(injp1);
		jp1.add(injp2);
		//底部
		jp2=new JPanel();
		jb1=new JButton("登陆");
		jb2=new JButton("注册");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jp2.add(jb1);
		jp2.add(jb2);
		this.add(jp1);
		this.add(jp2,BorderLayout.SOUTH);
		this.setSize(300,150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		LoginModel model;
		int flag=0;
		if(e.getSource()==jb1){
			//登陆
			String name=jtf.getText();
			String pwd=jpf.getText();
			System.out.println(pwd);
			model=new LoginModel();
			flag=model.login(2,name,pwd);
			switch(flag){
				case 0:
					//登陆成功
					Client.username=name;
					this.dispose();
					MainFrame mainframe=new MainFrame();
					break;
				case 1:
					//用户名不存在
					JOptionPane.showMessageDialog(null,"用户名不存在,请注册");
					break;
				case 2:
					//用户名密码错误
					JOptionPane.showMessageDialog(null,"密码错误,请重新输入");
					break;
				default:
					System.out.println("程序不应该执行到这里");
					break;
			}
		}
		if(e.getSource()==jb2){
			//注册
			SigDialog dialog=new SigDialog(this,"注册",true);
		}
	}

}