package it.metallicdonkey.tcp.login;

import it.metallicdonkey.tcp.models.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Session {

  private final StringProperty matricola;
  private final StringProperty password;
  public static Employee employee;
  
  public Session() {
      this(null, null);
  }
  public Session(String matricola, String password) {
      this.matricola = new SimpleStringProperty(matricola);
      this.password = new SimpleStringProperty(password);
  }
  /**
   * 
   * @return matricola 
   */
  public String getMatricola() {
      return matricola.get();
  }
  
  /**
   * 
   * @param matricola: la matricola dell'impiegato
   * @see it.metallicdonkey.tcp.Login
   */
  public void setMatricola(String matricola) {
      this.matricola.set(matricola);
  }

  public StringProperty getPassword() {
      return password;
  }
  
  public void setPassword(String password) {
      this.password.set(password);
  }
}