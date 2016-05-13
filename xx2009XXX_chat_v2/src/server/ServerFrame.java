package server;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerFrame extends JFrame implements ActionListener {

	private JTabbedPane tbp;
	private JPanel pnlServerInfo,pnlUserInfo,pnlState;
	
	private  JLabel  lblCurState,lblOnline,lblMaxOnline,lblServer,lblProtocol,lblip,lblPort;
	public JTextField  txtCurState,txtOnline,txtMaxOnline,txtServer,txtProtocol,txtip,txtPort;
	private JLabel  lblLog;
	public JTextArea  taLog;
	public JButton btnClose,btnLog;
	
	public  JTextArea taUserInfo;
	public JList lstUserList;
	public JTextField txtNotice;
	public JButton btnSend,btnKick;
	
	
	public ServerFrame(){
		super("服务器窗口");
		pnlServerInfo=new JPanel();    pnlServerInfo.setBackground(Color.BLUE);
		pnlUserInfo=new JPanel();       pnlUserInfo.setBackground(Color.PINK);
			pnlState=new JPanel();
		
		//========设置标签卡=======
		tbp=new JTabbedPane();
		this.tbp.addTab("服务器信息管理", pnlServerInfo);
		this.tbp.addTab("用户信息管理", pnlUserInfo);
		
		//==============设置服务器面板===============
		lblCurState=new JLabel("服务器状态");
		lblOnline=new JLabel("在线人数");
		lblMaxOnline=new JLabel("最多在线人数");
		lblServer=new JLabel("服务器：");
		lblProtocol=new JLabel("协议");
		lblip=new JLabel("ip地址'");
		lblPort=new JLabel("端口号");
		
		lblLog=new JLabel("服务器日记");
		taLog=new JTextArea(20,50);
		
		txtCurState=new JTextField(5);
		txtOnline=new JTextField(5);
		txtMaxOnline=new JTextField(5);
		txtServer=new JTextField(5);
		txtProtocol=new JTextField(5);
		txtip=new JTextField(5);
		txtPort=new JTextField(5);
		
		pnlState.setLayout(new GridLayout(14,1,5,0));
		pnlState.add(lblCurState );
		pnlState.add( txtCurState);
		pnlState.add(lblOnline );
		pnlState.add(txtOnline );
		pnlState.add( lblMaxOnline);
		pnlState.add( txtMaxOnline);
		pnlState.add( lblServer);
		pnlState.add( txtServer);
		pnlState.add( lblProtocol);
		pnlState.add(txtProtocol );
		pnlState.add(lblip );
		pnlState.add(txtip );
		pnlState.add(lblPort );	
		pnlState.add(txtPort );
		pnlState.setBackground(Color.LIGHT_GRAY);
		
		pnlServerInfo.setLayout(null);
		pnlServerInfo.add(pnlState); 		pnlState.setBounds(5, 10, 100, 400);	 
		pnlServerInfo.add(lblLog);          lblLog.setBounds(110,10, 100, 20);
		pnlServerInfo.add(taLog);            taLog.setBounds(110, 32, 450, 380);
		//按钮设置
		 btnClose=new JButton("关闭服务器");
		 btnLog=new JButton("保存日记");
		 pnlServerInfo.add(btnClose);
		 pnlServerInfo.add(btnLog);
		 btnClose.setBounds(200, 420, 120, 25);
		 btnLog.setBounds( 350, 420, 120, 25);
	//注册监听
		 btnClose.addActionListener(this);
		 btnLog.addActionListener(this);
		 
		//=========设置用户信息面板=========
		JLabel  lblUserInfo=new JLabel("【用户信息】");
		JLabel lblOnlineUser=new JLabel("【在线用户列表】");
		JLabel lblNotice=new JLabel("通知");
		JLabel lblOnlineCount=new JLabel("在线总人数 0");
		JScrollPane scPnlInfo= new JScrollPane();
		//scPnlInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JScrollPane scPnlUser = new JScrollPane();
		scPnlUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
		taUserInfo=new JTextArea();
		lstUserList=new JList();
		txtNotice=new JTextField(15);
		btnSend=new JButton("发送");
		btnKick=new JButton("踢人");
		
		scPnlInfo.setViewportView(this.taUserInfo );
		scPnlUser.setViewportView(lstUserList);
		lstUserList.setBackground(Color.CYAN);
		
		pnlUserInfo.setLayout(null);
		pnlUserInfo.add(lblUserInfo);
		pnlUserInfo.add(lblOnlineUser);
		pnlUserInfo.add(scPnlInfo);
	//	pnlUserInfo.add(taUserInfo);
		pnlUserInfo.add(scPnlUser);
	//	pnlUserInfo.add(lstUserList);
		pnlUserInfo.add(lblNotice);
		pnlUserInfo.add(txtNotice );
		pnlUserInfo.add(btnSend );
		pnlUserInfo.add(lblOnlineCount);
		pnlUserInfo.add(btnKick);
		
		lblUserInfo.setBounds(5,8, 150, 15);   lblOnlineUser.setBounds(350, 8, 150, 15);
		scPnlInfo.setBounds(5, 30, 350, 390);  scPnlUser.setBounds(360, 30, 220, 390);
		lblNotice.setBounds(5, 430 , 45, 20);
		txtNotice.setBounds(50, 430 , 150, 20);
		btnSend.setBounds(200, 430 , 80, 20);
		lblOnlineCount.setBounds(290, 430 , 100, 20);
		btnKick.setBounds(430, 430 , 80, 20);
		
		
		
		//======设置窗口========
		
		this.getContentPane().add(tbp);
		this.setSize(600, 530);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    new ServerFrame();

	}

}
