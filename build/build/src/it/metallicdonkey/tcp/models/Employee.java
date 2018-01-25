/**
 *
 */
package it.metallicdonkey.tcp.models;

import java.time.LocalDate;
import java.util.ArrayList;

import it.metallicdonkey.tcp.login.Role;

/**
 * @author crist
 *
 */

public class Employee {
	private String id;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String email;
	private String address;
	private double Salary;
	private StatusEmployee status;
	private Role role;
	private Workshift workshift;
	private String password;
	private ArrayList<AbsenceInterval> absenceIntervals;
	private ArrayList<Payment> payments;
	public ArrayList<Payment> getPayments() {
		return payments;
	}
	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}
	public Workshift getWorkshift() {
		return workshift;
	}
	public void setWorkshift(Workshift workshift) {
		this.workshift = workshift;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getSalary() {
		return Salary;
	}
	public void setSalary(double salary) {
		Salary = salary;
	}
	public StatusEmployee getStatus() {
		return status;
	}
	public void setStatus(StatusEmployee status) {
		this.status = status;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String toString() {
		return "Matricola: " + id + "\nNome: " + firstName + "\nCognome: " + lastName +
			"\nStato: " + status;
	}
	public ArrayList<AbsenceInterval> getAbsenceIntervals() {
		return absenceIntervals;
	}
	public void setAbsenceIntervals(ArrayList<AbsenceInterval> absenceIntervals) {
		this.absenceIntervals = absenceIntervals;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}