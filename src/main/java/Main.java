import java.io.FileNotFoundException;

/**
 * Created by Taylor on 5/25/16.
 *
 * This class is what starts the program
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        CSVParser.parse("people.csv");
    }
}
