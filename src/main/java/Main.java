import org.h2.tools.Server;
import org.omg.CORBA.IDLTypeOperations;
import org.omg.PortableInterceptor.INACTIVE;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Taylor on 5/25/16.
 *
 * This class is what starts the program
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        //CSVParser.parse("people.csv");

        Server server = Server.createTcpServer("-baseDir", "./data").start();
        Connection connection = DriverManager.getConnection("jdbc:h2:" + server.getURL() + "/main", "", null);

        PagingService pagingService = new PagingService(connection);
        pagingService.createTables(connection);

        pagingService.populateDatabase(connection);

        //GET endpoint for "/" webroot
        Spark.get(
                "/",
                (request, response) -> {

                    HashMap peopleMap = new HashMap();

                    ArrayList<Person> peopleArrayList = pagingService.selectPeople(connection);


                    int offset;

                    try {
                        String stringOffset = request.queryParams("offset");

                        offset = Integer.valueOf(stringOffset);

                    } catch (Exception e) {
                        offset = 0;
                    }

                    Integer backOffset = null;

                    if (offset != 0) {
                        backOffset = offset - 20;
                    }

                    peopleMap.put("backOffset", backOffset);

                    Integer nextOffset = null;

                    if (offset < peopleArrayList.size() - 20) {
                        nextOffset = offset + 20;
                    }
                    peopleMap.put("nextOffset", nextOffset);

                    ArrayList<Person> offsetPeopleArrayList = pagingService.selectPeople(connection, offset);

                    List peopleList = peopleArrayList.subList(offset, offset + 20);

                    peopleMap.put("people", peopleList);


                    return new ModelAndView(peopleMap, "index.mustache");

                },
                new MustacheTemplateEngine()
        );


        //GET endpoint for "/person"

        Spark.get(
                "/person",
                (request, response) -> {

                    HashMap personMap = new HashMap();

                    int inputId = Integer.valueOf(request.queryParams("id"));

                    Person selectedPerson = pagingService.selectPerson(connection, inputId);

                    personMap.put("person", selectedPerson);

                    return new ModelAndView(personMap, "person.mustache");
                },
                new MustacheTemplateEngine()
        );
    }
}
