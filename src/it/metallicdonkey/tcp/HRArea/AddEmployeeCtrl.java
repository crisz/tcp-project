package it.metallicdonkey.tcp.HRArea;

import java.awt.Button;
import java.awt.TextField;
import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddEmployeeCtrl {
	private App mainApp;
	@FXML
	private TextField matricola;
	@FXML
	private TextField nome;
	@FXML
	private TextField cognome;
	@FXML
	private TextField password;
	@FXML
	private TextField datanascita;
	@FXML
	private TextField email;
	@FXML
	private TextField indirizzo;
	@FXML
	private TextField ruolo;
	@FXML
	private TextField turno;
	@FXML
	private Button inviaButton;
	private EmployeeDataModel employee;
	@FXML
	public void initialize() {
	}
	@FXML
	private void submitEmployee() {
		Alert error = check();
		if (error != null) {
			error.showAndWait();
		} else {
			/*
			 * TODO: Sostituire questo codice con l'inserimento dell'impiegato nel database.
			 * Completare l'operazione con un Alert di successo o fallimento.
			 */
			String result = "L'impiegato con matricola " + matricola.getText() + " é stato inserito con successo";
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
	    // Check nome
	    if (nome.getText().equals("")) {
	    	alert.setContentText("Inserisci un nome");
	    	return alert;
	    }
	    // Check cognome
	    if (cognome.getText().equals("")) {
	    	alert.setContentText("Inserisci un cognome");
	    	return alert;
	    }
	    // Check email
	    if (email.getText().equals("")) {
	    	alert.setContentText("Inserisci un indirizzo e-mail");
	    	return alert;
	    }
	    // Check password
	    if (password.getText().equals("")) {
	    	alert.setContentText("Inserisci una password");
	    	return alert;
	    }
	    // Check indirizzo
	    if (indirizzo.getText().equals("")) {
	    	alert.setContentText("Inserisci un indirizzo di residenza");
	    	return alert;
	    }
	    // Check datanascita
	    if (datanascita.getText().equals("")) {
	    	alert.setContentText("Inserisci una data di nascita");
	    	return alert;
	    }
	    // Check ruolo
	    if (ruolo.getText().equals("")) {
	    	alert.setContentText("Inserisci un ruolo");
	    	return alert;
	    }
	    // Check turno
	    if (turno.getText().equals("")) {
	    	alert.setContentText("Inserisci un turno");
	    	return alert;
	    }
	    // Check if values are correct;
	    else {
/*	    	int numPostiASedere = -1;
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
*/
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
	public void setModel(EmployeeDataModel employee) {
		this.employee = employee;
	}
}
