package it.metallicdonkey.tcp.employeesManagement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.StatusEmployee;
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

public class ListSearchEmployeeCtrl {
	private App mainApp;
	@FXML
	private TableView<EmployeeDataModel> employees;
	@FXML
	private TextField filter;
	@FXML
	private TableColumn<EmployeeDataModel, String> firstNameColumn;
	@FXML
	private TableColumn<EmployeeDataModel, String> lastNameColumn;
	@FXML
	private TableColumn<EmployeeDataModel, String> idColumn;
	@FXML
	private TableColumn<EmployeeDataModel, String> editColumn;
	@FXML
	private TableColumn<EmployeeDataModel, String> absenceColumn;
	@FXML
	private TableColumn<EmployeeDataModel, String> removeColumn;
	@FXML
	private TableColumn<EmployeeDataModel, String> setAbsenceOrEndAbsence;
	ObservableList<EmployeeDataModel> data;
	public ListSearchEmployeeCtrl() throws SQLException {
		 this.data = DBHelperEmployee.getInstance().getAllEmployees();
	}
	
	public static EmployeeDataModel selectedEmployee;
	@FXML
	private void initialize() {
		// Initialization data
		idColumn.setCellValueFactory(
			new PropertyValueFactory<EmployeeDataModel, String>("id"));
		firstNameColumn.setCellValueFactory(
			new PropertyValueFactory<EmployeeDataModel, String>("firstName"));
		lastNameColumn.setCellValueFactory(
			new PropertyValueFactory<EmployeeDataModel, String>("lastName"));
		System.out.println(data);
		// Initialization filter
		FilteredList<EmployeeDataModel> filteredData = new FilteredList<>(data, p -> true);
		filter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(employee -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				return employee.getId().toLowerCase().contains(lowerCaseFilter) ||
						employee.getFirstName().toLowerCase().contains(lowerCaseFilter) ||
						employee.getLastName().toLowerCase().contains(lowerCaseFilter);
			});
		});
		SortedList<EmployeeDataModel> sortedData = new SortedList<>(filteredData);
		System.out.println(employees);
		sortedData.comparatorProperty().bind(employees.comparatorProperty());
		employees.setItems(sortedData);
		// Add actions!
		editColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));




		Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>> cellFactory
		= //
		new Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>>() {
			@Override
			public TableCell<EmployeeDataModel, String> call(final TableColumn<EmployeeDataModel, String> param) {
				final TableCell<EmployeeDataModel, String> cell = new TableCell<EmployeeDataModel, String>() {

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
								EmployeeDataModel employee = getTableView().getItems().get(getIndex());
								ListSearchEmployeeCtrl.selectedEmployee = employee;
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(App.class.getResource("employeesManagement/ChangeEmployeeScreen.fxml"));                           
								AnchorPane personalInfo;
								try {
									personalInfo = (AnchorPane) loader.load();
									
									Scene scene = new Scene(personalInfo);
									Stage stage = mainApp.getPrimaryStage();
									stage.setScene(scene);
									ChangeEmployeeCtrl lsvCtrl = loader.getController();
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
		
		// Action: absence
		absenceColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));


		Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>> cellFactory3
		= //
		new Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>>() {
			@Override
			public TableCell<EmployeeDataModel, String> call(final TableColumn<EmployeeDataModel, String> param) {
				final TableCell<EmployeeDataModel, String> cell = new TableCell<EmployeeDataModel, String>() {

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
								EmployeeDataModel employee = getTableView().getItems().get(getIndex());
								if(!(employee.getEmployee().getStatus() == StatusEmployee.ABSENT)) {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									alert.setTitle("Confirmation Dialog");
									alert.setHeaderText("Sei sicuro di voler segnalare l'impiegato come assente?");
									alert.setContentText("L'impiegato con matricola " + employee.getId() + " verrà segnalato come assente fino a nuova comunicazione.");

									Optional<ButtonType> result = alert.showAndWait();
									if (result.get() == ButtonType.OK){
										try {
											DBHelperEmployee.getInstance().insertAbsenceStartDay(employee.getEmployee());
										} catch (SQLException e) {
											e.printStackTrace();
											Alert a = new Alert(AlertType.WARNING);
											a.setTitle("Attenzione");		
											a.setHeaderText("Impossibile aggiornare lo status");
											a.setContentText("Lo status non può essere aggiornato a causa di un errore durante la connessione con il DBMS");
											a.showAndWait();
										}
										employee.getEmployee().setStatus(StatusEmployee.ABSENT);
										ImageView nimv = new ImageView(new Image(getClass().getResourceAsStream("../icons/nbv.png")));
										nimv.setFitHeight(24.0);
										nimv.setFitWidth(24.0);
										Button button = (Button) event.getSource();
										button.setGraphic(nimv);
										employees.getColumns().get(0).setVisible(false);
										employees.getColumns().get(0).setVisible(true);
									}

								}
								else {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									alert.setTitle("Confirmation Dialog");
									alert.setHeaderText("Sei sicuro di voler rendere disponibile l'impiegato?");
									alert.setContentText("L'impiegato con matricola " + employee.getId() + " verrà segnalato come disponibile.");

									Optional<ButtonType> result = alert.showAndWait();
									if (result.get() == ButtonType.OK){
										ImageView nimv = new ImageView(new Image(getClass().getResourceAsStream("../icons/bv.png")));
										nimv.setFitHeight(24.0);
										nimv.setFitWidth(24.0);
										Button button = (Button) event.getSource();
										button.setGraphic(nimv);
										employees.getColumns().get(0).setVisible(false);
										employees.getColumns().get(0).setVisible(true);
									}
								}
							});
							btn2.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px");
							bv.setFitWidth(24.0);
							bv.setFitHeight(24.0);
							EmployeeDataModel employee = getTableView().getItems().get(getIndex());
							if(employee.getEmployee().getStatus() == StatusEmployee.ABSENT) {
								System.out.println("Broken employee");
								bv.setImage(new Image(getClass().getResourceAsStream("../icons/nbv.png")));
							}
							setGraphic(btn2);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		absenceColumn.setCellFactory(cellFactory3);

		// Action: remove 

		removeColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		final ListSearchEmployeeCtrl lsvc = this;

		Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>> cellFactory2
		= //
		new Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>>() {
			@Override
			public TableCell<EmployeeDataModel, String> call(final TableColumn<EmployeeDataModel, String> param) {
				final TableCell<EmployeeDataModel, String> cell = new TableCell<EmployeeDataModel, String>() {

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
								EmployeeDataModel employee = getTableView().getItems().get(getIndex());
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Confirmation Dialog");
								alert.setHeaderText("Sei sicuro di voler eliminare l'impiegato?");
								alert.setContentText("L'impiegato con matricola " + employee.getId() + " verrà eliminato. Questa operazione è irreversibile.");

								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK){
									DBHelperEmployee.getInstance().removeEmployee(employee.getEmployee());
									data.remove(employee);
									lsvc.initialize();
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
	public void goHome() throws IOException {
		Home.getHome(null).goHome(this.mainApp);
	}
	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}

}