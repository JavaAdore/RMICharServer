package com.chat.server.business;

import java.rmi.RemoteException;

import com.chat.common.Feedback;
import com.chat.common.ServerInt;
import com.chat.common.User;
import com.chat.common.UserDTO;
import com.chat.server.view.ServerView;

public class ServerController  {
	
	private ServerInt model;
	private ServerView view;
	
	
	
	
	public ServerController() {
		
		
		
	}
	public ServerController(ServerInt model, ServerView view) {
		super();
		this.model = model;
		this.view = view;
	}
	public ServerInt getModel() {
		return model;
	}
	public void setModel(ServerInt model) {
		this.model = model;
	}
	public ServerView getView() {
		return view;
	}
	public void setView(ServerView view) {
		this.view = view;
	}
	public void downServer() {
		
		
	}
	public void newUserLoggedIn(UserDTO user)  {


	}
	public void newUserLoggedOut(Object key) {
		
			
	}
	public void setOccupiedPort(int pORT_NUMBER) {
		view.setPortNumber(pORT_NUMBER);
		
	}
	
	
	
	

}
