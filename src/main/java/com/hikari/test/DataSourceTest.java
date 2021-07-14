package com.hikari.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpServer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class DataSourceTest {

  public static HikariDataSource initDs () {
    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/testdb");
    config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    config.setPassword("123456");
    config.setUsername("root");
    config.setPoolName("hikariPool");
    config.setAutoCommit(false);
    return new HikariDataSource(config);
  }

  public static void main (String[] args) {
    final HikariDataSource ds = initDs();
    metricsDs(ds);
    try {
      mysqlOperator(ds);
    } catch (SQLException ignore) {
    }
  }

  private static void mysqlOperator (HikariDataSource init) throws SQLException {
    final Connection connection = init.getConnection();
    final PreparedStatement preparedStatement = connection.prepareStatement("insert into test set id = 2 ,name = 't1'");
    preparedStatement.execute();
    connection.commit();
  }

  private static void metricsDs (HikariDataSource ds) {
    //初始化prometheus exporter
    PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    try {
      ds.setMetricRegistry(prometheusRegistry);
      HttpServer server = HttpServer.create(new InetSocketAddress(1234), 0);
      server.createContext("/metrics", httpExchange -> {
        String response = prometheusRegistry.scrape();
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = httpExchange.getResponseBody()) {
          os.write(response.getBytes());
        }
      });
      new Thread(server::start).start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
