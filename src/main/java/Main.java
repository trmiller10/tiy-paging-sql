import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

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


        //GET endpoint for "/" webroot
        Spark.get(
                "/",
                (request, response) -> {

                    HashMap m = new HashMap();

                    String string = "u did it champ";
                    m.put("string", string);


                return new ModelAndView(m, "index.mustache");

                },

                new MustacheTemplateEngine()
        );


        //GET endpoint for "/person"



    }
}
