package it.metallicdonkey.tcp.vehiclesManagement;

import java.io.IOException;
import java.sql.SQLException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.StatusVehicle;
import it.metallicdonkey.tcp.models.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ChangeVehicleCtrl extends AddVehicleCtrl{
	private App mainApp;

	@FXML
	private TextField matricola;
	
	@FXML
	private TextField postiASedere;
	
	@FXML
	private TextField postiDisabili;
	
	@FXML
	private TextField targa;
	
	@FXML
	private TextField postiInPiedi;
	
	@FXML
	private Button inviaButton;

	@FXML
	private TextField modello;
	
	private VehicleDataModel vehicle;

	@FXML
	private void initialize() {
		this.vehicle = ListSearchVehicleCtrl.selectedVehicle;
		this.matricola.setText(this.vehicle.getId());
		this.postiASedere.setText(this.vehicle.getSeats());
		this.postiDisabili.setText(this.vehicle.getHSeats());
		this.postiInPiedi.setText(this.vehicle.getSSeats());
		this.modello.setText("Modello");
		// TODO: Aggiugnere targa in VehicleDataModel
		this.matricola.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent; -fx-background-color: #F4F4F4");
		this.matricola.setEditable(false);
		this.postiASedere.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent;-fx-background-color: #F4F4F4");
		this.postiASedere.setEditable(false);
		this.postiDisabili.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent;-fx-background-color: #F4F4F4");
		this.postiDisabili.setEditable(false);
		this.postiInPiedi.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent;-fx-background-color: #F4F4F4");
		this.postiInPiedi.setEditable(false);
		this.targa.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent;-fx-background-color: #F4F4F4");
		this.targa.setEditable(false);
		this.modello.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent;-fx-background-color: #F4F4F4");
		this.modello.setEditable(false);
		
	}
	
	@FXML
	private void change() {
		this.matricola.setStyle(null);
		this.matricola.setEditable(true);
		this.postiASedere.setStyle(null);
		this.postiASedere.setEditable(true);
		this.postiDisabili.setStyle(null);
		this.postiDisabili.setEditable(true);
		this.postiInPiedi.setStyle(null);
		this.postiInPiedi.setEditable(true);
		this.targa.setStyle(null);
		this.targa.setEditable(true);
		this.modello.setStyle(null);
		this.modello.setEditable(true);
		
		if(this.inviaButton.getText().equals("Invia")) {
			submitVehicle();
		}
		
		this.inviaButton.setText("Invia");
	}

	@FXML
	public void submitVehicle() {
		
		Alert error = check();
		if (error != null) {
			error.showAndWait();
		} else {
			Vehicle v = getNewVehicle();
			try {
				DBHelperVehicle.getInstance().removeVehicle(v);
				DBHelperVehicle.getInstance().insertVehicle(v);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String result = "Il veicolo " + v.getId() + " é stato inserito con successo";
			System.out.println(result);
		}
	}
	
	private Vehicle getNewVehicle() {
		Vehicle v = new Vehicle();
		v.setBrand(modello.getText());
		v.setId(matricola.getText());
		v.setPlacesForDisable(Integer.parseInt(postiDisabili.getText()));
		v.setPlate(targa.getText());
		v.setSeats(Integer.parseInt(postiASedere.getText()));
		v.setStandingPlaces(Integer.parseInt(postiInPiedi.getText()));
		v.setStatus(StatusVehicle.AVAILABLE);
		return v;
	}
	
	private Alert check() {
		Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Avviso");
	    alert.setHeaderText("Inserimento fallito!");
	    
		// Check matricola
	    if (matricola.getText().equals("")) {
	    	alert.setContentText("Inserisci una matricola");
	    	return alert;
	    }
	    // Check targa
	    if (targa.getText().equals("")) {
	    	alert.setContentText("Inserisci una targa");
	    	return alert;
	    }
	    // Check postiASedere
	    if (postiASedere.getText().equals("")) {
	    	alert.setContentText("Inserisci il numero di posti a sedere");
	    	return alert;
	    }
	    // Check postiDisabili
	    if (postiDisabili.getText().equals("")) {
	    	alert.setContentText("Inserisci il numero di posti per disabili");
	    	return alert;
	    }
	    // Check postiInPiedi
	    if (postiInPiedi.getText().equals("")) {
	    	alert.setContentText("Inserisci il numero di posti in piedi");
	    	return alert;
	    }
	    
	    // Check if values are numeric or not
	    else {
	    	int numPostiASedere = -1;
	    	int numPostiDisabili = -1;
	    	int numPostiInPiedi = -1;
	    	try {
	    		numPostiASedere = Integer.parseInt(postiASedere.getText());
	    		numPostiDisabili = Integer.parseInt(postiDisabili.getText());
	    		numPostiInPiedi = Integer.parseInt(postiInPiedi.getText());
	    	}
	    	catch(NumberFormatException e) {
	    		alert.setContentText("Inserisci un valore numerico nei campi del numero di posti");
	    		return alert;
	    	}
	    }
	    
	    // Data is ok
	    return null;
	}

	@FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }

	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}

	public void setModel(VehicleDataModel v) {
		this.vehicle = v;
	}
}
