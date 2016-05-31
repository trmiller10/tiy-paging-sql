import javax.management.relation.RelationSupport;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

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

    public void populateDatabase(Connection connection) throws FileNotFoundException, SQLException{

        CSVParser parser = new CSVParser();
        ArrayList<Person> parsedArrayList = parser.parse("people.csv");

        for (Person parsedPerson : parsedArrayList) {
            insertPerson(connection, parsedPerson);
        }
    }

    public ArrayList<Person> selectPeople(Connection connection) throws SQLException {
        ArrayList<Person> selectPeopleArrayList = new ArrayList<>();

        PreparedStatement prepStat = connection.prepareStatement("SELECT * FROM people ORDER BY ID ASC");

        ResultSet resultSet = prepStat.executeQuery();

        while (resultSet.next()) {
            Person readPerson = new Person(
                    resultSet.getInt("ID"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("EMAIL"),
                    resultSet.getString("COUNTRY"),
                    resultSet.getString("IPADDRESS")
            );
            selectPeopleArrayList.add(readPerson);
        }
        return selectPeopleArrayList;
    }
    public ArrayList<Person> selectPeople(Connection connection, int offset) throws SQLException {
        ArrayList<Person> selectPeopleArrayList = new ArrayList<>();

        PreparedStatement prepStat = connection.prepareStatement("SELECT * FROM people ORDER BY ID ASC LIMIT 20 OFFSET ?");
        prepStat.setInt(1, offset);

        ResultSet resultSet = prepStat.executeQuery();

        while (resultSet.next()) {
            Person readPerson = new Person(
                    resultSet.getInt("ID"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("EMAIL"),
                    resultSet.getString("COUNTRY"),
                    resultSet.getString("IPADDRESS")
            );
            selectPeopleArrayList.add(readPerson);
        }
        return selectPeopleArrayList;
    }
}

