package com.chat.server;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.chat.common.User;

@XmlRootElement
public class Users {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	@XmlElement
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User finUserByEmail(String email) {
		if (users != null) {
			for (User user : users) {
				if (user.getEmail().equalsIgnoreCase(email)) {
					return user;
				}
			}
		}
		return null;
	}

	public User addNewUser(User user) {
		if (users == null) {
			users = new ArrayList();
		}
		users.add(user);
		return user;
	}
	
	
	public void removeUser(User user) {
		if (users !=null) {
			users.remove(user);
		}
		
		
	}

}
