package it.metallicdonkey.tcp.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.*;

public class DBHelper {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelper instance;
	
	private DBHelper() {
		dbm.connect();
	}
	
	public static DBHelper getInstance() {
		if(instance != null) {
			return instance;
		}
		instance = new DBHelper();
		return instance;
	}
	
	public static ArrayList<Employee> getAllEmployees(String clause){
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM employee WHERE "+clause);
			// verify if the query returned an empty table
			if(!dbm.getResultSet().next()) {
				return null;
			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
			while(result.next()) {
				Employee e= new Employee();
				e.setId(result.getString("idEmployee"));
				e.setFirstName(result.getString("First Name"));
				e.setLastName(result.getString("Last Name"));
				e.setBirthDate(result.getDate("BirthDate").toLocalDate());
				e.setEmail(result.getString("Email"));
				e.setAddress(result.getString("Address"));
				e.setSalary(result.getDouble("Salary"));
				e.setStatus(StatusEmployee.valueOf(result.getString("Status")));
				e.setRole(Role.valueOf(result.getString("Role")));
				e.setWorkshift(Workshift.valueOf(result.getString("Workshift")));
				employees.add(e);				
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return employees;
	}
	
	public static ArrayList<Employee> getAllEmployees(){
		return getAllEmployees("TRUE");
	}

	// To Do 
	public static void insertEmployee(Employee e) {
		String id = e.getId();
		String fristName = e.getFirstName();
		String lastName = e.getLastName();
		Date birthDate = Date.from(e.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		String email = e.getEmail();
		String address = e.getAddress();
		double salary = e.getSalary();
		String status = e.getStatus().name();
	}
}
