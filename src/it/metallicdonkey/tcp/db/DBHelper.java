package it.metallicdonkey.tcp.db;
import java.security.spec.ECField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import it.metallicdonkey.tcp.administrativeArea.LineDataModel;
import it.metallicdonkey.tcp.HRArea.EmployeeDataModel;
import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.*;
import it.metallicdonkey.tcp.vehicleArea.VehicleDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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

				String w = result.getString("Workshift");
				System.out.println("wwww");
				System.out.println(w);
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
				String w = result.getString("Workshift");
				System.out.println("wwww");
				System.out.println(w);
				if(w.equals("MORNING")) {
					System.out.println("Questo impiegato è di mattina");
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
				String w = result.getString("Workshift");
				System.out.println("wwww");
				System.out.println(w);
				if(w.equals("MORNING")) {
					System.out.println("Questo impiegato è di mattina");
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
		preparedStmt.setString (5, this.workshiftToEnglish(e.getWorkshift()));
		preparedStmt.setDouble (6, e.getSalary());
		preparedStmt.setString (7, e.getEmail());
		preparedStmt.setString (8, e.getRole().name());
		preparedStmt.setString (9, "AVAILABLE");
		preparedStmt.setString (10, e.getAddress());
		preparedStmt.setString (11, "TCP_password");
		// execute the preparedstatement
		preparedStmt.execute();

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
				v.setStandingPlaces(result.getInt("StandingPlaces"));
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
	public ArrayList<Vehicle> getAllVehiclesArray() {
		ArrayList<Vehicle> vehicles= new ArrayList<>();
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
				v.setStandingPlaces(result.getInt("StandingPlaces"));
				v.setStatus(StatusVehicle.valueOf(result.getString("Status")));
				// vehicles.add(new VehicleDataModel(v, "In circolazione"));
				vehicles.add(v);
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		// ObservableList<VehicleDataModel> dataVehicles = FXCollections.observableArrayList(vehicles);
		return vehicles;
	}
	public ObservableList<VehicleDataModel> getAllVehicles(String clause) {
		ArrayList<VehicleDataModel> vehicles = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM vehicle WHERE " + clause);
			ResultSet result = dbm.getResultSet();
			result.beforeFirst();
			while(result.next()) {
				Vehicle v = new Vehicle();
				v.setId(result.getString("idVehicle"));
				v.setPlate(result.getString("Plate"));
				v.setPlacesForDisable(result.getInt("PlacesForDisabled"));
				v.setSeats(result.getInt("Seats"));
				v.setStandingPlaces(result.getInt("StandingPlaces"));
				v.setStatus(StatusVehicle.valueOf(result.getString("Status")));
				vehicles.add(new VehicleDataModel(v, "In circolazione"));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<VehicleDataModel> dataVehicles = FXCollections.observableArrayList(vehicles);
		return dataVehicles;
	}
	public String workshiftToEnglish(Workshift w) {
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
	public void insertVehicle(Vehicle v) throws SQLException {
		// the mysql insert statement
		String query = "insert into vehicle (idVehicle, Brand, Status, Seats, StandingPlaces, PlacesForDisabled, Plate)"
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
	// bisogna vedere come identificare un capolinea rispetto alle normali fermate
	public Stop getTerminal(Line line, boolean first) {
		Stop stop = new Stop();
		try {
			if(first == true) {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=FIRST");
			} else {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=END");
			}
			ResultSet result = dbm.getResultSet();
			stop.setAddress(result.getString("Address"));
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return stop;
	}
	public ArrayList<Stop> getStops(Line line, boolean going) {
		ArrayList<Stop> stops = new ArrayList<>();
		try {
			if(going == true) {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=GOING");
			} else {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=RETURN");
			}
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Stop stop = new Stop();
				stop.setAddress(resultSet.getString("Address"));
				stops.add(stop);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return stops;
	}
	public ObservableList<LineDataModel> getAllLines() {
		ArrayList<LineDataModel> lines = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM line");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Line line = new Line();
				line.setName(resultSet.getString("idLine"));
				line.setStartTerminal(this.getTerminal(line, true));
				line.setEndTerminal(this.getTerminal(line, false));
				line.setGoingStops(this.getStops(line, true));
				line.setReturnStops(this.getStops(line, false));
				lines.add(new LineDataModel(line));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<LineDataModel> dataLines = FXCollections.observableArrayList();
		return dataLines;
	}
	private String getNewAbsenceId() {
		// TODO: WTF? Qual è il motivo? L'ID viene generato automaticamente
		List<String> ids = new ArrayList<>();
		String idAbs = null;
		try {
			dbm.executeQuery("SELECT idAbsenceInterval FROM AbsenceInterval");
			ResultSet result = dbm.getResultSet();
			while(result.next()) {
				String id = result.getString("idAbsenceInterval");
				ids.add(id);
			}
			Random random = new Random();
			int idAbsence = random.nextInt(1024);
			idAbs = String.valueOf(idAbsence).toString();
			boolean isUnique = false;
			while(!isUnique) {
				if(!ids.contains(idAbs))
					isUnique = true;
				else {
					idAbsence = random.nextInt(1024);
					idAbs = String.valueOf(idAbsence).toString();
				}
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return idAbs;
	}
	public void insertAbsenceStartDay(Employee e) throws SQLException {
		LocalDate date = LocalDate.now();
		String id = this.getNewAbsenceId();
		int updated = -1;
		if(id != null) {
			String query = "INSERT INTO tcp.absenceInterval (idAbsenceInterval, StartDay, EndDay, Employee_idEmployee) "+
					"VALUES (?, ?, ?, ?)";
			String query2 = "UPDATE tcp.employee SET Status=\"ABSENT\" WHERE idEmployee=\""+e.getId() + "\"";
			e.setStatus(StatusEmployee.ABSENT);
			updated = dbm.executeUpdate(query2);
			if(updated < 1)
				throw new SQLException();
			PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
			preparedStmt.setString(3, "1970-01-01");
			preparedStmt.setString(4, e.getId());
			preparedStmt.execute();
		} else
			throw new SQLException();
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
	private String getNewBrokenId() {
		List<String> ids = new ArrayList<>();
		String idAbs = null;
		try {
			dbm.executeQuery("SELECT idBrokenInterval FROM BrokenInterval");
			ResultSet result = dbm.getResultSet();
			while(result.next()) {
				String id = result.getString("idBrokenInterval");
				ids.add(id);
			}
			Random random = new Random();
			int idAbsence = random.nextInt(1024);
			idAbs = String.valueOf(idAbsence).toString();
			boolean isUnique = false;
			while(!isUnique) {
				if(!ids.contains(idAbs))
					isUnique = true;
				else {
					idAbsence = random.nextInt(1024);
					idAbs = String.valueOf(idAbsence).toString();
				}
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return idAbs;
	}
	public void insertBrokenStartDay(Vehicle v) throws SQLException {
		String id = this.getNewBrokenId();
		LocalDate date = LocalDate.now();
		int updated = -1;
		if(id != null) {
			String query2 = "UPDATE vehicle SET Status=\"BROKEN\" WHERE idVehicle=\""+v.getId()+"\"";
			updated = dbm.executeUpdate(query2);
			String query = "INSERT INTO BrokenInterval (idBrokenInterval, StartDay, EndDay, Vehicle_idVehicle) "+
					"VALUES (?, ?, ?, ?)";
			if (updated < 1)
				throw new SQLException();
			PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
			preparedStmt.setString(3, "1970-01-01");
			preparedStmt.setString(4, v.getId());
			preparedStmt.execute();
			v.setStatus(StatusVehicle.BROKEN);
		} else
			throw new SQLException();
	}
	private String getBrokenId(Vehicle v) {
		String id = null;
		try {
			dbm.executeQuery("SELECT idBrokenInterval FROM BrokenInterval "+
					"WHERE Vehicle_idVehicle=\""+v.getId()+"\" AND endDay=\"1970-01-01\"");
			ResultSet result = dbm.getResultSet();
			while(result.next())
				id = result.getString("idBrokenInterval");
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return id;
	}
	public void insertBrokenEndDay(Vehicle v) throws SQLException {
		String id = this.getBrokenId(v);
		LocalDate date = LocalDate.now();
		int updated = -1;
		int updated2 = -1;
		if(id != null) {
			updated = dbm.executeUpdate("UPDATE vehicle SET Status=\"AVAILABLE\" WHERE idVehicle=\""+v.getId()+"\"");
			updated2 = dbm.executeUpdate("UPDATE tcp.BrokenInterval SET EndDay=\""+date.format(DateTimeFormatter.ISO_LOCAL_DATE)+
					"\" WHERE idBrokenInterval=\""+id + "\" AND Vehicle_idVehicle=\""+v.getId()+"\"");
			if (updated < 1 || updated2 < 1)
				throw new SQLException();
			v.setStatus(StatusVehicle.AVAILABLE);
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
	public ArrayList<BrokenInterval> getBrokenInterval(Vehicle v) {
		ArrayList<BrokenInterval> array = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM BrokenInterval WHERE Vehicle_idVehicle=\""+v.getId()+"\"");
			ResultSet result = dbm.getResultSet();
			result.beforeFirst();
			while(result.next()) {
				BrokenInterval bro = new BrokenInterval();
				bro.setId(result.getString("idBrokenInterval"));
				bro.setStartDay(LocalDate.parse(result.getString("startDay"), DateTimeFormatter.ISO_LOCAL_DATE));
				bro.setEndDay(LocalDate.parse(result.getString("endDay"), DateTimeFormatter.ISO_LOCAL_DATE));
				bro.setIdVehicle(result.getString("Vehicle_idVehicle"));
				array.add(bro);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
			return null;
		}
		return array;
	}
	//this method returns 1 if the query success
	public int removeEmployee(Employee e) {
		int result = dbm.executeUpdate("DELETE FROM tcp.employee WHERE idEmployee='"+e.getId()+"'");
		return result;
	}
	//this method returns 1 if the query success
	public int removeVehicle(Vehicle v) {
		int result = dbm.executeUpdate("DELETE FROM tcp.vehicle WHERE idVehicle='"+v.getId()+"'");
		return result;
	}
	//this method returns 1 if the query success
	public int removeLine(Line l) {
		int result = dbm.executeUpdate("DELETE FROM tcp.line WHERE idLine='"+l.getName()+"'");
		return result;
	}

	private ArrayList<Location> getLocations(String clause){
		ArrayList<Location> locations = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM tcp.location WHERE "+clause);
			// verify if the query returned an empty table
//			if(!dbm.getResultSet().next()) {
//				return null;
//			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
//			result.beforeFirst();
			while(result.next()) {
				Location l= new Location();
				l.setId_Location(result.getInt("idPlace"));
				l.setId_Vehicle(result.getString("Vehicle_idVehicle"));
				locations.add(l);
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return locations;
	}
	public ArrayList<Location> getAllLocations(){
		return this.getLocations("TRUE");
	}
	public ArrayList<Location> getAllFreeLocations(){
		ArrayList<Location> locations = this.getAllLocations();
		Iterator<Location> it = locations.iterator();
		ArrayList<Location> freeLocations = new ArrayList<>();

		while(it.hasNext()) {
			Location l = it.next();
			if(l.getId_Vehicle() == null) {
				freeLocations.add(l);
			}
		}
		return freeLocations;
	}
	public Location getLocation(Vehicle v) {
		ArrayList<Location> locations = this.getLocations("Vehicle_idVehicle='"+v.getId()+"'");
		return locations.get(0);
	}
}
