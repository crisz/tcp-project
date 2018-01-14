package it.metallicdonkey.tcp;

import java.io.IOException;

//import com.mysql.jdbc.Driver;

import it.metallicdonkey.tcp.login.LoginCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TCP for employees");
		try {
			initRootLayout();
			showLogin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initRootLayout() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource("RootLayout.fxml"));
		rootLayout = (BorderPane) loader.load();

		Scene scene = new Scene(rootLayout);
    primaryStage.setScene(scene);
    primaryStage.show();
	}

	public void showLogin() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("login/LoginScreen.fxml"));
    AnchorPane loginScreen = (AnchorPane) loader.load();

    // Set person overview into the center of root layout.
    rootLayout.setCenter(loginScreen);

    LoginCtrl loginCtrl = loader.getController();
    loginCtrl.setMainApp(this);
	}

  public Stage getPrimaryStage() {
    return primaryStage;
  }

	public static void main(String[] args) {
//  	try {
//			Driver d = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
//			DriverManager.registerDriver(d);
//			DriverManager.getConnection("jdbc:mysql://<host>:<porta>/<baseDati>");
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		launch(args);
	}
}
