import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Taylor on 5/25/16.
 */
public class CSVParser {

    /**
     * This method takes the name of a file to read and parses it into an ArrayList of Person instances
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Person> parse(String filename) throws FileNotFoundException {
        // Create an empty ArrayList of Person instances
        ArrayList<Person> personArrayList = new ArrayList<>();

        // create an instance of File for the file name passed in


        // Create an instance of Scanner to read the file
        Scanner scanFile = new Scanner(new File("people.csv")).useDelimiter(",");


        // read the first line of the file, but do nothing with the results.
        // We're doing this because the first line of the file contains column names and we don't want/need them.
        String firstLine = new String (scanFile.nextLine());


        // while the scanner has more lines do the following
        while(scanFile.hasNext()) {


            // Read the next line from the scanner and store it in a variable
            String textLine = scanFile.nextLine();

            //System.out.println(textLine);

            // Split the variable holding this line of data. Use comma as the delimiter.
            // note: there is one line of csv file that has a comma in the country name.
            // I'm ignoring that for the purpose of keeping this code simple.
            String[] splitLine = textLine.split(",");

            // Create a new instance of a Person object
            Person person = new Person();

            // set the person's ID using the split data from above
            person.setId(Integer.valueOf(splitLine[0]));

            // set the person's first name using the split data from above
            person.setFirstName(splitLine[1]);

            // set the person's last name using the split data from above
            person.setLastName(splitLine[2]);
            // set the person's email using the split data from above
            person.setEmail(splitLine[3]);
            // set the person's country using the split data from above
            person.setCountry(splitLine[4]);
            // set the person's ip address using the split data from above
            person.setIpAddress(splitLine[5]);

            // add the person to the array list of people
            personArrayList.add(person);
        }
        scanFile.close();

        // return the array list of people
        return personArrayList;

    }
}

