package com.revature.models;

import java.util.Objects;

public class Request {
	private int requestID;
	private int userID;
	private String type;//Lodging, Travel, Food, Other
	private String status;//pending, approved, denied
	private String dateCreated;
	private double amount;
	private String dateProcessed;
	
	public Request() {
		super();
	}

	public Request(int requestID, int userID, String type, String status, String dateCreated, Double amount, String dateProcessed) {
		setRequestID(requestID);
		setUserID(userID);
		setType(type);
		setStatus(status);
		setDateCreated(dateCreated);
		setAmount(amount);
		setDateProcessed(dateProcessed);
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(String dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, dateCreated, dateProcessed, requestID, status, type, userID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(dateCreated, other.dateCreated) && Objects.equals(dateProcessed, other.dateProcessed)
				&& requestID == other.requestID && Objects.equals(status, other.status)
				&& Objects.equals(type, other.type) && userID == other.userID;
	}

	@Override
	public String toString() {
		return "Request [requestID=" + requestID + ", userID=" + userID + ", type=" + type + ", status=" + status
				+ ", dateCreated=" + dateCreated + ", amount=" + amount + ", dateProcessed=" + dateProcessed + "]";
	}

}
