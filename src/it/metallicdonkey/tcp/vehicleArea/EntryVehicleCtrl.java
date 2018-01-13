package it.metallicdonkey.tcp.vehicleArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
	private void submitVehicle() {
		String result = "L'ingresso del veicolo " + vehicleField.getText() + " è avvenuto con successo. La postazione assegnata è la B241";
		resultLabel.setText(result);
		/*
		 * TODO: Sostituire con db
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
