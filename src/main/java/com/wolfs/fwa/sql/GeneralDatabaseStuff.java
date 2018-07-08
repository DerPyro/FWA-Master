package com.wolfs.fwa.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneralDatabaseStuff {
	
	public static Connection getConToDB() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:sqlserver://FENRIS\\FWA2_0",
				"standardUser",
				"12345687");
	}
	
	public static ResultSet doDatabaseShit(String query) throws SQLException {
		Statement stmt = getConToDB().createStatement();
		return stmt.executeQuery(query);
	}
	
	public static void doDatabaseShitWithoutReturn(String query) throws SQLException {
		Statement stmt = getConToDB().createStatement();
		stmt.executeQuery(query);
	}
	
	public static void changeSomethingInDatabase(String query, String password) throws SQLException {
		doDatabaseShit(query);
	}

}
