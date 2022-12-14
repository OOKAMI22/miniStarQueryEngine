package qengine.program;

import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


public class MainTest {
    static final String baseURI = null;
    static final String workingDir = "data/";
    static String queryFile = workingDir + "STAR_ALL_workload.queryset";
    static String dataFile = workingDir + "100K.nt";



    private static ArrayList<String> parseQueries( String queryLink ) throws FileNotFoundException, IOException {
        /**
         * Try-with-resources
         *
         * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">Try-with-resources</a>
         */
        /*
         * On utilise un stream pour lire les lignes une par une, sans avoir à toutes les stocker
         * entièrement dans une collection.
         */
        ArrayList<String> querieStrings = new ArrayList<String>();

        try (Stream<String> lineStream = Files.lines(Paths.get(queryFile))) {
            SPARQLParser sparqlParser = new SPARQLParser();
            Iterator<String> lineIterator = lineStream.iterator();
            StringBuilder queryString = new StringBuilder();


            while (lineIterator.hasNext())
                /*
                 * On stocke plusieurs lignes jusqu'à ce que l'une d'entre elles se termine par un '}'
                 * On considère alors que c'est la fin d'une requête
                 */
            {
                String line = lineIterator.next();
                queryString.append(line);

                if (line.trim().endsWith("}")) {
                    ParsedQuery query = sparqlParser.parseQuery(queryString.toString(), baseURI);
                    // Traitement de la requête, à adapter/réécrire pour votre programme

                    querieStrings.add(queryString.toString());
                    queryString.setLength(0); // Reset le buffer de la requête en chaine vide
                }
            }
        }
        return querieStrings;
    }
    public static void main(String[] args) throws Exception {
        ArrayList<String > querieStrings = parseQueries(dataFile);
        JenaTest jenaTest = new JenaTest(dataFile);
        ArrayList<ArrayList<String>> jenaOutput = new ArrayList<ArrayList<String>>();
        for (String queryString : querieStrings) {
            List<String> output = jenaTest.processAQuery(queryString.toString());
            System.out.println("JENA Result " + output);

        }
    }
}
