package client;

import java.io.Serializable;

public class Regist_Customer implements Serializable {
/**
 * 用户名
 */
	public String custName;
	/**
	 * 口令
	 */
	public String custPwd;
	/**
	 * 口令确认
	 */
	public String  checkCustPwd;
	/**
	 * 性别
	 */
	public String custSex;
	/**
	 * 年龄
	 */
	public int custAge;
	/**
	 * 邮件
	 */
	public String custEmail;
	
	
	
}
