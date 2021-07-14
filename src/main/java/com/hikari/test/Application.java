package com.hikari.test;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Application {

  public static void main (String[] args) throws SQLException {
    DataSourceConfig ds = new DataSourceConfig();
    final BusinessApplication application = new BusinessApplication(ds.initDs());
    application.businessOperator();
    hold();

  }

  private static void hold () {
    final Thread daemonThread = new Thread(() -> {
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    daemonThread.start();
  }
}
