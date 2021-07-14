import java.sql.SQLException;

import com.hikari.test.BusinessApplication;
import com.hikari.test.DataSourceConfig;
import org.junit.jupiter.api.Test;

public class DataSourceTest {

  @Test
  public void should_be_metrics () throws SQLException {
    DataSourceConfig ds = new DataSourceConfig();
    BusinessApplication application = new BusinessApplication(ds.initDs());
    application.businessOperator();
  }

}