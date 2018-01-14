package it.metallicdonkey.tcp.administrativeArea;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ListSearchLineCtrl {
	private App mainApp;
	@FXML
	private TableView<LineDataModel> lines;
	@FXML
	private TextField filter;
	@FXML
	private TableColumn<LineDataModel, String> nameColumn;
	@FXML
	private TableColumn<LineDataModel, String> startTerminalColumn;
	@FXML
	private TableColumn<LineDataModel, String> endTerminalColumn;
	@FXML
	private TableColumn<LineDataModel, String> editColumn;
	@FXML
	private TableColumn<LineDataModel, String> removeColumn;
	
	
	final ObservableList<LineDataModel> data = FXCollections.observableArrayList(
	    new LineDataModel("101", "Stazione Centrale", "Stadio"),
	    new LineDataModel("102", "Piazzale Giotto", "Stazione Centrale"),
	    new LineDataModel("806", "Politeama", "Mondello"),
	    new LineDataModel("108", "Politeama", "Ospedale Civico"),
	    new LineDataModel("534", "Piazzale Giotto", "Mondello")
	);
	
  @FXML
  private void initialize() {
  	// Initialization data
    nameColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("name"));
    startTerminalColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("startTerminal"));
    endTerminalColumn.setCellValueFactory(
        new PropertyValueFactory<LineDataModel, String>("endTerminal"));
  	System.out.println(data);
  	
  	// Initialization filter
    FilteredList<LineDataModel> filteredData = new FilteredList<>(data, p -> true);
    
    filter.textProperty().addListener((observable, oldValue, newValue) -> {
      filteredData.setPredicate(line -> {
          // If filter text is empty, display all persons.
          if (newValue == null || newValue.isEmpty()) {
              return true;
          }

          // Compare first name and last name of every person with filter text.
          String lowerCaseFilter = newValue.toLowerCase();
          	
          return line.getName().toLowerCase().contains(lowerCaseFilter) ||
          			 line.getStartTerminal().toLowerCase().contains(lowerCaseFilter) ||
          			 line.getEndTerminal().toLowerCase().contains(lowerCaseFilter);
      });
    });
    SortedList<LineDataModel> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(lines.comparatorProperty());

  	lines.setItems(sortedData);
  	
  	// Add actions!
  	
    editColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

    Callback<TableColumn<LineDataModel, String>, TableCell<LineDataModel, String>> cellFactory
            = //
            new Callback<TableColumn<LineDataModel, String>, TableCell<LineDataModel, String>>() {
        @Override
        public TableCell<LineDataModel, String> call(final TableColumn<LineDataModel, String> param) {
            final TableCell<LineDataModel, String> cell = new TableCell<LineDataModel, String>() {

                final Button btn = new Button("Modifica");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            LineDataModel line = getTableView().getItems().get(getIndex());
                            System.out.println(line.getName());
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

    Callback<TableColumn<LineDataModel, String>, TableCell<LineDataModel, String>> cellFactory2
            = //
            new Callback<TableColumn<LineDataModel, String>, TableCell<LineDataModel, String>>() {
        @Override
        public TableCell<LineDataModel, String> call(final TableColumn<LineDataModel, String> param) {
            final TableCell<LineDataModel, String> cell = new TableCell<LineDataModel, String>() {

                final Button btn = new Button("Rimuovi");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            LineDataModel line = getTableView().getItems().get(getIndex());
                            System.out.println(line.getName());
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
