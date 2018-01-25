package it.metallicdonkey.tcp.models;

public class Stop {
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Stop(String address) {
		this.setAddress(address);
	}

	public Stop() {
		this(null);
	}

	public String toString() {
		return address;
	}
}
