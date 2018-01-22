package it.metallicdonkey.tcp.employeesManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Session;
import it.metallicdonkey.tcp.models.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	Label ultimoPagamento;

	@FXML
	Label total;
	private App mainApp;
	private EmployeeDataModel employee;
	ObservableList<EmployeeDataModel> data;
	
	@FXML
	private void initialize() {
		// Initialization data
		data = DBHelperEmployee.getInstance().getAllEmployees();
		
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
			System.out.println(i);
		}
		total.setText(String.valueOf(totalSalary) + " €");
		
		ArrayList<Payment> ar = DBHelperEmployee.getInstance().getPayments(Session.employee);
		if(ar.size()==0) {
			ultimoPagamento.setText("Non è mai stato effettuato un pagamento");
			return;
		}
		LocalDate last = ar.get(0).getDate();
		LocalDate now = LocalDate.now();
		if(now.getDayOfYear() - last.getDayOfYear() < 30) {
			ultimoPagamento.setText("L'ultimo pagamento è stato effettuato meno di 30 giorni fa, in data "+last+".\n Non è possibile effettuare un nuovo pagamento se non sono passati almeno 30 giorni.");
		}
		else {
			ultimoPagamento.setText("L'ultimo pagamento è stato effettuato più di 30 giorni fa, in data "+last);
		}
	}
	
	@FXML
	public void pay() throws SQLException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.initOwner(mainApp.getPrimaryStage());
    alert.setTitle("Avviso");
    alert.setHeaderText("Sei sicuro di voler effettuare il pagamento?");
    alert.setContentText(this.total.getText()+" verranno prelevati dal conto aziendale e verranno generati i mandati di pagamento per ogni impiegato.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			for(int i=0; i<data.size(); i++) {
				Payment p = new Payment();
				p.setDate(LocalDate.now());
				p.setIdEmployee(data.get(i).getEmployee().getId());
				p.setNetSalary(data.get(i).getNetSalary());
				DBHelperEmployee.getInstance().insertPayment(p, data.get(i).getEmployee());
			}
			Alert alert2 = new Alert(AlertType.INFORMATION);
	    alert2.initOwner(mainApp.getPrimaryStage());
	    alert2.setTitle("Avviso");
	    alert2.setHeaderText("Il pagamento è stato effettuato.");
	    alert2.setContentText("I mandati di pagamento sono stati generati.");
	    alert2.showAndWait();
		}
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
