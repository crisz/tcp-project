package it.metallicdonkey.tcp.models;

public class Match {
	private Employee employee;
	private Line line;
	private Vehicle vehicle;
	public Match(Employee employee, Line line, Vehicle vehicle) {
		this.setEmployee(employee);
		this.setLine(line);
		this.setVehicle(vehicle);
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}