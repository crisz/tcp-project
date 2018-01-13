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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class CheckResultCtrl {
	private App mainApp;

	@FXML
	private TableView<CheckDataModel> checkM;
	@FXML
	private TableColumn<CheckDataModel, String> checkMEmployee;
	@FXML
	private TableColumn<CheckDataModel, String> checkMVehicle;
	@FXML
	private TableColumn<CheckDataModel, String> checkMLine;
	
	@FXML
	private TableView<CheckDataModel> checkP;
	@FXML
	private TableColumn<CheckDataModel, String> checkPEmployee;
	@FXML
	private TableColumn<CheckDataModel, String> checkPVehicle;
	@FXML
	private TableColumn<CheckDataModel, String> checkPLine;
	
	@FXML
	private TableView<CheckDataModel> checkS;

	@FXML
	private TableColumn<CheckDataModel, String> checkEmployeeM;
	@FXML
	private TableColumn<CheckDataModel, String> checkVehicleM;
	@FXML
	private TableColumn<CheckDataModel, String> checkLineM;
	
	@FXML
	private TableColumn<CheckDataModel, String> checkEmployeeP;
	@FXML
	private TableColumn<CheckDataModel, String> checkVehicleP;
	@FXML
	private TableColumn<CheckDataModel, String> checkLineP;
	
	@FXML
	private TableColumn<CheckDataModel, String> checkEmployeeS;
	@FXML
	private TableColumn<CheckDataModel, String> checkVehicleS;
	@FXML
	private TableColumn<CheckDataModel, String> checkLineS;
	
	
	private int turno = 0;
	
	private ObservableList<CheckDataModel> checkMData;
	private ObservableList<CheckDataModel> checkPData;
	private ObservableList<CheckDataModel> checkSData;
	
  @FXML
  private void initialize() {

    
    checkEmployeeM.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("employee"));
    checkVehicleM.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("vehicle"));
    checkLineM.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("line"));
    
    
    checkEmployeeP.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("employee"));
    checkVehicleP.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("vehicle"));
    checkLineP.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("line"));
    
    
    checkEmployeeS.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("employee"));
    checkVehicleS.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("vehicle"));
    checkLineS.setCellValueFactory(
        new PropertyValueFactory<CheckDataModel, String>("line"));
    
  }
  
  @FXML
  public void goHome() throws IOException {
  	Home.getHome(null).goHome(this.mainApp);
  }
  
  
  public void setData(ObservableList<CheckDataModel> check1, ObservableList<CheckDataModel> check2, ObservableList<CheckDataModel> check3) {
  	this.checkMData = check1;
  	this.checkPData = check2;
  	this.checkSData = check3;
  	this.checkS.setItems(checkSData);
  	this.checkP.setItems(checkSData);
  	this.checkM.setItems(checkMData);
  }

  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
