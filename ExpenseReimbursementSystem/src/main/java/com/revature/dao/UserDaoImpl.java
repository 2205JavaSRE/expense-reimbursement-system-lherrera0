package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserDaoImpl implements UserDao {

	@Override
	public User selectUserByID(int id) {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".\"user\" WHERE user_id=?";
		Connection connection = ConnectionFactory.getConnection();
		User selectedUser = null;
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				selectedUser = new User(rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("pass_word"),
						rs.getBoolean("is_manager"),
						rs.getString("first_name"),
						rs.getString("last_name"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return selectedUser;
	}

	@Override
	public User selectUserByUsername(String username) {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".\"user\" WHERE user_name=?";
		Connection connection = ConnectionFactory.getConnection();
		User selectedUser = null;
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				selectedUser = new User(rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("pass_word"),
						rs.getBoolean("is_manager"),
						rs.getString("first_name"),
						rs.getString("last_name"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return selectedUser;
	}

	@Override
	public List<User> selectAllUsers() {
		String sql = "SELECT * FROM \"EmployeeReimbursementSystem\".\"user\"";
		Connection connection = ConnectionFactory.getConnection();
		List<User> selectedUsers = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement(sql)){

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				selectedUsers.add(new User(rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("pass_word"),
						rs.getBoolean("is_manager"),
						rs.getString("first_name"),
						rs.getString("last_name")));
			}
		}catch(SQLException e){
			e.printStackTrace();
			selectedUsers = null;
		}
		
		return selectedUsers;
	}

	@Override
	public int insertUser(User u) {
		String sql = "INSERT INTO \"EmployeeReimbursementSystem\".\"user\" (user_id, user_name, pass_word, is_manager, first_name, last_name) VALUES (DEFAULT, ?, ?, ?, ?, ?) RETURNING user_id";
		Connection connection = ConnectionFactory.getConnection();
		int userID = -1;
        try (PreparedStatement ps = connection.prepareStatement(sql)){
			
        	ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setBoolean(3,u.isManager());
			ps.setString(4,u.getFirstName());
			ps.setString(5,u.getLastName());
			ResultSet rs = ps.executeQuery();
			rs.next();
			userID = rs.getInt("user_id");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userID;
	}

	@Override
	public void updateUser(User u) {
		String sql = "UPDATE \"EmployeeReimbursementSystem\".\"user\" (user_name, pass_word, is_manager, first_name, last_name) = (?, ?, ?, ?, ?) WHERE user_id=?";
		Connection connection = ConnectionFactory.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)){
			
        	ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setBoolean(3, u.isManager());
			ps.setString(4, u.getFirstName());
			ps.setString(5, u.getLastName());
			ps.setInt(6, u.getUserID());
			ps.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

}
