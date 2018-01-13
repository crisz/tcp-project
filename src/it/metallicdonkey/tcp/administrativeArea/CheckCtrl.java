package it.metallicdonkey.tcp.administrativeArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.HRArea.EmployeeDataModel;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Session;
import it.metallicdonkey.tcp.models.Line;
import it.metallicdonkey.tcp.vehicleArea.VehicleDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class CheckCtrl {
	private App mainApp;
	@FXML
	private TableView<LineDataModel> lines;
	@FXML
	private TableView<VehicleDataModel> vehicles;
	@FXML
	private TableView<EmployeeDataModel> employees;
	@FXML
	private TableView<CheckDataModel> check;
	@FXML
	private TableColumn<LineDataModel, String> nameColumn;
	@FXML
	private TableColumn<LineDataModel, String> startTerminalColumn;
	@FXML
	private TableColumn<LineDataModel, String> endTerminalColumn;
	@FXML
	private TableColumn<VehicleDataModel, String> vehicleId;
	@FXML
	private TableColumn<VehicleDataModel, String> seats;
	@FXML
	private TableColumn<EmployeeDataModel, String> nomeECognome;
	@FXML
	private TableColumn<CheckDataModel, String> checkEmployee;
	@FXML
	private TableColumn<CheckDataModel, String> checkVehicle;
	@FXML
	private TableColumn<CheckDataModel, String> checkLine;
	@FXML
	private Label workshiftLabel;
	@FXML
	private Button nextButton;
	private int turno = 0;
	private ObservableList<CheckDataModel> check1, check2;
	ObservableList<LineDataModel> dataLines;
	ObservableList<VehicleDataModel> dataVehicles;
	ObservableList<EmployeeDataModel> dataEmployees;
	ObservableList<CheckDataModel> dataCheck;
	// TODO: Sostituire i 4 ObservabileList con i dati scaricati dal db (check giorno precedente + guasti e assenti)
	public CheckCtrl() {
		ObservableList<LineDataModel> dataLines = FXCollections.observableArrayList(
		    new LineDataModel("101", "Stazione Centrale", "Stadio"),
		    new LineDataModel("102", "Piazzale Giotto", "Stazione Centrale"),
		    new LineDataModel("806", "Politeama", "Mondello"),
		    new LineDataModel("108", "Politeama", "Ospedale Civico"),
		    new LineDataModel("534", "Piazzale Giotto", "Mondello")
		);
		
		ObservableList<VehicleDataModel> dataVehicles = FXCollections.observableArrayList(
		    new VehicleDataModel("A320", 30, 140, 1, "214"),
		    new VehicleDataModel("B340", 20, 60, 1, "In circolazione"),
		    new VehicleDataModel("C120", 20, 70, 0, "041"),
		    new VehicleDataModel("A120", 30, 10, 0, "Guasto"),
		    new VehicleDataModel("A221", 50, 10, 2, "In circolazione")
		);
		ObservableList<EmployeeDataModel> dataEmployees = FXCollections.observableArrayList(
		    new EmployeeDataModel("Pippo", "Vattelappesca"),
		    new EmployeeDataModel("Giovanni", "Muciaccia"),
		    new EmployeeDataModel("Paolo", "Rossi"),
		    new EmployeeDataModel("Paolino", "Paperino"),
		    new EmployeeDataModel("Marco", "Verdi"),
		    new EmployeeDataModel("Luca", "Gialli"),
		    new EmployeeDataModel("Carlo", "Neri")
		);
		final ObservableList<CheckDataModel> dataCheck = FXCollections.observableArrayList(
		    new CheckDataModel(
		    		new EmployeeDataModel("Pietro","Gambadilegno"), 
		    		new VehicleDataModel("A320", 40, 0, 0, "210"), 
		    		new LineDataModel("806","Politeama","Mondello")
		    	)
		);
	}
	
	
  @FXML
  private void initialize() {
  	// Initialization data
    nameColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("name"));
    startTerminalColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("startTerminal"));
    endTerminalColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("endTerminal"));
  	this.lines.setItems(dataLines);
  	
  	vehicleId.setCellValueFactory(
        new PropertyValueFactory<VehicleDataModel, String>("id"));
    seats.setCellValueFactory(
        new PropertyValueFactory<VehicleDataModel, String>("seats"));
    this.vehicles.setItems(dataVehicles);
    
    nomeECognome.setCellValueFactory(
        new PropertyValueFactory<EmployeeDataModel, String>("nomeECognome"));
    this.employees.setItems(dataEmployees);
    
    checkEmployee.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("employee"));
    checkVehicle.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("vehicle"));
    checkLine.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("line"));
    this.check.setItems(dataCheck);
  }
  
  @FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }
  
  @FXML
  public void nextWorkshift() throws IOException {
  	this.turno++;
  	
  	if(turno==1) {
    	/*
    	 * TODO: aggiornare i dati dal database, replicando la logica nel costruttore, però per il turno pomeridiano.
    	 * 
    	 */
  		
  		this.workshiftLabel.setText("Pomeriggio");
  		this.check1 = FXCollections.observableArrayList(dataCheck);
  	} else if(turno==2) {
  		/*
  		 * 
  		 * TODO: aggiornare i dati dal database, con il turno serale.
  		 */
  		this.workshiftLabel.setText("Sera");
  		this.nextButton.setText("Fine");
  		this.check2 = FXCollections.observableArrayList(dataCheck);
  	} else {
  		System.out.println(check1);
  		System.out.println(check2);
  		System.out.println(dataCheck);
  		
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(App.class.getResource("administrativeArea/CheckResultScreen.fxml"));
      AnchorPane resultScreen = (AnchorPane) loader.load();
  		Scene scene = new Scene(resultScreen);
      mainApp.getPrimaryStage().setScene(scene);
      CheckResultCtrl resultCtrl = loader.getController();
      resultCtrl.setMainApp(mainApp);
      resultCtrl.setData(check1, check2, dataCheck);
  	}
  		
  }
  
  @FXML
  public void addBind() {
  	EmployeeDataModel e = this.employees.getSelectionModel().getSelectedItem();
  	VehicleDataModel v = this.vehicles.getSelectionModel().getSelectedItem();
  	LineDataModel l = this.lines.getSelectionModel().getSelectedItem();
  	dataCheck.add(new CheckDataModel(e, v, l));
  	dataEmployees.remove(e);
  	dataVehicles.remove(v);
  }
  
  @FXML
  public void removeBind() {
  	CheckDataModel c = this.check.getSelectionModel().getSelectedItem();
  	EmployeeDataModel e = c.getEmployeeModel();
  	VehicleDataModel v = c.getVehicleModel();
  	dataCheck.remove(c);
  	dataEmployees.add(e);
  	dataVehicles.add(v);
  }
  
  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
