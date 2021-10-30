package me.lenglet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

@SpringBootTest(classes = App.class)
@Import(SpringIntegrationTest.Config.class)
@TestPropertySource(properties = {
        "spring.liquibase.enabled=false",
})
public class SpringIntegrationTest {

    @TestConfiguration
    public static class Config {

        @Bean
        public DataSource mariaDbDockerDataSource() {
            return TestDatabase.getInstance().dataSource();
        }
    }

    @Test
    public void test() {
    }
}
