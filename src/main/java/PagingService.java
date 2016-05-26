import javax.management.relation.RelationSupport;
import java.sql.*;

/**
 * Created by Taylor on 5/25/16.
 *
 * This class holds the various methods that Main class will use
 */
public class PagingService {

    private final Connection connection;

    public PagingService(Connection connection) { this.connection = connection; }

    //initialize database method
    public void createTables(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS people");
        statement.execute("CREATE TABLE IF NOT EXISTS people (id IDENTITY, firstName VARCHAR, lastName VARCHAR, email VARCHAR, country VARCHAR, ipAddress VARCHAR)");
    }

    public void insertPerson(Connection connection, Person person) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO people VALUES (NULL, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setString(3, person.getEmail());
        preparedStatement.setString(4, person.getCountry());
        preparedStatement.setString(5, person.getIpAddress());

        preparedStatement.execute();

    }

    public Person selectPerson(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM people WHERE id = ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            Person person = new Person(
                    resultSet.getInt("ID"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("EMAIL"),
                    resultSet.getString("COUNTRY"),
                    resultSet.getString("IPADDRESS")
            );
            return person;
        }
        return null;
    }
}

