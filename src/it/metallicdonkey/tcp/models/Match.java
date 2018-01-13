package it.metallicdonkey.tcp.models;

public class Match {
	private Employee employee;
	private Line line;
	private Vehicle vehicle;
	private Workshift workshift;
	public Match(Employee employee, Line line, Vehicle vehicle, Workshift workshift) {
		this.setEmployee(employee);
		this.setLine(line);
		this.setVehicle(vehicle);
		this.setWorkshift(workshift);
	}
	public Workshift getWorkshift() {
		return workshift;
	}
	public void setWorkshift(Workshift workshift) {
		this.workshift = workshift;
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