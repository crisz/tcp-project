package it.metallicdonkey.tcp.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.StatusEmployee;
import it.metallicdonkey.tcp.models.Workshift;

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
			ResultSet result = dbm.getResultSet();
			while(result.next()) {
				Employee e= new Employee();
				e.setId(result.getString("idEmployee"));
				e.setFirstName(result.getString("Name"));
				e.setLastName(result.getString("Surname"));
				e.setBirthDate(result.getDate("BirthDate").toLocalDate());
				e.setEmail(result.getString("MailAddress"));
				e.setAddress(result.getString("Address"));
				e.setSalary(result.getDouble("GrossSalary"));
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
}
