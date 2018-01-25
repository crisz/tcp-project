package it.metallicdonkey.tcp.models;

import java.time.LocalDate;

public class AbsenceInterval {
	private String id;
	private LocalDate startDay;
	private LocalDate endDay;
	private String idEmployee;
	public AbsenceInterval(String id, LocalDate startDay, LocalDate endDay, String idEmployee) {
		this.setId(id);
		this.setIdEmployee(idEmployee);
		this.setStartDay(startDay);
		this.setEndDay(endDay);
	}
	public AbsenceInterval() {
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
	public String getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}
	public int getDays() {
		return Math.abs(this.getEndDay().getDayOfYear() - this.startDay.getDayOfYear());
	}
}
