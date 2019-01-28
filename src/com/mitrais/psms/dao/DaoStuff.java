package com.mitrais.psms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.mitrais.psms.dao.core.DataSourceFactory;
import com.mitrais.psms.dao.core.StuffDao;
import com.mitrais.psms.model.Stuff;

public class DaoStuff implements StuffDao {
	private DaoStuff() {
	}

	private static class SingletonHelper {
		private static final DaoStuff INSTANCE = new DaoStuff();
	}

	public static DaoStuff getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Stuff> find(String id) throws SQLException {

		String sql = "SELECT stuff_id,name,description,quantity,location FROM stuff WHERE stuff_id = ?";
		int id_stuff = 0, quantity = 0;
		String name = "", description = "", location = "";
		Connection conn = DataSourceFactory.getConnection();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, id);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			id_stuff = resultSet.getInt("stuff_id");
			name = resultSet.getString("name");
			description = resultSet.getString("description");
			quantity = resultSet.getInt("quantity");
			location = resultSet.getString("location");
		}
		return Optional.of(new Stuff(id_stuff, name, description, quantity, location));
	}

	@Override
	public List<Stuff> findAll() throws SQLException {
		List<Stuff> listStuff = new ArrayList<>();
		String sql = "SELECT stuff_id,name,description,quantity,location FROM stuff";

		Connection conn = DataSourceFactory.getConnection();
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int id = resultSet.getInt("stuff_id");
			String name = resultSet.getString("name");
			String description = resultSet.getString("description");
			int quantity = resultSet.getInt("quantity");
			String location = resultSet.getString("location");

			Stuff stuff = new Stuff(id, name, description, quantity, location);
			listStuff.add(stuff);
		}

		return listStuff;
	}

	@Override
	public boolean save(Stuff stuff) throws SQLException {

		String sql = "INSERT into stuff (name, description, quantity, location) VALUES (?, ?, ?, ?)";
		boolean rowInserted = false;
		Connection conn = DataSourceFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, stuff.getName());
			statement.setString(2, stuff.getDescription());
			statement.setInt(3, stuff.getQuantity());
			statement.setString(4, stuff.getLocation());
			rowInserted = statement.executeUpdate() > 0;
		return rowInserted;
	}

	@Override
	public boolean update(Stuff stuff) throws SQLException {
		String sql = "UPDATE stuff SET name = ?, description = ?, quantity = ?, location = ?";
		sql += " WHERE stuff_id = ?";
		boolean rowUpdateted = false;
		Connection conn = DataSourceFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, stuff.getName());
			statement.setString(2, stuff.getDescription());
			statement.setInt(3, stuff.getQuantity());
			statement.setString(4, stuff.getLocation());
			statement.setInt(5, stuff.getId());
			rowUpdateted = statement.executeUpdate() > 0;

		return rowUpdateted;
	}

	@Override
	public boolean delete(Stuff stuff) throws SQLException {

		String sql = "DELETE FROM stuff where stuff_id = ?";
		boolean rowDeleted = false;

		Connection conn = DataSourceFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, stuff.getId());
			rowDeleted = statement.executeUpdate() > 0;

		return rowDeleted;
	}

}
