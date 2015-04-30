package com.chat.server.view;

import com.chat.common.UserDTO;

public interface ServerView {

	public void userLogged(UserDTO user);
	
	public void userLoggedOut(UserDTO userDTO);
	
}
