import br.com.demo.db.DbException;
import br.com.demo.repository.dao.implementation.DepartmentDaoJDBC;
import br.com.demo.repository.dao.implementation.SellerDaoJDBC;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import util.DepartmentCreator;
import util.SellerCreator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Testcontainers
public class SellerDaoTest {

    private static SellerDaoJDBC sellerDaoJDBC;

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

        sellerDaoJDBC = new SellerDaoJDBC(connection);
    }

    @Test
    public void findAllShouldReturnAListWithSizeGreaterThanZero() {

        Assertions.assertTrue(sellerDaoJDBC.findAll().size() > 0);
    }

    @Test
    public void findByIdShouldNotReturnOptionalEmptyWhenIdExists() {

        Assertions.assertFalse(sellerDaoJDBC.findById(1).isEmpty());
    }

    @Test
    public void insertShouldReturnIntegerGreaterThanZero() {

        Assertions.assertTrue(sellerDaoJDBC.insert(SellerCreator.insertSeller()) > 0);
    }

    @Test
    public void updateShouldNotThrow() {

        Assertions.assertDoesNotThrow(() -> sellerDaoJDBC.update(SellerCreator.updateSeller()));
    }

    @Test
    public void deleteShouldThrowDBExceptionWhenDepartmentIdDoesNotExists() {

        Assertions.assertThrows(DbException.class, () -> sellerDaoJDBC.deleteById(999));
    }

    @AfterAll
    public static void tearDown() {
        mySQLContainer.stop();
    }
}