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
		//������������ⴰ��ServerFrame
		serverFrame=new ServerFrame();
		
		serverFrame.txtCurState.setText("������������");
		serverFrame.txtip.setText("127.0.0.1");
		serverFrame.txtPort.setText("1001");
		try{
			//�����������׽��֣��Ա��������
		 serverSocket=new ServerSocket(1001);
			System.out.println("������������");	
		}catch(Exception e){			
			System.out.println("��������ʧ��....."+e.toString());
		}
		//���ڷ���������ͬʱ���ܶ���ͻ����������Է������ļ�������������Ϊ���߳�
		this.start();
	}
	public void run(){
		
		try{
		while(true){
		
			//��ʼ��������������
			Socket client=serverSocket.accept();
			//���յ�����ת����������̹߳�����ͬʱ����������������Ҳһ������
			new Connection1(serverFrame,client );
		}
		}catch(Exception e){ 
		System.out.println("���ܼ���"+e.toString());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AppServer();
	}

}
