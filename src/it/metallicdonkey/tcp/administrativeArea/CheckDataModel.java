package it.metallicdonkey.tcp.administrativeArea;

import it.metallicdonkey.tcp.HRArea.EmployeeDataModel;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.Line;
import it.metallicdonkey.tcp.vehicleArea.VehicleDataModel;
import javafx.beans.property.SimpleStringProperty;

public class CheckDataModel {
	private final SimpleStringProperty employee;
	private final SimpleStringProperty vehicle;
	private final SimpleStringProperty line;
	private EmployeeDataModel e;
	private LineDataModel l;
	private VehicleDataModel v;
	// Da aggiungere veicolo
	
	public CheckDataModel(EmployeeDataModel impiegato, VehicleDataModel mezzo, LineDataModel linea) {
		this.e = impiegato;
		this.v = mezzo;
		this.l = linea;
		this.employee = new SimpleStringProperty(e.getNomeECognome());
		this.vehicle = new SimpleStringProperty(v.getId() + " - " + v.getSeats() + " posti");
		this.line = new SimpleStringProperty(l.getName() + " - " + l.getStartTerminal() + " - " + l.getEndTerminal());
	}
	public String getEmployee() {
		return this.employee.get();
	}
	public String getVehicle() {
		return this.vehicle.get();
	}
	public String getLine() {
		return this.line.get();
	}
	public EmployeeDataModel getEmployeeModel() {
		return e;
	}
	public void setEmployeeModel(EmployeeDataModel e) {
		this.e = e;
	}
	public LineDataModel getLineModel() {
		return l;
	}
	public void setLineModel(LineDataModel l) {
		this.l = l;
	}
	public VehicleDataModel getVehicleModel() {
		return v;
	}
	public void setVehicleModel(VehicleDataModel v) {
		this.v = v;
	}
}
