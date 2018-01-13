package it.metallicdonkey.tcp.employeeArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PersonalInfoCtrl {
	@FXML
	private Label matricola;
	@FXML
	private Label password;
	@FXML
	private Label nome;
	@FXML
	private Label cognome;
	@FXML
	private Label email;
	@FXML
	private Label residenza;
	@FXML
	private Label datanascita;
	@FXML
	private Label ruolo;
	@FXML
	private Label turno;
	@FXML
	private Label stipendiolordo;


	private App mainApp;
  @FXML
  private void initialize() {
  	this.matricola.setText(Session.employee.getId());
  	this.nome.setText(Session.employee.getFirstName());
  	this.cognome.setText(Session.employee.getLastName());
  	this.email.setText(Session.employee.getEmail());
  	this.residenza.setText(Session.employee.getAddress());
  	this.datanascita.setText(Session.employee.getBirthDate().toString());
  	this.ruolo.setText(Session.employee.getRole().name());
  	this.turno.setText(Session.employee.getWorkshift().name());
  	this.stipendiolordo.setText(String.valueOf(Session.employee.getSalary()));
  }

  @FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }

  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
