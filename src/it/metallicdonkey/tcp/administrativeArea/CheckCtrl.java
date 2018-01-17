package it.metallicdonkey.tcp.administrativeArea;

import java.io.IOException;
import java.sql.SQLException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.HRArea.EmployeeDataModel;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.Line;
import it.metallicdonkey.tcp.models.Match;
import it.metallicdonkey.tcp.models.Stop;
import it.metallicdonkey.tcp.models.Workshift;
import it.metallicdonkey.tcp.vehicleArea.VehicleDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
	private TableView<MatchDataModel> check;
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
	private TableColumn<MatchDataModel, String> checkEmployee;
	@FXML
	private TableColumn<MatchDataModel, String> checkVehicle;
	@FXML
	private TableColumn<MatchDataModel, String> checkLine;
	@FXML
	private Label workshiftLabel;
	@FXML
	private Button nextButton;
	private int turno = 0;
	private ObservableList<MatchDataModel> check1, check2;
	ObservableList<LineDataModel> dataLines;
	ObservableList<VehicleDataModel> dataVehicles;
	ObservableList<EmployeeDataModel> dataEmployees;
	ObservableList<MatchDataModel> dataCheck;

	FilteredList<EmployeeDataModel> empM;
	FilteredList<EmployeeDataModel> empP;
	FilteredList<EmployeeDataModel> empS;
	
	public CheckCtrl() throws SQLException {
	// TODO: Sostituire i 4 ObservabileList qua sottocon i dati scaricati dal db (check giorno precedente + guasti e assenti)


	}

	
	private void filter() {
		this.empM = new FilteredList<>(dataEmployees, e -> e.getEmployee().getWorkshift() == Workshift.MATTINA && e.getEmployee().getRole() == Role.Autista);
		this.empP = new FilteredList<>(dataEmployees, e -> e.getEmployee().getWorkshift() == Workshift.POMERIGGIO && e.getEmployee().getRole() == Role.Autista);
		this.empS = new FilteredList<>(dataEmployees, e -> e.getEmployee().getWorkshift() == Workshift.SERA && e.getEmployee().getRole() == Role.Autista);
	}

  @FXML
  private void initialize() throws SQLException {
		this.dataVehicles = DBHelperVehicle.getInstance().getAllVehicles();

System.out.println(dataVehicles);
		this.dataEmployees = DBHelperEmployee.getInstance().getAllEmployees();
		this.filter();
		System.out.println("tutti gli impiegati");
		for(int i=0; i<dataEmployees.size(); i++) {
			System.out.println("Impeigati, tutti: " + dataEmployees.get(i).getEmployee().getWorkshift().name() + " - " + dataEmployees.get(i).getEmployee().getRole().name() );
		}
		Line l = new Line();
		l.setName("101");
		l.setStartTerminal(new Stop("Stazione Centrale"));
		l.setEndTerminal(new Stop("Stadio"));

		this.dataLines = FXCollections.observableArrayList(
		    new LineDataModel(l)
		);

for(int i=0; i<empM.size(); i++) {
			System.out.println("Impeigati, mattina: " + empM.get(i).getEmployee().getWorkshift().name() +" "+empM.get(i).getEmployee().getFirstName() + empM.get(i).getEmployee().getId());
		}
		this.dataCheck = FXCollections.observableArrayList(
		);
		
		// fine costruttore
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
   
    this.employees.setItems(empM);

    checkEmployee.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("employee"));
    checkVehicle.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("vehicle"));
    checkLine.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("line"));
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
    	this.employees.setItems(empP);

  		this.workshiftLabel.setText("Pomeriggio");
  		this.check1 = FXCollections.observableArrayList(dataCheck);
  	} else if(turno==2) {
    	this.employees.setItems(empS);
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
    	Match m = new Match(e.getEmployee(), l.getLine(), v.getVehicle());
    	
    	dataCheck.add(new MatchDataModel(m));
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
	  	MatchDataModel c = this.check.getSelectionModel().getSelectedItem();
	  	EmployeeDataModel e = c.getEmployeeModel();
	  	VehicleDataModel v = c.getVehicleModel();
	  	
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
