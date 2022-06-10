package com.revature.controller;

import java.util.List;
import com.revature.models.User;
import com.revature.services.ERSServices;
import io.javalin.http.Context;

public class UserController {

	private ERSServices ersService = new ERSServices();
	
	public boolean login(Context ctx) {
		if(ersService.login(ctx.formParam("username"), ctx.formParam("password"))) {
			User selectedUser = ersService.getUserByUsername(ctx.formParam("username"));
			ctx.cookieStore("access", true);
			ctx.cookieStore("userID", selectedUser.getUserID());
			ctx.cookieStore("manager", selectedUser.isManager());
			ctx.result("You are logged in");
			ctx.status(201);
			return true;
		}else {
			ctx.result("those credentials are invalid");
			ctx.status(401);
			return false;
		}
	}
	
	public void getMyUser(Context ctx) {
		User selectedUser = null;
		selectedUser = ersService.getUserByID(ctx.cookieStore("userID"));
		if(selectedUser != null)
			ctx.json(selectedUser);
		else
			ctx.result("User does not exist");
	}
	public void getUserByID(Context ctx) {
		User selectedUser = null;
		try {
			if(ctx.cookieStore("manager").equals(true))
				selectedUser = ersService.getUserByID(Integer.parseInt(ctx.pathParam("id")));
			else
				selectedUser = ersService.getUserByID(ctx.cookieStore("userID"));
			if(selectedUser != null)
				ctx.json(selectedUser);
			else
				ctx.result("User does not exist");

		}catch(NumberFormatException e) {
			//e.printStackTrace();
			getUserByUsername(ctx);
			throw e;
		}
	}

	public void getUserByUsername(Context ctx) {
		User selectedUser = ersService.getUserByUsername(ctx.pathParam("id"));
		if(selectedUser != null)
			ctx.json(selectedUser);
		else
			ctx.result("User does not exist");
	}
	
	public void getAllUsers(Context ctx) {
		List<User> userList = ersService.getAllUsers();
		if(!userList.isEmpty())
			ctx.json(userList);
		else
			ctx.result("User does not exist");
	}

	public void createUser(Context ctx) {
		User jsonUser = ctx.bodyAsClass(User.class);
		
		if(ersService.createUser(jsonUser)) {
			ctx.result("User was created");
			ctx.status(201);
		}else {
			ctx.status(418);
		}
	}
	
	public void updateUser(Context ctx) {
		User jsonUser = ctx.bodyAsClass(User.class);
		
		ersService.updateUser(jsonUser);
		ctx.result("User was updated");
		ctx.status(201);
	}
}
