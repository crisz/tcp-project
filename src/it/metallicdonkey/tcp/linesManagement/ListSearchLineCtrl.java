package it.metallicdonkey.tcp.linesManagement;

import java.io.IOException;
import java.util.Optional;

import it.metallicdonkey.tcp.App;
import it.metallicdonkey.tcp.login.Home;
import it.metallicdonkey.tcp.models.Line;
import it.metallicdonkey.tcp.models.Stop;
import javafx.collections.FXCollections;
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
	public static LineDataModel selectedLine;
	
	ObservableList<LineDataModel> data;
	
  @FXML
  private void initialize() {
		Line l = new Line();
		l.setName("101");
		l.setStartTerminal(new Stop("Stazione Centrale"));
		l.setEndTerminal(new Stop("Stadio"));

		data =  FXCollections.observableArrayList(
		    new LineDataModel(l)
		);
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
								LineDataModel line = getTableView().getItems().get(getIndex());
								ListSearchLineCtrl.selectedLine = line;
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(App.class.getResource("vehicleArea/ChangeLineScreen.fxml"));                           
								AnchorPane personalInfo;
								try {
									personalInfo = (AnchorPane) loader.load();

									Scene scene = new Scene(personalInfo);
									Stage stage = mainApp.getPrimaryStage();
									stage.setScene(scene);
									ChangeLineCtrl lsvCtrl = loader.getController();
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

		// Action: remove 

		removeColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		final ListSearchLineCtrl lsvc = this;

		Callback<TableColumn<LineDataModel, String>, TableCell<LineDataModel, String>> cellFactory2
		= //
		new Callback<TableColumn<LineDataModel, String>, TableCell<LineDataModel, String>>() {
			@Override
			public TableCell<LineDataModel, String> call(final TableColumn<LineDataModel, String> param) {
				final TableCell<LineDataModel, String> cell = new TableCell<LineDataModel, String>() {

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
								LineDataModel line = getTableView().getItems().get(getIndex());
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Confirmation Dialog");
								alert.setHeaderText("Sei sicuro di voler eliminare il veicolo?");
								alert.setContentText("Il veicolo con matricola " + line.getName() + " verrà eliminato. Questa operazione è irreversibile.");

								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK){
									// TODO: implementare cancellazione su db
									data.remove(line);
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
