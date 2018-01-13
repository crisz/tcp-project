package it.metallicdonkey.tcp.vehicleArea;

import java.io.IOException;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.login.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	
	final ObservableList<VehicleDataModel> data = FXCollections.observableArrayList(
	    new VehicleDataModel("A320", 30, 140, 1, "214"),
	    new VehicleDataModel("B340", 20, 60, 1, "In circolazione"),
	    new VehicleDataModel("C120", 20, 70, 0, "041"),
	    new VehicleDataModel("A120", 30, 10, 0, "Guasto"),
	    new VehicleDataModel("A221", 50, 10, 2, "In circolazione")
	);
	
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
          	
          return v.getId().toLowerCase().contains(lowerCaseFilter) ||
          			 v.getVLocation().toLowerCase().contains(lowerCaseFilter);
      });
    });
    SortedList<VehicleDataModel> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(vehicles.comparatorProperty());

  	vehicles.setItems(sortedData);
  	
  	// Add actions!
  	
    editColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

    Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>> cellFactory
            = //
            new Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>>() {
        @Override
        public TableCell<VehicleDataModel, String> call(final TableColumn<VehicleDataModel, String> param) {
            final TableCell<VehicleDataModel, String> cell = new TableCell<VehicleDataModel, String>() {

                final Button btn = new Button("Modifica");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            VehicleDataModel line = getTableView().getItems().get(getIndex());
                            System.out.println(line.getId());
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

    Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>> cellFactory2
            = //
            new Callback<TableColumn<VehicleDataModel, String>, TableCell<VehicleDataModel, String>>() {
        @Override
        public TableCell<VehicleDataModel, String> call(final TableColumn<VehicleDataModel, String> param) {
            final TableCell<VehicleDataModel, String> cell = new TableCell<VehicleDataModel, String>() {

                final Button btn = new Button("Rimuovi");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            VehicleDataModel line = getTableView().getItems().get(getIndex());
                            System.out.println(line.getId());
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
