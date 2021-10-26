package fi.helsinki.ohtu.orgrekouservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
class OrgrekOuServiceApplicationTests {

	@Test
	void contextLoads() {



	}

}
