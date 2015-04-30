package com.chat.server.utils;

import java.io.Console;
import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;

import com.chat.common.Constants;
import com.chat.common.User;
import com.chat.common.UserDTO;
import com.chat.server.Users;

public class Utils {

	private static Users users = null;

	@SuppressWarnings("finally")
	public Users unmarchallUsersFile(String path) {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Users.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			users = (Users) unmarshaller.unmarshal(new File(path));

		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			return users;
		}

	}

	public static boolean includesRequiredData(UserDTO user) {

		return user != null && user.getUserName() != null
				&& user.getEmail() != null && user.getPassword() != null
				&& user.getCredit() != null;
	}

	public static boolean registerNewUser(User tempUser) {
		try {
			if (addNewUser(tempUser) != null) {

				JAXBContext context = JAXBContext.newInstance(Users.class);
				Marshaller marchaller = context.createMarshaller();
				marchaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marchaller.marshal(users, new StreamResult(new File(
						Constants.DATABASE_LOCATION)));
				return true;
			}

		} catch (JAXBException ex) {
			removeAddedUser(tempUser);
		}
		return false;

	}

	private static void removeAddedUser(User user) {
		if (users != null) {
			users.removeUser(user);
		}
	}

	public static User findUserByEmail(String email) {
		if (users != null) {
			return users.finUserByEmail(email);
		}
		return null;
	}

	public static User addNewUser(User user) {
		if (users != null) {
			return users.addNewUser(user);
		}
		return null;
	}

}
