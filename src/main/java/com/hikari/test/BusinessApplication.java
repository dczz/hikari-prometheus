package com.hikari.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class BusinessApplication {

  private final DataSource dataSource;

  public BusinessApplication (DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void businessOperator () throws SQLException {
    final Connection connection = dataSource.getConnection();
    final PreparedStatement preparedStatement = connection.prepareStatement("insert into test set name = 't1'");
    preparedStatement.execute();
    connection.commit();
  }

}
