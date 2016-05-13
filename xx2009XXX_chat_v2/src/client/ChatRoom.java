package client;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.*;
public class ChatRoom extends JFrame  implements ActionListener,Runnable ,MouseListener {
//窗口控件声名
	private   JPanel  downPanel;
	private   JLabel lblOnLineUserList,lblOnLineTotal ,lblOnLineCount,lblMessage;
	private  JLabel lblToUser,lblChatContent;
	
	private JList  lstUserList;
	private JTextArea  txtMessage;
	private JComboBox cmbUser,cmbFace;
	private String  onLineUserList[]={"所有人"};
	private JTextField  txtChatContent;
	private JButton btnSend,btnClear,btnSave,btnExt;
	
	String chatUser;   //当前用户名
public ChatRoom(String  name){
	//创建窗口以及面板对象
	  super("聊天室【用户："+name+"】");
	 chatUser=name;
	downPanel=new JPanel(); downPanel.setBackground(Color.GREEN);
	//创建标签控件
	lblOnLineUserList=new JLabel("【在线用户列表】");
	lblOnLineTotal=new JLabel("在线人数:");
	lblOnLineCount=new JLabel("0");
	lblMessage=new JLabel("【聊天信息】");
	
	lstUserList=new JList();
		
	lstUserList.setBackground(Color.pink);
	txtMessage=new JTextArea(18,35 );
	txtMessage.setBackground(Color.white);
	//为使在线用户列表带有滚动条，将列表框填入面板
	JScrollPane  jscrollP=new JScrollPane  (lstUserList);  
	//为使聊天内容显示区带有滚动条，将文本域填入面板
	JScrollPane  jscrollP2=new JScrollPane  (txtMessage);  

	//添加控件/*
 
	this.getContentPane().add(lblOnLineUserList);
	this.getContentPane().add(this.lblOnLineTotal);
	this.getContentPane().add(this.lblOnLineCount); 
	this.getContentPane().add(this.lblMessage);
	this.getContentPane().add(jscrollP);         //添加列表框的面板
	this.getContentPane().add(jscrollP2);    //添加文本域的面板
	
	lblOnLineUserList.setBounds(5, 5, 110, 20);
	lblOnLineTotal.setBounds( 120, 5, 60, 20);
	lblOnLineCount.setBounds( 180, 5, 20,20  );
	lblMessage.setBounds( 250, 5, 100, 20);
	
	jscrollP.setBounds(5, 25, 200, 320);	 
	jscrollP2.setBounds( 210, 25, 380, 320);

	//下一个面板的
	lblToUser=new JLabel("你对：");
	lblChatContent=new JLabel("说：");
	 
	//创建控件	
	//下一个面板的
	cmbUser=new  JComboBox(onLineUserList);  //用户列表
	cmbFace=new  JComboBox(new String[] { "表情", "微笑",
			"甜笑", "惊喜", "嘻嘻", "扮酷", "嘿嘿", "傻笑", "好奇", "媚眼", "鬼脸", "陶醉",
			"害羞", "生气", "嚷嚷", "发怒", "伤心", "高明", "菜鸟", "问号", "狂笑", "大哭",
			"示爱", "呻吟", "想想" });                   //表情列表
	
	txtChatContent=new JTextField(25);//该尺寸会严重影响界面
	btnSend=new JButton("发送");
	btnClear=new JButton("清屏");
	btnSave=new JButton("保存");
	btnExt=new JButton("退出");
	
	//添加控件
	//downPanel.setLayout(null);
	this.downPanel.add(this.lblToUser);
	this.downPanel.add(this.cmbUser);
	this.downPanel.add(this.lblChatContent);
	this.downPanel.add(this.txtChatContent);
	this.downPanel.add(cmbFace );
	this.downPanel.add(this.btnSend);
	this.downPanel.add(btnClear );
	this.downPanel.add(btnSave );
	this.downPanel.add(btnExt );	
	lblToUser.setBounds(5, 5, 15, 10);
	cmbUser.setBounds( 20, 5, 20 , 10);
	lblChatContent.setBounds( 40, 5, 10, 10);
	txtChatContent.setBounds(50, 5, 20, 10);  
	cmbFace.setBounds( 72, 5, 20, 10);
	btnSend.setBounds(90, 5, 20, 10);	 
//设置窗口以及窗口内面板
	this.getContentPane().setLayout(null);		 
	this.getContentPane().add(downPanel); 
	downPanel.setBounds(0, 350, 600, 150);
	this.setSize(600, 500);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//注册监听
	btnSend.addActionListener(this);
	btnClear.addActionListener(this);
	btnSave.addActionListener(this);
	btnExt.addActionListener(this);
	
	this.lstUserList.addMouseListener(this);
 	// 为刷新聊天页面信息，启动新线程
	Thread t = new Thread(this);
	t.start();	
}
Message messageObj;

public void run() {
	// TODO Auto-generated method stub
//向服务器发送Message消息，接收服务器的Message消息，
	boolean isFirstLogin = true; // 判断是否刚登陆
	int  userOnLineCount=0;
	Vector  lastUserOnLine=new Vector();
	 String [] userName=null;
	try {
		while(true){
			int n=0;
			messageObj=new Message();
		//创建socket,
		Socket toServer=new Socket("127.0.0.1",1001);
		//创建输出流
		ObjectOutputStream outStream=new ObjectOutputStream(toServer.getOutputStream());
		//输出对象
		outStream.writeObject( messageObj );
	 
		//创建输入流
		ObjectInputStream inStream=new ObjectInputStream(toServer.getInputStream());
		//读取服务器返回的数据
		messageObj=(Message)inStream.readObject();
		//------------------刷新聊天信息列表-----------------------
	/*	if (isFirstLogin) // 如果刚登陆
		{
			n = messageObj.chat.size(); // 屏蔽该用户登陆前的聊天内容
			isFirstLogin = false;
		}
	*/ 	
		
	//	 this.txtMessage.setText("");   //清空窗口中聊天内容
		//检查服务器返回信息的聊天内容
	// for (int i = n; i < messageObj.chat.size(); i++) {
			Chat temp = (Chat) messageObj.chat;
			
			
		if(!temp.equals(null)) {
			if(!temp.chatMessage.equals("")){
			 
			String temp_message = null;
			if (temp.chatUser.equals(chatUser)) {   //如果聊天的是当前登陆用户时
				if (temp.chatToUser.equals(chatUser)) {
					temp_message = "系统提示您：请不要自言自语！" + "\n";
				} else {										 
					temp_message = "【你】对【" + temp.chatToUser + "】" + "说：" + temp.chatMessage+ "\n";
				}  
				}else {  //如果聊天的不是当前用户时，而是别的登录用户
					if (temp.chatToUser.equals(chatUser)) {    //聊天 的对象是当前用户时
						temp_message = "【" + temp.chatUser + "】对【你】"+ "说：" + temp.chatMessage	+ "\n";
					}else {        //聊天 的对象也不是当前用户时
						if (!temp.chatUser.equals(temp.chatToUser)) // 对方没有自言自语
						{
							temp_message = "【" + temp.chatUser + "】对【"
					 					+ temp.chatToUser + "】" +  "说：" + temp.chatMessage + "\n";
						}			 
			      }			
		      }	
			//将聊天信息添加到文本框
			this.txtMessage.append(temp_message);
			n++;
//		System.out.print(temp_message );
 //	}
			}
		}
			
//----------显示用户进入聊天室-----------------

		 
		 
		 
		 	//------------------显示用户离开聊天室----------------------
			
			 for(int  a=0;a<lastUserOnLine.size();a++){//对每一个上次在线的用户搜索 
				 Boolean flag=false;
				 Customer  cus1=(Customer)lastUserOnLine.elementAt(a);
				 for(int b=0;b<messageObj.userOnLine.size();b++){//对每一个当前在线的用户  
					 Customer  cus2=(Customer) messageObj.userOnLine.elementAt(b);
					 if(cus1.getCustName().equals(cus2.getCustName())){
						 flag=true;
						 break;
					 }					 
				 }
				 if(flag==false){//该用户不存在
					 this.txtMessage.append("【"+cus1.getCustName()+"】离开聊天室\n");
				 }
			 }

	//------------------刷新在线用户列表-----------------------
		 	 userName=new String[messageObj.userOnLine.size()];	//创建数组	 			 	 
				for(int i=0;i<messageObj.userOnLine.size();i++){
					 client.Customer  t= 	(client.Customer)messageObj.userOnLine.elementAt(i);
					 userName[i]=t.getCustName();      //获得每一个在线用户名
				}
				this.lstUserList.removeAll();
				this.lstUserList.setListData( userName ) ;   //在列表框中列出在线用户名称
			 
		 
		 
		lastUserOnLine=messageObj.userOnLine;
		userOnLineCount=messageObj.userOnLine.size();
		
		 
		//关闭流
		outStream.close();
		toServer.close();		
 
		toServer.close();
		Thread.sleep(3000);
		}
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

}
	//按钮事件
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton  sourceObj=(JButton)arg0.getSource();
		if(sourceObj.equals(btnSend)){   //监听到发送按钮
			sendMessage();
		}
		if(sourceObj.equals(btnClear)){    //监听到清空按钮
				clearMessage();
			}
		if(sourceObj.equals(btnSave)){  //监听到保存按钮
			saveMessage();
		}
		if(sourceObj.equals(btnExt)){  //监听到退出按钮
			exitChat();
		}
		
	}
	//退出
	private void exitChat() {
	// TODO Auto-generated method stub
		//创建退出对象，并设置该对象
		ExitUser  eixtuser=new ExitUser();
		eixtuser.exitUserName=chatUser;
		
		
		try {
			//创建Socket
			Socket toServer =new Socket("127.0.01",1001);
			//创建流
			ObjectOutputStream outStream=new ObjectOutputStream( toServer.getOutputStream());
			//输出对象
			outStream.writeObject( eixtuser);
			this.dispose();
			System.out.println("退出");
			//关闭对象
			outStream.close();
			toServer.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private   void saveMessage() {
		// TODO Auto-generated method stub
		
	}
	private void clearMessage() {
		// TODO Auto-generated method stub
		this.txtMessage.setText("");
	}
	private void sendMessage() {
		// TODO Auto-generated method stub
		//获得聊天信息对象，以发送到服务器
		Chat  chat=new Chat();
		chat.chatUser=chatUser;
		chat.chatMessage =this.txtChatContent.getText();
		chat.chatToUser=this.cmbUser.getSelectedItem().toString();
		//chat.emote=this.cmbFace.getSelectedItem().toString();
		//chat.whisper=false;
		
		//发送
		try {
		//创建socket,
			Socket toServer=new Socket("127.0.0.1",1001);
		//创建输出流
			ObjectOutputStream outStream=new ObjectOutputStream(toServer.getOutputStream());
		//输出对象
			outStream.writeObject( chat );
		//清空
			this.txtChatContent.setText("");
		//关闭流
			  outStream.close();     
		 	toServer.close();			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 	
	public static void main(String[] args) {
		new ChatRoom("feng");
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int flag=0;
		//如果双击列表框的话		
		if(arg0.getClickCount()==2){
			for(int i=0;i<cmbUser.getItemCount();i++){//检查下拉列表框是否已经有该用户
				if(cmbUser.getItemAt( i).equals(lstUserList.getSelectedValue())){
	                     flag=1;    //用户名已经存在时
	                     break;
				}
				
			}
			if(flag==0){ //用户名已经不存在的话，添加该用户。并选中它
				 this.cmbUser.addItem( lstUserList.getSelectedValue());
				 this.cmbUser.setSelectedItem( lstUserList.getSelectedValue() );
			}             
		}
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
