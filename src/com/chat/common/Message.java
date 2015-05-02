package com.chat.common;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageText;

	
	private String email;
	
	private String sender;
	
	private Date sendingDate;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Message() {
		super();
	}

	public Message(String messageText) {
		super();
		this.messageText = messageText;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}
	
	
	
	
}
