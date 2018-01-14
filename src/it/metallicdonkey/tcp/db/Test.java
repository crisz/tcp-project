package it.metallicdonkey.tcp.db;
import it.metallicdonkey.tcp.models.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Test {
	public static void main(String[] args) {
		DBHelper db = DBHelper.getInstance();
		ArrayList<Employee> employees = new ArrayList<>();
		Iterator<Employee> it;
		
		if(db.getAllEmployees() == null) {
			System.out.println("No Data");
		}
		
	}
}
