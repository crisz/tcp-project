package it.metallicdonkey.tcp.HRArea;


import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

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
	private DatePicker datanascita;
	@FXML
	private TextField stipendio;
	@FXML
	private TextField email;
	@FXML
	private TextField indirizzo;
	@FXML
	private Spinner<String> ruolo;
	@FXML
	private Button inviaButton;
	private EmployeeDataModel employee;
	@FXML
	public void initialize() {
		// Init entries for the role spinner
		String[] rolesEntries = {"Autista" ,"Addetto agli impiegati" , "Addetto ai mezzi" ,"Impiegato amministrativo" , "Impiegato"};
		ObservableList<String> roles = FXCollections.observableArrayList(rolesEntries);
		
		SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(roles);
		ruolo.setValueFactory(valueFactory);
		
		// Default value
		ruolo.getValueFactory().setValue(rolesEntries[0]);
		
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
	
	/*
	 * This method return the employee created with the values inserted by the user
	 * Make sure to call this methods after the check for the values
	 */
	
	private Employee getNewEmployee() {
		Employee e = new Employee();
		e.setFirstName(nome.getText());
		e.setLastName(cognome.getText());
		e.setBirthDate(datanascita.getValue());
		e.setAddress(indirizzo.getText());
		e.setId(matricola.getText());
		e.setSalary(Double.parseDouble(stipendio.getText()));
		e.setEmail(email.getText());
		e.setRole(Role.valueOf(this.ruolo.getValue().replace(" ", "_")));
		return e;
	}
	
	/*
	 * TODO ADD Maximum date selectable
	 */
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
	    if (datanascita.getValue() == null) {
	    	alert.setContentText("Inserisci una data di nascita");
	    	return alert;
	    }
	    // Check stipendio
	    if (stipendio.getText().equals("")) {
	    	alert.setContentText("Inserisci uno stipendio");
	    	return alert;
	    }
	    else {
	    	 //Check email pattern
	    	String regex = "^[\\w\\d\\.]+@[\\w\\.]+\\.\\w+$";
		    Pattern p = Pattern.compile(regex);
		    Matcher m = p.matcher(email.getText());
		    if(! m.matches()) {
		    	alert.setContentText("Inserisci un indirizzo email valido");
		    	return alert;
		    }
		    // Numberical check for stipendio
	    	try {
	    		double d = Double.parseDouble(stipendio.getText());
	    	}
	    	catch(NumberFormatException e) {
	    		alert.setContentText("Inserisci un valore numerico per lo stipendio");
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
	public void setModel(EmployeeDataModel employee) {
		this.employee = employee;
	}
}
