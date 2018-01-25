package it.metallicdonkey.tcp.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import it.metallicdonkey.tcp.models.BrokenInterval;
import it.metallicdonkey.tcp.models.StatusVehicle;
import it.metallicdonkey.tcp.models.Vehicle;
import it.metallicdonkey.tcp.models.Workshift;
import it.metallicdonkey.tcp.vehiclesManagement.VehicleDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBHelperVehicle {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelperVehicle instance;

	private DBHelperVehicle() throws SQLException {
		dbm.connect();
	}

	public static DBHelperVehicle getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBHelperVehicle();
		return instance;
	}

	public Vehicle getVehicleById(String id) throws SQLException {
		return getAllVehiclesArray("idVehicle='" + id + "'").get(0);
	}

	public ArrayList<Vehicle> getAllVehiclesArray(String clause) throws SQLException {
		ArrayList<Vehicle> vehicles= new ArrayList<>();
		clause = (clause == null)? "true" :clause;

		dbm.executeQuery("SELECT * FROM tcp.vehicle WHERE " + clause);
		ResultSet result = dbm.getResultSet();
		while(result.next()) {
			Vehicle v= new Vehicle();
			v.setId(result.getString("idVehicle"));
			v.setBrand(result.getString("Brand"));
			v.setPlate(result.getString("Plate"));
			v.setPlacesForDisable(result.getInt("PlacesForDisabled"));
			v.setSeats(result.getInt("Seats"));
			v.setStandingPlaces(result.getInt("StandingPlaces"));
			v.setStatus(StatusVehicle.valueOf(result.getString("Status")));
			vehicles.add(v);
		}
		return vehicles;
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
				v.setBrand(result.getString("Brand"));
				v.setPlate(result.getString("Plate"));
				v.setPlacesForDisable(result.getInt("PlacesForDisabled"));
				v.setSeats(result.getInt("Seats"));
				v.setStandingPlaces(result.getInt("StandingPlaces"));
				v.setStatus(StatusVehicle.valueOf(result.getString("Status")));
				vehicles.add(new VehicleDataModel(v, statusVehicleToItalian(v.getStatus())));
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<VehicleDataModel> dataVehicles = FXCollections.observableArrayList(vehicles);
		return dataVehicles;
	}

	private String statusVehicleToItalian(StatusVehicle status) {
		switch (status) {
		case ON_ACTION:
			return "In circolazione";
		case AVAILABLE:
			return "In deposito";
		case BROKEN:
			return "Guasto";
		default:
			return "Invalid location";
		}
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
				v.setBrand(result.getString("Brand"));
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
	public int insertVehicle(Vehicle v) throws SQLException {
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
		int location = DBHelperDeposit.getInstance().setLocation(v);
		return location;
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

	public void insertBrokenStartDay(Vehicle v) throws SQLException {
		// String id = this.getNewBrokenId();
		LocalDate date = LocalDate.now();
		int updated = -1;
		String query2 = "UPDATE vehicle SET Status=\"BROKEN\" WHERE idVehicle=\""+v.getId()+"\"";
		updated = dbm.executeUpdate(query2);
		String query = "INSERT INTO BrokenInterval (StartDay, EndDay, Vehicle_idVehicle) "+
				"VALUES (?, ?, ?)";
		if (updated < 1)
			throw new SQLException();
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		// preparedStmt.setString(1, id);
		preparedStmt.setString(1, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		preparedStmt.setString(2, "1970-01-01");
		preparedStmt.setString(3, v.getId());
		preparedStmt.execute();
		if(v.getStatus() == StatusVehicle.AVAILABLE)
			DBHelperDeposit.getInstance().freeLocation(v);
		v.setStatus(StatusVehicle.BROKEN);
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

	public int removeVehicle(Vehicle v) {
		int removed1 = -1;
		int removed2 = -1;
		int result = -1;
		removed1 = dbm.executeUpdate("DELETE FROM tcp.match WHERE Vehicle_idVehicle = '"+
				v.getId()+"'");
		removed2 = dbm.executeUpdate("UPDATE tcp.Location SET Vehicle_idVehicle=null WHERE Vehicle_idVehicle='"+
				v.getId()+"'");
		if(removed1 < 0)
			return -1;
		result = dbm.executeUpdate("DELETE FROM tcp.vehicle WHERE idVehicle='"+v.getId()+"'");
		return result;
	}
	public void updateVehicle(Vehicle newV) throws SQLException {
		int updated = -1;
		String query = "UPDATE tcp.vehicle SET "+
				"Brand='"+newV.getBrand()+"', "+
				"Status = '" + newV.getStatus().name() +"',"+
				"Seats= "+newV.getSeats()+", "+
				"StandingPlaces="+newV.getStandingPlaces()+", "+
				"PlacesForDisabled="+newV.getPlacesForDisable()+", "+
				"Plate='"+newV.getPlate()+"' "+
				"WHERE idVehicle='"+newV.getId()+"'";
		updated = dbm.executeUpdate(query);
		if(updated < 1)
			throw new SQLException();
	}

	public ArrayList<String> getIds() throws SQLException{
		ArrayList<String> allIds = new ArrayList<>();
		dbm.executeQuery("SELECT idVehicle FROM tcp.vehicle");
		ResultSet result = dbm.getResultSet();
		while(result.next()) {
			allIds.add(result.getString("idVehicle"));
		}
		return allIds;
	}
}
