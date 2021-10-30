package me.lenglet;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.Map;

public class TestDatabase {

    private static TestDatabase INSTANCE = null;

    private final GenericContainer<?> mariaDbContainer = new GenericContainer<>(DockerImageName.parse("mariadb:10.6.4-focal"))
            .withEnv(Map.of(
                    "MARIADB_ROOT_PASSWORD", "jim_morrison",
                    "MARIADB_USER", "jim",
                    "MARIADB_PASSWORD", "morrison",
                    "MARIADB_DATABASE", "testdb"
            ))
            .withExposedPorts(3306);

    private TestDatabase() {
    }

    public static TestDatabase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestDatabase();
            INSTANCE.start();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    public void start() {
        mariaDbContainer.start();
    }

    public void stop() {
        mariaDbContainer.stop();
    }

    public String host() {
        return mariaDbContainer.getHost();
    }

    public Integer port() {
        return mariaDbContainer.getFirstMappedPort();
    }

    private void init() {
        try {

            final var database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(this.dataSource().getConnection()));
            final var liquibase = new Liquibase(
                    "/scripts/changeLog.yml",
                    new FileSystemResourceAccessor(new File(System.getProperty("user.dir"))),
                    database
            );
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            throw new IllegalStateException("Exception occurred initializing database", e);
        }
    }

    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.mariadb.jdbc.Driver")
                .username("jim")
                .password("morrison")
                .url("jdbc:mariadb://" + this.host() + ":" + this.port() + "/testdb")
                .build();
    }
}
