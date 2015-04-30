package com.chat.common;

public class Feedback {

	public static final Integer SUCCESS = 0;

	public static final int FAILED = -1;

	private Integer code;
	
	private String message;

	public Feedback(){}
	
	
	public Feedback(String message) {
		super(); 
		this.message = message;
	}
	
	
	public Feedback(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}


	public Feedback(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}

	
	
	public Feedback(Integer code, String message, Object object) {
		super();
		this.code = code;
		this.message = message;
		this.object = object;
	}



	private Object object;
	
	
	
	
	public Object getObject() {
		return object;
	}




	public void setObject(Object object) {
		this.object = object;
	}


	






	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public Feedback setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public boolean isSuccess()
	{
		return code!=null && code.equals(SUCCESS);
	}
	
	public boolean isFailed()
	{
		return code!=null && code.equals(FAILED);
	}
}
