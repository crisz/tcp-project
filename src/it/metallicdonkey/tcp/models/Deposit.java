package it.metallicdonkey.tcp.models;

public class Deposit {
	private final int MAX = 2000;
	private Location[] location;
	private Deposit() {
		location = new Location[MAX];
	}
}
