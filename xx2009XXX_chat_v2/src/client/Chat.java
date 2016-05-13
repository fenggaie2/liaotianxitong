package client;

import java.io.Serializable;

public class Chat implements Serializable {
	/**
	 * 聊天用户名称
	 */
	public String chatUser;
	/**
	 * 聊天内容
	 */
	public String chatMessage;
	/**
	 * 聊天对象
	 */
	public String chatToUser;
	/**
	 * 聊天表情
	 */
	public String emote;
	/**
	 * 私聊
	 */
	public boolean  whisper;
}
