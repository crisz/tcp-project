package it.metallicdonkey.tcp.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

//import com.mysql.jdbc.Driver;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.administrativeArea.AdministrativeAreaCtrl;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.StatusEmployee;
import it.metallicdonkey.tcp.vehicleArea.VehicleAreaCtrl;

public class LoginCtrl {
	@FXML
	private TextField matricola;
	@FXML
	private TextField password;

	private App mainApp;

	public LoginCtrl() {

	}

  @FXML
  private void initialize() {
  }

  @FXML
  private void submitLogin() throws IOException {
  	/* Qua viene effettuato il login **/
		Session.employee = new Employee();
		Employee e = Session.employee;
		e.setId("0641265");
		e.setAddress("Via dei pini, 41");
		e.setBirthDate(LocalDate.of(1955, 10, 1));
		e.setEmail("pippo@gmail.com");
		e.setFirstName(matricola.getText());
		e.setLastName("Vattelappesca");
		e.setSalary(2501.24);
		e.setStatus(StatusEmployee.AVAILABLE);
		e.setRole(Role.Autista);
		Home home = Home.getHome(Role.Addetto_ai_mezzi);
		home.goHome(mainApp);

//  	if(false) {
//  		Alert alert = new Alert(AlertType.WARNING);
//      alert.initOwner(mainApp.getPrimaryStage());
//      alert.setTitle("Login information");
//      alert.setHeaderText("Login fallito!");
//      alert.setContentText("Controlla i dati inseriti e riprova.");
//      alert.showAndWait();
//  	}
  }

  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }

}
