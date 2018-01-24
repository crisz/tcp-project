package it.metallicdonkey.tcp.vehiclesManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.depositManagement.EntryVehicleCtrl;
import it.metallicdonkey.tcp.depositManagement.ExitVehicleCtrl;
import it.metallicdonkey.tcp.employeesManagement.PersonalInfoCtrl;
import it.metallicdonkey.tcp.linesManagement.AddLineCtrl;
import it.metallicdonkey.tcp.linesManagement.AdministrativeAreaCtrl;
import it.metallicdonkey.tcp.linesManagement.CheckCtrl;
import it.metallicdonkey.tcp.linesManagement.ListSearchLineCtrl;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.LoginCtrl;
import it.metallicdonkey.tcp.login.Session;
import it.metallicdonkey.tcp.models.StatusVehicle;
import it.metallicdonkey.tcp.models.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class VehicleAreaCtrl extends AdministrativeAreaCtrl {
	private App mainApp;

	@FXML
	private Label gua;
	@FXML
	private Label cir;
	@FXML
	private Label dep;

  @FXML
  private void initialize() throws SQLException {
  	ArrayList<Vehicle> vehicles = DBHelperVehicle.getInstance().getAllVehiclesArray();

  	int guasti = 0;
  	int circolazione = 0;
  	int deposito = 0;

  	for (int i=0; i<vehicles.size(); i++) {
  		if(vehicles.get(i).getStatus() == StatusVehicle.AVAILABLE)
  			deposito++;
  		else if(vehicles.get(i).getStatus() == StatusVehicle.ON_ACTION)
  			circolazione++;
  		else
  			guasti++;
  	}

  	gua.setText(gua.getText()+guasti);
  	cir.setText(cir.getText()+circolazione);
  	dep.setText(dep.getText()+deposito);
  }

  @FXML
  public void showPersonalInfo() throws IOException {
  	System.out.println("Showing personal info...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("employeesManagement/PersonalInfoScreen.fxml"));
    AnchorPane personalInfo = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(personalInfo);
		System.out.println(scene);
		System.out.println(personalInfo);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    PersonalInfoCtrl lsvCtrl = loader.getController();
    lsvCtrl.setMainApp(mainApp);
  }

  @FXML
  private void showAddVehicle() throws IOException {
  	System.out.println("Showing personal info...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("vehiclesManagement/AddVehicleScreen.fxml"));
    AnchorPane personalInfo = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(personalInfo);
		System.out.println(scene);
		System.out.println(personalInfo);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    AddVehicleCtrl lsvCtrl = loader.getController();
    lsvCtrl.setMainApp(mainApp);
	  }

  @FXML
  private void showAddLine() throws IOException {
  	System.out.println("Showing personal info...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("linesManagement/AddLineScreen.fxml"));
    AnchorPane personalInfo = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(personalInfo);
		System.out.println(scene);
		System.out.println(personalInfo);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    AddLineCtrl lsvCtrl = loader.getController();
    lsvCtrl.setMainApp(mainApp);
	  }
  @FXML
  private void showSearchLine() throws IOException {
  	System.out.println("Showing search info line...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("linesManagement/ListSearchLineScreen.fxml"));
    AnchorPane lsl = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(lsl);
		System.out.println(scene);
		System.out.println(mainApp);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    ListSearchLineCtrl lslCtrl = loader.getController();
    lslCtrl.setMainApp(mainApp);
  }

  @FXML
  public void showSearchVehicle() throws IOException {
  	System.out.println("Showing search info vehicle...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("vehiclesManagement/ListSearchVehicleScreen.fxml"));
    AnchorPane lsv = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(lsv);
		System.out.println(scene);
		System.out.println(mainApp);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    ListSearchVehicleCtrl lsvCtrl = loader.getController();
    lsvCtrl.setMainApp(mainApp);
  }

  @FXML
  private void showCheck() throws IOException {
  	System.out.println("Showing check...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("linesManagement/CheckScreen.fxml"));
    AnchorPane check = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(check);
		System.out.println(scene);
		System.out.println(mainApp);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    CheckCtrl lsvCtrl = loader.getController();
    lsvCtrl.setMainApp(mainApp);
  }

  @FXML
  private void showEntryVehicle() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("depositManagement/EntryVehicleScreen.fxml"));
    AnchorPane check = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(check);
		System.out.println(scene);
		System.out.println(mainApp);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    EntryVehicleCtrl lsvCtrl = loader.getController();
    lsvCtrl.setMainApp(mainApp);
  }

  @FXML
  private void showExitVehicle() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("depositManagement/ExitVehicleScreen.fxml"));
    AnchorPane check = (AnchorPane) loader.load();
    System.out.println("Resource done!");

		Scene scene = new Scene(check);
		System.out.println(scene);
		System.out.println(mainApp);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
    ExitVehicleCtrl lsvCtrl = loader.getController();
    lsvCtrl.setMainApp(mainApp);
  }
  @FXML
  private void logout() throws IOException {
	  System.out.println("logging out...");
	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(App.class.getResource("login/LoginScreen.fxml"));
	  AnchorPane login = (AnchorPane) loader.load();
	  System.out.println("Resource done!");
	  Scene scene = new Scene(login);
	  System.out.println(scene);
	  System.out.println(login);
	  Stage stage = mainApp.getPrimaryStage();
	  stage.setScene(scene);
	  Session.employee = null;
	  Home.getHome(null).destroyHome();
	  LoginCtrl loginCtrl = loader.getController();
	  loginCtrl.setMainApp(this.mainApp);
  }
  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
