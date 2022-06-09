package com.revature.controller;

import java.util.List;
import com.revature.models.User;
import com.revature.services.ERSServices;
import io.javalin.http.Context;

public class UserController {

	private ERSServices ersService = new ERSServices();
	
	public void login(Context ctx) {
		if(ersService.login(ctx.formParam("username"), ctx.formParam("password"))) {
			User selectedUser = ersService.getUserByUsername(ctx.formParam("username"));
			ctx.cookieStore("access", true);
			ctx.cookieStore("userID", selectedUser.getUserID());
			ctx.cookieStore("manager", selectedUser.isManager());
			ctx.html("You are logged in");
			ctx.status(201);
		}else {
			ctx.html("those credentials are invalid");
			ctx.status(401);
		}
	}
	
	public void getUserByID(Context ctx) {
		User selectedUser = ersService.getUserByID(Integer.parseInt(ctx.pathParam("id")));
		if(selectedUser != null)
			ctx.json(selectedUser);
		else
			ctx.result("User does not exist");
	}

	public void getUserByUsername(Context ctx) {
		User selectedUser = ersService.getUserByUsername(ctx.pathParam("username"));
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
			ctx.status(201);
		}else {
			ctx.status(418);
		}
	}
	
	public void updateUser(Context ctx) {
		User jsonUser = ctx.bodyAsClass(User.class);
		
		ersService.updateUser(jsonUser);
		ctx.status(201);
	}
}
