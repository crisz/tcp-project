package it.metallicdonkey.tcp.employeesManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.db.DBHelperEmployee;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Session;
import it.metallicdonkey.tcp.models.AbsenceInterval;
import it.metallicdonkey.tcp.models.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PersonalInfoCtrl {
	@FXML
	private Label matricola;
	@FXML
	private Label password;
	@FXML
	private Label nome;
	@FXML
	private Label cognome;
	@FXML
	private Label email;
	@FXML
	private Label residenza;
	@FXML
	private Label datanascita;
	@FXML
	private Label ruolo;
	@FXML
	private Label turno;
	@FXML
	private Label stipendiolordo;
	@FXML
	private ListView<String> assenze;
	@FXML
	private ListView<String> pagamento;

	private App mainApp;
	@FXML
	private void initialize() {
		this.matricola.setText(Session.employee.getId());
		this.nome.setText(Session.employee.getFirstName());
		this.cognome.setText(Session.employee.getLastName());
		this.email.setText(Session.employee.getEmail());
		this.residenza.setText(Session.employee.getAddress());
		this.datanascita.setText(Session.employee.getBirthDate().toString());
		this.ruolo.setText(Session.employee.getRole().name().replaceAll("_", " "));
		this.turno.setText(Session.employee.getWorkshift().name());
		this.stipendiolordo.setText(String.valueOf(Session.employee.getSalary()));

		ArrayList<AbsenceInterval> absences = DBHelperEmployee.getInstance().getAbsenceInterval(Session.employee);
		ObservableList<String> ol;
		ArrayList<String> als = new ArrayList<>();
		for(int i=0; i<absences.size(); i++) {
			String interval = "";
			LocalDate start = absences.get(i).getStartDay();
			LocalDate end = absences.get(i).getEndDay();
			interval+=start.getDayOfMonth()+"/"+start.getMonthValue()+"/"+start.getYear();
			if(end.getYear() != 1970) {
				interval+=" - ";
				interval+=end.getDayOfMonth()+"/"+end.getMonthValue()+"/"+end.getYear();
			}
			als.add(interval);
		}
		ol = FXCollections.observableArrayList(als);
		this.assenze.setItems(ol);
		fullfillPayment();
	}

	private void fullfillPayment() {
		ArrayList<Payment> payments = DBHelperEmployee.getInstance().getPayments(Session.employee);
		ObservableList<String> ol;
		ArrayList<String> als = new ArrayList<>();
		for(int i=0; i<payments.size(); i++) {
			LocalDate date = payments.get(i).getDate();
			als.add(date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear()+" - "+payments.get(i).getNetSalary()+"€");
		}
		ol = FXCollections.observableArrayList(als);
		this.pagamento.setItems(ol);

	}

	@FXML
	public void goHome() throws IOException {
		Home.getHome(null).goHome(this.mainApp);
	}

	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}
}

