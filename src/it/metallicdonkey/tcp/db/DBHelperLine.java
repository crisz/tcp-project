package it.metallicdonkey.tcp.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.metallicdonkey.tcp.linesManagement.LineDataModel;
import it.metallicdonkey.tcp.models.Line;
import it.metallicdonkey.tcp.models.Stop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBHelperLine {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelperLine instance;

	private DBHelperLine() throws SQLException {
		dbm.connect();
	}
	
	public static DBHelperLine getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBHelperLine();
		return instance;
	
	}
	
//bisogna vedere come identificare un capolinea rispetto alle normali fermate
	public Stop getTerminal(Line line, boolean first) {
		Stop stop = new Stop();
		try {
			if(first == true) {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=FIRST");
			} else {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=END");
			}
			ResultSet result = dbm.getResultSet();
			stop.setAddress(result.getString("Address"));
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return stop;
	}
	public ArrayList<Stop> getStops(Line line, boolean going) {
		ArrayList<Stop> stops = new ArrayList<>();
		try {
			if(going == true) {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=GOING");
			} else {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, lines_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine=" + line.getName() +
						" AND l.idLine=ls.Line_idLine AND ls.type=RETURN");
			}
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Stop stop = new Stop();
				stop.setAddress(resultSet.getString("Address"));
				stops.add(stop);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return stops;
	}
	public ObservableList<LineDataModel> getAllLines() {
		ArrayList<LineDataModel> lines = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM line");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Line line = new Line();
				line.setName(resultSet.getString("idLine"));
				line.setStartTerminal(this.getTerminal(line, true));
				line.setEndTerminal(this.getTerminal(line, false));
				line.setGoingStops(this.getStops(line, true));
				line.setReturnStops(this.getStops(line, false));
				lines.add(new LineDataModel(line));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<LineDataModel> dataLines = FXCollections.observableArrayList();
		return dataLines;
	}
	
	public int removeLine(Line l) {
		int result = dbm.executeUpdate("DELETE FROM tcp.line WHERE idLine='"+l.getName()+"'");
		return result;
	}
}
