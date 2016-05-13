package client;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Regist extends JFrame implements ActionListener {

	private JLabel lblUserName,lblPwd,lblConfirmPwd,lblSex,lblAge,lblEmail;
	private JTextField txtUserName,txtAge,txtEmail;
	private JPasswordField txtPwd,txtConfirmPwd;
	private JRadioButton rdobtnMale,rdobtnFemale;
	private JButton btnOK,btnBack,btnClear;
	
	private JPanel p;
	public Regist(){
		super("ע�ᴰ");//���õ�¼�������
		//�����������
		lblUserName=new JLabel("�� �� ���� ");
		lblPwd=new JLabel("��    �");
		lblConfirmPwd=new JLabel("�� �� ȷ �ϣ�");
		lblSex=new JLabel("��    ��");
		lblAge=new JLabel("��    �䣺");
		lblEmail=new JLabel("�� �� �� ����");
		
		txtUserName=new JTextField(20);
		txtPwd=new JPasswordField(20);
		txtConfirmPwd=new JPasswordField(20);
		txtAge=new JTextField(20);
		txtEmail=new JTextField(25);
		
		rdobtnMale=new JRadioButton("��",true);
		rdobtnFemale=new JRadioButton ("Ů");
		ButtonGroup  group=new ButtonGroup();
		group.add(rdobtnMale);
		group.add(rdobtnFemale);
		
		btnOK=new JButton("ȷ��");
		btnBack=new JButton("����");
		btnClear=new JButton("���");
		
		//������壬��������
		p=new JPanel();
		p.setLayout(null);
		p.add(lblUserName);		p.add(txtUserName);		
		p.add(lblPwd);		        p.add(txtPwd);
		p.add(this.lblConfirmPwd); p.add(this.txtConfirmPwd);
		p.add(this.lblSex);        p.add(this.rdobtnMale);   p.add(this.rdobtnFemale );
		p.add(this.lblAge);        p.add(this.txtAge );
		p.add(this.lblEmail );     p.add(this.txtEmail );
		
		p.add(btnOK); 		        
		p.add(btnBack);
		p.add(btnClear);
		//�������������λ��		
				
		lblUserName.setBounds(25, 30, 80, 30);		txtUserName.setBounds(105, 30, 100, 20);
		lblPwd.setBounds( 25, 60, 80, 30);		txtPwd.setBounds(105, 60 , 100, 20);
		this.lblConfirmPwd.setBounds(25, 90, 80, 30);this.txtConfirmPwd.setBounds(105, 90,100, 20);
		this.lblSex.setBounds(25, 120, 80, 30);      this.rdobtnMale.setBounds(105, 120, 50, 30); this.rdobtnFemale.setBounds(150, 120, 50, 30);
		this.lblAge.setBounds(25, 150, 80, 30);  this.txtAge.setBounds(105, 150, 100, 20);
		this.lblEmail.setBounds(25,180,80,30);     this.txtEmail.setBounds(105, 180, 100, 20);		
		
		btnOK.setBounds(20, 210, 60,  20);	btnBack.setBounds(90, 210, 60,  20);	btnClear.setBounds(160, 210, 60,  20);
	
		//������������
		getContentPane().add(p);
		
		//���ô�������	
		setSize(250, 290);
		setVisible(true);
		
		//��ťע�����
		btnOK.addActionListener(this);
		btnBack.addActionListener(this);
		btnClear.addActionListener(this);
		
		//���ô��ڹرհ�ť�Ĳ���
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//�������Դ
		JButton  source=(JButton)arg0.getSource();
		if(source==btnOK)	{  
			//�����ȷ����ť�Ļ������
			if(txtUserName.getText().equals(""))
				JOptionPane.showMessageDialog(null, "�û�������Ϊ�� ");
			if(txtPwd.getPassword().length ==0)
				JOptionPane.showMessageDialog(null, "�����Ϊ��");
			if(!(txtUserName.getText().equals("")) && txtPwd.getPassword().length !=0)
				regist();
		}
		if(source==btnBack){
			//����ǵ�¼��ť�Ļ�,����ע�ᴰ��
			new Login();
	    	this.dispose();;
		}
		if(source==btnClear){
			//������˳���յĻ���
			this.txtUserName.setText("");
			this.txtPwd.setText("");
			this.txtConfirmPwd.setText("");
			this.txtEmail.setText("");
			this.txtAge.setText("");
			this.rdobtnMale.setSelected(true);

		}
	}

	public void regist(){
		 
		//����ע���û�����
		Regist_Customer  registClient=new Regist_Customer();
		//����ע���û���ֵ.....
		registClient.custName=this.txtUserName.getText();
		registClient.custPwd=new String(this.txtPwd.getPassword());
		registClient.custAge=Integer.parseInt(this.txtAge.getText());
		registClient.custSex=this.rdobtnMale.isSelected() ? "��":"Ů";
		registClient.custEmail=this.txtEmail.getText();
		
		
		
		
		//����Socket����
		try {
			Socket toServer=new Socket("127.0.0.1",1001);
			//ʹ��Socket�����������������������
			ObjectOutputStream   streamToServer=new ObjectOutputStream(toServer.getOutputStream());
			 streamToServer.writeObject(registClient);
			 
			 System.out.println("��������������󡣡�����");	
			    BufferedReader fromServer= 	new BufferedReader(new InputStreamReader(toServer.getInputStream()));
			    String status=fromServer.readLine();
			    
			    //��ʾ�ɹ���Ϣ
	            JOptionPane op=new JOptionPane();
	            op.showMessageDialog(null,status);
	            
	            
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Regist();
	}

}
