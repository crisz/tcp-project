package it.metallicdonkey.tcp.vehicleArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddVehicleCtrl {
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

	private VehicleDataModel vehicle;

	@FXML
	private void initialize() {
	}

	@FXML
	private void submitVehicle() {
		
		Alert error = check();
		if (error != null) {
			error.showAndWait();
		} else {
			/*
			 * TODO: Sostituire questo codice con l'inserimento del veicolo nel database.
			 * Completare l'operazione con un Alert di successo o fallimento. 
			 */
			String result = "Il veicolo " + matricola.getText() + " é stato inserito con successo";
			System.out.println(result);
		}
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

	public void setModel(VehicleDataModel line) {
		this.vehicle = line;
	}
}
