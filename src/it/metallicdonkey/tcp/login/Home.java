package it.metallicdonkey.tcp.login;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.administrativeArea.AdministrativeAreaCtrl;
import it.metallicdonkey.tcp.vehicleArea.VehicleAreaCtrl;
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

	public void goHome(App mainApp) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;
		switch(role) {
			case Autista:
			case Impiegato:
	      loader.setLocation(App.class.getResource("employeeArea/EmployeeAreaScreen.fxml"));
	      AnchorPane adminScreen = (AnchorPane) loader.load();
	  		scene = new Scene(adminScreen);
	      mainApp.getPrimaryStage().setScene(scene);
	      AdministrativeAreaCtrl adminCtrl = loader.getController();
	      adminCtrl.setMainApp(mainApp);
			break;
			case Addetto_ai_mezzi:;
	      loader.setLocation(App.class.getResource("vehicleArea/VehicleAreaScreen.fxml"));
	      AnchorPane vehicleScreen = (AnchorPane) loader.load();
	  		scene = new Scene(vehicleScreen);
	      mainApp.getPrimaryStage().setScene(scene);
	      VehicleAreaCtrl vehicleCtrl = loader.getController();
	      vehicleCtrl.setMainApp(mainApp);
			break;
			
			default:
				System.out.println("No such role");
			break;  
		}
	}
}