package me.syncwrld.boosters.database.connector;

import com.google.common.base.Preconditions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.experimental.Tolerate;
import org.bukkit.configuration.ConfigurationSection;

public class DatabaseConnector {

  private final String dbType;
  private Connection connection;
  private String file;

  private String host;
  private String database;
  private String username;
  private String password;
  private String errorMessage = "Connection Failed";

  public DatabaseConnector(String dbType, String file) {
    this.dbType = dbType;
    this.file = file;
  }

  public DatabaseConnector(
      String dbType, String host, String database, String username, String password) {
    this.dbType = dbType;
    this.host = host;
    this.database = database;
    this.username = username;
    this.password = password;
  }

  public static DatabaseConnector withConfiguration(ConfigurationSection section) {
    String dbType = section.getString("type", "mysql");

    if ("sqlite".equalsIgnoreCase(dbType)) {
      return new DatabaseConnector(dbType, section.getString("file"));
    }

    String host = section.getString("host", "localhost");
    String database = section.getString("database");
    String username = section.getString("username");
    String password = section.getString("password");
    return new DatabaseConnector(dbType, host, database, username, password);
  }

  public boolean connect() {
    if ("mysql".equalsIgnoreCase(dbType)) {
      try {
        Preconditions.checkNotNull(host, "Database Host can't be null");
        Preconditions.checkNotNull(database, "Database Name can't be null");
        Preconditions.checkNotNull(username, "Database Username can't be null");
        Preconditions.checkNotNull(password, "Database Password can't be null");

        this.connection =
            DriverManager.getConnection(
                "jdbc:mysql://" + host + "/" + database + "?autoReconnect=true",
                username,
                password);
        return true;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else {
      try {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        return true;
      } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException(errorMessage, e);
      }
    }
  }

  public boolean connect(String errorMessage) {
    this.errorMessage = errorMessage;
    return connect();
  }

  public void disconnect() {
    if (isConnected()) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Tolerate
  public Connection getConnection() {
    return connection;
  }

  public boolean isConnected() {
    return connection != null;
  }

  public String getDatabaseType() {
    return dbType;
  }

  public String getFormattedType() {
    return dbType.equals("sqlite") ? "SQLite" : "MySQL";
  }

}
