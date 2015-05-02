package com.chat.common;

public class UserDTO extends User{

	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	
	
	private ClientInt clientInt;
	
	

	public ClientInt getClientInt() {
		return clientInt;
	}

	public void setClientInt(ClientInt clientInt) {
		this.clientInt = clientInt;
	}

	@Override
	public String toString() {
		return super.getEmail();
	}

	

	
	
	
	
	
}
