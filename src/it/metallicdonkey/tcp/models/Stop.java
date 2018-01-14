package it.metallicdonkey.tcp.models;

public class Stop {
	private String adress;

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public Stop(String adress) {
		this.setAdress(adress);
	}
	
	public Stop() {
		this(null);
	}
	
	public String toString() {
		return adress;
	}
}
