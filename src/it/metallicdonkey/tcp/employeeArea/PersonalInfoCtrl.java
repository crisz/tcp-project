package it.metallicdonkey.tcp.employeeArea;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PersonalInfoCtrl {
	@FXML
	private Label matricola;
	@FXML
	private Label nomecognome;
	@FXML
	private Label email;
	@FXML
	private Label datanascita;
	@FXML
	private Label ruolo;
	@FXML
	private Label stipendiolordo;


	private App mainApp;
  @FXML
  private void initialize() {
  	this.matricola.setText(Session.employee.getId());
  	this.nomecognome.setText(Session.employee.getFirstName() + " " + Session.employee.getLastName());
  	this.email.setText(Session.employee.getEmail());
  	this.datanascita.setText(Session.employee.getBirthDate().toString());
  	this.ruolo.setText(Session.employee.getRole().name());
  	this.stipendiolordo.setText(String.valueOf(Session.employee.getSalary()));
  	
  }
  
  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
