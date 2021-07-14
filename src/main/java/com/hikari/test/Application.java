package com.hikari.test;

import java.sql.SQLException;

public class Application {

  public static void main (String[] args) throws SQLException {
    DataSourceConfig ds = new DataSourceConfig();
    final BusinessApplication application = new BusinessApplication(ds.initDs());
    application.businessOperator();
  }

}
