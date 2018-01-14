package it.metallicdonkey.tcp.HRArea;

import java.io.IOException;

import org.omg.CORBA.INITIALIZE;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.administrativeArea.AdministrativeAreaCtrl;
import it.metallicdonkey.tcp.administrativeArea.CheckCtrl;
import it.metallicdonkey.tcp.administrativeArea.ListSearchLineCtrl;
import it.metallicdonkey.tcp.vehicleArea.AddLineCtrl;
import it.metallicdonkey.tcp.vehicleArea.AddVehicleCtrl;
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
	private void showAddEmployee() throws IOException {
		//System.out.println("Showing personal info...");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource("HRArea/AddEmployeeScreen.fxml"));
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
		loader.setLocation(App.class.getResource("HRArea/AddEmployeeScreen.fxml"));
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
	private void showSearchEmployee() throws IOException {
		System.out.println("Showing search info employee...");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource("HRArea/ListSearchEmployeeScreen.fxml"));
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
		loader.setLocation(App.class.getResource("administrativeArea/CheckScreen.fxml"));
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
		loader.setLocation(App.class.getResource("HRArea/PaySalaryScreen.fxml"));
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
}
