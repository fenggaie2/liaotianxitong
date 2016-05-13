package server;

import java.net.ServerSocket;
import java.net.Socket;

public class AppServer extends Thread {

	/**
	 * @param args
	 */
	ServerSocket serverSocket;
	ServerFrame  serverFrame;
	public AppServer(){
		//启动服务器检测窗口ServerFrame
		serverFrame=new ServerFrame();
		
		serverFrame.txtCurState.setText("服务器已启动");
		serverFrame.txtip.setText("127.0.0.1");
		serverFrame.txtPort.setText("1001");
		try{
			//创建服务器套接字，以便监听请求
		 serverSocket=new ServerSocket(1001);
			System.out.println("启动。。。。");	
		}catch(Exception e){			
			System.out.println("服务启动失败....."+e.toString());
		}
		//由于服务器允许同时接受多个客户的请求，所以服务器的监听工作，设置为多线程
		this.start();
	}
	public void run(){
		
		try{
		while(true){
		
			//开始监听，接受请求
			Socket client=serverSocket.accept();
			//将收到请求转交给另外的线程工作，同时将服务器监听窗口也一并交互
			new Connection1(serverFrame,client );
		}
		}catch(Exception e){ 
		System.out.println("不能监听"+e.toString());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AppServer();
	}

}
