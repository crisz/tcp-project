package it.metallicdonkey.tcp.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;

import it.metallicdonkey.tcp.employeesManagement.EmployeeDataModel;
import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.AbsenceInterval;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.Payment;
import it.metallicdonkey.tcp.models.StatusEmployee;
import it.metallicdonkey.tcp.models.Workshift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBHelperEmployee {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelperEmployee instance;

	private DBHelperEmployee() {
		dbm.connect();
	}

	public static DBHelperEmployee getInstance() {
		if(instance != null) {
			return instance;
		}
		instance = new DBHelperEmployee();
		return instance;
	}
	public Employee login(String id, String password) throws SQLException {
		Employee employee = new Employee();
		employee = this.getEmployeeById(id);
		System.out.println(employee.getPassword());
		System.out.println(password);
		if( !(employee.getPassword().equals(password))) {
			return null;
		}
		return employee;
	}
	public ArrayList<Employee> getAllEmployees(String clause){
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM employee WHERE "+clause);
			// verify if the query returned an empty table
//			if(!dbm.getResultSet().next()) {
//				return null;
//			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
//			result.beforeFirst();
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
				e.setPassword(result.getString("Password"));
				String w = result.getString("Workshift");
				if(w.equals("MORNING"))
					e.setWorkshift(Workshift.MATTINA);
				if(w.equals("AFTERNOON"))
					e.setWorkshift(Workshift.POMERIGGIO);
				else
					e.setWorkshift(Workshift.SERA);
				employees.add(e);
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return employees;
	}

	public ObservableList<EmployeeDataModel> getAllEmployees() {
		ArrayList<EmployeeDataModel> employees = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM employee");
			// verify if the query returned an empty table
//			if(!dbm.getResultSet().next()) {
//				return null;
//			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
			result.beforeFirst();

			while(result.next()) {
				Employee e = new Employee();
				e.setId(result.getString("idEmployee"));
				e.setFirstName(result.getString("First Name"));
				e.setLastName(result.getString("Last Name"));
				e.setBirthDate(result.getDate("BirthDate").toLocalDate());
				e.setEmail(result.getString("Email"));
				e.setAddress(result.getString("Address"));
				e.setSalary(result.getDouble("Salary"));
				e.setStatus(StatusEmployee.valueOf(result.getString("Status")));
				e.setRole(Role.valueOf(result.getString("Role")));
				e.setPassword(result.getString("Password"));
				String w = result.getString("Workshift");
				if(w.equals("MORNING")) {
					e.setWorkshift(Workshift.MATTINA);
				}
				else if(w.equals("AFTERNOON"))
					e.setWorkshift(Workshift.POMERIGGIO);
				else
					e.setWorkshift(Workshift.SERA);
				employees.add(new EmployeeDataModel(e));
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<EmployeeDataModel> dataEmployees = FXCollections.observableArrayList(employees);
		return dataEmployees;
	}

	public Employee getEmployeeById(String id) throws SQLException {
		System.out.println("Getting the employee with id " + id);
		ArrayList<Employee> ar = getAllEmployees("idEmployee = '" + id + "'");
		if(ar.size() == 0) {
			// TODO: come dovremmo comportarci se l'impiegato non esiste?
			return new Employee();
		}
		else return ar.get(0);
	}

	public ArrayList<Employee> getAllEmployeesArray() {
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM employee");
			// verify if the query returned an empty table
//			if(!dbm.getResultSet().next()) {
//				return null;
//			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
			result.beforeFirst();

			while(result.next()) {
				Employee e = new Employee();
				e.setId(result.getString("idEmployee"));
				e.setFirstName(result.getString("First Name"));
				e.setLastName(result.getString("Last Name"));
				e.setBirthDate(result.getDate("BirthDate").toLocalDate());
				e.setEmail(result.getString("Email"));
				e.setAddress(result.getString("Address"));
				e.setSalary(result.getDouble("Salary"));
				e.setStatus(StatusEmployee.valueOf(result.getString("Status")));
				e.setRole(Role.valueOf(result.getString("Role")));
				e.setPassword(result.getString("Password"));
				String w = result.getString("Workshift");
				if(w.equals("MORNING")) {
					e.setWorkshift(Workshift.MATTINA);
				}
				else if(w.equals("AFTERNOON"))
					e.setWorkshift(Workshift.POMERIGGIO);
				else
					e.setWorkshift(Workshift.SERA);
				employees.add(e);
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
			return null;
		}
		// ObservableList<EmployeeDataModel> dataEmployees = FXCollections.observableArrayList(employees);
		return employees;
	}

	public void insertEmployee(Employee e) throws SQLException {
		String query = " INSERT INTO tcp.employee ()" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString (1, e.getId());
		preparedStmt.setString (2, e.getFirstName());
		preparedStmt.setString (3, e.getLastName());
		preparedStmt.setString (4, e.getBirthDate().getYear()+"-"+e.getBirthDate().getMonthValue()+"-"+e.getBirthDate().getDayOfMonth());
		preparedStmt.setString (5, workshiftToEnglish(e.getWorkshift()));
		preparedStmt.setDouble (6, e.getSalary());
		preparedStmt.setString (7, e.getEmail());
		preparedStmt.setString (8, e.getRole().name());
		preparedStmt.setString (9, "AVAILABLE");
		preparedStmt.setString (10, e.getAddress());
		preparedStmt.setString (11, e.getPassword());
		// execute the preparedstatement
		preparedStmt.execute();

	}

	public static String workshiftToEnglish(Workshift w) {
		switch(w) {
		case MATTINA:
			return "MORNING";
		case POMERIGGIO:
			return "AFTERNOON";
		case SERA:
			return "EVENING";
		}
		return "MORNING";
	}

	public void insertAbsenceStartDay(Employee e) throws SQLException {
		LocalDate date = LocalDate.now();
		// String id = this.getNewAbsenceId();
		int updated = -1;
		String query = "INSERT INTO tcp.absenceInterval (StartDay, EndDay, Employee_idEmployee) "+
				"VALUES (?, ?, ?)";
		String query2 = "UPDATE tcp.employee SET Status=\"ABSENT\" WHERE idEmployee=\""+e.getId() + "\"";
		e.setStatus(StatusEmployee.ABSENT);
		updated = dbm.executeUpdate(query2);
		if(updated < 1)
			throw new SQLException();
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		// preparedStmt.setString(1, id);
		preparedStmt.setString(1, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		preparedStmt.setString(2, "1970-01-01");
		preparedStmt.setString(3, e.getId());
		preparedStmt.execute();
	}
	private String getAbsenceId(Employee e) {
		String id = null;
		try {
			dbm.executeQuery("SELECT * FROM AbsenceInterval "+
					"WHERE Employee_idEmployee=\""+e.getId()+"\" AND endDay=\"1970-01-01\"");
			ResultSet result = dbm.getResultSet();
			while(result.next())
				id = result.getString("idAbsenceInterval");
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return id;
	}

	/*
	 * TODO Fix this method
	 */
	public void insertAbsenceEndDay(Employee e) throws SQLException {
		String id = this.getAbsenceId(e);
		LocalDate date = LocalDate.now();
		int updated1 = -1;
		int updated2 = -1;
		if(id != null) {
			updated1 = dbm.executeUpdate("UPDATE tcp.Employee SET Status=\"AVAILABLE\" WHERE idEmployee=\""+e.getId()+"\"");
			updated2 = dbm.executeUpdate("UPDATE tcp.absenceInterval SET EndDay=\""+date.format(DateTimeFormatter.ISO_LOCAL_DATE)+
					"\" WHERE idAbsenceInterval=\""+id+"\" AND Employee_idEmployee=\""+e.getId()+"\"");
			if(updated1 < 1 || updated2 < 1)
				throw new SQLException();
			e.setStatus(StatusEmployee.AVAILABLE);
		} else
			throw new SQLException();
	}

	public ArrayList<AbsenceInterval> getAbsenceInterval(Employee e) {
		ArrayList<AbsenceInterval> array = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM AbsenceInterval WHERE Employee_idEmployee="+e.getId());
			ResultSet result = dbm.getResultSet();
			result.beforeFirst();
			while(result.next()) {
				AbsenceInterval abs = new AbsenceInterval();
				abs.setId(result.getString("idAbsenceInterval"));
				abs.setStartDay(LocalDate.parse(result.getString("startDay"), DateTimeFormatter.ISO_LOCAL_DATE));
				abs.setEndDay(LocalDate.parse(result.getString("endDay"), DateTimeFormatter.ISO_LOCAL_DATE));
				abs.setIdEmployee(result.getString("Employee_idEmployee"));
				array.add(abs);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
			return null;
		}
		return array;
	}

	public int removeEmployee(Employee e) {
		int removed1 = -1;
		int removed2 = -1;
		int result = -1;
		removed1 = dbm.executeUpdate("DELETE FROM tcp.match WHERE Employee_idEmployee = '"+
				e.getId()+"'");
		removed2 = dbm.executeUpdate("DELETE FROM tcp.payment WHERE Employee_idEmployee = '"+
				e.getId()+"'");
		if(removed1 < 0 || removed2 < 0)
			return -1;
		result = dbm.executeUpdate("DELETE FROM tcp.employee WHERE idEmployee='"+e.getId()+"'");
		return result;
	}

	public void insertPayment(Payment p, Employee e) throws SQLException {
		String query = " INSERT INTO tcp.payment (Date, NetSalary, Employee_idEmployee)" + " values (?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString (1, p.getDate().getYear()+"-"+p.getDate().getMonthValue()+"-"+p.getDate().getDayOfMonth());
		preparedStmt.setDouble (2, p.getNetSalary());
		preparedStmt.setString (3, e.getId());
		// execute the preparedstatement
		preparedStmt.execute();

	}

	public ArrayList<Payment> getPayments(Employee e){
		ArrayList<Payment> payments = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM tcp.payment WHERE Employee_idEmployee = '"+e.getId()+"' ORDER BY Date DESC LIMIT 6");
			ResultSet result = dbm.getResultSet();

			while(result.next()) {
				Payment p = new Payment();
				p.setDate(result.getDate("Date").toLocalDate());
				p.setNetSalary(result.getDouble("NetSalary"));
				p.setIdEmployee(result.getString("Employee_idEmployee"));

				payments.add(p);
			}
		}
		catch(SQLException exc){
			exc.printStackTrace();
		}
		return payments;
	}

	public void updateEmployee(Employee e) throws SQLException{
		String query = "UPDATE tcp.employee SET " +
				"`First Name` = '" + e.getFirstName()+"', "+
				"`Last Name` = '" + e.getLastName() +"', "+
				"BirthDate = '" + new Date(e.getBirthDate().toEpochDay()) +"', "+
				"Workshift = '" + workshiftToEnglish(e.getWorkshift())+"', "+
				"Salary = " + e.getSalary()+", "+
				"Email = '" + e.getEmail()+"', "+
				"Role = '" + e.getRole().name()+"', "+
				"Status = '" + e.getStatus().name()+"', "+
				"Address = '" + e.getAddress()+"', "+
				"Password = '" + e.getPassword()+"' "+
				"WHERE idEmployee = '" + e.getId()+"';";
		System.out.println(query);
		dbm.executeUpdate(query);
	}
}
