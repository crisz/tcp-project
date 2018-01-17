package it.metallicdonkey.tcp.vehicleArea;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.administrativeArea.LineDataModel;
import it.metallicdonkey.tcp.administrativeArea.ListSearchLineCtrl;
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

public class ChangeLineCtrl {
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
	
	@FXML
	private void initialize() {
		stops = FXCollections.observableArrayList();
		line = ListSearchLineCtrl.selectedLine;
		
		
		
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
		l.setGoingStops((ArrayList<Stop>) Arrays.asList(stops.toArray(new Stop[stops.size()])));
		l.setStartTerminal(stops.get(0));
		l.setEndTerminal(stops.get(stops.size()-1));
		
		ArrayList<Stop> reverseList = new ArrayList<>();
		reverseList.addAll(l.getGoingStops());
		Collections.reverse(reverseList);
		l.setReturnStops(reverseList);
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

	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}
}
