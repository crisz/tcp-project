package it.metallicdonkey.tcp.employeesManagement;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.linesManagement.AddLineCtrl;
import it.metallicdonkey.tcp.linesManagement.CheckCtrl;
import it.metallicdonkey.tcp.linesManagement.ListSearchLineCtrl;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.LoginCtrl;
import it.metallicdonkey.tcp.login.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HRAreaCtrl {
	private App mainApp;
	@FXML
	public void initialize(){
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
		PersonalInfoCtrl ctrl = loader.getController();
	    ctrl.setMainApp(mainApp);
	}
	@FXML
	private void showAddEmployee() throws IOException {
		//System.out.println("Showing personal info...");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource("employeesManagement/AddEmployeeScreen.fxml"));
		AnchorPane personalInfo = (AnchorPane) loader.load();
		System.out.println("Resource done!");
		Scene scene = new Scene(personalInfo);
		System.out.println(scene);
		System.out.println(personalInfo);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddEmployeeCtrl lsvCtrl = loader.getController();
		lsvCtrl.setMainApp(mainApp);
	}
	@FXML
	private void showAddLine() throws IOException {
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
	private void showSearchEmployee() throws IOException {
		System.out.println("Showing search info employee...");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource("employeesManagement/ListSearchEmployeeScreen.fxml"));
		AnchorPane lsl = (AnchorPane) loader.load();
		System.out.println("Resource done!");
		Scene scene = new Scene(lsl);
		System.out.println(scene);
		System.out.println(mainApp);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		ListSearchEmployeeCtrl lseCtrl = loader.getController();
		lseCtrl.setMainApp(mainApp);
	}
	@FXML
	private void showCheck() throws IOException {
		System.out.println("Showing check...");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource("linesManagement/CheckScreen.fxml"));
		AnchorPane lsl = (AnchorPane) loader.load();
		System.out.println("Resource done!");
		Scene scene = new Scene(lsl);
		System.out.println(scene);
		System.out.println(mainApp);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		CheckCtrl cCtrl = loader.getController();
		cCtrl.setMainApp(mainApp);
	}
	@FXML
	private void showPaySalary() throws IOException {
		System.out.println("Showing pay salary...");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource("employeesManagement/PaySalaryScreen.fxml"));
		AnchorPane lsl = (AnchorPane) loader.load();
		System.out.println("Resource done!");
		Scene scene = new Scene(lsl);
		System.out.println(scene);
		System.out.println(mainApp);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		PaySalaryCtrl cCtrl = loader.getController();
		cCtrl.setMainApp(mainApp);
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
