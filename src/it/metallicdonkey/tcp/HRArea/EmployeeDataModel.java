package it.metallicdonkey.tcp.HRArea;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeDataModel {
	private final SimpleStringProperty id;
	private final SimpleStringProperty nome;
	private final SimpleStringProperty cognome;
	public EmployeeDataModel(String id, String nome, String cognome) {
		this.id = new SimpleStringProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
	}

	public String getNomeECognome() {
		return this.nome.get()+" "+this.cognome.get();
	}
	public String getId() {
		return this.id.get();
	}
	public String getFirstName() {
		return this.nome.get();
	}
	public String getLastName() {
		return this.cognome.get();
	}
}
