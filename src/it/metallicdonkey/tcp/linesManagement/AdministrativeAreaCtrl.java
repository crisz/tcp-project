package it.metallicdonkey.tcp.linesManagement;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.employeesManagement.EmployeeAreaCtrl;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.LoginCtrl;
import it.metallicdonkey.tcp.login.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AdministrativeAreaCtrl extends EmployeeAreaCtrl {
	private App mainApp;

  @FXML
  private void initialize() {
  }

  @FXML
  private void showPersonalInfo() throws IOException {
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
