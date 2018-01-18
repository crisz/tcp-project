package it.metallicdonkey.tcp.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.metallicdonkey.tcp.models.Check;
import it.metallicdonkey.tcp.models.Match;
import it.metallicdonkey.tcp.models.Workshift;

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
	
	public void insertCheck(Check c) throws SQLException {
		String query = " INSERT INTO tcp.check ()" + " values (?, ?)";
		
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
		preparedStmt.setString(2, DBHelperEmployee.workshiftToEnglish(c.getWorkshift()));
		preparedStmt.execute();
		
		List<Match> matches = c.getMatches(); 
		query = "INSERT INTO tcp.match (Employee_idEmployee,Vehicle_idVehicle,Line_idLine,Check_Date) values (?, ?, ?, ?)";
		preparedStmt = dbm.getConnection().prepareStatement(query);
		
		for(int i=0; i< matches.size(); i++) {
			preparedStmt.setString(1, matches.get(i).getEmployee().getId());
			preparedStmt.setString(2, matches.get(i).getVehicle().getId());
			preparedStmt.setString(3, matches.get(i).getLine().getName());
			preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			preparedStmt.execute();
		}
	}
	
	private String workshiftToItalian(String s) {
		if(s.equals("Morning"))
			return "MATTINA";
		if(s.equals("Afternoon"))
			return "POMERIGGIO";
		else
			return "SERA";
	}
	
	/**
	 * 
	 * @return the 3 checks for MORNING, AFTERNOON AND EVENING of the day before
	 * @throws SQLException
	 */	
	public List<Check> getLastChecks() throws SQLException {
		String query = "SELECT * FROM tcp.check ORDER BY Date DESC LIMIT 3";
		ResultSet resultSet = dbm.executeQuery(query);
		
		List<Check> checks = new ArrayList<>(3); 
		
		while(resultSet.next()) {
			Check c = new Check();
			
			c.setWorkshift(Workshift.valueOf(workshiftToItalian(resultSet.getString(2))));
			
			// get the yesterday date
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterday = new Date(cal.getTimeInMillis());
			
			// get all the related matches
			query = "SELECT * FROM tcp.match;";
			ResultSet resultSet2 = dbm.executeQuery(query);
			while(resultSet2.next()) {
				Match m = new Match();
				m.setEmployee(DBHelperEmployee.getInstance().getEmployeeById(resultSet2.getString(2)));
				m.setVehicle(DBHelperVehicle.getInstance().getVehicleById(resultSet2.getString(3)));
				m.setLine(DBHelperLine.getInstance().getLineById(resultSet2.getString(4)));
				c.addMatch(m);
			}
			checks.add(c);
		}
		return checks;
	}
		
	/**
	 * Remove the 3 checks of the day before from the database.
	 * @throws SQLException
	 */
	public void removeLastChecks() throws SQLException{
		// Remove first all the matches related to Checks of the day before
		String removeMatchesQuery = "DELETE FROM tcp.match";
		dbm.executeUpdate(removeMatchesQuery);
		
		String removeChecksQuery = "DELETE FROM tcp.check";
		dbm.executeUpdate(removeChecksQuery);
	}
}
