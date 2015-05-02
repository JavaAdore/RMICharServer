package com.chat.common;

import java.io.Serializable;

public class SearchingCriteria extends User implements Serializable {

	private int minAge;

	private int maxAge;

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
}
