package com.mitrais.psms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mitrais.psms.model.Stuff;


public class StuffDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public StuffDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	protected void connect() throws SQLException {
		if(jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}
	
	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	public boolean insertStuff(Stuff stuff) throws SQLException{
		String sql ="INSERT into stuff (name, description, quantity, location) VALUES (?, ?, ?, ?)";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, stuff.getName());
		statement.setString(2, stuff.getDescription());
		statement.setInt(3, stuff.getQuantity());
		statement.setString(4, stuff.getLocation());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Stuff> listAllStuff() throws SQLException{
		List<Stuff> listStuff = new ArrayList<>();
		
		String sql = "SELECT * FROM stuff";
		
		connect();
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("stuff_id");
			String name = resultSet.getString("name");
			String description = resultSet.getString("description");
			int quantity = resultSet.getInt("quantity");
			String location = resultSet.getString("location");
			
			Stuff stuff = new Stuff(id,name, description, quantity, location);
			listStuff.add(stuff);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listStuff;		
	}
	
	public boolean deleteStuff(Stuff stuff) throws SQLException{
		String sql = "DELETE FROM stuff where stuff_id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, stuff.getId());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;
	}
	
	public boolean updateStuff(Stuff stuff) throws SQLException{
		
		String sql = "UPDATE stuff SET name = ?, description = ?, quantity = ?, location = ?";
		sql +=" WHERE stuff_id = ?";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, stuff.getName());
		statement.setString(2, stuff.getDescription());
		statement.setInt(3, stuff.getQuantity());
		statement.setString(4, stuff.getLocation());
		statement.setInt(5, stuff.getId());
		
		boolean rowUpdateted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdateted;
	}
	
	public Stuff getStuff (int id) throws SQLException{
		Stuff stuff = null;
		String sql = "SELECT * FROM stuff WHERE stuff_id = ?";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String name = resultSet.getString("name");
			String description = resultSet.getString("description");
			int quantity = resultSet.getInt("quantity");
			String location = resultSet.getString("location");
			
			stuff = new Stuff(id, name, description, quantity, location);
		}
		
		resultSet.close();
		statement.close();
		return stuff;
	}
	
}
