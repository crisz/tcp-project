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
//		ArrayList<Employee> employees = new ArrayList<>();
		Iterator<Employee> it;
		Iterator<AbsenceInterval> it2;
		Iterator<Vehicle> it3;
		Iterator<BrokenInterval> it4;
//		Employee e = new Employee();
//		e.setStatus(StatusEmployee.AVAILABLE);
//		System.out.println(e.getStatus().name());
		Employee employee = new Employee();
		Employee employee2 = new Employee();
		Vehicle vehicle = new Vehicle();
		Vehicle vehicle2 = new Vehicle();
		AbsenceInterval abs = new AbsenceInterval();
		BrokenInterval bro = new BrokenInterval();
		ArrayList<Employee> employees = new ArrayList<>();
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		ArrayList<AbsenceInterval> absenceIntervals = new ArrayList<>();
		ArrayList<BrokenInterval> brokenIntervals = new ArrayList<>();

		employee.setId("0781457");
		employee.setFirstName("Paolo");
		employee.setLastName("Neri");
		employee.setBirthDate(LocalDate.parse("1983-12-11", DateTimeFormatter.ISO_LOCAL_DATE));
		employee.setWorkshift(Workshift.POMERIGGIO);
		employee.setSalary(3000.0);
		employee.setEmail("paoloneri@mail.it");
		employee.setRole(Role.Autista);
		employee.setAddress("Corso Tukory 26");
		employee.setSalary(2500.0);
		employee.setStatus(StatusEmployee.AVAILABLE);
		employee.setPassword("paoloneri");

		vehicle.setId("1234");
		vehicle.setBrand("Fiat");
		vehicle.setPlacesForDisable(1);
		vehicle.setPlate("AF124CB");
		vehicle.setSeats(50);
		vehicle.setStandingPlaces(25);
		vehicle.setStatus(StatusVehicle.AVAILABLE);

		employees = db.getAllEmployeesArray();
		it = employees.iterator();
		while(it.hasNext()) {
			employee2 = it.next();
			System.out.println(employee2.toString() + "\n");
			absenceIntervals = db.getAbsenceInterval(employee2);
			it2 = absenceIntervals.iterator();
			while(it2.hasNext()) {
				abs = it2.next();
				System.out.println(abs.toString() + "\n");
			}
		}

		try {
			db.insertAbsenceStartDay(employee);
		} catch (SQLException exc) {
			exc.printStackTrace();
		}

		employees = db.getAllEmployeesArray();
		it = employees.iterator();
		while(it.hasNext()) {
			employee2 = it.next();
			System.out.println(employee2.toString() + "\n");
			absenceIntervals = db.getAbsenceInterval(employee2);
			it2 = absenceIntervals.iterator();
			while(it2.hasNext()) {
				abs = it2.next();
				System.out.println(abs.toString() + "\n");
			}
		}

		try {
			db.insertAbsenceEndDay(employee);
		} catch (SQLException exc) {
			exc.printStackTrace();
		}

		employees = db.getAllEmployeesArray();
		it = employees.iterator();
		while(it.hasNext()) {
			employee2 = it.next();
			System.out.println(employee2.toString() + "\n");
			absenceIntervals = db.getAbsenceInterval(employee2);
			it2 = absenceIntervals.iterator();
			while(it2.hasNext()) {
				abs = it2.next();
				System.out.println(abs.toString() + "\n");
			}
		}

		vehicles = db.getAllVehiclesArray();
		it3 = vehicles.iterator();
		while(it3.hasNext()) {
			vehicle2 = it3.next();
			System.out.println(vehicle2.toString()+ "\n");
			brokenIntervals = db.getBrokenInterval(vehicle2);
			it4 = brokenIntervals.iterator();
			while(it4.hasNext()) {
				bro = it4.next();
				System.out.println(bro.toString()+ "\n");
			}
		}

		try {
			db.insertBrokenStartDay(vehicle);
		} catch (SQLException exc) {
			exc.printStackTrace();
		}

		vehicles = db.getAllVehiclesArray();
		it3 = vehicles.iterator();
		while(it3.hasNext()) {
			vehicle2 = it3.next();
			System.out.println(vehicle2.toString()+ "\n");
			brokenIntervals = db.getBrokenInterval(vehicle2);
			it4 = brokenIntervals.iterator();
			while(it4.hasNext()) {
				bro = it4.next();
				System.out.println(bro.toString()+ "\n");
			}
		}

		try {
			db.insertBrokenEndDay(vehicle);
		} catch (SQLException exc) {
			exc.printStackTrace();
		}

		vehicles = db.getAllVehiclesArray();
		it3 = vehicles.iterator();
		while(it3.hasNext()) {
			vehicle2 = it3.next();
			System.out.println(vehicle2.toString()+ "\n");
			brokenIntervals = db.getBrokenInterval(vehicle2);
			it4 = brokenIntervals.iterator();
			while(it4.hasNext()) {
				bro = it4.next();
				System.out.println(bro.toString()+ "\n");
			}
		}
		/*if(db.getAllEmployees() == null) {
			System.out.println("No Data");
		}*/

//		Vehicle v = new Vehicle();
//
//		v.setId("12345");
//		int result = db.removeVehicle(v);
//		System.out.println(result);

//		Line l = new Line();
//		l.setName("101");
//		int result = db.removeLine(l);
//		System.out.println(result);

//		ArrayList<Location> locations = db.getAllFreeLocations();
//		Iterator<Location> it = locations.iterator();
//		while(it.hasNext()) {
//			Location l = it.next();
//			System.out.println(l.getId_Location()+" "+ l.getId_Vehicle());
//		}
	}
}
