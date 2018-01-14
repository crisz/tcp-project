package it.metallicdonkey.tcp.vehicleArea;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class VehicleDataModel {
  private final SimpleStringProperty id;
  private final SimpleIntegerProperty seats;
  private final SimpleIntegerProperty sSeats;
  private final SimpleIntegerProperty hSeats;
  private final SimpleStringProperty location;
  
  public VehicleDataModel(String id, int seats, int sSeats, int hSeats, String location) {
    this.id = new SimpleStringProperty(id);
    this.seats = new SimpleIntegerProperty(seats);
    this.hSeats = new SimpleIntegerProperty(hSeats);
    this.sSeats = new SimpleIntegerProperty(sSeats);
    this.location = new SimpleStringProperty(location);
  }
//  public VehicleDataModel(Vehicle v) {
//  	/** TODO: da implementare */
//  }


	
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
}
