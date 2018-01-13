package it.metallicdonkey.tcp.vehicleArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.administrativeArea.AdministrativeAreaCtrl;
import it.metallicdonkey.tcp.administrativeArea.CheckCtrl;
import it.metallicdonkey.tcp.administrativeArea.ListSearchLineCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class VehicleAreaCtrl {
	private App mainApp;
	
  @FXML
  private void initialize() {
  }
 
  @FXML 
  private void showPersonalInfo() throws IOException {
  	System.out.println("Showing personal info...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("employeeArea/PersonalInfoScreen.fxml"));
    AnchorPane personalInfo = (AnchorPane) loader.load();
    System.out.println("Resource done!");
    
		Scene scene = new Scene(personalInfo);
		System.out.println(scene);
		System.out.println(personalInfo);
    Stage stage = mainApp.getPrimaryStage();
    stage.setScene(scene);
  }
  
  @FXML 
  private void showSearchLine() throws IOException {
  	System.out.println("Showing search info line...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("administrativeArea/ListSearchLineScreen.fxml"));
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
  private void showSearchVehicle() throws IOException {
  	System.out.println("Showing search info vehicle...");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("vehicleArea/ListSearchVehicleScreen.fxml"));
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
    loader.setLocation(App.class.getResource("administrativeArea/CheckScreen.fxml"));
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
  
  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}