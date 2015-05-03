package com.chat.server.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import com.chat.common.UserDTO;
import com.chat.server.business.ServerController;

public class ServerForm extends JFrame implements ServerView {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ServerController control;
	private JButton closeServer;
	private JList<UserDTO> jList;
	private DefaultListModel<UserDTO> model;
	
	private JLabel portNumber  ;
	private JLabel portNumberLabel;
	
	
	public  ServerForm(final ServerController controller) {
		this.control = controller;
		setSize(300, 300);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				super.windowClosing(arg0);
				control.downServer();
			}
		});

		closeServer = new JButton("Close Server");
		closeServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.downServer();
				System.exit(0);
			}
		});

		model = new DefaultListModel();
		jList = new JList(model);
		portNumber = new JLabel();
		portNumberLabel = new JLabel("Server Port Is ");
		
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(portNumberLabel,BorderLayout.NORTH);
		getContentPane().add(portNumber,BorderLayout.CENTER);
		setVisible(true);
		setResizable(false);

	}

	@Override
	public void userLogged(UserDTO user) {

		model.addElement(user);

	}

	@Override
	public void userLoggedOut(UserDTO userDTO) {
		model.removeElement(userDTO);

	}

	public JList<UserDTO> getjList() {
		return jList;
	}

	public void setjList(JList<UserDTO> jList) {
		this.jList = jList;
	}

	public DefaultListModel<UserDTO> getModel() {
		return model;
	}

	public void setModel(DefaultListModel<UserDTO> model) {
		this.model = model;
	}

	@Override
	public void setPortNumber(int port) {
		portNumber.setText(String.valueOf(port));
		
	}


}
