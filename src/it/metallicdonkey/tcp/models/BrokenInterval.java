package it.metallicdonkey.tcp.models;

import java.time.LocalDate;

public class BrokenInterval {
	private String id;
	private LocalDate startDay;
	private LocalDate endDay;
	private String idVehicle;
	public BrokenInterval(String id, LocalDate startDay, LocalDate endDay, String idVehicle) {
		this.setId(id);
		this.setIdVehicle(idVehicle);
		this.setStartDay(startDay);
		this.setEndDay(endDay);
	}
	public BrokenInterval() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDate getStartDay() {
		return startDay;
	}
	public void setStartDay(LocalDate startDay) {
		this.startDay = startDay;
	}
	public LocalDate getEndDay() {
		return endDay;
	}
	public void setEndDay(LocalDate endDay) {
		this.endDay = endDay;
	}
	public String getIdVehicle() {
		return idVehicle;
	}
	public void setIdVehicle(String idVehicle) {
		this.idVehicle = idVehicle;
	}
}