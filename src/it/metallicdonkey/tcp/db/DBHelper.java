package it.metallicdonkey.tcp.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.*;
import it.metallicdonkey.tcp.vehicleArea.VehicleDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBHelper {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelper instance;
	
	private DBHelper() throws SQLException {
		dbm.connect();
	}
	
	public static DBHelper getInstance() throws SQLException {
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
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			
			dbm.executeQuery("SELECT * FROM employee");
			// verify if the query returned an empty table
			if(!dbm.getResultSet().next()) {
				return null;
			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
			while(dbm.getResultSet().next()) {
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

	public ObservableList<VehicleDataModel> getAllVehicles() {
		ArrayList<VehicleDataModel> vehicles= new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM vehicle");
			// verify if the query returned an empty table
//			if(!dbm.getResultSet().next()) {
//				return null;
//			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
			result.beforeFirst();
			while(result.next()) {
				Vehicle v= new Vehicle();
				v.setId(result.getString("idVehicle"));
				v.setPlate(result.getString("Plate"));
				v.setPlacesForDisable(result.getInt("PlacesForDisabled"));
				v.setSeats(result.getInt("Seats"));
				v.setStandingPlaces(result.getInt("StandingSeats"));
				v.setStatus(StatusVehicle.valueOf(result.getString("Status")));
				vehicles.add(new VehicleDataModel(v, "In circolazione"));
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<VehicleDataModel> dataVehicles = FXCollections.observableArrayList(vehicles);
		return dataVehicles;
	}

	public void insertVehicle(Vehicle v) throws SQLException {
    // the mysql insert statement
    String query = " insert into vehicle (idVehicle, Brand, Status, Seats, StandingSeats, PlacesForDisabled, Plate)"
      + " values (?, ?, ?, ?, ?, ?, ?)";


    PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
    preparedStmt.setString(1, v.getId());
    preparedStmt.setString(2, v.getBrand());
    preparedStmt.setString(3, v.getStatus().name());
    preparedStmt.setInt(4, v.getSeats());
    preparedStmt.setInt(5, v.getStandingPlaces());
    preparedStmt.setInt(6, v.getPlacesForDisable());
    preparedStmt.setString(7, v.getPlate());

    // execute the preparedstatement
    preparedStmt.execute();
	}
}
