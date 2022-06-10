package com.revature.controller;

import java.util.List;
import com.revature.models.Request;
import com.revature.services.ERSServices;
import io.javalin.http.Context;

public class RequestController {
	
	private ERSServices ersService = new ERSServices();

	public void getRequestByID(Context ctx) {
		try {
		Request selectedUser = ersService.getRequestByID(Integer.parseInt(ctx.pathParam("id")));
		ctx.json(selectedUser);
		}catch(NumberFormatException e){
			e.printStackTrace();
			throw e;
		}
	}

	public void getAllRequests(Context ctx) {
		List<Request> requestList = ersService.getAllRequests();
		ctx.json(requestList);
	}

	public void getRequestsByUserID(Context ctx) {
		int id = -1;
		try{
			if(ctx.cookieStore("manager").equals(true))
				id = Integer.parseInt(ctx.pathParam("userID"));
			else
				id = ctx.cookieStore("userID");
			List<Request> requestList = ersService.getRequestsByUserID(id);
			ctx.json(requestList);
		}catch(NumberFormatException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public void getRequestsByStatus(Context ctx) {
		List<Request> requestList = ersService.getRequestsByStatus(ctx.pathParam("status"));
		if(ctx.cookieStore("manager").equals(false))
			requestList.removeIf(r -> (!ctx.cookieStore("userID").equals(r.getUserID())));
		ctx.json(requestList);
	}
	
	public void getRequestsByDate(Context ctx){
		List<Request> requestList = ersService.getRequestsByDate(ctx.pathParam("date"));
		if(ctx.cookieStore("manager").equals(false))
			requestList.removeIf(r -> (!ctx.cookieStore("userID").equals(r.getUserID())));
		ctx.json(requestList);
	}
	
	public void getRequestsBetweenDates(Context ctx){
		List<Request> requestList = ersService.getRequestsByDate(ctx.pathParam("startDate"), ctx.pathParam("endDate"));
		if(ctx.cookieStore("manager").equals(false))
			requestList.removeIf(r -> (!ctx.cookieStore("userID").equals(r.getUserID())));
		ctx.json(requestList);
	}
	
	public void createRequest(Context ctx) {
		Request jsonRequest = ctx.bodyAsClass(Request.class);
		jsonRequest.setUserID(ctx.cookieStore("userID"));
		if(ersService.createRequest(jsonRequest)) {
			ctx.result("Request submitted for approval");
			ctx.status(201);
		}else {
			ctx.status(418);
		}
		
	}
	
	public boolean updateRequest(Context ctx) {
		Request jsonRequest = ctx.bodyAsClass(Request.class);
		if(jsonRequest.getStatus().equals("approved") || jsonRequest.getStatus().equals("denied")) {
			ersService.updateRequest(jsonRequest);
			ctx.result("Request updated");
			ctx.status(201);
			return jsonRequest.getStatus().equals("approved");
		}else {
			ctx.result("The request must be approved or denied");
			ctx.status(418);
			return false;
		}
	}

}
