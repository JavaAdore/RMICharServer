package com.chat.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chat.common.Constants;
import com.chat.common.ServerInt;
import com.chat.server.business.ServerBusiness;
import com.chat.server.business.ServerController;
import com.chat.server.view.ServerForm;

public class RMIServer {
	
	public RMIServer()
	{
		try {
			Registry registry = LocateRegistry.createRegistry(9999);
			ServerController controller = new ServerController();			
			ServerInt serverInt = new ServerBusiness(controller);
			registry.bind(Constants.SERVICE_NAME, serverInt);
			ServerForm jframe = new ServerForm(controller);
			controller.setModel(serverInt);
			controller.setView(jframe);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "later");
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	public static void main(String [] args)
	{
		
		new RMIServer();
	}

}
