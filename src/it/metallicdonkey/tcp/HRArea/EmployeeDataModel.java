package it.metallicdonkey.tcp.HRArea;


import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeDataModel {
	private final SimpleStringProperty id;
	private final SimpleStringProperty nome;
	private final SimpleStringProperty cognome;
	private final SimpleStringProperty role;
	private final SimpleDoubleProperty salary;
	
	public EmployeeDataModel(String id, String nome, String cognome) {
		this.id = new SimpleStringProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
		this.role = null;
		this.salary = null;
	}
	
	public EmployeeDataModel(String id, String role, double salary) {
		this.id = new SimpleStringProperty(id);
		this.role = new SimpleStringProperty(role);
		this.salary = new SimpleDoubleProperty(salary);
		this.nome = null;
		this.cognome = null;
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
	
	public String getRole() {
		return this.role.get();
	}
	
	public double getSalary() {
		return this.salary.get();
	}
}
