package com.hikari.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class DataSourceConfig {

  public HikariDataSource initDs () {
    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/testdb");
    config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    config.setPassword("123456");
    config.setUsername("root");
    config.setPoolName("hikariPool");
    config.setAutoCommit(false);
    final HikariDataSource ds = new HikariDataSource(config);
    this.metricsDs(ds);
    return ds;
  }

  private void metricsDs (HikariDataSource ds) {
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
