package com.mitrais.psms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.datatype.DatatypeFactory;

import com.mitrais.psms.dao.core.DataSourceFactory;
import com.mitrais.psms.dao.core.StuffDao;
import com.mitrais.psms.model.Stuff;

public class DaoStuff implements StuffDao{
	
	private DaoStuff() {}
	
	private static class SingletonHelper
	{
		private static final DaoStuff INSTANCE = new DaoStuff();
	}
	
	public static DaoStuff getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	@Override
	public Optional<Stuff> find(String id) throws SQLException {
		// TODO Auto-generated method stub
		//Stuff stuff = null;
		String sql = "SELECT * FROM stuff WHERE stuff_id = ?";
		try (Connection conn = DataSourceFactory.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				int id_stuff = resultSet.getInt("stuff_id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				int quantity = resultSet.getInt("quantity");
				String location = resultSet.getString("location");
				
				Stuff stuff = new Stuff(id_stuff, name, description, quantity, location);
			return Optional.of(stuff);
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return Optional.empty();
	}

	@Override
	public List<Stuff> findAll() throws SQLException {
		// TODO Auto-generated method stub
		List<Stuff> listStuff = new ArrayList<>();		
		String sql = "SELECT * FROM stuff";
		
		try (Connection conn = DataSourceFactory.getConnection()) {
			Statement statement = conn.createStatement();
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
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
		return listStuff;
	}

	@Override
	public boolean save(Stuff stuff) throws SQLException {
		// TODO Auto-generated method stub
		String sql ="INSERT into stuff (name, description, quantity, location) VALUES (?, ?, ?, ?)";
		boolean rowInserted=false;
		try (Connection conn = DataSourceFactory.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, stuff.getName());
			statement.setString(2, stuff.getDescription());
			statement.setInt(3, stuff.getQuantity());
			statement.setString(4, stuff.getLocation());
			
			rowInserted = statement.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rowInserted;
	}

	@Override
	public boolean update(Stuff stuff) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "UPDATE stuff SET name = ?, description = ?, quantity = ?, location = ?";
		sql +=" WHERE stuff_id = ?";
		boolean rowUpdateted = false;
		try (Connection conn = DataSourceFactory.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, stuff.getName());
			statement.setString(2, stuff.getDescription());
			statement.setInt(3, stuff.getQuantity());
			statement.setString(4, stuff.getLocation());
			statement.setInt(5, stuff.getId());
			
			rowUpdateted = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return rowUpdateted;
	}

	@Override
	public boolean delete(Stuff stuff) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM stuff where stuff_id = ?";
		boolean rowDeleted = false;
		
		try (Connection conn = DataSourceFactory.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, stuff.getId());
			
			rowDeleted = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
		return rowDeleted;
	}

}
