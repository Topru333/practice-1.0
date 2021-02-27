package com.topru.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.topru.server.db.model.User;

public class DB {
	private Connection conn;

	public void Connect() throws ClassNotFoundException, SQLException {
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:users.db");
		
		System.out.println("Connected");
	}

	public void ReadUsers() throws ClassNotFoundException, SQLException {
		Statement statmt = conn.createStatement();
		ResultSet resSet = statmt.executeQuery("SELECT * FROM user");
		
		while (resSet.next()) {
			
			String login = resSet.getString("login");
			String password = resSet.getString("password");
			User user = new User(login, password);
			System.out.println("login = " + login);
			System.out.println("password = " + password);
			System.out.println();
		}
		
		statmt.close();
		resSet.close();
	}
	
	public void testDataInsert() {
		try {
			Statement statmt = conn.createStatement();
			List<User> testUsers = new ArrayList<User>();
			testUsers.add(new User("guest","guest").hash());
			testUsers.add(new User("admin","admin").hash());
			testUsers.add(new User("bob","bob").hash());
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO user (login, password) VALUES ");
			for (User user : testUsers) {
				query.append("(\"").append(user.getLogin()).append("\",\"").append(user.getPassword()).append("\"),");
			}
			query.deleteCharAt(query.length()-1);
			query.append(";");
			System.out.println(query.toString());
			statmt.executeQuery(query.toString());
			statmt.close();
		} catch (SQLException e) {
			System.out.println("Error in test data inserts: "  +e.getMessage());
			e.printStackTrace();
		}
	}
	
	public User getUser(String login) {
		try {
			Statement statmt = conn.createStatement();
			ResultSet resSet = statmt.executeQuery(new StringBuilder().append("SELECT * FROM user WHERE login=\"").append(login).append("\"").toString());
			if(resSet.next()) {
				return new User(resSet.getString("login"), resSet.getString("password"), true);
			}
		} catch (SQLException e) {
			System.out.println("Error in user validation statement");
			e.printStackTrace();
		}		
		return null;
	}

	public void CloseConnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Disconnected");
	}

}
