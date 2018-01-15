package it.metallicdonkey.tcp.db;

import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class Test {
	public static void main(String[] args) throws SQLException {
		DBHelper db = DBHelper.getInstance();
//		Vehicle v = new Vehicle();
//		
//		v.setId("12345");
//		int result = db.removeVehicle(v);
//		System.out.println(result);
		
//		Line l = new Line();
//		l.setName("101");
//		int result = db.removeLine(l);
//		System.out.println(result);
		
		ArrayList<Location> locations = db.getAllFreeLocations();
		Iterator<Location> it = locations.iterator();
		while(it.hasNext()) {
			Location l = it.next();
			System.out.println(l.getId_Location()+" "+ l.getId_Vehicle());
		}
	}
}
