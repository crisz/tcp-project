package it.metallicdonkey.tcp.HRArea;
import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableColumn<EmployeeDataModel, String> removeColumn;
	@FXML
	private TableColumn<EmployeeDataModel, String> setAbsenceOrEndAbsence;
	final ObservableList<EmployeeDataModel> data = FXCollections.observableArrayList(
		    new EmployeeDataModel("0643436", "Matteo", "Farina"),
		    new EmployeeDataModel("0641612", "Cristian", "Traina"),
		    new EmployeeDataModel("0651713", "Mauro", "Liuzzo"),
		    new EmployeeDataModel("0647813", "Giovanni", "Giglio") );
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
	            	final Button btn = new Button("Modifica");

	                @Override
	                public void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                        setText(null);
	                    } else {
	                        btn.setOnAction(event -> {
	                            EmployeeDataModel employee = getTableView().getItems().get(getIndex());
	                            System.out.println(employee.getFirstName());
	                        });
	                        setGraphic(btn);
	                        setText(null);
	                    }
	                }
	            };
	            return cell;
	        }
	    };
	    editColumn.setCellFactory(cellFactory);
	    // Action: remove
	    removeColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
	    Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>> cellFactory2
	    			= //
	    			new Callback<TableColumn<EmployeeDataModel, String>, TableCell<EmployeeDataModel, String>>() {
	    	@Override
	    	public TableCell<EmployeeDataModel, String> call(final TableColumn<EmployeeDataModel, String> param) {
	            final TableCell<EmployeeDataModel, String> cell = new TableCell<EmployeeDataModel, String>() {
	                final Button btn = new Button("Rimuovi");
	                @Override
	                public void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                        setText(null);
	                    } else {
	                        btn.setOnAction(event -> {
	                            EmployeeDataModel employee = getTableView().getItems().get(getIndex());
	                            System.out.println(employee.getFirstName());
	                        });
	                        setGraphic(btn);
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