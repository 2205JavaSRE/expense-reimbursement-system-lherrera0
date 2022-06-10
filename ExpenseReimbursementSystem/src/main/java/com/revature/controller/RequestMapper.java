package com.revature.controller;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import com.revature.models.Request;

import io.javalin.Javalin;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.DiskSpaceMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class RequestMapper {
	private UserController userController = new UserController();
	private RequestController requestController = new RequestController();
	
	
	public void configureRoutes(Javalin app, PrometheusMeterRegistry registry) {
		
		new ClassLoaderMetrics().bindTo(registry);
		new JvmMemoryMetrics().bindTo(registry);
		new JvmGcMetrics().bindTo(registry);
		new JvmThreadMetrics().bindTo(registry);
		new UptimeMetrics().bindTo(registry);
		new ProcessorMetrics().bindTo(registry);
		new DiskSpaceMetrics(new File(System.getProperty("user.dir"))).bindTo(registry);
		
		Counter totalReimbursed = Counter
				.builder("path_request_total_approved_reimbursable")
				.description("Keep track of the total amount reimbursed")
				.tag("purpose", "finance tracking")
				.register(registry);
		
		AtomicInteger activeUsers = registry.gauge("numberGauge", new AtomicInteger(0));

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
		
		app.get("/metrics", ctx ->{
			ctx.result(registry.scrape());
		});

		app.post("/login", ctx -> {
			if(userController.login(ctx))
				activeUsers.incrementAndGet();			
		});
		
		app.get("/login", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				ctx.result("You are logged in");
				ctx.status(201);
			}
			else {
				ctx.result("those credentials are invalid");
				ctx.status(401);
			}
		});

		app.get("/logout", ctx -> {
			ctx.clearCookieStore();
			activeUsers.decrementAndGet();
			ctx.result("You are logged out");
			ctx.status(201);
		});
		
		app.get("/api/user", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				userController.getMyUser(ctx);
			}
			else
				ctx.result("You dont have access");
		});

		app.get("/api/user/{id}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true)) 
				userController.getUserByID(ctx);
			else
				ctx.result("You dont have access");
		});

		app.get("/api/users", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				userController.getAllUsers(ctx);
			else
				ctx.result("You dont have access");
		});
		
		app.post("/api/user-create", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				userController.createUser(ctx);
			else
				ctx.result("You dont have access");
		});	
		
		app.post("/api/user-update", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				userController.updateUser(ctx);
			else
				ctx.result("You dont have access");
		});	
		
		app.get("/api/request/{id}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				requestController.getRequestByID(ctx);
			else
				ctx.result("You dont have access");
		});

		app.get("/api/requests", ctx ->{
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				requestController.getAllRequests(ctx);
			else if (ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				requestController.getRequestsByUserID(ctx);
			else
				ctx.result("You dont have access");
		});
		
		app.get("/api/requests-by-userID/{userID}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true))
				requestController.getRequestsByUserID(ctx);
			else
				ctx.result("You dont have access");
		});
		
		app.get("/api/requests-by-status/{status}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				requestController.getRequestsByStatus(ctx);
			else
				ctx.result("You dont have access");
		});
		
		app.get("/api/requests-by-date/{date}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				ctx.result("Enter your date in yyyy-MM-dd format");
				requestController.getRequestsByDate(ctx);
			}else
				ctx.result("You dont have access");
		});
		
		app.get("/api/requests-between-dates/{startDate}/{endDate}", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true)) {
				ctx.result("Enter your dates in yyyy-MM-dd format");
				requestController.getRequestsBetweenDates(ctx);
			}else
				ctx.result("You dont have access");

		});
		
		app.get("/api/request-create", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				ctx.result("Enter your request in the following format\n"
						+ "requestID (use 0)\n"
						+ "userID\n"
						+ "type (Lodging, Travel, Food, Other)\n"
						+ "status (will remain Pending until Approved/Denied by Manager)\n"
						+ "dateCreated (todays date in yyyy-MM-dd formart)\n"
						+ "amount\n"
						+ "dateProcessed (use \"\")\n");
			else
				ctx.result("You dont have access");
		});	
		
		app.post("/api/request-create", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true))
				requestController.createRequest(ctx);
			else
				ctx.result("You dont have access");
		});	
		
		app.get("/api/request-update", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true)) {
				ctx.result("Enter your request in the following format\n"
						+ "requestID\n"
						+ "userID\n"
						+ "type (Lodging, Travel, Food, Other)\n"
						+ "status (will remain Pending until Approved/Denied by Manager)\n"
						+ "dateCreated (todays date in yyyy-MM-dd formart)\n"
						+ "amount\n"
						+ "dateProcessed (leave blank)\n");
			}else
				ctx.result("You dont have access");
		});	

		app.post("/api/request-update", ctx -> {
			if(ctx.cookieStore("access") != null && ctx.cookieStore("access").equals(true) && ctx.cookieStore("manager").equals(true)) {
				Double total = ctx.bodyAsClass(Request.class).getAmount();
				if(requestController.updateRequest(ctx))
					totalReimbursed.increment(total);
			}else
				ctx.result("You dont have access");
		});	

	}
}