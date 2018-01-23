package it.metallicdonkey.tcp.login;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.employeesManagement.EmployeeAreaCtrl;
import it.metallicdonkey.tcp.employeesManagement.HRAreaCtrl;
import it.metallicdonkey.tcp.linesManagement.AdministrativeAreaCtrl;
import it.metallicdonkey.tcp.vehiclesManagement.VehicleAreaCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Home {
	private Role role;
	private static Home home;

	private Home(Role role) {
		this.role = role;
	}

	public static Home getHome(Role r) {
		System.out.println("getHome called, home is:");
		System.out.println(home);
		if(home != null) {
			return home;
		} else {
			Home h = new Home(r);
			home = h;
			return h;
		}
	}
	public void destroyHome() {
		Home.home = null;
	}
	public void goHome(App mainApp) throws IOException {
		System.out.println("GOHOME called!!");
		FXMLLoader loader = new FXMLLoader();
		Scene scene;
		switch(role) {
		case Autista:
		case Impiegato:
			System.out.println("It's a driver!");
			loader.setLocation(App.class.getResource("employeesManagement/employeeAreaScreen.fxml"));
			AnchorPane employeeScreen = (AnchorPane) loader.load();
			scene = new Scene(employeeScreen);
			try {
				EmployeeAreaCtrl employeeCtrl = loader.getController();
				System.out.println("About to set main app...");
				employeeCtrl.setMainApp(mainApp);
				System.out.println("Main: "+mainApp);
				System.out.println("PrimaryStage: "+mainApp.getPrimaryStage());
				System.out.println("Scene: "+scene);
				mainApp.getPrimaryStage().setScene(scene);
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
			
			break;
		case Impiegato_amministrativo:
			loader.setLocation(App.class.getResource("linesManagement/administrativeAreaScreen.fxml"));
			AnchorPane adminScreen = (AnchorPane) loader.load();
			scene = new Scene(adminScreen);
			mainApp.getPrimaryStage().setScene(scene);
			EmployeeAreaCtrl adminCtrl = loader.getController();
			adminCtrl.setMainApp(mainApp);
			break;
		case Addetto_ai_mezzi:
			loader.setLocation(App.class.getResource("vehiclesManagement/vehicleAreaScreen.fxml"));
			AnchorPane vehicleScreen = (AnchorPane) loader.load();
			scene = new Scene(vehicleScreen);
			mainApp.getPrimaryStage().setScene(scene);
			VehicleAreaCtrl vehicleCtrl = loader.getController();
			vehicleCtrl.setMainApp(mainApp);
			break;

		case Addetto_agli_impiegati:
			loader.setLocation(App.class.getResource("employeesManagement/HRAreaScreen.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			scene = new Scene(anchorPane);
			System.out.println(mainApp.getPrimaryStage());
			mainApp.getPrimaryStage().setScene(scene);
			HRAreaCtrl ctrl = loader.getController();
			ctrl.setMainApp(mainApp);
			break;
		default:
			System.out.println("No such role");
			break;
		}
	}
}
