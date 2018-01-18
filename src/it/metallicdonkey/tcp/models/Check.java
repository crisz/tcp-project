package it.metallicdonkey.tcp.models;

import java.util.ArrayList;
import java.util.List;

public class Check {
	private ArrayList<Match> matchesList = new ArrayList<>();
	private Workshift workshift;
	public void addMatch(Match match) {
		matchesList.add(match);
	}
	public ArrayList<Match> getMatches() {
		return this.matchesList;
	}
	public void setWorkshift(Workshift workshift) {
		this.workshift = workshift;
	}
	public Workshift getWorkshift() {
		return this.workshift;
	}
}
