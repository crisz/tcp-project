package it.metallicdonkey.tcp.linesManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperLine;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.employeesManagement.HRAreaCtrl;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.Line;
import it.metallicdonkey.tcp.models.Stop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChangeLineCtrl extends AddLineCtrl {
	private App mainApp;

	@FXML
	private TextField name;

	@FXML
	ListView<Stop> stopsList;

	ObservableList<Stop> stops;

	@FXML
	TextField newStop;

	@FXML
	Button addStop;

	@FXML
	Button removeStop;

	@FXML
	private Button sendButton;

	@FXML
	private TextField priority;

	LineDataModel line;
	
	Line oldLine;

	@FXML
	private void initialize() throws SQLException {
		stops = FXCollections.observableArrayList();
		line = ListSearchLineCtrl.selectedLine;
		int sequence = -1;

		Stop start = DBHelperLine.getInstance().getTerminal(line.getLine(), true);
		ArrayList<Stop> stopsGoing = DBHelperLine.getInstance().getStops(line.getLine(), true);
		ArrayList<Stop> stopsReturn = DBHelperLine.getInstance().getStops(line.getLine(), false);
		Stop end = DBHelperLine.getInstance().getTerminal(line.getLine(), false);
		start.setAddress(start.getAddress());
		end.setAddress(end.getAddress());
		System.out.println(stopsGoing);
		stops.add(++sequence, start);
		System.out.println(stops);
		for(int i=0; i<stopsGoing.size(); i++) {
			stops.add(++sequence, stopsGoing.get(i));
		}
		System.out.println(stops);
		stops.add(++sequence, end);
		System.out.println(stops);
		for(int i=0; i<stopsReturn.size(); i++) {
			stops.add(++sequence, stopsReturn.get(i));
		}
		System.out.println(stops);
		stops.add(++sequence, start);
		System.out.println(stops);
		this.stopsList.setItems(stops);
		this.name.setText(line.getName());

		this.priority.setText(Integer.toString(line.getLine().getPriority()));

		this.priority.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent; -fx-background-color: #F4F4F4");
		this.priority.setEditable(false);
		this.name.setStyle("-fx-text-box-border: transparent; -fx-focus-color: transparent;-fx-background-color: #F4F4F4");
		this.name.setEditable(false);
		
		oldLine = getNewLine();
	}


	@FXML
	private void onAddStopClicked() {
		// Check for valid text
		if (newStop.getText().equals("")) {
			return;
		}
		Stop stop = new Stop(newStop.getText());
		stops.add(stop);
		stopsList.setItems(stops);

		// Clean the input field
		newStop.setText("");
	}

	@FXML
	private void onRemoveStopClicked() {
		int index = stopsList.getSelectionModel().getSelectedIndex();
		if(index == -1) {	// No stop is selected
			// Check first if there are stops
			if(stops.size() == 0)	return;

			index = stops.size()-1;  // Select the last stop
		}
		// Remove the selected stop from both stops and stopsList
		stops.remove(index);
		stopsList.setItems(stops);
	}

	private Line getNewLine() {
		Line l = new Line();
		l.setName(name.getText());
		l.setPriority(Integer.parseInt(priority.getText()));
		l.setStartTerminal(stops.get(0));

		ArrayList<Stop> going = new ArrayList<>();
		for(int i=1; i<stops.size()/2; i++)
			going.add(stops.get(i));
		l.setGoingStops(going);

		l.setEndTerminal(stops.get(stops.size()/2));

		ArrayList<Stop> ret = new ArrayList<>();
		for(int i=(stops.size()/2)+1; i<stops.size()-1; i++)
			ret.add(stops.get(i));
		l.setReturnStops(ret);
		return l;
	}

	@FXML
	private void submitVehicle() {

		Alert error = check();
		if(error != null) {
			error.showAndWait();
		} else {
			Line outputLine = getNewLine();
			String result = "La linea " + outputLine.getName() + " é stata inserita con successo";
			System.out.println(result);
			try {
				DBHelperLine.getInstance().removeLine(oldLine);
				DBHelperLine.getInstance().insertLine(outputLine);
				System.out.println(outputLine);
			}
			catch(SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
			    alert.initOwner(mainApp.getPrimaryStage());
			    alert.setTitle("Avviso");
			    alert.setHeaderText("Modifica fallita");
			    alert.setContentText("Errore durante l'interfacciamento con il database");
			}
		}
	}

	private Alert check() {
		Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Avviso");
	    alert.setHeaderText("Inserimento fallito!");

	    if (stops.size() < 4) {
	    	alert.setContentText("Inserisci almeno 4 fermate.");
	    	return alert;
	    }
	    if (name.getText().equals("")) {
	    	alert.setContentText("Inserisci un nome alla linea.");
	    	return alert;
	    }
	    if (priority.getText().equals("")) {
	    	alert.setContentText("Inserisci una priorità alla linea.");
	    	return alert;
	    }
	    else {
		    try {
		    	Integer.parseInt(priority.getText());
		    }
		    catch (NumberFormatException e) {
		    	alert.setContentText("Inserisci un valore numerico nel campo priorità");
		    	return alert;
			}
	    }
	    // If data is OK
	    return null;

	}

	@FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }
	@FXML
	public void goBack() throws IOException{
		AdministrativeAreaCtrl hrac = new AdministrativeAreaCtrl();
		hrac.setMainApp(this.mainApp);
		hrac.showSearchLine();
	}
	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}
	
	private String capitalize(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
}
