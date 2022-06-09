package com.revature.dao;
import com.revature.models.*;
import java.util.List;

public interface RequestDao {
	
	public Request selectRequestByID(int id);
	public List<Request> selectAllRequests();
	public List<Request> selectRequestsByUserID(int id);
	public List<Request> selectRequestsByStatus(String status);
	public List<Request> selectRequestsByDate(String date);
	public List<Request> selectRequestsByDate(String dateRangeBegin, String dateRangeEnd);
	
	
	public int insertRequest(Request r);
	public void updateRequest(Request r);

}
