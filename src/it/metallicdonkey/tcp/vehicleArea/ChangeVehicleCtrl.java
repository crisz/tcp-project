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

	private VehicleDataModel vehicle;

	@FXML
	private void initialize() {
		this.vehicle = ListSearchVehicleCtrl.selectedVehicle;
		this.matricola.setText(this.vehicle.getId());
		this.postiASedere.setText(this.vehicle.getSeats());
		this.postiDisabili.setText(this.vehicle.getHSeats());
		this.postiInPiedi.setText(this.vehicle.getSSeats());
		// TODO: Aggiugnere targa in VehicleDataModel
	}

	@FXML
	private void submitVehicle() {
		String result = "Il veicolo " + matricola.getText() + " é stato inserito con successo";
		System.out.println(result);
		/*
		 * TODO: Sostituire con aggiornamento veicolo
		 */
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
