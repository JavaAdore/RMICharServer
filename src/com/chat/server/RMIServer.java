package com.chat.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chat.common.Constants;
import com.chat.common.CustomException;
import com.chat.common.ServerInt;
import com.chat.server.business.ServerBusiness;
import com.chat.server.business.ServerController;
import com.chat.server.view.ServerForm;

public class RMIServer {

	public static int PORT_NUMBER;
	Random rand = new Random();

	public RMIServer() {
		boolean condition = true;
		while (condition) {
			try {

				PORT_NUMBER = 2000 + rand.nextInt(3000);
				System.out.println(PORT_NUMBER);
				Registry registry = LocateRegistry.createRegistry(PORT_NUMBER);
				ServerController controller = new ServerController();
				ServerInt serverInt = new ServerBusiness(controller);
				registry.bind(Constants.SERVICE_NAME, serverInt);
				ServerForm jframe = new ServerForm(controller);
				controller.setModel(serverInt);
				controller.setView(jframe);
				condition = false;
				rand = null;

			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (AlreadyBoundException e) {

				e.printStackTrace();
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				condition = false;
			}
		}

	}

	public static void main(String[] args) {

		new RMIServer();
	}

}
