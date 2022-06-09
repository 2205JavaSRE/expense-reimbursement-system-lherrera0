package com.revature.models;

import java.util.Objects;

public class User {
	private int userID;
	private String username;
	private String password;
	private boolean isManager;
	private String firstName;
	private String lastName;
	
	public User(){
		super();
	}
	
	public User(int userID, String username, String password, boolean isManager, String firstName, String lastName) {
		setUserID(userID);
		setUsername(username);
		setPassword(password);
		setIsManager(isManager);
		setFirstName(firstName);
		setLastName(lastName);
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isManager() {
		return isManager;
	}
	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(firstName, isManager, lastName, password, userID, username);
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
		return Objects.equals(firstName, other.firstName) && isManager == other.isManager
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& userID == other.userID && Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", password=" + password + ", isManager="
				+ isManager + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
