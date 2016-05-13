package client;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Login extends JFrame implements ActionListener {

	private JTextField txtServer,txtUserName;
	private JPasswordField txtPwd;
	private JButton btnLogin,btnRegist,btnExit;
	
	private JPanel p;
	public Login(){
		
		super("��¼��");//���õ�¼�������
		JLabel lblServer,lblUserName,lblPwd;
		//�����������
		lblServer=new JLabel("��������");
		lblUserName=new JLabel("�û�����");
		lblPwd=new JLabel("�� �");
		
		txtServer=new JTextField(20);
		txtUserName=new JTextField(20);
		txtPwd=new JPasswordField(20);
		
		btnLogin=new JButton("��½");
		btnRegist=new JButton("ע��");
		btnExit=new JButton("�˳�");
		
		//������壬��������
		p=new JPanel();
		p.setLayout(null);
		p.add(lblServer);
		p.add(txtServer);
		p.add(lblUserName);
		p.add(txtUserName);
		p.add(lblPwd);
		p.add(txtPwd);
		p.add(btnLogin);
		p.add(btnRegist);
		p.add(btnExit);
		//�������������λ��		
		lblServer.setBounds(5, 30, 100, 30);
		txtServer.setBounds(105, 30, 100, 25);
		
		lblUserName.setBounds(5, 65, 100, 30);
		txtUserName.setBounds(105, 65, 100, 25);
		
		lblPwd.setBounds( 5, 100, 100, 30);
		txtPwd.setBounds(105, 100 , 100, 25);
		
		btnLogin.setBounds(10, 135, 60,  25);
		btnRegist.setBounds(90, 135, 60,  25);
		btnExit.setBounds(170, 135, 60,  25);
	
		//������������
		getContentPane().add(p);
		
		//���ô�������	
		setSize(250, 220);
		setVisible(true);
		
		//��ťע�����
		btnLogin.addActionListener(this);
		btnRegist.addActionListener(this);
		btnExit.addActionListener(this);
		
		//���ô��ڹرհ�ť�Ĳ���
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	public void actionPerformed(ActionEvent arg0) {
		//�������Դ
		JButton  source=(JButton)arg0.getSource();
		if(source==btnLogin)	{  
			//����ǵ�¼��ť�Ļ�������û���������ʱ��½
			if(txtUserName.getText().equals(""))
				JOptionPane.showMessageDialog(null, "�û�������Ϊ��");
			if(txtPwd.getPassword().length ==0)
				JOptionPane.showMessageDialog(null, "�����Ϊ��");
			if(!(txtUserName.getText().equals("")) && txtPwd.getPassword().length !=0)
				login();
		}
		if(source==btnRegist){
			//����ǵ�¼��ť�Ļ�,����ע�ᴰ��
			regist();
		}
		if(source==btnExit){
			//������˳���ť�Ļ���
			System.exit(1);
		}
	}
	//��½��ť����
public void login(){
	Socket  socketToServer=null;
	//������¼�û�����
	Customer data=new Customer();
	//���õ�¼�û�������û����Ϳ���
	
	data.setCustName( txtUserName.getText());
	
	 try{
		data.setCustPassword(new String(txtPwd.getPassword()));
	 }catch(LengthTooLongException  ee  ){
		 System.out.print(ee.Message() );
		 
	 } 
	 
	try{
		//����socket����
		socketToServer=new Socket("127.0.0.1",8888);
		//���������  
		ObjectOutputStream  streamToServer= new ObjectOutputStream(socketToServer.getOutputStream());
		//����������Ϳͻ���½��Ϣ
		streamToServer.writeObject(data );
		//���������������ܷ�����������Ϣ
		BufferedReader   streamFromServer=  new BufferedReader(
				       new InputStreamReader(socketToServer.getInputStream())
				  		);
		//����������Ϣ
		String  info=streamFromServer.readLine();
		if(info.equals("��½�ɹ�")){
		  //����ʼ����
		  new ChatRoom(this.txtUserName.getText() );
		  
		}else{
		   JOptionPane op=new JOptionPane();
           op.showMessageDialog(null,info);
		}	  
	}catch(IOException e){
	
	}finally{
		try{
			socketToServer.close();
			}catch(Exception ee){}
	}
}
// ע�ᰴť����
public void regist(){
	new Regist();
	this.dispose();
}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();

	}
         
}
