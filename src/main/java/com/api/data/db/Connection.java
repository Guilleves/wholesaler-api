package com.api.data.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connection {
	private Properties prop = new Properties();
	private InputStream input = null;

	private static Connection instancia;
	public static Connection getInstancia() {
		if(instancia == null)
			instancia = new Connection();

		return instancia;
	}

	private static String dbUrl;
	private static String dbUser;
	private static String dbPassword;

	private java.sql.Connection conn;
	static int cantCon=0;

	//Construtor Default
	private Connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=null;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public java.sql.Connection getConn() {
		try {
			input = getClass().getResourceAsStream("/config.properties");

			prop.load(input);
			dbUrl = prop.getProperty("db-url");
			dbUser = prop.getProperty("db-user");
			dbPassword = prop.getProperty("db-password");

			if (conn == null || !conn.isValid(3)) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			}

			cantCon++;
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return conn;
	}

	//Cerrar conexión y manejo de errores
	public void closeConn() {
		try {
			cantCon--;

			if(cantCon==0)
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void beginTransaction() throws SQLException {
		this.getConn().setAutoCommit(false);
	}

	public void commitTransaction() throws SQLException {
		java.sql.Connection conn = this.getConn();
		conn.commit();
		conn.setAutoCommit(true);
	}

	public void rollbackTransaction() throws SQLException {
		java.sql.Connection conn = this.getConn();
		conn.rollback();
		conn.setAutoCommit(true);
	}
}
