package it.metallicdonkey.tcp.HRArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PaySalaryCtrl {
	@FXML
	TableView<EmployeeDataModel> mainTable;

	@FXML
	TableColumn<EmployeeDataModel, String> idColumn;

	@FXML
	TableColumn<EmployeeDataModel, String> roleColumn;

	@FXML
	TableColumn<EmployeeDataModel, Double> salaryColumn;

	@FXML
	TextField filter;

	@FXML
	Label total;
	private App mainApp;
	private EmployeeDataModel employee;

	final ObservableList<EmployeeDataModel> data = FXCollections.observableArrayList(
		    new EmployeeDataModel("0643436", "Addetto la personale", 2500),
		    new EmployeeDataModel("0641612", "Addetto ai mezzi", 2700),
		    new EmployeeDataModel("0651713", "Autista", 1800),
		    new EmployeeDataModel("0647813", "Addetto ai mezzi", 2700) );
	@FXML
	private void initialize() {
		// Initialization data
		idColumn.setCellValueFactory(
			new PropertyValueFactory<EmployeeDataModel, String>("id"));
		roleColumn.setCellValueFactory(
			new PropertyValueFactory<EmployeeDataModel, String>("role"));
		salaryColumn.setCellValueFactory(
			new PropertyValueFactory<EmployeeDataModel, Double>("salary"));
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
						employee.getRole().toLowerCase().contains(lowerCaseFilter) ||
						String.valueOf(employee.getSalary()).contains(lowerCaseFilter);
			});
		});
		SortedList<EmployeeDataModel> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(mainTable.comparatorProperty());
		mainTable.setItems(sortedData);

		// Init the totat label
		double totalSalary = 0;
		for(int i=0; i<mainTable.getItems().size(); i++) {
			totalSalary+= salaryColumn.getCellObservableValue(i).getValue();
		}

		total.setText(String.valueOf(totalSalary) + " €");

	}
	/*
	 * TODO Fix the back and home buttons
	 */
	@FXML
	public void goHome() throws IOException {
		Home.getHome(null).goHome(this.mainApp);
	}
	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}
	public void setModel(EmployeeDataModel employee) {
		this.employee = employee;
	}
}
