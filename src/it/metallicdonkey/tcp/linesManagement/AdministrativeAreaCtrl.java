package it.metallicdonkey.tcp.linesManagement;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AdministrativeAreaCtrl {
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
  
  public void setMainApp(App mainApp) {
    this.mainApp = mainApp;
  }
}
