package it.metallicdonkey.tcp.db;

import java.sql.SQLException;

public class DBHelperCheck {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelperCheck instance;

	private DBHelperCheck() throws SQLException {
		dbm.connect();
	}
	
	public static DBHelperCheck getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBHelperCheck();
		return instance;
	}
}
