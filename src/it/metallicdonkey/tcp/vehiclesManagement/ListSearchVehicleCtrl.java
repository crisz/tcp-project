package it.metallicdonkey.tcp.vehiclesManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperDeposit;
import it.metallicdonkey.tcp.db.DBHelperVehicle;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.StatusVehicle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListSearchVehicleCtrl {
	private App mainApp;
	@FXML
	private TableView<VehicleDataModel> vehicles;
	@FXML
	private TextField filter;
	@FXML
	private TableColumn<VehicleDataModel, String> id;
	@FXML
	private TableColumn<VehicleDataModel, String> seats;
	@FXML
	private TableColumn<VehicleDataModel, String> sSeats;
	@FXML
	private TableColumn<VehicleDataModel, String> hSeats;
	@FXML
	private TableColumn<VehicleDataModel, String> vLocation;
	@FXML
	private TableColumn<VehicleDataModel, String> editColumn;
	@FXML
	private TableColumn<VehicleDataModel, String> brokenColumn;
	@FXML
	private TableColumn<VehicleDataModel, String> removeColumn;

	public static VehicleDataModel selectedVehicle;

	ObservableList<VehicleDataModel> data;

	public ListSearchVehicleCtrl() throws SQLException {
		this.data = DBHelperVehicle.getInstance().getAllVehicles();
		System.out.println("Data loaded");
		System.out.println(data);
	}
	
	private void setButtons() {
		final ListSearchVehicleCtrl lsvc = this;
		// Add actions!

		editColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

		Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>> cellFactory
		= //
		new Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>>() {
			@Override
			public TableCell<VehicleDataModel, String> call(final TableColumn<VehicleDataModel, String> param) {
				final TableCell<VehicleDataModel, String> cell = new TableCell<VehicleDataModel, String>() {

					Image ive = new Image(getClass().getResourceAsStream("../icons/ve.png"));
					ImageView ve = new ImageView(ive);
					final Button btn1 = new Button("", ve);

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn1.setOnAction(event -> {
								VehicleDataModel vehicle = getTableView().getItems().get(getIndex());
								ListSearchVehicleCtrl.selectedVehicle = vehicle;
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(App.class.getResource("vehiclesManagement/ChangeVehicleScreen.fxml"));
								AnchorPane personalInfo;
								try {
									personalInfo = (AnchorPane) loader.load();

									Scene scene = new Scene(personalInfo);
									Stage stage = mainApp.getPrimaryStage();
									stage.setScene(scene);
									ChangeVehicleCtrl lsvCtrl = loader.getController();
									lsvCtrl.setMainApp(mainApp);


								} catch (IOException e) {
									e.printStackTrace();
								}
							});
							btn1.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px");
							ve.setFitWidth(43.5);
							ve.setFitHeight(24.0);
							setGraphic(btn1);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		editColumn.setCellFactory(cellFactory);
		// Action: broken
		brokenColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));


		Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>> cellFactory3
		= //
		new Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>>() {
			@Override
			public TableCell<VehicleDataModel, String> call(final TableColumn<VehicleDataModel, String> param) {
				final TableCell<VehicleDataModel, String> cell = new TableCell<VehicleDataModel, String>() {

					Image ibv = new Image(getClass().getResourceAsStream("../icons/bv.png"));
					ImageView bv = new ImageView(ibv);
					final Button btn2 = new Button("", bv);
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn2.setOnAction(event -> {
								VehicleDataModel vehicle = getTableView().getItems().get(getIndex());
								if(!vehicle.getVLocation().equals("Guasto")) {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									alert.setTitle("Confirmation Dialog");
									alert.setHeaderText("Sei sicuro di voler segnalare il veicolo come guasto?");
									alert.setContentText("Il veicolo con matricola " + vehicle.getId() + " verrà segnalato come guasto e non sarà utilizzabile fino a nuova comunicazione.");

									Optional<ButtonType> result = alert.showAndWait();
									if (result.get() == ButtonType.OK){
										try {
											DBHelperVehicle.getInstance().insertBrokenStartDay(vehicle.getVehicle());
										} catch (SQLException e) {
											e.printStackTrace();
											Alert a = new Alert(AlertType.WARNING);
											a.setTitle("Attenzione");
											a.setHeaderText("Impossibile aggiornare lo status");
											a.setContentText("Lo status non può essere aggiornato a causa di un errore durante la connessione con il DBMS");
											a.showAndWait();
										}
										vehicle.setVLocation("Guasto");
										ImageView nimv = new ImageView(new Image(getClass().getResourceAsStream("../icons/nbv.png")));
										nimv.setFitHeight(24.0);
										nimv.setFitWidth(24.0);
										Button button = (Button) event.getSource();
										button.setGraphic(nimv);
										lsvc.initialize();

									}

								}
								else {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									alert.setTitle("Confirmation Dialog");
									alert.setHeaderText("Sei sicuro di voler segnalare il veicolo come funzionante?");
									alert.setContentText("Il veicolo con matricola " + vehicle.getId() + " verrà segnalato come funzionante e sarà utilizzabile.");

									Optional<ButtonType> result = alert.showAndWait();
									if (result.get() == ButtonType.OK){
										try {
											DBHelperVehicle.getInstance().insertBrokenStartDay(vehicle.getVehicle());
										} catch (SQLException e) {
											e.printStackTrace();
											Alert a = new Alert(AlertType.WARNING);
											a.setTitle("Attenzione");
											a.setHeaderText("Impossibile aggiornare lo status");
											a.setContentText("Lo status non può essere aggiornato a causa di un errore durante la connessione con il DBMS");
											a.showAndWait();
										}
										try {
											DBHelperVehicle.getInstance().insertBrokenEndDay(vehicle.getVehicle());
											vehicle.setVLocation(""+DBHelperDeposit.getInstance().setLocation(vehicle.getVehicle()));
										} catch (SQLException e) {
											Alert a = new Alert(AlertType.WARNING);
											a.setTitle("Attenzione");
											a.setHeaderText("Impossibile aggiornare lo status");
											a.setContentText("Lo status non può essere aggiornato a causa di un errore durante la connessione con il DBMS");
											a.showAndWait();
										}

										ImageView nimv = new ImageView(new Image(getClass().getResourceAsStream("../icons/bv.png")));
										nimv.setFitHeight(24.0);
										nimv.setFitWidth(24.0);

										Button button = (Button) event.getSource();
										button.setGraphic(nimv);
										lsvc.initialize();
									}
								}
							});
							btn2.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px");
							bv.setFitWidth(24.0);
							bv.setFitHeight(24.0);
							VehicleDataModel vehicle = getTableView().getItems().get(getIndex());
							if(vehicle.getVLocation().equals("Guasto") || vehicle.getVehicle().getStatus() == StatusVehicle.BROKEN) {
								System.out.println("Broken vehicle");
								bv.setImage(new Image(getClass().getResourceAsStream("../icons/nbv.png")));
								getTableRow().setStyle("-fx-background-color: #e55e5e");
								System.out.println("Il veicolo con matricola "+vehicle.getId()+" è guasto");
							}
							else {
								bv.setImage(new Image(getClass().getResourceAsStream("../icons/bv.png")));
								System.out.println("Il veicolo con matricola "+vehicle.getId()+" NON è guasto");
								getTableRow().setStyle(null);
							}
							setGraphic(btn2);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		brokenColumn.setCellFactory(cellFactory3);

		// Action: remove

		removeColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

		Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>> cellFactory2
		= //
		new Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>>() {
			@Override
			public TableCell<VehicleDataModel, String> call(final TableColumn<VehicleDataModel, String> param) {
				final TableCell<VehicleDataModel, String> cell = new TableCell<VehicleDataModel, String>() {

					Image irv = new Image(getClass().getResourceAsStream("../icons/rv.png"));
					ImageView rv = new ImageView(irv);
					final Button btn3 = new Button("", rv);
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn3.setOnAction(event -> {
								VehicleDataModel vehicle = getTableView().getItems().get(getIndex());
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Confirmation Dialog");
								alert.setHeaderText("Sei sicuro di voler eliminare il veicolo?");
								alert.setContentText("Il veicolo con matricola " + vehicle.getId() + " verrà eliminato. Questa operazione è irreversibile.");

								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK){
									try {
										DBHelperVehicle.getInstance().removeVehicle(vehicle.getVehicle());
										data.remove(vehicle);
										lsvc.initialize();
									} catch (SQLException e) {
										alert = new Alert(AlertType.WARNING);
							            alert.initOwner(null);
							            alert.setTitle("Connection Information");
							            alert.setHeaderText("Connessione Non Disponibile");
							            alert.setContentText("Controlla la connessione e riprova.");
							            alert.showAndWait();
									}
								}
							});
							btn3.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px");
							rv.setFitWidth(24.0);
							rv.setFitHeight(24.0);
							setGraphic(btn3);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		removeColumn.setCellFactory(cellFactory2);
	}
	
	@FXML
	private void initialize() {
		// Initialization data
		id.setCellValueFactory(
				new PropertyValueFactory<VehicleDataModel, String>("id"));
		seats.setCellValueFactory(
				new PropertyValueFactory<VehicleDataModel, String>("seats"));
		hSeats.setCellValueFactory(
				new PropertyValueFactory<VehicleDataModel, String>("hSeats"));
		sSeats.setCellValueFactory(
				new PropertyValueFactory<VehicleDataModel, String>("sSeats"));
		vLocation.setCellValueFactory(
				new PropertyValueFactory<VehicleDataModel, String>("vLocation"));
		// Initialization filter
		FilteredList<VehicleDataModel> filteredData = new FilteredList<>(data, p -> true);

		filter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(v -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				this.setButtons();
				return v.getId().toLowerCase().contains(lowerCaseFilter) ||
						v.getVLocation().toLowerCase().contains(lowerCaseFilter);
			});
		});
		SortedList<VehicleDataModel> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(vehicles.comparatorProperty());

		vehicles.setItems(sortedData);
		this.setButtons();
	}

	@FXML
	public void goHome() throws IOException {
		Home.getHome(null).goHome(this.mainApp);
	}

	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}
}
