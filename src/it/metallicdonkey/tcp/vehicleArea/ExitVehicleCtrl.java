package it.metallicdonkey.tcp.vehicleArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
		String result = "L'uscita del veicolo " + vehicleField.getText() + " è avvenuto con successo.";
		resultLabel.setText(result);
		/*
		 * Sostituire con db
		 */
	}

	@FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }

	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}
}
