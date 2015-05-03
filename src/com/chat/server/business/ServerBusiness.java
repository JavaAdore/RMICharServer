package com.chat.server.business;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chat.common.Constants;
import com.chat.common.CustomException;
import com.chat.common.Feedback;
import com.chat.common.Hobies;
import com.chat.common.Message;
import com.chat.common.SearchingCriteria;
import com.chat.common.ServerInt;
import com.chat.common.SmartMap;
import com.chat.common.User;
import com.chat.common.UserDTO;
import com.chat.server.Users;
import com.chat.server.utils.Utils;

public class ServerBusiness extends UnicastRemoteObject implements ServerInt {

	private ServerController controller;

	private Map<String, UserDTO> onlineUsers;

	public ServerBusiness(ServerController controller) throws CustomException,
			RemoteException {
		super();
		this.controller = controller;
		onlineUsers = new SmartMap(this);

		Utils.unmarchallUsersFile(Constants.DATABASE_NAME);

	}

	@Override
	public Feedback register(UserDTO user) throws RemoteException {
		if (Utils.includesRequiredData(user)) {
			User tempUser = findUserByEmail(user.getEmail());
			if (tempUser == null) {
				if (Utils.registerNewUser(user)) {
					Utils.migrateData(tempUser, user);

					onlineUsers.put(user.getEmail(), user);
					Utils.fireEmail(user.getEmail(), "", new Date(), "Welcome "
							+ user.getUserName() + " Your password is + "
							+ user.getPassword());
					return new Feedback(Feedback.SUCCESS, "Consgrats", user);
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
					Utils.migrateData(tempUser, user);
					user.setSubscriptionType(tempUser.getSubscriptionType());
					onlineUsers.put(user.getEmail(), user);
					
					return new Feedback(Feedback.SUCCESS, "Welcome", user);
				}
			}
		}
		return new Feedback(Feedback.FAILED, "Invalid Username or password");

	}

	@Override
	public Set<UserDTO> loadApproperateClients(UserDTO user)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(final UserDTO user) throws RemoteException {

		final UserDTO userDTO = onlineUsers.get(user.getEmail());
		userDTO.setClientInt(null);
		new Thread() {
			public void run() {
				synchronized (onlineUsers) {
					Iterator<UserDTO> users = onlineUsers.values().iterator();
					while (users.hasNext()) {
						try {
							UserDTO currentUser = users.next();
							if (currentUser.getEmail().equalsIgnoreCase(
									user.getEmail()) == false) {

								currentUser.getClientInt().userLoggedOut(user);

							}
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}

		}.start();

	}

	public User findUserByEmail(String email) {
		if (email != null) {
			return Utils.findUserByEmail(email);
		}
		return null;
	}

	@Override
	public Feedback updateProfile(User user) throws RemoteException {

		if (Utils.updateUser(user)) {
			UserDTO tempUserDTO = onlineUsers.get(user.getEmail());

			Utils.migrateData(user, tempUserDTO);

			return new Feedback(Feedback.SUCCESS,
					"Profile Updated successfully");
		} else {
			return new Feedback(Feedback.FAILED,
					"An error happend while updating profile , Please try again later");
		}

	}

	@Override
	public Feedback findBestMatch(User me, SearchingCriteria targetedUser,
			List<String> blackList) throws RemoteException {

		synchronized (onlineUsers) {
			UserDTO bestMatch = null;
			int maxMatchingRatio = 0;
			for (UserDTO currentUser : onlineUsers.values()) {
				int currentMatchingRatio = 0;
				if (currentUser.getEmail().equalsIgnoreCase(me.getEmail()) == false) {
					if (blackList != null
							&& blackList.contains(currentUser.getEmail())) {
						continue;
					}
					if (bestMatch == null) {
						bestMatch = currentUser;
					}
					currentMatchingRatio += matchingInCountry(targetedUser,
							currentUser);
					currentMatchingRatio += matchingInHoppies(targetedUser,
							currentUser);
					currentMatchingRatio += matchingInKeywords(targetedUser,
							currentUser);
					currentMatchingRatio += matchingInAgeCriteria(targetedUser,
							currentUser);
					if (currentMatchingRatio > maxMatchingRatio) {
						maxMatchingRatio = currentMatchingRatio;
						bestMatch = currentUser;
					}
				}

			}
			return bestMatch != null ? new Feedback(Feedback.SUCCESS,
					"Success", bestMatch) : new Feedback(Feedback.FAILED,
					"No Result Found ", null);

		}

	}

	private int matchingInAgeCriteria(SearchingCriteria targetedUser,
			UserDTO currentUser) {
		int result = 0;
		if (targetedUser.getMinAge() != 0 && targetedUser.getMaxAge() != 0
				&& currentUser.getBirthYear() != null
				&& currentUser.getBirthYear().intValue() != 0) {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			if (year - targetedUser.getMinAge() >= currentUser.getBirthYear()) {
				result++;
			}

			if (year - targetedUser.getMaxAge() <= currentUser.getBirthYear()) {
				result++;
			}

		}
		return result;
	}

	private int matchingInKeywords(SearchingCriteria targetedUser,
			UserDTO currentUser) {
		int result = 0;
		if (targetedUser.getKeywords() != null
				&& currentUser.getKeywords() != null) {
			for (String keyword : targetedUser.getKeywords()) {
				if (currentUser.getKeywords().contains(keyword)) {
					result++;
				}
			}
		}
		return result;
	}

	private int matchingInHoppies(SearchingCriteria targetedUser,
			UserDTO currentUser) {
		int result = 0;
		if (targetedUser.getHobbies() != null
				&& currentUser.getHobbies() != null) {
			for (Hobies hobby : targetedUser.getHobbies()) {
				if (currentUser.getHobbies().contains(hobby)) {
					result++;
				}
			}
		}
		return result;
	}

	private int matchingInCountry(SearchingCriteria targetedUser,
			UserDTO currentUser) {
		if (targetedUser.getCountry() != null
				&& currentUser.getCountry() == null
				&& targetedUser.getCountry().equals(currentUser.getCountry())) {
			return 1;
		}
		return 0;
	}

	@Override
	public Feedback sendMessageAsEmail(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		boolean result = Utils.fireEmail(message.getEmail(),
				message.getSender(), message.getSendingDate(),
				message.getMessageText());

		if (result) {
			return new Feedback(Feedback.SUCCESS, "Success");
		} else {
			return new Feedback(Feedback.FAILED, "Failed to send message to "
					+ message.getEmail());

		}
	}

	@Override
	public void ping() throws RemoteException {
		// do absoltely nothing

	}

}
