package it.metallicdonkey.tcp.depositManagement;

import java.io.IOException;
import java.sql.SQLException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperDeposit;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.StatusVehicle;
import it.metallicdonkey.tcp.models.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ExitVehicleCtrl {
	private App mainApp;

	@FXML
	private TextField vehicleField;
	@FXML
	private Label resultLabel;

	@FXML
	private void initialize() {
		resultLabel.setText("");
	}

	@FXML
	private void submitVehicle() {
		if(vehicleField.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Avviso");
			alert.setHeaderText("Il campo è vuoto!");
			alert.setContentText("Inserisci una matricola e ritenta.");
			alert.showAndWait();
			return;
		}
		try {
			Vehicle v = DBHelperVehicle.getInstance().getVehicleById(vehicleField.getText().trim());
			if (v == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Uscita non riuscita");
				alert.setContentText("Il veicolo non esiste");
				alert.showAndWait();
				return;
			}
			if (v.getStatus().name().equals("ON_ACTION")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Uscita non riuscita");
				alert.setContentText("Il veicolo è in circolazione");
				alert.showAndWait();
				return;
			}
			if (v.getStatus().name().equals("BROKEN")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Uscita non riuscita");
				alert.setContentText("Il veicolo è in riparazione");
				alert.showAndWait();
				return;
			}
			else {	// If the vehicle exists
				// Free the location in the db
				DBHelperDeposit.getInstance().freeLocation(v);

				// update the status of the vehicle
				v.setStatus(StatusVehicle.ON_ACTION);
				DBHelperVehicle.getInstance().updateVehicle(v);
			}
		}
		catch(SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Avviso");
			alert.setHeaderText("Connessione non riuscita");
			alert.setContentText("Controlla la connessione e riprova");
			alert.showAndWait();
		}
		String result = "L'uscita del veicolo " + vehicleField.getText() + " è avvenuto con successo.";
		resultLabel.setText(result);
	}

	@FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }

	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}
}
