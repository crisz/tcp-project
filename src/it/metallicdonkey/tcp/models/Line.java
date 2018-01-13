package it.metallicdonkey.tcp.models;

public class Line {
	private String name;
	private Stop startTerminal;
	private Stop endTerminal;
	private Stop[] stops;
	public Line(String name, Stop[] stops) {
		this.setName(name);
		this.setStops(stops);
		this.setStartTerminal(stops[0]);
		this.setEndTerminal(stops[stops.length/2]);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Stop getStartTerminal() {
		return startTerminal;
	}
	public void setStartTerminal(Stop startTerminal) {
		this.startTerminal = startTerminal;
	}
	public Stop getEndTerminal() {
		return endTerminal;
	}
	public void setEndTerminal(Stop endTerminal) {
		this.endTerminal = endTerminal;
	}
	public Stop[] getStops() {
		return stops;
	}
	public void setStops(Stop[] stops) {
		this.stops = stops;
	}
}