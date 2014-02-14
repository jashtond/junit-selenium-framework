package com.lotaris.selenium.page.user;

/**
 * User object to store credentials
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class User {
	private String user;
	private String password;

	public User(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
