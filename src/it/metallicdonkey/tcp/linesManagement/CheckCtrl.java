package it.metallicdonkey.tcp.linesManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperCheck;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.db.DBHelperLine;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.employeesManagement.EmployeeDataModel;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Role;
import it.metallicdonkey.tcp.models.Check;
import it.metallicdonkey.tcp.models.Employee;
import it.metallicdonkey.tcp.models.Match;
import it.metallicdonkey.tcp.models.StatusEmployee;
import it.metallicdonkey.tcp.models.Workshift;
import it.metallicdonkey.tcp.vehiclesManagement.VehicleDataModel;
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
	ArrayList<EmployeeDataModel> employeesInCheck = new ArrayList<>();
	boolean oldChecksExists;
	List<Check> oldChecks;
	Workshift currentWorkshift = Workshift.MATTINA;


	@FXML
  public void addBind() {
  	try {
    	EmployeeDataModel e = this.employees.getSelectionModel().getSelectedItem();
    	VehicleDataModel v = this.vehicles.getSelectionModel().getSelectedItem();
    	LineDataModel l = this.lines.getSelectionModel().getSelectedItem();
    	Match m = new Match(e.getEmployee(), l.getLine(), v.getVehicle());


    	dataCheck.add(new MatchDataModel(m));
    	this.setItemsForEmployee();
    	this.setItemsForVehicle();
  	} catch(NullPointerException e) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Error");
	    alert.setHeaderText("Impossibile creare l'abbinamento");
	    alert.setContentText("Seleziona almeno un impiegato, una linea e un mezzo dalle 3 liste superiori.");
	    alert.showAndWait();
  	}
  }

	/*
   * Creates a Check object for a workshift
   */
  private Check createCheck(Workshift workshift) {
	  Check c = new Check();
	  c.setWorkshift(workshift);

	  ObservableList<MatchDataModel> source = null;
	  switch (workshift) {
	  	case MATTINA:
	  		source = check1;
	  		break;
	  	case POMERIGGIO:
	  		source = check2;
	  		break;
	  	case SERA:
	  		source = dataCheck;
	  		break;
	  	default:
	  		break;
	  }
	  for(int i=0; i< source.size(); i++) {
		  c.addMatch(source.get(i).getMatch());
	  }
	  return c;
  }

	@FXML
	private void goBack() throws IOException, SQLException {
		this.turno -= 2;
		if(this.turno<-1)
			this.turno = -1;
		this.nextWorkshift();

		nextButton.setText("Turno successivo");
	}

	@FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }

  private void initColumns() {
    this.nameColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("name"));
    this.startTerminalColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("startTerminal"));
    this.endTerminalColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("endTerminal"));
  	this.lines.setItems(dataLines);

  	this.vehicleId.setCellValueFactory(
        new PropertyValueFactory<VehicleDataModel, String>("id"));
    this.seats.setCellValueFactory(
        new PropertyValueFactory<VehicleDataModel, String>("seats"));

    this.nomeECognome.setCellValueFactory(
        new PropertyValueFactory<EmployeeDataModel, String>("nomeECognome"));

    this.checkEmployee.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("employee"));
    this.checkVehicle.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("vehicle"));
    this.checkLine.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("line"));
    this.check.setItems(dataCheck);


	}

  @FXML
  private void initialize() throws SQLException {
		this.dataVehicles = DBHelperVehicle.getInstance().getAllVehicles();
		this.dataEmployees = DBHelperEmployee.getInstance().getAllEmployees();
		this.dataLines = DBHelperLine.getInstance().getAllLines();
		this.dataCheck = FXCollections.observableArrayList();

		// Check if there are the last 3 checks in the db
		this.oldChecks = DBHelperCheck.getInstance().getLastChecks();
		this.oldChecksExists = this.oldChecks.size() != 0;

		this.showAbsentEmployees();
		this.initColumns();
		this.setItemsForEmployee();
		this.setItemsForVehicle();
		this.setAvailableDrivers();
		removeDuplicates();
  }

  @FXML
  public void nextWorkshift() throws IOException, SQLException {
	  if(turno == -1) {
		  this.workshiftLabel.setText("Mattina");
		  this.currentWorkshift = Workshift.MATTINA;
		  // Clear dataCheck
		  dataCheck.clear();

		  if(oldChecksExists) {

			  ArrayList<Match> alm = oldChecks.get(0).getMatches();
			  for(int i=0; i<alm.size(); i++) {
				  dataCheck.add(new MatchDataModel(alm.get(i)));
			  }
		  }
		  this.setItemsForEmployee();
		  this.setItemsForVehicle();
		  this.setAvailableDrivers();
		  this.turno++;
		  removeDuplicates();
	  }
	  else if(turno == 0) {
		  this.workshiftLabel.setText("Pomeriggio");
		  this.currentWorkshift = Workshift.POMERIGGIO;
		  this.check1 = FXCollections.observableArrayList(dataCheck);

		  // Clear dataCheck
		  dataCheck.clear();

		  if(oldChecksExists) {

			  ArrayList<Match> alm = oldChecks.get(1).getMatches();
			  for(int i=0; i<alm.size(); i++) {
				  dataCheck.add(new MatchDataModel(alm.get(i)));
			  }
		  }
		  this.setItemsForEmployee();
		  this.setItemsForVehicle();
		  this.setAvailableDrivers();
		  this.turno++;
		  removeDuplicates();
	  }

	  else if(turno == 1) {
	  	this.currentWorkshift = Workshift.SERA;
		  this.workshiftLabel.setText("Sera");
		  this.nextButton.setText("Fine");
		  this.check2 = FXCollections.observableArrayList(dataCheck);
		  // Clear dataCheck
		  dataCheck.clear();
		  if(oldChecksExists) {
			  ArrayList<Match> alm = oldChecks.get(2).getMatches();
			  for(int i=0; i<alm.size(); i++) {
				  dataCheck.add(new MatchDataModel(alm.get(i)));
			  }
		  }
		  this.setItemsForEmployee();
		  this.setItemsForVehicle();
		  this.setAvailableDrivers();
		  this.turno++;
		  removeDuplicates();
	  }
	  else {

  		/*
  		 * Save checks into the database
  		 */

  		// Create Check objects
  		Check checkMattina = createCheck(Workshift.MATTINA);
  		Check checkPomeriggio = createCheck(Workshift.POMERIGGIO);
  		Check checkSera = createCheck(Workshift.SERA);

  		try {
  			// First remove the checks of the day before
  			DBHelperCheck.getInstance().removeLastChecks();
  			DBHelperCheck.getInstance().insertCheck(checkMattina);
  			DBHelperCheck.getInstance().insertCheck(checkPomeriggio);
  			DBHelperCheck.getInstance().insertCheck(checkSera);
  		}
  		catch (SQLException e) {
  			e.printStackTrace();
  			Alert alert = new Alert(AlertType.ERROR);
  		    alert.initOwner(mainApp.getPrimaryStage());
  		    alert.setTitle("Errore");
  		    alert.setHeaderText("Salvataggio fallito!");
  		    alert.setContentText("Non è stato possibile salvare i dati nel database");
  		    alert.showAndWait();
		}

  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(App.class.getResource("linesManagement/CheckResultScreen.fxml"));
  		AnchorPane resultScreen = (AnchorPane) loader.load();
  		Scene scene = new Scene(resultScreen);
  		mainApp.getPrimaryStage().setScene(scene);
  		CheckResultCtrl resultCtrl = loader.getController();
  		resultCtrl.setMainApp(mainApp);
  		resultCtrl.setData(check1, check2, dataCheck);
  	}

  }

  @FXML
  public void removeBind() {
  	try {
	  	MatchDataModel c = this.check.getSelectionModel().getSelectedItem();
	  	check.getItems().remove(c);
	  	this.setItemsForEmployee();
    	this.setItemsForVehicle();
  	}
  	catch(NullPointerException e) {
		Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(mainApp.getPrimaryStage());
	    alert.setTitle("Error");
	    alert.setHeaderText("Impossibile rimuovere l'abbinamento");
	    alert.setContentText("Seleziona almeno un abbinamento dalla lista inferiore.");
	    alert.showAndWait();
  	}
  }

  private void setAvailableDrivers() {
		HashMap<LineDataModel, Integer> es  = new HashMap<>();
		// Order lines for priority
		int sum = 0;
		for(LineDataModel ldm: this.lines.getItems()) {
			sum+=ldm.getLine().getPriority();
		}

		System.out.println(sum);
		for(LineDataModel ldm: this.lines.getItems()) {
			es.put(ldm, (int)(ldm.getLine().getPriority() * this.employees.getItems().size() /sum +1));
		}

		System.out.println(es);

		for(EmployeeDataModel ee: this.employees.getItems()) {
			if(lines.getItems().size()<1) return;
			if(vehicles.getItems().size()<1) return;
			if(es.size()<1) return;
			int randomNumber = (int) Math.floor(Math.random()*es.size());

//			LineDataModel randomLine = lines.getItems().get(randomNumber);


			LineDataModel randomLine = new ArrayList<LineDataModel>(es.keySet()).get(randomNumber);
			int randomNumber2 = (int) Math.floor(Math.random()*vehicles.getItems().size());

			VehicleDataModel randomVehicle = vehicles.getItems().get(randomNumber2);

    	Match m = new Match(ee.getEmployee(), randomLine.getLine(), randomVehicle.getVehicle());

    	dataCheck.add(new MatchDataModel(m));
    	this.setItemsForEmployee();
    	this.setItemsForVehicle();
    	//this.setAvailableDrivers();
			es.put(randomLine, es.get(randomLine)-1);
			if(es.get(randomLine)==0)
				es.remove(randomLine);
		}
	}

  private void setItemsForEmployee() {
		this.employees.setItems(new FilteredList<>(dataEmployees, e -> {
			for(int i=0; i<dataCheck.size(); i++) {
				if(dataCheck.get(i).getEmployeeModel().getEmployee().getId().equals(e.getEmployee().getId())) {
					return false;
				}
			}
			return e.getEmployee().getWorkshift() == this.currentWorkshift
					&& e.getEmployee().getRole() == Role.Autista;
		}));
	}


	private void setItemsForVehicle() {

    this.vehicles.setItems(new FilteredList<>(dataVehicles, v -> {
			for(int i=0; i<dataCheck.size(); i++) {
				if(dataCheck.get(i).getVehicleModel().getVehicle().getId().equals(v.getVehicle().getId())) {
					return false;
				}
			}
			return true;
		}));
	}

	public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }


	private void showAbsentEmployees() {
		String absentEmployees = "";
		if (oldChecksExists) {
			ArrayList<Match> alm = oldChecks.get(0).getMatches();
			for(int i=0; i<alm.size(); i++) {
				MatchDataModel mdm = new MatchDataModel(alm.get(i));
				Employee emdm = mdm.getEmployeeModel().getEmployee();
				if(emdm.getStatus() == StatusEmployee.ABSENT) {
					absentEmployees += emdm.getId() + " - " + emdm.getFirstName() + " - " +emdm.getLastName() + "\n";
					for(int j=0; j<dataEmployees.size(); j++) {
						if(emdm.getId().equals(dataEmployees.get(j).getId())) {
							dataEmployees.remove(j);
						}
					}
				}
				else {
					dataCheck.add(mdm);
				}
			}
		}
		if(absentEmployees.length()!=0) {
			Alert alert = new Alert(AlertType.WARNING);
	    alert.initOwner(null);
	    alert.setTitle("Avviso");
	    alert.setHeaderText("Alcuni impiegati sono assenti.");
	    alert.setContentText("I seguenti impiegati sono assenti e vanno sostituiti:\n" + absentEmployees);
	    alert.showAndWait();
		}
	}
	
	private void removeDuplicates() {
		if(dataCheck.isEmpty())
			return;
		
		Iterator<MatchDataModel> it = dataCheck.iterator();
		MatchDataModel previous = null, current = null;
		while(it.hasNext()) {
			if(previous == null) {
				previous = it.next();
				continue;
			}
			
			current = it.next();
			// Check for duplicated employees
			if(current.getEmployee().equals(previous.getEmployee())) {
				System.out.println("REMOVED Duplicated Employee" + current.getEmployee());
				it.remove();
				this.setItemsForEmployee();
		    	this.setItemsForVehicle();
			}
			// Check for duplicated Vehicles
			else if(current.getVehicle().equals(previous.getVehicle())) {
				System.out.println("REMOVED Duplicated Vehicle" + current.getVehicle());
				it.remove();
				this.setItemsForEmployee();
		    	this.setItemsForVehicle();
			}
			previous = current;
		}
		for(int i=0; i< dataCheck.size() -1; i++) {
			// Check for duplicated Employees
			if(dataCheck.get(i).getEmployee().equals(dataCheck.get(i+1).getEmployee())) {
		    	System.out.println("REMOVED Duplicated Employee" + dataCheck.get(i).getEmployee());
				check.getItems().remove(i+1);
				
			}
			
			// Check for duplicated Vehicles
			if(dataCheck.get(i).getVehicle().equals(dataCheck.get(i+1).getEmployee())) {
		    	System.out.println("REMOVED Duplicated Vehicle" + dataCheck.get(i).getVehicle());
				check.getItems().remove(i+1);
				this.setItemsForEmployee();
		    	this.setItemsForVehicle();
			}
			
		}
	}
}