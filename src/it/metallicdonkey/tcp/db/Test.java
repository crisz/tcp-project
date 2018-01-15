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

		employee.setId("0641144");
		employee.setFirstName("Mario");
		employee.setLastName("Rossi");
		employee.setBirthDate(LocalDate.parse("1961-02-15", DateTimeFormatter.ISO_LOCAL_DATE));
		employee.setWorkshift(Workshift.MATTINA);
		employee.setSalary(3000.0);
		employee.setEmail("mariorossi@mail.it");
		employee.setRole(Role.Autista);

		vehicle.setId("1235");
		vehicle.setBrand("Fiat");
		vehicle.setPlacesForDisable(1);
		vehicle.setPlate("AF124CB");
		vehicle.setSeats(50);
		vehicle.setStandingPlaces(25);
		vehicle.setStatus(StatusVehicle.AVAILABLE);

		db.insertEmployee(employee);
		db.insertVehicle(vehicle);

		db.insertAbsenceStartDay(employee);
		employees = db.getAllEmployeesArray();
		it = employees.iterator();
		while(it.hasNext()) {
			employee2 = it.next();
			System.out.println(employee2.toString());
			absenceIntervals = db.getAbsenceInterval(employee2);
			it2 = absenceIntervals.iterator();
			while(it2.hasNext()) {
				abs = it2.next();
				System.out.println(abs.toString());
			}
		}

		db.insertAbsenceEndDay(employee);
		employees = db.getAllEmployeesArray();
		it = employees.iterator();
		while(it.hasNext()) {
			employee2 = it.next();
			System.out.println(employee2.toString());
			absenceIntervals = db.getAbsenceInterval(employee2);
			it2 = absenceIntervals.iterator();
			while(it2.hasNext()) {
				abs = it2.next();
				System.out.println(abs.toString());
			}
		}

		db.insertBrokenStartDay(vehicle);
		vehicles = db.getAllVehiclesArray();
		it3 = vehicles.iterator();
		while(it3.hasNext()) {
			vehicle2 = it3.next();
			System.out.println(vehicle2.toString());
			brokenIntervals = db.getBrokenInterval(vehicle2);
			it4 = brokenIntervals.iterator();
			while(it4.hasNext()) {
				bro = it4.next();
				System.out.println(bro.toString());
			}
		}
		db.insertBrokenEndDay(vehicle);
		vehicles = db.getAllVehiclesArray();
		it3 = vehicles.iterator();
		while(it3.hasNext()) {
			vehicle2 = it3.next();
			System.out.println(vehicle2.toString());
			brokenIntervals = db.getBrokenInterval(vehicle2);
			it4 = brokenIntervals.iterator();
			while(it4.hasNext()) {
				bro = it4.next();
				System.out.println(bro.toString());
			}
		}
		/*if(db.getAllEmployees() == null) {
			System.out.println("No Data");
		}*/

	}
}
