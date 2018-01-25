package it.metallicdonkey.tcp.depositManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperDeposit;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.Location;
import it.metallicdonkey.tcp.models.StatusVehicle;
import it.metallicdonkey.tcp.models.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EntryVehicleCtrl {
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
	private void submitVehicle() throws SQLException {
		if(vehicleField.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Avviso");
			alert.setHeaderText("Il campo è vuoto!");
			alert.setContentText("Inserisci una matricola e ritenta.");
			alert.showAndWait();
			return;
		}
		ArrayList<Location> al = DBHelperDeposit.getInstance().getAllFreeLocations();
		
		System.out.println("al");

		if(al.isEmpty()) {
			Alert a = new Alert(AlertType.WARNING);
			a.initOwner(mainApp.getPrimaryStage());
			a.setTitle("Avviso");
			a.setHeaderText("Il deposito è pieno");
			a.setContentText("Non è possibile inserire il mezzo nel deposito");
			a.showAndWait();
			return;
		}
		
		
		Vehicle v = null;
		ArrayList<Vehicle> av = DBHelperVehicle.getInstance().getAllVehiclesArray();
		
		System.out.println(av);
		for(int i=0; i<av.size(); i++) {
			if(av.get(i).getId().equals(vehicleField.getText()))
				v = av.get(i);
		}
		
		if(v==null) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Avviso");
	    alert.setHeaderText("Inserimento fallito!");
	    alert.setContentText("Il veicolo con matricola " + vehicleField.getText() + " non esiste.");
	    alert.showAndWait();
	    return;
		}
		
		if(v.getStatus() == StatusVehicle.AVAILABLE) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Avviso");
	    alert.setHeaderText("Inserimento fallito!");
	    alert.setContentText("Il veicolo con matricola " + vehicleField.getText() + " è già in deposito.");
	    alert.showAndWait();
	    return;
		}
		
		if(v.getStatus() == StatusVehicle.BROKEN) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Avviso");
	    alert.setHeaderText("Inserimento fallito!");
	    alert.setContentText("Il veicolo con matricola " + vehicleField.getText() + " è guasto. Si prega di segnalare il termine di guasto.");
	    alert.showAndWait();
	    return;
		}
		
		v.setStatus(StatusVehicle.AVAILABLE);
		/*System.out.println("Brand: "+v.getBrand());
		DBHelperVehicle.getInstance().removeVehicle(v);
		System.out.println("Inserting "+v);
		DBHelperVehicle.getInstance().insertVehicle(v);*/
		// Save modifications into db
		DBHelperVehicle.getInstance().updateVehicle(v);
		int l = DBHelperDeposit.getInstance().setLocation(v);
		if(l==-1) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Avviso");
	    alert.setHeaderText("Inserimento fallito!");
	    alert.setContentText("Il deposito è pieno");
	    alert.showAndWait();
	    return;
		}
		String result = "L'ingresso del veicolo " + vehicleField.getText() + " è avvenuto con successo. La postazione assegnata è: " + l;
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
