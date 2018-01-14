package it.metallicdonkey.tcp.db;
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

			if(!dbm.getResultSet().next()) {
				return null;
			}
			
			dbm.executeQuery("SELECT * FROM employee WHERE "+clause);
			while(dbm.getResultSet().next()) {
				Employee e= new Employee();
				e.setId(dbm.getResultSet().getString("idEmployee"));
				e.setFirstName(dbm.getResultSet().getString("Name"));
				e.setLastName(dbm.getResultSet().getString("Surname"));
				LocalDate date = dbm.getResultSet().getDate("BirthDate").toLocalDate();
				e.setBirthDate(date);
				e.setEmail(dbm.getResultSet().getString("MailAddress"));
				e.setAddress(dbm.getResultSet().getString("Address"));
				e.setSalary(dbm.getResultSet().getDouble("GrossSalary"));
				String stringa = dbm.getResultSet().getString("Status");
				e.setStatus(StatusEmployee.valueOf(stringa));
				stringa = dbm.getResultSet().getString("Role");
				e.setRole(Role.valueOf(stringa));
				dbm.getResultSet().getString("Workshift");
				e.setWorkshift(Workshift.valueOf(stringa));
				employees.add(e);				
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return employees;
	}

	public static ArrayList<Employee> getAllEmployees(){
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM employee");
			if(!dbm.getResultSet().next()) {
				return null;
			}
			while(dbm.getResultSet().next()) {
				Employee e= new Employee();
				e.setId(dbm.getResultSet().getString("idEmployee"));
				e.setFirstName(dbm.getResultSet().getString("Name"));
				e.setLastName(dbm.getResultSet().getString("Surname"));
				LocalDate date = dbm.getResultSet().getDate("BirthDate").toLocalDate();
				e.setBirthDate(date);
				e.setEmail(dbm.getResultSet().getString("MailAddress"));
				e.setAddress(dbm.getResultSet().getString("Address"));
				e.setSalary(dbm.getResultSet().getDouble("GrossSalary"));
				String stringa = dbm.getResultSet().getString("Status");
				e.setStatus(StatusEmployee.valueOf(stringa));
				stringa = dbm.getResultSet().getString("Role");
				e.setRole(Role.valueOf(stringa));
				dbm.getResultSet().getString("Workshift");
				e.setWorkshift(Workshift.valueOf(stringa));
				employees.add(e);				
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return employees;
	}

	// To Do 
	/*public static void insertEmployee(Employee e) {
		String id = e.getId();
		String fristName = e.getFirstName();
		String lastName = e.getLastName();
		Date birthDate = Date.from(e.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		String email = e.getEmail();
		String address = e.getAddress();
		double salary = e.getSalary();
		String status = e 
	}*/
}
