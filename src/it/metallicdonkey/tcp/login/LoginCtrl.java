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

import com.mysql.jdbc.Driver;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.administrativeArea.AdministrativeAreaCtrl;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.Status;
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
  	if(matricola.getText().equals("pippo")) {
  		
  		/*
  		 * A questo punto già sappiamo chi è l'impiegato, o in alternativa si fa una nuova query
  		 */
  		Session.employee = new Employee();
  		Employee e = Session.employee;
  		e.setId("0641265");
  		e.setAdress("Via dei pini, 41");
  		e.setBirthDate(LocalDate.of(1955, 10, 1));
  		e.setEmail("pippo@gmail.com");
  		e.setFirstName("Pippo");
  		e.setLastName("Vattelappesca");
  		e.setSalary(2501.24);
  		e.setStatus(Status.AVAILABLE);
  		e.setRole("Autista");
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(App.class.getResource("employeeArea/EmployeeAreaScreen.fxml"));
      AnchorPane adminScreen = (AnchorPane) loader.load();
  		
  		Scene scene = new Scene(adminScreen);
      mainApp.getPrimaryStage().setScene(scene);
      AdministrativeAreaCtrl adminCtrl = loader.getController();
      adminCtrl.setMainApp(mainApp);
  	} 
  	else if (matricola.getText().equals("bob")) {
  		Session.employee = new Employee();
  		Employee e = Session.employee;
  		e.setId("0641265");
  		e.setAdress("Via dei pini, 41");
  		e.setBirthDate(LocalDate.of(1955, 10, 1));
  		e.setEmail("pippo@gmail.com");
  		e.setFirstName("Bob");
  		e.setLastName("Vattelappesca");
  		e.setSalary(2501.24);
  		e.setStatus(Status.AVAILABLE);
  		e.setRole("Addetto ai mezzi");
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(App.class.getResource("vehicleArea/VehicleAreaScreen.fxml"));
      AnchorPane vehicleScreen = (AnchorPane) loader.load();
  		
  		Scene scene = new Scene(vehicleScreen);
      mainApp.getPrimaryStage().setScene(scene);
      VehicleAreaCtrl vehicleCtrl = loader.getController();
      vehicleCtrl.setMainApp(mainApp);
  	} else {
  		Alert alert = new Alert(AlertType.WARNING);
      alert.initOwner(mainApp.getPrimaryStage());
      alert.setTitle("Login information");
      alert.setHeaderText("Login fallito!");
      alert.setContentText("Controlla i dati inseriti e riprova.");
      alert.showAndWait();
  	}
  }

  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
	
}
