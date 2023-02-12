import br.com.demo.db.DbException;
import br.com.demo.repository.dao.implementation.DepartmentDaoJDBC;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import util.DepartmentCreator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Testcontainers
public class DepartmentDaoTest {

    private static DepartmentDaoJDBC departmentDaoJDBC;

    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql")
            .withDatabaseName("example_db")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("db.sql");

    @BeforeAll
    public static void setUp() {

        Connection connection = null;
        Properties properties = new Properties();
        mySQLContainer.start();

        properties.setProperty("dburl", mySQLContainer.getJdbcUrl());
        properties.setProperty("user", mySQLContainer.getUsername());
        properties.setProperty("password", mySQLContainer.getPassword());

        try {
            connection = DriverManager.getConnection(properties.getProperty("dburl"), properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        departmentDaoJDBC = new DepartmentDaoJDBC(connection);
    }

    @Test
    public void findAllShouldReturnAListWithSizeGreaterThanZero() {

        Assertions.assertTrue(departmentDaoJDBC.findAll().size() > 0);
    }

    @Test
    public void findByIdShouldNotReturnOptionalEmptyWhenIdExists() {

        Assertions.assertFalse(departmentDaoJDBC.findById(1).isEmpty());
    }

    @Test
    public void insertShouldReturnIntegerGreaterThanZero() {

        Assertions.assertTrue(departmentDaoJDBC.insert(DepartmentCreator.insertDepartment()) > 0);
    }

    @Test
    public void updateShouldNotThrow() {

        Assertions.assertDoesNotThrow(() -> departmentDaoJDBC.update(DepartmentCreator.updateDepartment()));
    }

    @Test
    public void deleteShouldThrowDBExceptionWhenDepartmentIdAlreadyBeingUsed() {

        try {
            departmentDaoJDBC.deleteById(1);
        } catch (DbException e) {
            Assertions.assertTrue(e.getMessage().contains("a foreign key constraint fails"));
        }
    }

    @Test
    public void deleteShouldThrowDBExceptionWhenDepartmentIdDoesNotExists() {

        Assertions.assertThrows(DbException.class, () -> departmentDaoJDBC.deleteById(999));
    }

    @AfterAll
    public static void tearDown() {
        mySQLContainer.stop();
    }
}