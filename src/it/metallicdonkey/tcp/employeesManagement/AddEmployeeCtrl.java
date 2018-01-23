package it.metallicdonkey.tcp.employeesManagement;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.Workshift;
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
	private Spinner<String> turno;
	@FXML
	private Button inviaButton;
	private EmployeeDataModel employee;
	@FXML
	public void initialize() {
		// Init entries for the role spinner
		String[] rolesEntries = {"Autista" ,"Addetto agli impiegati" , "Addetto ai mezzi" ,"Impiegato amministrativo" , "Impiegato"};
		String[] workshiftEntries = {"Mattina", "Pomeriggio", "Sera"};
		ObservableList<String> roles = FXCollections.observableArrayList(rolesEntries);
		ObservableList<String> workshifts = FXCollections.observableArrayList(workshiftEntries);

		SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(roles);
		SpinnerValueFactory<String> valueFactory2 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(workshifts);


		ruolo.setValueFactory(valueFactory);
		turno.setValueFactory(valueFactory2);

		// Default value
		ruolo.getValueFactory().setValue(rolesEntries[0]);
		turno.getValueFactory().setValue(workshiftEntries[0]);

	}
	@FXML
	private void submitEmployee() {
		System.out.println(turno.getValue());
		Alert error = check();
		if (error != null) {
			error.showAndWait();
		} else {
			try {
				DBHelperEmployee.getInstance().insertEmployee(getNewEmployee());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento avvenuto con successo!");
				alert.setContentText("L'impiegato è stato aggiunto all'elenco degli impiegati");
				alert.showAndWait();

				matricola.setText("");
				nome.setText("");
				cognome.setText("");
				email.setText("");
				password.setText("");
				indirizzo.setText("");
				datanascita.setValue(LocalDate.of(1900, 1, 1));;
				stipendio.setText("");

			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento fallito!");
				alert.setContentText("Il numero di matricola inserito è già stato assegnato");
				alert.showAndWait();
				e.printStackTrace();
			}
			String result = "L'impiegato con matricola " + matricola.getText() + " é stato inserito con successo";
			System.out.println(result);
		}
	}

	/*
	 * This method return the employee created with the values inserted by the user
	 * Make sure to call this methods after the check for the values
	 */

	private Employee getNewEmployee() throws SQLException {
		Employee e = new Employee();
		e.setFirstName(nome.getText());
		e.setLastName(cognome.getText());
		e.setBirthDate(datanascita.getValue());
		e.setAddress(indirizzo.getText());
		if(matricola.getText().equals("")) {
			e.setId(generateId());
		}else {
			e.setId(matricola.getText());
		}
		e.setSalary(Double.parseDouble(stipendio.getText()));
		e.setEmail(email.getText());
		e.setRole(Role.valueOf(this.ruolo.getValue().replace(" ", "_")));
		e.setWorkshift(Workshift.valueOf(this.turno.getValue().toUpperCase()));
		e.setPassword(password.getText());
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
		// Check lunghezza matricola
		if (matricola.getText().length() > 7) {
			alert.setContentText("La matricola non deve essere più lunga di 7 caratteri");
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
			// Numerical check for stipendio
			try {
				double s = Double.parseDouble(stipendio.getText());
				if(s <= 0) throw new NumberFormatException();
			}
			catch(NumberFormatException e) {
				alert.setContentText("Inserisci un valore numerico positivo per lo stipendio");
				return alert;
			}
			// Numerical check for matricola
			if (!matricola.getText().equals("")) {
				try {
					int a = Integer.parseInt(matricola.getText());
					if(a < 0)	throw new NumberFormatException();
				}
				catch(NumberFormatException e) {
					alert.setContentText("Inserisci un valore numerico positivo per la matricola");
					return alert;
				}
			}
			// Alfabetical check for name
//			if( (Integer.parseInt(nome.getText())) | (Integer.parseInt(cognome.getText())) ) {
//				alert.setContentText("Nome e cognome non possono contenere solo numeri");
//				return alert;
//			}
			// TODO: String check for nome e cognome
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
	private String generateId() throws SQLException {
		ArrayList<String> allIds = DBHelperEmployee.getInstance().getIds();
		String id = "";
		Random r = new Random();
		boolean unique = false;
		while(!unique) {
			for(int i = 0; i < 7; i++) {
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
