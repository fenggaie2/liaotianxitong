package client;

import java.io.Serializable;
import java.util.Vector;
public class Message implements Serializable {
	/**
	 * �����û���
	 */
	public  Vector userOnLine;
	/**
	 * ������Ϣ��
	 */
	public client.Chat  chat;	
	/**
	 * ������Ϣ
	 */
	public  client.Chat  chatContent;
	/**
	 * ����������
	 */
	public String tiUser;
	/**
	 * ����
	 */
	public String serverMessage;
}
