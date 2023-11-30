package org.powertester.database;

import static org.junit.Assert.fail;

import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.powertester.config.TestConfig;

@Slf4j
public class DBConnection {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();
  private static final String DB_URL = CONFIG.getString("DB_URL");
  private static final String DB_USER = CONFIG.getString("DB_USER");
  private static final String DB_PASSWORD = CONFIG.getString("DB_PASSWORD");
  private static final DBConnection INSTANCE = new DBConnection();

  private HikariDataSource dataSource;

  private DBConnection() {
    dataSource = getDataSource();
  }

  public static DBConnection getInstance() {
    return INSTANCE;
  }

  private HikariDataSource getDataSource() {
    try {
      if (dataSource == null) {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setMaximumPoolSize(20); // 20 connections
        dataSource.setMinimumIdle(10); // 10 connections
        dataSource.setConnectionTimeout(30000); // 30 seconds
        dataSource.setIdleTimeout(30000); // 30 seconds
        dataSource.setMaxLifetime(1800000); // 30 minutes
        dataSource.setLeakDetectionThreshold(30000); // 30 seconds
        dataSource.setPoolName("PowerTester");
      }
    } catch (Exception e) {
      log.error("Error initializing Hikari datasource", e);
      log.error("⚠ Cancelling test run since tests depend on Database Connection");
      System.exit(1);
    }

    log.info("Hikari datasource initialized");
    return dataSource;
  }

  public Connection getConnection() throws SQLException {
    Connection connection = dataSource.getConnection();
    try (Statement statement = connection.createStatement()) {
      statement.execute(CONFIG.getString("QUERY_TO_SET_SCHEMA_USER"));
      statement.execute(CONFIG.getString("QUERY_TO_SET_DATE_FORMAT"));
    } catch (Exception e) {
      throw new IllegalStateException("Error setting schema and date format", e);
    }
    return connection;
  }

  // Execute update query
  public void executeUpdate(String sql) {
    try (Connection connection = getConnection();
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (Exception e) {
      throw new IllegalStateException("Error executing update query" + sql, e);
    }
  }

  // Preferred option 1: Execute a prepared statement and return the resultSet data as a list of map
  // of column name and value
  public List<Map<String, String>> executePreparedStatement(String sql, String... parameters) {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      // Set the parameters
      int parameterIndex = 1;
      for (String parameter : parameters) {
        statement.setObject(parameterIndex++, parameter);
      }

      try (ResultSet resultSet = statement.executeQuery()) {
        return getResultListFromResultSet(resultSet);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Error executing prepared statement" + sql, e);
    }
  }

  private static List<Map<String, String>> getResultListFromResultSet(ResultSet resultSet)
      throws SQLException {
    List<Map<String, String>> resultList = new ArrayList<>();

    ResultSetMetaData metaData = resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();
    while (resultSet.next()) {
      Map<String, String> row = new HashMap<>();
      for (int i = 1; i <= columnCount; i++) {
        row.put(metaData.getColumnName(i), resultSet.getString(i));
      }
      resultList.add(row);
    }
    return resultList;
  }

  // Preferred option 2: Execute a query and return the resultSet data as a list of map of column
  // name and value
  public List<Map<String, String>> executeQuery(String sql) {
    try (Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {

      return getResultListFromResultSet(resultSet);
    } catch (Exception e) {
      throw new IllegalStateException("Error executing query" + sql, e);
    }
  }

  // Run stored procedure with parameters
  public Map<String, String> runStoredProcedure(String sql, String... outputParameters) {
    try (Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(sql)) {

      // Register output parameters
      int parameterIndex = 1;
      for (String outputParameter : outputParameters) {
        statement.setObject(parameterIndex++, outputParameter);
      }

      // Execute stored procedure
      statement.execute();

      // Get output parameters
      Map<String, String> resultMap = new HashMap<>();
      for (String outputParameter : outputParameters) {
        resultMap.put(outputParameter, statement.getString(outputParameter));
      }

      // Print result and return
      log.info("Stored procedure result: {}", resultMap);

      // Return map if not empty. Otherwise, fail this method.
      if (resultMap.isEmpty()) {
        fail("Stored procedure returned empty result");
      }

      return resultMap;
    } catch (Exception e) {
      throw new IllegalStateException("Error executing stored procedure" + sql, e);
    }
  }

  // Close connection pool
  public void closeConnectionPool() {
    log.info("Closing Hikari datasource pool (only once) at the end of the whole test run");
    dataSource.close();
  }
}
