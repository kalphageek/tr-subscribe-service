package me.kalpha.trsubscribeservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class TrSubscribeServiceApplicationTests {
	@Autowired
	DataSource dataSource;

	@Test
	void contextLoads() throws SQLException {
		System.out.println("============================================================");
		System.out.println(">>>>>>> Hikari Datasource: {}" + dataSource.getConnection().getMetaData());
		System.out.println("============================================================");
	}

}
