package it.metallicdonkey.tcp.models;

import java.util.ArrayList;

public class Line {
	private String name;
	private Stop startTerminal;
	private Stop endTerminal;
	private ArrayList<Stop> goingStops;
	private ArrayList<Stop> returnStops;
	private int priority;
	public Line(String name, Stop startTerminal, Stop endTerminal, ArrayList<Stop> goingStops, ArrayList<Stop> returnStops) {
		this.setName(name);
		this.setGoingStops(goingStops);
		this.setStartTerminal(startTerminal);
		this.setEndTerminal(endTerminal);
		this.setReturnStops(returnStops);
	}
	public Line() {
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
	public ArrayList<Stop> getGoingStops() {
		return goingStops;
	}
	public void setGoingStops(ArrayList<Stop> goingStops) {
		this.goingStops = goingStops;
	}
	public ArrayList<Stop> getReturnStops() {
		return returnStops;
	}
	public void setReturnStops(ArrayList<Stop> returnStops) {
		this.returnStops = returnStops;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public ArrayList<Stop> getAllStops(){
		ArrayList<Stop> stops = new ArrayList<>();
		stops.add(getStartTerminal());
		stops.addAll(this.goingStops);
		stops.add(this.getEndTerminal());
		stops.addAll(this.returnStops);
		return stops;
	}
	
	public String toString() {
		return name + " " + getAllStops();
	}
}