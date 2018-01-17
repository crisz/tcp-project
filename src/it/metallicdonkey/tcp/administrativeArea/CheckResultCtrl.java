package it.metallicdonkey.tcp.administrativeArea;

import java.io.File;
import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.Check;
import it.metallicdonkey.tcp.models.Workshift;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CheckResultCtrl {
	private App mainApp;

	@FXML
	private TableView<MatchDataModel> checkM;
	@FXML
	private TableColumn<MatchDataModel, String> checkMEmployee;
	@FXML
	private TableColumn<MatchDataModel, String> checkMVehicle;
	@FXML
	private TableColumn<MatchDataModel, String> checkMLine;
	
	@FXML
	private TableView<MatchDataModel> checkP;
	@FXML
	private TableColumn<MatchDataModel, String> checkPEmployee;
	@FXML
	private TableColumn<MatchDataModel, String> checkPVehicle;
	@FXML
	private TableColumn<MatchDataModel, String> checkPLine;
	
	@FXML
	private TableView<MatchDataModel> checkS;

	@FXML
	private TableColumn<MatchDataModel, String> checkEmployeeM;
	@FXML
	private TableColumn<MatchDataModel, String> checkVehicleM;
	@FXML
	private TableColumn<MatchDataModel, String> checkLineM;
	
	@FXML
	private TableColumn<MatchDataModel, String> checkEmployeeP;
	@FXML
	private TableColumn<MatchDataModel, String> checkVehicleP;
	@FXML
	private TableColumn<MatchDataModel, String> checkLineP;
	
	@FXML
	private TableColumn<MatchDataModel, String> checkEmployeeS;
	@FXML
	private TableColumn<MatchDataModel, String> checkVehicleS;
	@FXML
	private TableColumn<MatchDataModel, String> checkLineS;
	
	
	private int turno = 0;
	
	private ObservableList<MatchDataModel> checkMData;
	private ObservableList<MatchDataModel> checkPData;
	private ObservableList<MatchDataModel> checkSData;
	
  @FXML
  private void initialize() {

    
    checkEmployeeM.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("employee"));
    checkVehicleM.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("vehicle"));
    checkLineM.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("line"));
    
    
    checkEmployeeP.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("employee"));
    checkVehicleP.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("vehicle"));
    checkLineP.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("line"));
    
    
    checkEmployeeS.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("employee"));
    checkVehicleS.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("vehicle"));
    checkLineS.setCellValueFactory(
        new PropertyValueFactory<MatchDataModel, String>("line"));
    
  }
  
  @FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }
  
  @FXML
  public void printCheck() throws IOException {
  	Check c1 = new Check();
  	c1.setWorkshift(Workshift.MATTINA);
  	for(int i=0; i<checkMData.size(); i++) {
  		c1.addMatch(checkMData.get(i).getMatch());
  	}
  	Check c2 = new Check();
  	c2.setWorkshift(Workshift.POMERIGGIO);
  	for(int i=0; i<checkPData.size(); i++) {
  		c2.addMatch(checkPData.get(i).getMatch());
  	}
  	Check c3 = new Check();
  	c3.setWorkshift(Workshift.SERA);
  	for(int i=0; i<checkSData.size(); i++) {
  		c3.addMatch(checkSData.get(i).getMatch());
  	}
  	
  	System.out.println("Sto stampando i check");
  	System.out.println("Check 3 ha "+c3.getMatches().size()+" entry");
  	PDFCheck cpdf = new PDFCheck();
  	String path = cpdf.print(c1, c2, c3);
  	File a = new File(path);
  	
  	String absolute = a.getCanonicalPath();
  	
		Alert alert = new Alert(AlertType.INFORMATION);
    alert.initOwner(mainApp.getPrimaryStage());
    alert.setTitle("Avviso");
    alert.setHeaderText("PDF Generato con successo!");
    alert.setContentText("Lo puoi trovare all'indirizzo "+absolute+".");
    alert.showAndWait();
  }
  
  public void setData(ObservableList<MatchDataModel> check1, ObservableList<MatchDataModel> check2, ObservableList<MatchDataModel> check3) {
  	this.checkMData = check1;
  	this.checkPData = check2;
  	this.checkSData = check3;
  	this.checkS.setItems(checkSData);
  	this.checkP.setItems(checkPData);
  	this.checkM.setItems(checkMData);
  }

  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
