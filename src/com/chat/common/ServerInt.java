package com.chat.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface ServerInt extends Serializable, Remote {


	public Feedback login(UserDTO user) throws RemoteException;

	public Set<UserDTO> loadApproperateClients(UserDTO user)
			throws RemoteException;
	
	public void logout(UserDTO userDTO) throws RemoteException;

	Feedback register(UserDTO user) throws RemoteException;
	
	Feedback updateProfile(User user) throws RemoteException;
	
	 public Feedback findBestMatch(User me, SearchingCriteria targetedUser,
            List<String> blackList) throws RemoteException;;
            
     public Feedback sendMessageAsEmail(Message message) throws RemoteException;       
}
