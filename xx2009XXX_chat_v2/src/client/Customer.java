package client;

import java.io.Serializable;

public class Customer implements Serializable {
	/**
	 * �û���
	 */
	private  String custName;
	/**
	 * 
	 * @return String ����û�����
	 */
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * ����
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
		return "���Ŀ��������3--10λ";
	}
}








