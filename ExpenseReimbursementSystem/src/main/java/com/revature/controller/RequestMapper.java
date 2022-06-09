package com.revature.controller;

import io.javalin.Javalin;

public class RequestMapper {
	private UserController userController = new UserController();
	private RequestController requestController = new RequestController();
	
	public void configureRoutes(Javalin app) {

		app.get("/", ctx -> {
			ctx.html("<h1>Please Login to continue</h1>\n"
					+ "<form method=\"post\" action=\"/login\">\n"
					+ "  <label for=\"username\">Username:</label><br>\n"
					+ "  <input type=\"text\" id=\"username\" name=\"username\"><br>\n"
					+ "  <label for=\"password\">Password:</label><br>\n"
					+ "  <input type=\"password\" id=\"password\" name=\"password\">\n"
					+ "  <button>Submit</button>\n"
					+ "</form>");
		});

		app.post("/login", ctx -> {
			userController.login(ctx);
		});
		
		app.get("/login", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				ctx.html("You are logged in");
				ctx.status(201);
			}
			else {
				ctx.html("those credentials are invalid");
				ctx.status(401);
			}
		});

		app.get("/logout", ctx -> {
			ctx.clearCookieStore();
			ctx.html("You are logged out");
			ctx.status(201);
		});
		
		app.get("/api/user", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				userController.getMyUser(ctx);
			}
			else
				ctx.html("You dont have access");
		});

		app.get("/api/user/{id}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true)) 
				userController.getUserByID(ctx);
			else
				ctx.html("You dont have access");
		});

		app.get("/api/users", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				userController.getAllUsers(ctx);
			else
				ctx.html("You dont have access");
		});
		
		app.post("/api/user-create", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true)) {
				ctx.html("");
				userController.createUser(ctx);
			}else
				ctx.html("You dont have access");
		});	
		
		app.post("/api/user-update", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				userController.updateUser(ctx);
			else
				ctx.html("You dont have access");
		});	
		
		app.get("/api/request/{id}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				requestController.getRequestByID(ctx);
			else
				ctx.html("You dont have access");
		});

		app.get("/api/requests", ctx ->{
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				requestController.getAllRequests(ctx);
			else if (ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				requestController.getRequestsByUserID(ctx);
			else
				ctx.html("You dont have access");
		});
		
		app.get("/api/requests-by-id/{userID}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				requestController.getRequestsByUserID(ctx);
			else
				ctx.html("You dont have access");
		});
		
		app.get("/api/requests-by-status/{status}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				requestController.getRequestsByStatus(ctx);
			else
				ctx.html("You dont have access");
		});
		
		app.get("/api/requests-by-date/{date}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				ctx.html("Enter your date in yyyy-MM-dd format");
				requestController.getRequestsByDate(ctx);
			}else
				ctx.html("You dont have access");
		});
		
		app.get("/api/requests-between-dates/{startDate}/{endDate}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				ctx.html("Enter your dates in yyyy-MM-dd format");
				requestController.getRequestsBetweenDates(ctx);
			}else
				ctx.html("You dont have access");

		});
		
		app.get("/api/request-create", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				ctx.html("Enter your request in the following format<br>\n"
						+ "requestID (use 0)<br>\n"
						+ "userID<br>\n"
						+ "type (Lodging, Travel, Food, Other)<br>\n"
						+ "status (will remain Pending until Approved/Denied by Manager)<br>\n"
						+ "dateCreated (todays date in yyyy-MM-dd formart)<br>\n"
						+ "amount<br>\n"
						+ "dateProcessed (use \"\")<br>\n");
			else
				ctx.html("You dont have access");
		});	
		
		app.post("/api/request-create", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				requestController.createRequest(ctx);
			else
				ctx.html("You dont have access");
		});	
		
		app.get("/api/request-update", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true)) {
				ctx.html("Enter your request in the following format<br>\n"
						+ "requestID<br>\n"
						+ "userID<br>\n"
						+ "type (Lodging, Travel, Food, Other)<br>\n"
						+ "status (will remain Pending until Approved/Denied by Manager)<br>\n"
						+ "dateCreated (todays date in yyyy-MM-dd formart)<br>\n"
						+ "amount<br>\n"
						+ "dateProcessed (leave blank)<br>\n");
			}else
				ctx.html("You dont have access");
		});	

		app.post("/api/request-update", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				requestController.updateRequest(ctx);
			else
				ctx.html("You dont have access");
		});	

	}
}