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
		super("注册窗");//设置登录窗体标题
		//创建组件对象
		lblUserName=new JLabel("用 户 名： ");
		lblPwd=new JLabel("口    令：");
		lblConfirmPwd=new JLabel("口 令 确 认：");
		lblSex=new JLabel("性    别：");
		lblAge=new JLabel("年    龄：");
		lblEmail=new JLabel("电 子 邮 件：");
		
		txtUserName=new JTextField(20);
		txtPwd=new JPasswordField(20);
		txtConfirmPwd=new JPasswordField(20);
		txtAge=new JTextField(20);
		txtEmail=new JTextField(25);
		
		rdobtnMale=new JRadioButton("男",true);
		rdobtnFemale=new JRadioButton ("女");
		ButtonGroup  group=new ButtonGroup();
		group.add(rdobtnMale);
		group.add(rdobtnFemale);
		
		btnOK=new JButton("确定");
		btnBack=new JButton("返回");
		btnClear=new JButton("清空");
		
		//创建面板，并添加组件
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
		//设置组件在面板的位置		
				
		lblUserName.setBounds(25, 30, 80, 30);		txtUserName.setBounds(105, 30, 100, 20);
		lblPwd.setBounds( 25, 60, 80, 30);		txtPwd.setBounds(105, 60 , 100, 20);
		this.lblConfirmPwd.setBounds(25, 90, 80, 30);this.txtConfirmPwd.setBounds(105, 90,100, 20);
		this.lblSex.setBounds(25, 120, 80, 30);      this.rdobtnMale.setBounds(105, 120, 50, 30); this.rdobtnFemale.setBounds(150, 120, 50, 30);
		this.lblAge.setBounds(25, 150, 80, 30);  this.txtAge.setBounds(105, 150, 100, 20);
		this.lblEmail.setBounds(25,180,80,30);     this.txtEmail.setBounds(105, 180, 100, 20);		
		
		btnOK.setBounds(20, 210, 60,  20);	btnBack.setBounds(90, 210, 60,  20);	btnClear.setBounds(160, 210, 60,  20);
	
		//给窗体添加面板
		getContentPane().add(p);
		
		//设置窗口属性	
		setSize(250, 290);
		setVisible(true);
		
		//按钮注册监听
		btnOK.addActionListener(this);
		btnBack.addActionListener(this);
		btnClear.addActionListener(this);
		
		//设置窗口关闭按钮的操作
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//获得数据源
		JButton  source=(JButton)arg0.getSource();
		if(source==btnOK)	{  
			//如果是确定按钮的话，检查
			if(txtUserName.getText().equals(""))
				JOptionPane.showMessageDialog(null, "用户名不能为空 ");
			if(txtPwd.getPassword().length ==0)
				JOptionPane.showMessageDialog(null, "口令不能为空");
			if(!(txtUserName.getText().equals("")) && txtPwd.getPassword().length !=0)
				regist();
		}
		if(source==btnBack){
			//如果是登录按钮的话,调用注册窗口
			new Login();
	    	this.dispose();;
		}
		if(source==btnClear){
			//如果是退出清空的话，
			this.txtUserName.setText("");
			this.txtPwd.setText("");
			this.txtConfirmPwd.setText("");
			this.txtEmail.setText("");
			this.txtAge.setText("");
			this.rdobtnMale.setSelected(true);

		}
	}

	public void regist(){
		 
		//创建注册用户对象
		Regist_Customer  registClient=new Regist_Customer();
		//设置注册用户的值.....
		registClient.custName=this.txtUserName.getText();
		registClient.custPwd=new String(this.txtPwd.getPassword());
		registClient.custAge=Integer.parseInt(this.txtAge.getText());
		registClient.custSex=this.rdobtnMale.isSelected() ? "男":"女";
		registClient.custEmail=this.txtEmail.getText();
		
		
		
		
		//创建Socket对象
		try {
			Socket toServer=new Socket("127.0.0.1",1001);
			//使用Socket创建到服务器的输出流对象
			ObjectOutputStream   streamToServer=new ObjectOutputStream(toServer.getOutputStream());
			 streamToServer.writeObject(registClient);
			 
			 System.out.println("向服务器发送请求。。。。");	
			    BufferedReader fromServer= 	new BufferedReader(new InputStreamReader(toServer.getInputStream()));
			    String status=fromServer.readLine();
			    
			    //显示成功消息
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
