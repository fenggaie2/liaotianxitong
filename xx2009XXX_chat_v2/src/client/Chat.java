package client;

import java.io.Serializable;

public class Chat implements Serializable {
	/**
	 * �����û�����
	 */
	public String chatUser;
	/**
	 * ��������
	 */
	public String chatMessage;
	/**
	 * �������
	 */
	public String chatToUser;
	/**
	 * �������
	 */
	public String emote;
	/**
	 * ˽��
	 */
	public boolean  whisper;
}
