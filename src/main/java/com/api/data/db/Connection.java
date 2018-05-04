package com.api.data.db;

// import java.io.IOException;
// import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
// import java.util.Properties;

public class Connection {
	// private Properties prop = new Properties();
	// private InputStream input = null;

	private static Connection instancia;
	public static Connection getInstancia() {
		if(instancia == null)
			instancia = new Connection();

		return instancia;
	}

	private static String dbUrl = "jdbc:mysql://localhost:3306/wholesaler?useSSL=false";
	private static String dbUser = "root";
	private static String dbPassword = "root";
	// private static String env = "DEV";

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
			/*input = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config.properties");

			prop.load(input);

			if (env.equals("PRD")) {
				dbUrl = prop.getProperty("db-url-prd");
				dbUser = prop.getProperty("db-user-prd");
				dbPassword = prop.getProperty("db-password-prd");
			}
			else if (env.equals("DEV")) {
				dbUrl = prop.getProperty("db-url-dev");
				dbUser = prop.getProperty("db-user-dev");
				dbPassword = prop.getProperty("db-password-dev");
			}*/

			if (conn == null || !conn.isValid(3)) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			}

			cantCon++;
			System.out.println("Nueva conexión abierta -> cantidad de conexiones abiertas: " + cantCon);
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
		/*catch (IOException e) {
			e.printStackTrace();
		}*/

		return conn;
	}

	//Cerrar conexión y manejo de errores
	public void closeConn() {
		try {
			cantCon--;
			System.out.println("Se cerró una conexión -> cantidad de conexiones abiertas: " + cantCon);

			if(cantCon==0)
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void beginTransaction() {
		try {
			this.getConn().setAutoCommit(false);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commitTransaction() {
		try {
			this.conn.commit();
			this.conn.setAutoCommit(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConn();
		}
	}

	public void rollbackTransaction() {
		try {
			this.conn.rollback();
			this.conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConn();
		}
	}
}
