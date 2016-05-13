package client;

import java.io.Serializable;
import java.util.Vector;
public class Message implements Serializable {
	/**
	 * 在线用户集
	 */
	public  Vector userOnLine;
	/**
	 * 聊天信息集
	 */
	public client.Chat  chat;	
	/**
	 * 最新信息
	 */
	public  client.Chat  chatContent;
	/**
	 * 被踢者姓名
	 */
	public String tiUser;
	/**
	 * 公告
	 */
	public String serverMessage;
}
