package com.revature.services;

import java.util.List;
import com.revature.models.*;
import com.revature.dao.*;

public class ERSServices {

	private UserDao uDao = new UserDaoImpl();
	private RequestDao rDao = new RequestDaoImpl();
	
	//Login
	public boolean login(String username, String password) {
		return getUserByUsername(username).getPassword().equals(password);
	}
	
	//User services
	public User getUserByID(int id) {
		return uDao.selectUserByID(id);
	}

	public User getUserByUsername(String username) {
		return uDao.selectUserByUsername(username);
	}

	public List<User> getAllUsers() {
		return uDao.selectAllUsers();
	}

	public boolean createUser(User jsonUser) {
		return uDao.insertUser(jsonUser) > 0;
	}
	
	public void updateUser(User jsonUser) {
		uDao.updateUser(jsonUser);
	}
	
	//Request services
	public Request getRequestByID(int id) {
		return rDao.selectRequestByID(id);
	}
	public List<Request> getAllRequests() {
		return rDao.selectAllRequests();
	}

	public List<Request> getRequestsByUserID(int id) {
		return rDao.selectRequestsByUserID(id);
	}
	
	public List<Request> getRequestsByStatus(String status) {
		return rDao.selectRequestsByStatus(status);
	}
	
	public List<Request> getRequestsByDate(String date) {
		return rDao.selectRequestsByDate(date);
	}
	
	public List<Request> getRequestsByDate(String dateRangeBegin, String dateRangeEnd) {
		return rDao.selectRequestsByDate(dateRangeBegin, dateRangeEnd);
	}
	
	public boolean createRequest(Request jsonRequest) {
		return rDao.insertRequest(jsonRequest) > 0;
	}
	
	public void updateRequest(Request jsonRequest) {
		rDao.updateRequest(jsonRequest);
	}
}
