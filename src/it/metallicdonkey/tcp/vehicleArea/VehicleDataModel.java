package it.metallicdonkey.tcp.vehicleArea;

import it.metallicdonkey.tcp.models.Vehicle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class VehicleDataModel {
  private final SimpleStringProperty id;
  private final SimpleIntegerProperty seats;
  private final SimpleIntegerProperty sSeats;
  private final SimpleIntegerProperty hSeats;
  private final SimpleStringProperty status;
  private Vehicle vehicle;
  private SimpleStringProperty location;
  
  private VehicleDataModel(String id, int seats, int sSeats, int hSeats, String status) {
    this.id = new SimpleStringProperty(id);
    this.seats = new SimpleIntegerProperty(seats);
    this.hSeats = new SimpleIntegerProperty(hSeats);
    this.sSeats = new SimpleIntegerProperty(sSeats);
    this.status = new SimpleStringProperty(status);
    this.location = null;
  }
  public VehicleDataModel(Vehicle v, String location) {
  	this(v.getId(), v.getSeats(), v.getStandingPlaces(), v.getPlacesForDisable(), v.getStatus().toString());
  	this.setVehicle(v);
  	this.location = new SimpleStringProperty(location);
  }

  

	public String getStatus() {
		return this.status.get();
	}
	
	public String getEdit() {
		return "edit";
	}
	
	public String getDelete() {
		return "delete";
	}

	public String getBroken() {
		return "broken";
	}


	public String getId() {
		return id.get();
	}



	public String getSeats() {
		return String.valueOf(seats.get());
	}



	public String getSSeats() {
		return String.valueOf(sSeats.get());
	}



	public String getHSeats() {
		return String.valueOf(hSeats.get());
	}



	public String getVLocation() {
		return location.get();
	}



	public void setVLocation(String string) {
		location.set(string);
		
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
