package it.metallicdonkey.tcp.vehiclesManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperDeposit;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.StatusVehicle;
import it.metallicdonkey.tcp.models.Vehicle;
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
	private TextField modello;

	@FXML
	private Button inviaButton;


	private VehicleDataModel vehicle;

	@FXML
	private void initialize() {
	}

	@FXML
	public void submitVehicle() throws SQLException {
		ArrayList<String> ids = DBHelperVehicle.getInstance().getIds();
		Alert error = check(ids);
		if (error != null) {
			error.showAndWait();
		} else {
			try {
				Vehicle v = getNewVehicle();
				int l = DBHelperVehicle.getInstance().insertVehicle(v);
				String result = "Il veicolo " + v.getId() + " é stato inserito con successo";
				System.out.println(result);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento avvenuto con successo!");
				alert.setContentText("Il veicolo con matricola " + v.getId() + " è stato aggiunto all'elenco dei veicoli.\n La sua postazione in deposito è: "+l);
				alert.showAndWait();
				matricola.setText("");
				postiASedere.setText("");
				postiDisabili.setText("");
				postiInPiedi.setText("");
				targa.setText("");
				modello.setText("");
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(null);
	            alert.setTitle("Connection Information");
	            alert.setHeaderText("Connessione Non Disponibile");
	            alert.setContentText("Controlla la connessione e riprova.");
	            alert.showAndWait();
				e.printStackTrace();
			}
		}
	}

	private Vehicle getNewVehicle() throws SQLException {
		Vehicle v = new Vehicle();
		v.setBrand(modello.getText());
		if(matricola.getText().equals("")) {
			v.setId(generateId());
		}else {
			v.setId(matricola.getText());
		}
		v.setPlacesForDisable(Integer.parseInt(postiDisabili.getText()));
		v.setPlate(targa.getText());
		v.setSeats(Integer.parseInt(postiASedere.getText()));
		v.setStandingPlaces(Integer.parseInt(postiInPiedi.getText()));
		v.setStatus(StatusVehicle.AVAILABLE);
		return v;
	}

	private Alert check(ArrayList<String> ids) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		//		// Check matricola
		//	    if (matricola.getText().equals("")) {
		//	    	alert.setContentText("Inserisci una matricola");
		//	    	return alert;
		//	    }

		if(ids.contains(matricola.getText())) {
			alert.setContentText("La matricola inserita non è disponibile");
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
		// Check idMatricola < 7 char
		if (matricola.getText().length() > 7) {
			alert.setContentText("La matricola non deve superare i 7 caratteri");
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

	private String generateId() throws SQLException {
		ArrayList<String> allIds = DBHelperVehicle.getInstance().getIds();
		String id = "";
		Random r = new Random();
		boolean unique = false;
		while(!unique) {
			for(int i = 0; i < 4; i++) {
				id = id + r.nextInt(10);
			}
			Iterator<String> iterator = allIds.iterator();
			unique = isUnique(id, iterator);
		}
		return id;
	}
	private boolean isUnique(String string, Iterator<String> iterator) {
		while(iterator.hasNext()) {
			if(string.equals(iterator.next())) {
				return false;
			}
		}
		return true;
	}

}
