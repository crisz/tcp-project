/**
 *
 */
package it.metallicdonkey.tcp.models;

public class Vehicle {
	private String id;
	private String plate;
	private int seats;
	private int standingPlaces;
	private int placesForDisable;
	private StatusVehicle status;
	
	public Vehicle() {
		
	}
	
	public Vehicle(String id, String plate, int seats, int standingPlaces, int placesForDisable, StatusVehicle status) {
		this.setId(id);
		this.setPlate(plate);
		this.setSeats(seats);
		this.setStandingPlaces(standingPlaces);
		this.setPlacesForDisable(placesForDisable);
	}
	public int getStandingPlaces() {
		return standingPlaces;
	}
	public void setStandingPlaces(int standingPlaces) {
		this.standingPlaces = standingPlaces;
	}
	public int getPlacesForDisable() {
		return placesForDisable;
	}
	public void setPlacesForDisable(int placesForDisable) {
		this.placesForDisable = placesForDisable;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public StatusVehicle getStatus() {
		return status;
	}
	public void setStatus(StatusVehicle status) {
		this.status = status;
	}
}