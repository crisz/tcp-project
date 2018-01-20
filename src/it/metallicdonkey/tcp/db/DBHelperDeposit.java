package it.metallicdonkey.tcp.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import it.metallicdonkey.tcp.models.Location;
import it.metallicdonkey.tcp.models.Vehicle;

public class DBHelperDeposit {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelperDeposit instance;

	private DBHelperDeposit() throws SQLException {
		dbm.connect();
	}
	
	public static DBHelperDeposit getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBHelperDeposit();
		return instance;
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
		return (locations.isEmpty())? null: locations.get(0);
	}
	public int setLocation(Vehicle v) throws SQLException {
		ArrayList<Location> locations = this.getAllFreeLocations();
		if(locations.size() == 0) 
			return -1;
		Location location = locations.get((int)Math.floor(Math.random()*locations.size()));
		int updated = -1;
		updated = dbm.executeUpdate("UPDATE tcp.location SET Vehicle_idVehicle='"+v.getId()+"' "+
		"WHERE idPlace='"+location.getId_Location()+"'");
		if(updated < 1)
			throw new SQLException();
		location.setId_Vehicle(v.getId());
		return location.getId_Location();
	}
	public void freeLocation(Vehicle v) throws SQLException {
		Location l= this.getLocation(v);
		
		int updated = -1;
		updated = dbm.executeUpdate("UPDATE tcp.location SET Vehicle_idVehicle=null WHERE idPlace='"+
				l.getId_Location()+"'");
		if(updated < 1)
			throw new SQLException();
	}
}
