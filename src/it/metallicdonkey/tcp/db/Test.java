package it.metallicdonkey.tcp.db;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import it.metallicdonkey.tcp.models.Employee;

import it.metallicdonkey.tcp.models.Vehicle;

public class Test {
	public static void main(String[] args) throws SQLException {
		DBHelperVehicle db = DBHelperVehicle.getInstance();
		try {
			Vehicle old = db.getVehicleById("2587");
			old.setBrand("SO000CA");
			db.updateVehicle(old);
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
}
