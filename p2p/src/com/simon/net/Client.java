package com.simon.net;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

import com.simon.msg.Msg;
import com.simon.msg.ResMsg;
import com.simon.view.LoginFrame;
import com.simon.view.MainFrame;
import com.simon.view.UploadDialog;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
public class Client {
	public static int port=9000;
	public static String username;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoginFrame login=new LoginFrame();
		Thread t=new Thread(new FileTransThread());
		t.start();
	}
}
