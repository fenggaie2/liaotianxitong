package server;
import client.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

public class Connection1 extends Thread {
	private ServerFrame sFrame;  	// 服务器日志窗体
	private Socket netClient;    //与客户端通讯Socket 
	private static Vector<client.Customer> userOnline=new Vector(1, 1); //在线用户列表 
	private static Vector<client.Chat> chatList=new Vector(1, 1);  //聊天信息 
	private ObjectInputStream fromClient; //从客户到服务器 输入流 
	private PrintStream toClient;  //传到客户端 打印流 
	private static Vector vList = new Vector();  //注册用户列表 
	private Object obj;           //最新收到的对象
	private static client.Chat  newChat=new  client.Chat();   //最新的聊天内容，当客户刷新信息时将会被返回。
	
	public Connection1(ServerFrame serverFame,Socket  client){
		netClient=client;
		sFrame=serverFame;
		try {
			//对监听到的客户请求创建输入流，读取请求内容obj
			  fromClient=new ObjectInputStream(netClient.getInputStream());
			  obj=fromClient.readObject();
			//对监听到的客户请求创建输出流，向客户端发送消息
			  toClient=new PrintStream(netClient.getOutputStream());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}catch(ClassNotFoundException  e2){
			e2.printStackTrace();			
		}
		
		
		this.start();       
	}
	public void run(){			
			//以下具体处理客户不同请求时服务器的不同处理
			// 1--如果是请求登录 			 
			if(obj.getClass().getName().equals("client.Customer")){
				System.out.println("收到登陆请求。。。。");	
				serverLogin();   	//检查登陆用户是否存在
				
			}
			//2--如果是请求注册 
			if(obj.getClass().getName().equals("client.Regist_Customer")){			 
				serverRegist();  	//把注册用户信息保存
			}
			//3--如果是请求"聊天内容"，发送过来的聊天信息
			if(obj.getClass().getName().equals("client.Chat")){
				serverChat();   
			}
			
			//4--如果是请求“message” ，为刷新聊天室
			if(obj.getClass().getName().equals("client.Message")){
				//System.out.println("收到消息请求。。。。");	
				serverMessage();  	//把注册用户信息保存
	}
			
			
			//5--如果是请求退出
			if(obj.getClass().getName().equals("client.ExitUser")){
			//	 System.out.println("收到退出请求。。。。");	
				serverExitChat();					
			}			
	}	
	private void serverExitChat() {
		//获得退出用户对象
		client.ExitUser  exituser=(client.ExitUser)obj;
	//	System.out.print("退出前："+this.userOnline.size());
		//  在在线用户列表集合中删除退出用户
		for(int i=0;i<userOnline.size();i++){
			if(userOnline.elementAt(i).getCustName().equals( exituser.exitUserName)){
				this.userOnline.removeElementAt(i);				
			}
		}		
		System.out.print("服务器退出,在线人数："+this.userOnline.size());		
	}
	//当客户有请求“消息”的时候，服务器如何处理请求
	private void serverMessage() {
	//创建返回客户的Message对象，并设置其属性值
		client.Message  message=new client.Message();
		message.chat=newChat;
		message.serverMessage="";
		message.tiUser="";
		message.userOnLine=this.userOnline;
	//	System.out.println("服务器信息在线用户数:"+message.userOnLine.size());
	//	System.out.println("服务器信息chat的大小:"+message.chat.size());		
		try{
			//创建输出流
			ObjectOutputStream outputstream = new ObjectOutputStream(netClient.getOutputStream());
			outputstream.writeObject(message);		
			newChat=new client.Chat();
			newChat.chatMessage="";			
			netClient.close();
			outputstream.close();
		}catch(Exception  e ){
			e.printStackTrace();
		}		
	}
	//当客户有登陆请求的时候，服务器如何处理请求
	public void serverLogin(){
		client.Customer  customer=(client.Customer)obj;   
		newChat.chatMessage="";
		newChat.chatToUser="";
		newChat.chatUser="";
		//
		try {
			boolean isLogin=false;
			ObjectInputStream objInput=new ObjectInputStream(new FileInputStream("user.txt"));
			vList=(Vector)objInput.readObject();			
			for(int i=0;i<vList.size();i++){
				client.Regist_Customer rigistedUser=(client.Regist_Customer)vList.elementAt(i);
			 	if(rigistedUser.custName.equals(customer.getCustName())){
					if(rigistedUser.custPwd .equals(customer.getCustPassword() )){						
						toClient.println("登陆成功");
						userOnline.addElement(customer);					 
						objInput.close();
						isLogin=true;
						break;
					}else{
						System.out.println("密码不正确");
						toClient.println("密码不正确");
						isLogin=true;
						break;
					}					
				} 
			}
			if(isLogin==false){
					System.out.println("用户名不存在，请先登录！");
			 		toClient.println("用户名不存在，请先登录！");
			}			 
		 	toClient.close();
		 	fromClient.close();
		    netClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	//当客户有注册请求的时候，服务器如何处理请求
	public void serverRegist(){
		client.Regist_Customer registClient=(client.Regist_Customer)obj;
		// System.out.println("收到请求。。。。");	
		boolean flag=false;   //默认客户没有注册  
		//获得文件user.txt输入流，检查文件是否为空		
		try {
			File f=new File("user.txt");
			if(f.length()!=0){             //检查文件是否为空
				//读取文件中的对象
				ObjectInputStream objStream=new  ObjectInputStream(new FileInputStream(f));
				vList=(Vector) objStream.readObject(); 
				//循环读取vector中的每个对象，检查是否已有该对象
				for(int i=0;i<vList.size();i++){
					client.Regist_Customer registed=(client.Regist_Customer)vList.elementAt(i);
					if(registClient.custName.equals(registed.custName)){  //已注册
						flag=true;
						toClient.println("该名称已经存在");
						objStream.close();
						break;
					}else if (registClient.custName.equals("所有人")) {
						toClient.println("禁止使用此注册名,请另外选择");
						flag =true;
						objStream.close();
						break;
					}					
				}		
				
			}//文件不空时
			if(flag==false){//允许注册时
				vList.add(registClient);
				ObjectOutputStream outStream=new ObjectOutputStream(new FileOutputStream(f));
				outStream.writeObject(vList);				
				toClient.println("注册成功");
				outStream.close();				
			}
			toClient.close();
			fromClient.close();
			netClient.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	//当客户有聊天请求的时候，服务器如何处理请求
	public void serverChat(){
		//isNewChat=true;
		//将收到的聊天信息对象转化
		client.Chat  chatObj=(client.Chat)obj;   
		newChat=chatObj;
		//将聊天对象保存到vector
		chatList.addElement( chatObj);		
	//	System.out.println(" 共有聊天记录userChat.size()=="+userChat.size());
	//	for(int i=0;i<userChat.size();i++){			
	//	System.out.println("聊天内容==="+userChat.elementAt(i).chatMessage);
	//	}
	}	
}