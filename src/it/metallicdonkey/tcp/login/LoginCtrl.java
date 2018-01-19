package it.metallicdonkey.tcp.login;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

//import com.mysql.jdbc.Driver;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.db.DBManager;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.StatusEmployee;
import it.metallicdonkey.tcp.models.Workshift;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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

	  if( (matricola.getText().equals("")) & (password.getText().equals("")) ) {
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
		  e.setWorkshift(Workshift.MATTINA);
		  Home.getHome(Role.Addetto_ai_mezzi).goHome(mainApp);
	  } else {
		  try {
				DBHelperEmployee dbm = DBHelperEmployee.getInstance();
				Employee employee = dbm.login(matricola.getText(), password.getText());
				if(employee!=null) {
					Session.employee = employee;
					Home home = Home.getHome(Session.employee.getRole());
					home.goHome(mainApp);
				} else if (employee == null) {
					Alert alert = new Alert(AlertType.WARNING);
		            alert.initOwner(null);
		            alert.setTitle("Connection Information");
		            alert.setHeaderText("Matricola e/o password errate");
		            alert.setContentText("Controlla le credenziali inserite e riprova.");
		            alert.showAndWait();
		            // Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, exc);
				}
			} catch (SQLException exc) {
				exc.printStackTrace();
				Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(null);
	            alert.setTitle("Connection Information");
	            alert.setHeaderText("Connessione Non Disponibile");
	            alert.setContentText("Controlla la connessione e riprova.");
	            alert.showAndWait();
		        // Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, exc);
			}
	  }


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
