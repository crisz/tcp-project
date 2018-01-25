package it.metallicdonkey.tcp.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.metallicdonkey.tcp.linesManagement.LineDataModel;
import it.metallicdonkey.tcp.models.Check;
import it.metallicdonkey.tcp.models.Line;
import it.metallicdonkey.tcp.models.Match;
import it.metallicdonkey.tcp.models.Stop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBHelperLine {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "tcp");
	private static DBHelperLine instance;

	private DBHelperLine() {
		dbm.connect();
	}

	public static DBHelperLine getInstance() {
		if(instance != null) {
			return instance;
		}
		instance = new DBHelperLine();
		return instance;

	}

	public Line getLineById(String id) throws SQLException {
		return getAllLinesArray("idLine = '" + id + "'").get(0);
	}

	public ArrayList<Line> getAllLinesArray(String clause) throws SQLException{
		ArrayList<Line> lines = new ArrayList<>();

		clause = (clause == null)? "TRUE":clause;

		dbm.executeQuery("SELECT * FROM line WHERE " + clause);
		ResultSet resultSet = dbm.getResultSet();
		while(resultSet.next()) {
			Line line = new Line();
			line.setName(resultSet.getString("idLine"));
			line.setPriority(resultSet.getInt("Priority"));
			line.setStartTerminal(this.getTerminal(line, true));
			line.setEndTerminal(this.getTerminal(line, false));
			line.setGoingStops(this.getStops(line, true));
			line.setReturnStops(this.getStops(line, false));
			lines.add(line);
		}
		return lines;
	}

//bisogna vedere come identificare un capolinea rispetto alle normali fermate
	public Stop getTerminal(Line line, boolean first) {
		Stop stop = new Stop();
		try {
			if(first == true) {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, line_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine='" + line.getName() +
						"' AND l.idLine=ls.Line_idLine AND ls.type='FIRST'");
			} else {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, line_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine='" + line.getName() +
						"' AND l.idLine=ls.Line_idLine AND ls.type='END'");
			}
			ResultSet result = dbm.getResultSet();
			if(!result.next()) {
				Stop s = new Stop();
				s.setAddress("");
				return s;
			}
			stop.setAddress(result.getString("s.Address"));
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return stop;
	}
	public ArrayList<Stop> getStops(Line line, boolean going) {
		ArrayList<Stop> stops = new ArrayList<>();
		try {
			if(going == true) {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, line_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine='" + line.getName() +
						"' AND l.idLine=ls.Line_idLine AND ls.type = 'GOING'"+
						"ORDER BY ls.sequenceNumber");
			} else {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, line_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine='" + line.getName() +
						"' AND l.idLine=ls.Line_idLine AND ls.type='RETURN'"+
						"ORDER BY ls.sequenceNumber");
			}
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			int i=-1;
			while(resultSet.next()) {
				Stop stop = new Stop();
				stop.setAddress(resultSet.getString("s.Address"));
				stops.add(++i, stop);
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
				System.out.println("Line!");
				Line line = new Line();
				line.setName(resultSet.getString("idLine"));
				line.setPriority(resultSet.getInt("Priority"));
				line.setStartTerminal(this.getTerminal(line, true));
				line.setEndTerminal(this.getTerminal(line, false));
				line.setGoingStops(this.getStops(line, true));
				line.setReturnStops(this.getStops(line, false));
				lines.add(new LineDataModel(line));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<LineDataModel> dataLines = FXCollections.observableArrayList(lines);
		return dataLines;
	}

	public void insertLine(Line l) throws SQLException {
		String query = " INSERT INTO tcp.line () values (?, ?)";

		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString(1, l.getName());
		preparedStmt.setInt(2, l.getPriority());
		preparedStmt.execute();

		// Create the stops in case they don't exist.
		insertStop(l.getStartTerminal());
		insertStop(l.getEndTerminal());

		ArrayList<Stop> going = l.getGoingStops();
		ArrayList<Stop> ret = l.getReturnStops();

		for(int i=0; i<going.size(); i++) {
			insertStop(going.get(i));
		}

		for(int i=0; i<ret.size(); i++) {
			insertStop(ret.get(i));
		}

		connectLine(l);

	}

	private void connectLine(Line l) throws SQLException {

		int sequence = 0;

		insertLineHasStop(l.getName(), getIdStop(l.getStartTerminal().getAddress()), "FIRST", ++sequence);
		ArrayList<Stop> going = l.getGoingStops();
		ArrayList<Stop> ret = l.getReturnStops();

		for(int i=0; i<going.size(); i++) {
			insertLineHasStop(l.getName(), getIdStop(going.get(i).getAddress()), "GOING", ++sequence);
		}
		insertLineHasStop(l.getName(), getIdStop(l.getEndTerminal().getAddress()), "END", ++sequence);
		for(int i=0; i<ret.size(); i++) {
			insertLineHasStop(l.getName(), getIdStop(ret.get(i).getAddress()), "RETURN", ++sequence);
		}
		insertLineHasStop(l.getName(), getIdStop(l.getStartTerminal().getAddress()), "FIRST", ++sequence);
	}

	private void insertLineHasStop(String id, int idStop, String type, int sequence) throws SQLException {
		String query = "INSERT INTO tcp.line_has_stop () values (?, ?, ?, ?)";
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString(1, id);
		preparedStmt.setInt(2, idStop);
		preparedStmt.setString(3, type);
		preparedStmt.setInt(4, sequence);
		preparedStmt.execute();
	}

	public int getIdStop(String address) throws SQLException {
		dbm.executeQuery("SELECT idStop from tcp.stop WHERE stop.Address = '"+address+"'");
		ResultSet result = dbm.getResultSet();
		if(!dbm.getResultSet().next()) {
			return -1;
		}
		return result.getInt("idStop");
	}

	private void insertStop(Stop s) throws SQLException {
		int id = this.getIdStop(s.getAddress());
		if(id == -1) {	// If it's a new Stop
			String query = "INSERT INTO tcp.stop (Address) values (?)";
			PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
			preparedStmt.setString(1, s.getAddress());
			preparedStmt.execute();
		}
	}

	public int removeLine(Line l) {
		int removed1 = -1;
		int removed2 = -1;
		int result = -1;
		removed1 = dbm.executeUpdate("DELETE FROM tcp.match WHERE Line_idLine='"+l.getName()+"'");
		removed2 = dbm.executeUpdate("DELETE FROM tcp.line_has_stop WHERE Line_idLine = '"+
				l.getName()+"'");
		if(removed1 < 0 || removed2 < 0)
			return -1;
		result = dbm.executeUpdate("DELETE FROM tcp.line WHERE idLine='"+l.getName()+"'");
		return result;
	}

	public ArrayList<String> getIds() throws SQLException{
		ArrayList<String> allIds = new ArrayList<>();
		dbm.executeQuery("SELECT idLine FROM tcp.line");
		ResultSet result = dbm.getResultSet();
		while(result.next()) {
			allIds.add(result.getString("idLine"));
		}
		return allIds;
	}
}
