package it.metallicdonkey.tcp.employeesManagement;

import java.util.ArrayList;

import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.models.AbsenceInterval;
import it.metallicdonkey.tcp.models.Employee;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeDataModel {
	private Employee employee;
	private final SimpleStringProperty id;
	private final SimpleStringProperty nome;
	private final SimpleStringProperty cognome;
	private final SimpleStringProperty role;
	private final SimpleDoubleProperty salary;

	private EmployeeDataModel(String id, String nome, String cognome) {
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

	public EmployeeDataModel(Employee e) {
		this.id = new SimpleStringProperty(e.getId());
		this.nome = new SimpleStringProperty(e.getFirstName());
		this.cognome = new SimpleStringProperty(e.getLastName());
		this.role = new SimpleStringProperty(e.getRole().name());
		this.salary = new SimpleDoubleProperty(e.getSalary());
		this.setEmployee(e);
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getRole() {
		return this.role.get().replaceAll("_", " ");
	}

	public double getSalary() {
		int absenceDays = 0;
		ArrayList<AbsenceInterval> al = DBHelperEmployee.getInstance().getAbsenceInterval(this.getEmployee());
		for(int i=0; i<al.size(); i++)
			absenceDays += al.get(i).getDays();
		double s = this.salary.get() * (30 - absenceDays) / 30;
		return Math.floor(s*100)/100;
	}
	
	public double getNetSalary() {
		return 0.66*getSalary();
	}
}
