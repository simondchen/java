package com.simon.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.simon.model.LoginModel;
public class SigDialog extends JDialog implements ActionListener{
	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2,jl3;
	JTextField jtf1;
	JPasswordField jpf1,jpf2;
	JButton jb;
	Frame owner;
	public SigDialog(Frame owner,String title,boolean modal){
		super(owner,title,modal);
		this.owner=owner;
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jl1=new JLabel("用户名");
		jl2=new JLabel("密码");
		jl3=new JLabel("再次输入密码");
		jtf1=new JTextField(10);
		jpf1=new JPasswordField(10);
		jpf2=new JPasswordField(10);
		jb=new JButton("确定");
		jb.addActionListener(this);
		jp1.setLayout(new GridLayout(3,1));
		jp2.setLayout(new GridLayout(3,1));
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp2.add(jtf1);
		jp2.add(jpf1);
		jp2.add(jpf2);
		jp3.add(jb);
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		this.setSize(240,120);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb){
		String name=jtf1.getText();
		String pwd=jpf1.getText();
		String pwd2=jpf2.getText();
		int flag=0;
		if(!pwd.equals(pwd2)){
			JOptionPane.showMessageDialog(null,"两次输入的密码不一致,请再次输入");
			return;
		}
		LoginModel model=new LoginModel();
		flag=model.login(3,name,pwd);
		switch(flag){
			case 3:
				//注册成功
				owner.dispose();
				this.dispose();
				MainFrame mainframe=new MainFrame();
				break;
			case 4:
				//用户名已注册
				JOptionPane.showMessageDialog(null,"用户名已经注册,请重新输入用户名");
				break;
			default:
				System.out.println("不应该执行到这里");
				break;
		}
	}
	}
}
