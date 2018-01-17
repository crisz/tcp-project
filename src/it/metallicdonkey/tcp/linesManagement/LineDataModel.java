package it.metallicdonkey.tcp.linesManagement;

import it.metallicdonkey.tcp.models.Line;
import javafx.beans.property.SimpleStringProperty;

public class LineDataModel {
  private final SimpleStringProperty name;
  private final SimpleStringProperty startTerminal;
  private final SimpleStringProperty endTerminal;
  private Line line;

  private LineDataModel(String name, String startTerminal, String endTerminal) {
    this.name = new SimpleStringProperty(name);
    this.startTerminal = new SimpleStringProperty(startTerminal);
    this.endTerminal = new SimpleStringProperty(endTerminal);
  }
  public LineDataModel(Line l) {
  	this.setLine(l);
    this.name = new SimpleStringProperty(l.getName());
    this.startTerminal = new SimpleStringProperty(l.getStartTerminal().getAddress());
    this.endTerminal = new SimpleStringProperty(l.getEndTerminal().getAddress());
  }

	public String getName() {
		return name.get();
	}

	public String getStartTerminal() {
		return startTerminal.get();
	}

	public String getEndTerminal() {
		return endTerminal.get();
	}

	public String getEdit() {
		return "edit";
	}

	public String getDelete() {
		return "delete";
	}
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
}
