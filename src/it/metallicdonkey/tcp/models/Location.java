package it.metallicdonkey.tcp.models;

public class Location {
	private String id_Vehicle;
	private int id_Location;
	public Location(String vehicle, int location) {
		this.setId_Vehicle(vehicle);
		this.setId_Location(location);
	}
	public String getId_Vehicle() {
		return id_Vehicle;
	}
	public void setId_Vehicle(String id_Vehicle) {
		this.id_Vehicle = id_Vehicle;
	}
	public int getId_Location() {
		return id_Location;
	}
	public void setId_Location(int id_Location) {
		this.id_Location = id_Location;
	}
}
