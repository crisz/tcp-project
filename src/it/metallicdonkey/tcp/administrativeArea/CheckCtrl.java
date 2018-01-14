package it.metallicdonkey.tcp.administrativeArea;

import java.io.IOException;
import java.sql.SQLException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.HRArea.EmployeeDataModel;
import it.metallicdonkey.tcp.db.DBHelper;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.vehicleArea.VehicleDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
	
	
	public CheckCtrl() throws SQLException {
	// TODO: Sostituire i 4 ObservabileList qua sottocon i dati scaricati dal db (check giorno precedente + guasti e assenti)
		
		this.dataLines = FXCollections.observableArrayList(
		    new LineDataModel("101", "Stazione Centrale", "Stadio"),
		    new LineDataModel("102", "Piazzale Giotto", "Stazione Centrale"),
		    new LineDataModel("806", "Politeama", "Mondello"),
		    new LineDataModel("108", "Politeama", "Ospedale Civico"),
		    new LineDataModel("534", "Piazzale Giotto", "Mondello")
		);
		
		this.dataVehicles = DBHelper.getInstance().getAllVehicles();
		
		this.dataEmployees = FXCollections.observableArrayList(
		    new EmployeeDataModel("Pippo", "Vattelappesca"),
		    new EmployeeDataModel("Giovanni", "Muciaccia"),
		    new EmployeeDataModel("Paolo", "Rossi"),
		    new EmployeeDataModel("Paolino", "Paperino"),
		    new EmployeeDataModel("Marco", "Verdi"),
		    new EmployeeDataModel("Luca", "Gialli"),
		    new EmployeeDataModel("Carlo", "Neri")
		);
		this.dataCheck = FXCollections.observableArrayList(
		    new CheckDataModel(
		    		new EmployeeDataModel("Pietro","Gambadilegno"), 
		    		DBHelper.getInstance().getAllVehicles().get(0) ,
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
  	try {
    	EmployeeDataModel e = this.employees.getSelectionModel().getSelectedItem();
    	VehicleDataModel v = this.vehicles.getSelectionModel().getSelectedItem();
    	LineDataModel l = this.lines.getSelectionModel().getSelectedItem();
    	dataCheck.add(new CheckDataModel(e, v, l));
    	dataEmployees.remove(e);
    	dataVehicles.remove(v);
  	} catch(NullPointerException e) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Error");
	    alert.setHeaderText("Impossibile creare l'abbinamento");
	    alert.setContentText("Seleziona almeno un impiegato, una linea e un mezzo dalle 3 liste superiori.");
	    alert.showAndWait();
  	}
  }
  
  @FXML
  public void removeBind() {
  	try {
	  	CheckDataModel c = this.check.getSelectionModel().getSelectedItem();
	  	EmployeeDataModel e = c.getEmployeeModel();
	  	VehicleDataModel v = c.getVehicleModel();
	  	dataCheck.remove(c);
	  	dataEmployees.add(e);
	  	dataVehicles.add(v);
  	} catch(NullPointerException e) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Error");
	    alert.setHeaderText("Impossibile rimuovere l'abbinamento");
	    alert.setContentText("Seleziona almeno un abbinamento dalla lista inferiore.");
	    alert.showAndWait();
  	}
  }
  
  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
