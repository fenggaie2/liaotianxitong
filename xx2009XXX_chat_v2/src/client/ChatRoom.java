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
//���ڿؼ�����
	private   JPanel  downPanel;
	private   JLabel lblOnLineUserList,lblOnLineTotal ,lblOnLineCount,lblMessage;
	private  JLabel lblToUser,lblChatContent;
	
	private JList  lstUserList;
	private JTextArea  txtMessage;
	private JComboBox cmbUser,cmbFace;
	private String  onLineUserList[]={"������"};
	private JTextField  txtChatContent;
	private JButton btnSend,btnClear,btnSave,btnExt;
	
	String chatUser;   //��ǰ�û���
public ChatRoom(String  name){
	//���������Լ�������
	  super("�����ҡ��û���"+name+"��");
	 chatUser=name;
	downPanel=new JPanel(); downPanel.setBackground(Color.GREEN);
	//������ǩ�ؼ�
	lblOnLineUserList=new JLabel("�������û��б�");
	lblOnLineTotal=new JLabel("��������:");
	lblOnLineCount=new JLabel("0");
	lblMessage=new JLabel("��������Ϣ��");
	
	lstUserList=new JList();
		
	lstUserList.setBackground(Color.pink);
	txtMessage=new JTextArea(18,35 );
	txtMessage.setBackground(Color.white);
	//Ϊʹ�����û��б���й����������б���������
	JScrollPane  jscrollP=new JScrollPane  (lstUserList);  
	//Ϊʹ����������ʾ�����й����������ı����������
	JScrollPane  jscrollP2=new JScrollPane  (txtMessage);  

	//��ӿؼ�/*
 
	this.getContentPane().add(lblOnLineUserList);
	this.getContentPane().add(this.lblOnLineTotal);
	this.getContentPane().add(this.lblOnLineCount); 
	this.getContentPane().add(this.lblMessage);
	this.getContentPane().add(jscrollP);         //����б������
	this.getContentPane().add(jscrollP2);    //����ı�������
	
	lblOnLineUserList.setBounds(5, 5, 110, 20);
	lblOnLineTotal.setBounds( 120, 5, 60, 20);
	lblOnLineCount.setBounds( 180, 5, 20,20  );
	lblMessage.setBounds( 250, 5, 100, 20);
	
	jscrollP.setBounds(5, 25, 200, 320);	 
	jscrollP2.setBounds( 210, 25, 380, 320);

	//��һ������
	lblToUser=new JLabel("��ԣ�");
	lblChatContent=new JLabel("˵��");
	 
	//�����ؼ�	
	//��һ������
	cmbUser=new  JComboBox(onLineUserList);  //�û��б�
	cmbFace=new  JComboBox(new String[] { "����", "΢Ц",
			"��Ц", "��ϲ", "����", "���", "�ٺ�", "ɵЦ", "����", "����", "����", "����",
			"����", "����", "����", "��ŭ", "����", "����", "����", "�ʺ�", "��Ц", "���",
			"ʾ��", "����", "����" });                   //�����б�
	
	txtChatContent=new JTextField(25);//�óߴ������Ӱ�����
	btnSend=new JButton("����");
	btnClear=new JButton("����");
	btnSave=new JButton("����");
	btnExt=new JButton("�˳�");
	
	//��ӿؼ�
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
//���ô����Լ����������
	this.getContentPane().setLayout(null);		 
	this.getContentPane().add(downPanel); 
	downPanel.setBounds(0, 350, 600, 150);
	this.setSize(600, 500);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//ע�����
	btnSend.addActionListener(this);
	btnClear.addActionListener(this);
	btnSave.addActionListener(this);
	btnExt.addActionListener(this);
	
	this.lstUserList.addMouseListener(this);
 	// Ϊˢ������ҳ����Ϣ���������߳�
	Thread t = new Thread(this);
	t.start();	
}
Message messageObj;

public void run() {
	// TODO Auto-generated method stub
//�����������Message��Ϣ�����շ�������Message��Ϣ��
	boolean isFirstLogin = true; // �ж��Ƿ�յ�½
	int  userOnLineCount=0;
	Vector  lastUserOnLine=new Vector();
	 String [] userName=null;
	try {
		while(true){
			int n=0;
			messageObj=new Message();
		//����socket,
		Socket toServer=new Socket("127.0.0.1",1001);
		//���������
		ObjectOutputStream outStream=new ObjectOutputStream(toServer.getOutputStream());
		//�������
		outStream.writeObject( messageObj );
	 
		//����������
		ObjectInputStream inStream=new ObjectInputStream(toServer.getInputStream());
		//��ȡ���������ص�����
		messageObj=(Message)inStream.readObject();
		//------------------ˢ��������Ϣ�б�-----------------------
	/*	if (isFirstLogin) // ����յ�½
		{
			n = messageObj.chat.size(); // ���θ��û���½ǰ����������
			isFirstLogin = false;
		}
	*/ 	
		
	//	 this.txtMessage.setText("");   //��մ�������������
		//��������������Ϣ����������
	// for (int i = n; i < messageObj.chat.size(); i++) {
			Chat temp = (Chat) messageObj.chat;
			
			
		if(!temp.equals(null)) {
			if(!temp.chatMessage.equals("")){
			 
			String temp_message = null;
			if (temp.chatUser.equals(chatUser)) {   //���������ǵ�ǰ��½�û�ʱ
				if (temp.chatToUser.equals(chatUser)) {
					temp_message = "ϵͳ��ʾ�����벻Ҫ�������" + "\n";
				} else {										 
					temp_message = "���㡿�ԡ�" + temp.chatToUser + "��" + "˵��" + temp.chatMessage+ "\n";
				}  
				}else {  //�������Ĳ��ǵ�ǰ�û�ʱ�����Ǳ�ĵ�¼�û�
					if (temp.chatToUser.equals(chatUser)) {    //���� �Ķ����ǵ�ǰ�û�ʱ
						temp_message = "��" + temp.chatUser + "���ԡ��㡿"+ "˵��" + temp.chatMessage	+ "\n";
					}else {        //���� �Ķ���Ҳ���ǵ�ǰ�û�ʱ
						if (!temp.chatUser.equals(temp.chatToUser)) // �Է�û����������
						{
							temp_message = "��" + temp.chatUser + "���ԡ�"
					 					+ temp.chatToUser + "��" +  "˵��" + temp.chatMessage + "\n";
						}			 
			      }			
		      }	
			//��������Ϣ��ӵ��ı���
			this.txtMessage.append(temp_message);
			n++;
//		System.out.print(temp_message );
 //	}
			}
		}
			
//----------��ʾ�û�����������-----------------

		 
		 
		 
		 	//------------------��ʾ�û��뿪������----------------------
			
			 for(int  a=0;a<lastUserOnLine.size();a++){//��ÿһ���ϴ����ߵ��û����� 
				 Boolean flag=false;
				 Customer  cus1=(Customer)lastUserOnLine.elementAt(a);
				 for(int b=0;b<messageObj.userOnLine.size();b++){//��ÿһ����ǰ���ߵ��û�  
					 Customer  cus2=(Customer) messageObj.userOnLine.elementAt(b);
					 if(cus1.getCustName().equals(cus2.getCustName())){
						 flag=true;
						 break;
					 }					 
				 }
				 if(flag==false){//���û�������
					 this.txtMessage.append("��"+cus1.getCustName()+"���뿪������\n");
				 }
			 }

	//------------------ˢ�������û��б�-----------------------
		 	 userName=new String[messageObj.userOnLine.size()];	//��������	 			 	 
				for(int i=0;i<messageObj.userOnLine.size();i++){
					 client.Customer  t= 	(client.Customer)messageObj.userOnLine.elementAt(i);
					 userName[i]=t.getCustName();      //���ÿһ�������û���
				}
				this.lstUserList.removeAll();
				this.lstUserList.setListData( userName ) ;   //���б�����г������û�����
			 
		 
		 
		lastUserOnLine=messageObj.userOnLine;
		userOnLineCount=messageObj.userOnLine.size();
		
		 
		//�ر���
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
	//��ť�¼�
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton  sourceObj=(JButton)arg0.getSource();
		if(sourceObj.equals(btnSend)){   //���������Ͱ�ť
			sendMessage();
		}
		if(sourceObj.equals(btnClear)){    //��������հ�ť
				clearMessage();
			}
		if(sourceObj.equals(btnSave)){  //���������水ť
			saveMessage();
		}
		if(sourceObj.equals(btnExt)){  //�������˳���ť
			exitChat();
		}
		
	}
	//�˳�
	private void exitChat() {
	// TODO Auto-generated method stub
		//�����˳����󣬲����øö���
		ExitUser  eixtuser=new ExitUser();
		eixtuser.exitUserName=chatUser;
		
		
		try {
			//����Socket
			Socket toServer =new Socket("127.0.01",1001);
			//������
			ObjectOutputStream outStream=new ObjectOutputStream( toServer.getOutputStream());
			//�������
			outStream.writeObject( eixtuser);
			this.dispose();
			System.out.println("�˳�");
			//�رն���
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
		//���������Ϣ�����Է��͵�������
		Chat  chat=new Chat();
		chat.chatUser=chatUser;
		chat.chatMessage =this.txtChatContent.getText();
		chat.chatToUser=this.cmbUser.getSelectedItem().toString();
		//chat.emote=this.cmbFace.getSelectedItem().toString();
		//chat.whisper=false;
		
		//����
		try {
		//����socket,
			Socket toServer=new Socket("127.0.0.1",1001);
		//���������
			ObjectOutputStream outStream=new ObjectOutputStream(toServer.getOutputStream());
		//�������
			outStream.writeObject( chat );
		//���
			this.txtChatContent.setText("");
		//�ر���
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
		//���˫���б��Ļ�		
		if(arg0.getClickCount()==2){
			for(int i=0;i<cmbUser.getItemCount();i++){//��������б���Ƿ��Ѿ��и��û�
				if(cmbUser.getItemAt( i).equals(lstUserList.getSelectedValue())){
	                     flag=1;    //�û����Ѿ�����ʱ
	                     break;
				}
				
			}
			if(flag==0){ //�û����Ѿ������ڵĻ�����Ӹ��û�����ѡ����
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
