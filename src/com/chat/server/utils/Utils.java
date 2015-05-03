package com.chat.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;

import com.chat.common.Constants;
import com.chat.common.CustomException;
import com.chat.common.User;
import com.chat.common.UserDTO;
import com.chat.server.Users;

public class Utils {

	private static Users users = null;

	static {
		try {
			unmarchallUsersFile(Constants.DATABASE_NAME);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Users unmarchallUsersFile(String fileName)
			throws CustomException {
		JAXBContext context;           
 
		try { 
			context = JAXBContext.newInstance(Users.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
            File xmlFile = new File( Constants.DATABASE_NAME);
           
            users = (Users) unmarshaller.unmarshal( new FileInputStream( xmlFile));
					
			 
			return users;
		} catch (JAXBException e) {
			throw new CustomException("Cannot read database");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException("Cannot find database file database");
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
						Constants.DATABASE_NAME)));
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

	public void test() {

	}

	public static boolean updateUser(User user) {
		User tempUser = user.clone();
		if (tempUser != null) {
			removeAddedUser(user);

			return registerNewUser(user);
		} else {
			registerNewUser(tempUser);
			return false;
		}

	}

	public static void migrateData(Object from, Object to) {
      
		Class target = from.getClass(); 
		for (Field currentField : target.getDeclaredFields()) {
			if (Modifier.isStatic(currentField.getModifiers()))
				continue;

			String properMethodName = currentField.getName().substring(0, 1)
					.toUpperCase();
			if (currentField.getName().length() > 1) {
				properMethodName += currentField.getName().substring(1,
						currentField.getName().length());

			}

			try {
				Method getter = target.getDeclaredMethod("get"
						+ properMethodName);
				Object result = getter.invoke(from, null);
				Method setter = target.getDeclaredMethod("set"
						+ properMethodName, currentField.getType());
				setter.invoke(to, result);

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception  
				e.printStackTrace();
			}

		}
	}

	public static boolean fireEmail(final String toEmail, final String sender,
			final Date date, final String body) {
		new Thread() {

			public void run() {
				try {

					final String emailusername = "apcdiabetic@gmail.com";
					final String emailpassword = "01277526990";

					Properties props = new Properties();

					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.starttls.enable", "true");
					props.put("mail.imap.ssl.enable", "true");
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.port", "587");
					/*
					 * props.put("mail.smtp.host", "smtp.gmail.com");
					 * props.put("mail.smtp.socketFactory.port", "465");
					 * props.put("mail.smtp.socketFactory.class",
					 * "javax.net.ssl.SSLSocketFactory");
					 * props.put("mail.smtp.auth", "true");
					 * props.put("mail.smtp.port", "465");
					 */

					Session session = Session.getInstance(props,
							new javax.mail.Authenticator() {
								@Override
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(
											emailusername, emailpassword);
								}
							});

					MimeMessage msg = new MimeMessage(session);
					// set message headers
					msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
					msg.addHeader("format", "flowed");
					msg.addHeader("Content-Transfer-Encoding", "8bit");

					msg.setFrom(new InternetAddress(sender));

					msg.setReplyTo(InternetAddress.parse(sender, false));

					msg.setSubject("Chat Message ", "UTF-8");

					msg.setText(body, "UTF-8");
					msg.setSentDate(new Date());

					msg.setRecipients(javax.mail.Message.RecipientType.TO,
							InternetAddress.parse(toEmail, false));
					System.out.println("Message is ready");
					Transport.send(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
		return true;
	}

}
