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
	private ServerFrame sFrame;  	// ��������־����
	private Socket netClient;    //��ͻ���ͨѶSocket 
	private static Vector<client.Customer> userOnline=new Vector(1, 1); //�����û��б� 
	private static Vector<client.Chat> chatList=new Vector(1, 1);  //������Ϣ 
	private ObjectInputStream fromClient; //�ӿͻ��������� ������ 
	private PrintStream toClient;  //�����ͻ��� ��ӡ�� 
	private static Vector vList = new Vector();  //ע���û��б� 
	private Object obj;           //�����յ��Ķ���
	private static client.Chat  newChat=new  client.Chat();   //���µ��������ݣ����ͻ�ˢ����Ϣʱ���ᱻ���ء�
	
	public Connection1(ServerFrame serverFame,Socket  client){
		netClient=client;
		sFrame=serverFame;
		try {
			//�Լ������Ŀͻ����󴴽�����������ȡ��������obj
			  fromClient=new ObjectInputStream(netClient.getInputStream());
			  obj=fromClient.readObject();
			//�Լ������Ŀͻ����󴴽����������ͻ��˷�����Ϣ
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
			//���¾��崦��ͻ���ͬ����ʱ�������Ĳ�ͬ����
			// 1--����������¼ 			 
			if(obj.getClass().getName().equals("client.Customer")){
				System.out.println("�յ���½���󡣡�����");	
				serverLogin();   	//����½�û��Ƿ����
				
			}
			//2--���������ע�� 
			if(obj.getClass().getName().equals("client.Regist_Customer")){			 
				serverRegist();  	//��ע���û���Ϣ����
			}
			//3--���������"��������"�����͹�����������Ϣ
			if(obj.getClass().getName().equals("client.Chat")){
				serverChat();   
			}
			
			//4--���������message�� ��Ϊˢ��������
			if(obj.getClass().getName().equals("client.Message")){
				//System.out.println("�յ���Ϣ���󡣡�����");	
				serverMessage();  	//��ע���û���Ϣ����
	}
			
			
			//5--����������˳�
			if(obj.getClass().getName().equals("client.ExitUser")){
			//	 System.out.println("�յ��˳����󡣡�����");	
				serverExitChat();					
			}			
	}	
	private void serverExitChat() {
		//����˳��û�����
		client.ExitUser  exituser=(client.ExitUser)obj;
	//	System.out.print("�˳�ǰ��"+this.userOnline.size());
		//  �������û��б�����ɾ���˳��û�
		for(int i=0;i<userOnline.size();i++){
			if(userOnline.elementAt(i).getCustName().equals( exituser.exitUserName)){
				this.userOnline.removeElementAt(i);				
			}
		}		
		System.out.print("�������˳�,����������"+this.userOnline.size());		
	}
	//���ͻ���������Ϣ����ʱ�򣬷�������δ�������
	private void serverMessage() {
	//�������ؿͻ���Message���󣬲�����������ֵ
		client.Message  message=new client.Message();
		message.chat=newChat;
		message.serverMessage="";
		message.tiUser="";
		message.userOnLine=this.userOnline;
	//	System.out.println("��������Ϣ�����û���:"+message.userOnLine.size());
	//	System.out.println("��������Ϣchat�Ĵ�С:"+message.chat.size());		
		try{
			//���������
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
	//���ͻ��е�½�����ʱ�򣬷�������δ�������
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
						toClient.println("��½�ɹ�");
						userOnline.addElement(customer);					 
						objInput.close();
						isLogin=true;
						break;
					}else{
						System.out.println("���벻��ȷ");
						toClient.println("���벻��ȷ");
						isLogin=true;
						break;
					}					
				} 
			}
			if(isLogin==false){
					System.out.println("�û��������ڣ����ȵ�¼��");
			 		toClient.println("�û��������ڣ����ȵ�¼��");
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
	//���ͻ���ע�������ʱ�򣬷�������δ�������
	public void serverRegist(){
		client.Regist_Customer registClient=(client.Regist_Customer)obj;
		// System.out.println("�յ����󡣡�����");	
		boolean flag=false;   //Ĭ�Ͽͻ�û��ע��  
		//����ļ�user.txt������������ļ��Ƿ�Ϊ��		
		try {
			File f=new File("user.txt");
			if(f.length()!=0){             //����ļ��Ƿ�Ϊ��
				//��ȡ�ļ��еĶ���
				ObjectInputStream objStream=new  ObjectInputStream(new FileInputStream(f));
				vList=(Vector) objStream.readObject(); 
				//ѭ����ȡvector�е�ÿ�����󣬼���Ƿ����иö���
				for(int i=0;i<vList.size();i++){
					client.Regist_Customer registed=(client.Regist_Customer)vList.elementAt(i);
					if(registClient.custName.equals(registed.custName)){  //��ע��
						flag=true;
						toClient.println("�������Ѿ�����");
						objStream.close();
						break;
					}else if (registClient.custName.equals("������")) {
						toClient.println("��ֹʹ�ô�ע����,������ѡ��");
						flag =true;
						objStream.close();
						break;
					}					
				}		
				
			}//�ļ�����ʱ
			if(flag==false){//����ע��ʱ
				vList.add(registClient);
				ObjectOutputStream outStream=new ObjectOutputStream(new FileOutputStream(f));
				outStream.writeObject(vList);				
				toClient.println("ע��ɹ�");
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
	//���ͻ������������ʱ�򣬷�������δ�������
	public void serverChat(){
		//isNewChat=true;
		//���յ���������Ϣ����ת��
		client.Chat  chatObj=(client.Chat)obj;   
		newChat=chatObj;
		//��������󱣴浽vector
		chatList.addElement( chatObj);		
	//	System.out.println(" ���������¼userChat.size()=="+userChat.size());
	//	for(int i=0;i<userChat.size();i++){			
	//	System.out.println("��������==="+userChat.elementAt(i).chatMessage);
	//	}
	}	
}