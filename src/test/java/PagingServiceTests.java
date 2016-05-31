import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.RealSystem;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Taylor on 5/25/16.
 *
 * Holds test methods for PagingService
 */
public class PagingServiceTests {
    Connection connection;

    PagingService pagingService;

    @Before
    public void before() throws SQLException {
        Server server = Server.createTcpServer("-baseDir", "./data").start();

        connection = DriverManager.getConnection("jdbc:h2:" + server.getURL() + "/test", "", null);

        pagingService = new PagingService(connection);

        pagingService.createTables(connection);
    }

    /**
     *
     * Given: H2 server, database, and connection
     * When: new Person object created with all properties
     * Then: Person object successfully inserted into database and successfully selected from database
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void testPersonMethods() throws SQLException, InterruptedException {

        //Arrange
        PagingService service = new PagingService(connection);
        Person testPerson = new Person(1, "TEST", "test", "testEmail", "testCountry", "testIpAddress");

        //Act
        service.insertPerson(connection, testPerson);
        Person selectPerson = service.selectPerson(connection, 1);

        connection.close();

        //Assert
        assertThat(selectPerson.getId(), is(1));
    }

    /**
     * Populate database
     */
    @Test
    public void testPopulateDatabaseMethod() throws FileNotFoundException, SQLException {

        PagingService service = new PagingService(connection);

        service.populateDatabase(connection);

        Person person = service.selectPerson(connection, 1);

        connection.close();

        //assertThat(person, is("Martha"));
    }
/*
    @Test
    public void testSelectPeopleMethod() throws FileNotFoundException, SQLException {
        PagingService service = new PagingService(connection);

        service.populateDatabase(connection);

        ArrayList<Person> testPeopleList = service.selectPeople(connection);

        connection.close();

    }
*/
}
