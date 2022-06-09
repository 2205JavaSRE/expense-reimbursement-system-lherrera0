package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import com.revature.models.Request;
import com.revature.util.ConnectionFactory;

public class RequestDaoImpl implements RequestDao {

	@Override
	public Request selectRequestByID(int id) {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".request WHERE request_id=?";
		Connection connection = ConnectionFactory.getConnection();
		Request selectedRequest = null;
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				selectedRequest = new Request(rs.getInt("request_id"),
					rs.getInt("user_id"),
					rs.getString("type"),
					rs.getString("status"),
					rs.getDate("date_created").toString(),
					rs.getDouble("amount"),
				rs.getDate("date_processed") == null? "not processed" : rs.getDate("date_processed").toString());
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return selectedRequest;
	}

	@Override
	public List<Request> selectAllRequests() {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".request";
		Connection connection = ConnectionFactory.getConnection();
		List<Request> requests = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				requests.add(new Request(rs.getInt("request_id"),
					rs.getInt("user_id"),
					rs.getString("type"),
					rs.getString("status"),
					rs.getDate("date_created").toString(),
					rs.getDouble("amount"),
					rs.getDate("date_processed") == null? "not processed" : rs.getDate("date_processed").toString()));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return requests;
	}

	@Override
	public List<Request> selectRequestsByUserID(int id) {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".request WHERE user_id=?";
		Connection connection = ConnectionFactory.getConnection();
		List<Request> requests = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				requests.add(new Request(rs.getInt("request_id"),
					rs.getInt("user_id"),
					rs.getString("type"),
					rs.getString("status"),
					rs.getDate("date_created").toString(),
					rs.getDouble("amount"),
					rs.getDate("date_processed") == null? "not processed" : rs.getDate("date_processed").toString()));
			}
		}catch(SQLException e){
			requests = null;
			e.printStackTrace();
		}
		
		return requests;
	}

	@Override
	public List<Request> selectRequestsByStatus(String status) {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".request WHERE status=?";
		Connection connection = ConnectionFactory.getConnection();
		List<Request> requests = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, status);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				requests.add(new Request(rs.getInt("request_id"),
					rs.getInt("user_id"),
					rs.getString("type"),
					rs.getString("status"),
					rs.getDate("date_created").toString(),
					rs.getDouble("amount"),
					rs.getDate("date_processed") == null? "not processed" : rs.getDate("date_processed").toString()));
			}
		}catch(SQLException e){
			requests = null;
			e.printStackTrace();
		}
		
		return requests;
	}

	@Override
	public List<Request> selectRequestsByDate(String date) {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".request WHERE date_created=?";
		Connection connection = ConnectionFactory.getConnection();
		List<Request> requests = new ArrayList<>();

		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setDate(1, new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				requests.add(new Request(rs.getInt("request_id"),
					rs.getInt("user_id"),
					rs.getString("type"),
					rs.getString("status"),
					rs.getDate("date_created").toString(),
					rs.getDouble("amount"),
					rs.getDate("date_processed") == null? "not processed" : rs.getDate("date_processed").toString()));
			}
		}catch(SQLException | java.text.ParseException e){
			requests = null;
			e.printStackTrace();
		}
		
		return requests;
	}

	@Override
	public List<Request> selectRequestsByDate(String dateRangeBegin, String dateRangeEnd) {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".request WHERE date_created>=? AND date_created<=?";
		Connection connection = ConnectionFactory.getConnection();
		List<Request> requests = new ArrayList<>();

		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setDate(1, new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateRangeBegin).getTime()));
			ps.setDate(2, new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateRangeEnd).getTime()));

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				requests.add(new Request(rs.getInt("request_id"),
					rs.getInt("user_id"),
					rs.getString("type"),
					rs.getString("status"),
					rs.getDate("date_created").toString(),
					rs.getDouble("amount"),
					rs.getDate("date_processed") == null? "not processed" : rs.getDate("date_processed").toString()));
			}
		}catch(SQLException | java.text.ParseException e){
			requests = null;
			e.printStackTrace();
		}
		
		return requests;
	}

	@Override
	public int insertRequest(Request r) {
		String sql = "INSERT INTO \"EmployeeReimbursementSystem\".request (request_id, user_id, type, status, date_created, amount, date_processed) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING request_id";
		Connection connection = ConnectionFactory.getConnection();
		int requestID = -1;
		Date now = new Date(System.currentTimeMillis());
        try (PreparedStatement ps = connection.prepareStatement(sql)){
			
			ps.setInt(1,r.getUserID());
			ps.setString(2,r.getType());
			ps.setString(3,"pending");
			ps.setDate(4,now);
			ps.setDouble(5,r.getAmount());
			ps.setNull(6,java.sql.Types.DATE);
			ResultSet rs = ps.executeQuery();
			rs.next();
			requestID = rs.getInt("request_id");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return requestID;
		
	}

	@Override
	public void updateRequest(Request r) {
		String sql = "UPDATE \"EmployeeReimbursementSystem\".request SET (user_id, type, status, date, amount) = (?, ?, ?, ?, ?, ?) WHERE request_id=?";
		Connection connection = ConnectionFactory.getConnection();
		Date now = new Date(System.currentTimeMillis());
		try (PreparedStatement ps = connection.prepareStatement(sql)){
			
			ps.setInt(1,r.getUserID());
			ps.setString(2,r.getType());
			ps.setString(3,r.getStatus());
			ps.setDate(4,new Date(new SimpleDateFormat("yyyy-MM-dd").parse(r.getDateCreated()).getTime()));
			ps.setDouble(5,r.getAmount());
			ps.setDate(6, now);
			ps.setInt(7,r.getRequestID());
			ps.executeQuery();
		}catch(SQLException | java.text.ParseException e){
			e.printStackTrace();
		}			
	}
}
