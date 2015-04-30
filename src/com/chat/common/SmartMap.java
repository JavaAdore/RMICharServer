package com.chat.common;

import java.rmi.RemoteException;
import java.util.HashMap;

import com.chat.server.business.ServerBusiness;
import com.chat.server.business.ServerController;

public class SmartMap extends HashMap<String, UserDTO> {

	public ServerBusiness serverBusiness;

	public SmartMap(ServerBusiness serverBusiness) {
		this.serverBusiness = serverBusiness;
	}

	@Override
	public UserDTO put(String key, UserDTO user) {
		nofifyOthersByHisComming(user);
		return super.put(key, user);
	}

	private void nofifyOthersByHisComming(final UserDTO user) {
		for (final UserDTO curUser : values()) {
			new Thread() {
				public void run() {
					if (curUser.equals(user) == false) {
						try {
							curUser.getClientInt().userLoggedIn(user);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}

	}

	@Override
	public UserDTO remove(Object key) {
		nofifyOthersByHisleaving(key);

		return super.remove(key);
	}

	private void nofifyOthersByHisleaving(Object key) {
		final UserDTO user = get(key);
		if(user !=null)
		{
		for (final UserDTO curUser : values()) {
			new Thread() {
				public void run() {
					if (curUser.equals(user) == false) {
						try {
							curUser.getClientInt().userLoggedOut(user);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		}
	}

}
