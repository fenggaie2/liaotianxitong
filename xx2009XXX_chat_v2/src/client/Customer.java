package client;

import java.io.Serializable;

public class Customer implements Serializable {
	/**
	 * 用户名
	 */
	private  String custName;
	/**
	 * 
	 * @return String 获得用户名称
	 */
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * 口令
	 */
	private  String custPassword;

	public String getCustPassword() {
		return custPassword;
	}

	public void setCustPassword(String custPassword) throws LengthTooLongException    {
		if(custPassword.length()>3 && custPassword.length()<10){		
			this.custPassword = custPassword;
		}else
		{
			throw   new LengthTooLongException();
		}


	}	 
}
class  LengthTooLongException  extends Exception{
	public String Message(){		
		return "您的口令必须是3--10位";
	}
}








