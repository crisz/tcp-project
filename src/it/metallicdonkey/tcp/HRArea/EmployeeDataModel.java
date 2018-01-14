package it.metallicdonkey.tcp.HRArea;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeDataModel {
	private final SimpleStringProperty nome;
	private final SimpleStringProperty cognome;
	public EmployeeDataModel(String nome, String cognome) {
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
	}
	
	public String getNomeECognome() {
		return this.nome.get()+" "+this.cognome.get();
		
	}
}
