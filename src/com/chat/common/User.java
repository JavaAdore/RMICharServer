package com.chat.common;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlType
	@XmlEnum(String.class)
	public enum SubscriptionType {
		@XmlEnumValue("blind")
		BLIND, @XmlEnumValue("selection")
		SELECTION, @XmlEnumValue("vip")
		VIP
	}

	@XmlType
	@XmlEnum(String.class)
	public enum Gender {
		@XmlEnumValue("male")
		MALE, @XmlEnumValue("female")
		FEMALE
	}

	private String userName;

	private String password;

	private String email;

	private Double credit;

	private SubscriptionType subscriptionType;

	private Gender gender;

	private List<String> keywords;

	private List<String> hobbies;

	private Integer birthYear;

	public List<String> getKeywords() {
		return keywords;
	}

	@XmlAttribute(required = true)
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	@XmlAttribute(required = true)
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	@XmlAttribute(required = true)
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	@XmlAttribute(required = true)
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlAttribute(required = true)
	public void setPassword(String password) {
		this.password = password;
	}

	@XmlAttribute(required = true)
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlAttribute(required=true)
	public void setCredit(Double credit) {
		this.credit = credit;
	}

	@XmlAttribute(required=true)
	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	@XmlAttribute
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Gender getGender() {
		return gender;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public Double getCredit() {
		return credit;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
