package it.metallicdonkey.tcp.db;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
	
	public static ArrayList<Employee> queryEmployee(String select, String where){
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT "+select+" FROM employee WHERE "+where);
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
				//e.setStatus(dbm.getResultSet().getString("Status"));
				//e.setRole(dbm.getResultSet().getString("Role"));
				//e.setWorkshift(dbm.getResultSet().getString("Workshift"));
				employees.add(e);
				
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return employees;
	}
}
