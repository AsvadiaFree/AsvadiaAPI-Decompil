package fr.asvadia.api.common.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
  private final String host;
  
  private final String user;
  
  private final String password;
  
  private final String dbName;
  
  private final int port;
  
  private final String url;
  
  private Connection connection;
  
  public SQLConnection(String host, int port, String user, String password, String dbName) {
    this.host = host;
    this.user = user;
    this.password = password;
    this.dbName = dbName;
    this.port = port;
    this.url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
    connect();
  }
  
  public void connect() {
    try {
      this.connection = DriverManager.getConnection(this.url, this.user, this.password);
    } catch (SQLException e) {
      System.out.println("Error connexion to " + this.dbName);
    } 
  }
  
  public void close() {
    try {
      if (this.connection != null && !this.connection.isClosed())
        this.connection.close(); 
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } 
  }
  
  public Connection getConnection() {
    try {
      if (this.connection != null && !this.connection.isClosed())
        return this.connection; 
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } 
    connect();
    return this.connection;
  }
}
