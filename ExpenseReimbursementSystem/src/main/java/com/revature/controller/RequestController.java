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
		try{
			int id = Integer.parseInt(ctx.pathParam("userID"));
			List<Request> requestList = ersService.getRequestsByUserID(id);
			ctx.json(requestList);
		}catch(NumberFormatException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public void getRequestsByStatus(Context ctx) {
		List<Request> requestList = ersService.getRequestsByStatus(ctx.pathParam("status"));
		ctx.json(requestList);
	}
	
	public void getRequestsByDate(Context ctx){
		List<Request> requestList = ersService.getRequestsByDate(ctx.pathParam("date"));
		ctx.json(requestList);
	}
	
	public void getRequestsBetweenDates(Context ctx){
		List<Request> requestList = ersService.getRequestsByDate(ctx.pathParam("startDate"), ctx.pathParam("endDate"));
		ctx.json(requestList);
	}
	
	public void createRequest(Context ctx) {
		Request jsonRequest = ctx.bodyAsClass(Request.class);
		
		if(ersService.createRequest(jsonRequest)) {
			ctx.status(201);
		}else {
			ctx.status(418);
		}
		
	}
	
	public void updateRequest(Context ctx) {
		Request jsonRequest = ctx.bodyAsClass(Request.class);
		
		ersService.updateRequest(jsonRequest);
			ctx.status(201);
	}

}
