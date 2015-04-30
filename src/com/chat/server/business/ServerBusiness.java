package com.chat.server.business;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.chat.common.Feedback;
import com.chat.common.ServerInt;
import com.chat.common.SmartMap;
import com.chat.common.User;
import com.chat.common.UserDTO;
import com.chat.server.utils.Utils;

public class ServerBusiness  implements ServerInt {

	private ServerController controller;

	private Map<String, UserDTO> blindUsers;
	private Map<String, UserDTO> selectionUsers;
	private Map<String, UserDTO> vip;

	public ServerBusiness(ServerController controller) {
		super();
		this.controller = controller;
		blindUsers = new SmartMap(this);
		selectionUsers = new SmartMap(this);
		vip = new SmartMap(this);

	}

	

	@Override
	public Feedback register(UserDTO user) throws RemoteException {
		if (Utils.includesRequiredData(user)) {
			User tempUser = findUserByEmail(user.getEmail());
			if (tempUser == null) {
				if (Utils.registerNewUser(tempUser)) {
					return new Feedback(Feedback.SUCCESS, "Consgrats");
				}

			} else {
				return new Feedback(Feedback.FAILED, "Email Already Exist");
			}
		}

		return new Feedback(Feedback.FAILED, "Bad Inputs");
	}

	@Override
	public Feedback login(UserDTO user) throws RemoteException {
		if (user != null && user.getEmail() != null
				&& user.getPassword() != null) {
			User tempUser = findUserByEmail(user.getEmail());
			if (tempUser != null) {
				if (tempUser.getPassword().equals(user.getPassword())) {
					addToApproperateMap(user);
					return new Feedback(Feedback.SUCCESS, "Welcome", tempUser);
				}
			}
		}
		return new Feedback(Feedback.FAILED, "Invalid Username or password");

	}

	private void addToApproperateMap(UserDTO user) {
		switch (user.getSubscriptionType()) {
		case BLIND:
			blindUsers.put(user.getEmail(), user);
			break;
		case SELECTION:
			selectionUsers.put(user.getEmail(), user);
			break;
		case VIP:
			vip.put(user.getEmail(), user);
		}
	}

	@Override
	public Set<UserDTO> loadApproperateClients(UserDTO user)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(UserDTO user) throws RemoteException {
		switch (user.getSubscriptionType()) {
		case BLIND:
			blindUsers.remove(user.getEmail());
			break;
		case SELECTION:
			selectionUsers.remove(user.getEmail());
			break;
		case VIP:
			vip.remove(user.getEmail());
		}

	}

	public User findUserByEmail(String email) {
		if (email != null) {
			return Utils.findUserByEmail(email);
		}
		return null;
	}

}
