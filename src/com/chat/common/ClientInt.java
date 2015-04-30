package com.chat.common;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface ClientInt extends Serializable {

	Feedback recieveMessage(Message message) throws RemoteException;

	public void logout(UserDTO userDTO) throws RemoteException;

	void userLoggedIn(UserDTO user) throws RemoteException;

	void userLoggedOut(UserDTO user) throws RemoteException;

}
