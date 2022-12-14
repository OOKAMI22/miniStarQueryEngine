package qengine.program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.eclipse.rdf4j.query.algebra.Projection;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;

import com.opencsv.CSVWriter;

/**
 * Programme simple lisant un fichier de requête et un fichier de données.
 * 
 * <p>
 * Les entrées sont données ici de manière statique,
 * à vous de programmer les entrées par passage d'arguments en ligne de commande comme demandé dans l'énoncé.
 * </p>
 * 
 * <p>
 * Le présent programme se contente de vous montrer la voie pour lire les triples et requêtes
 * depuis les fichiers ; ce sera à vous d'adapter/réécrire le code pour finalement utiliser les requêtes et interroger les données.
 * On ne s'attend pas forcémment à ce que vous gardiez la même structure de code, vous pouvez tout réécrire.
 * </p>
 * 
 * @author Olivier Rodriguez <olivier.rodriguez1@umontpellier.fr>
 */
final class Main {
	static final String baseURI = null;

	/**
	 * Votre répertoire de travail où vont se trouver les fichiers à lire
	 */
	static final String workingDir = "data/";
	public static String outputFile;


	/**
	 * Fichier contenant les requêtes sparql
	 */
	//static String queryFile = workingDir + "sample_query.queryset";
	static String queryFile = workingDir + "STAR_ALL_workload.queryset";

	/**
	 * Fichier contenant des données rdf
	 */
	static String dataFile = workingDir + "100K.nt";

	// ========================================================================

	static String queries = queryFile;
	static String data = dataFile;
	static String output = "data.csv";
	static boolean jena = true;

	static int warm = 0;
	static boolean shuffle = false;

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());

		System.out.println("first pattern : " + patterns.get(0));
		System.out.println("object of the first pattern : " + patterns.get(0).getObjectVar().getValue());
		System.out.println("object of the first pattern : " + patterns.get(0).getObjectVar().getValue());
		System.out.println("predicate of the first pattern : " + patterns.get(0).getObjectVar().getValue());
		System.out.println("variables to project : ");

		// Utilisation d'une classe anonyme
		query.getTupleExpr().visit(new AbstractQueryModelVisitor<RuntimeException>() {

			public void meet(Projection projection) {
				System.out.println(projection.getProjectionElemList().getElements());
			}
		});
	}

	/**
	 * Entrée du programme
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("je suis modifié");
		
		long startTimeProgram = System.nanoTime();
		
		
		MainRDFHandler mrh = new MainRDFHandler();


		long startTimeDATA = System.nanoTime();
		parseData(mrh, queries);
		long stopTimeDATA = System.nanoTime();
		float timeData = (float)(stopTimeDATA - startTimeDATA)/1000000;
		
		long startTimeQUERY = System.nanoTime();
		ArrayList<String > querieStrings = parseQueries(mrh, data);
		
		
		// melanger les requête
		if(shuffle){
			Collections.shuffle(querieStrings);
		}

		// traiter les queries apres le shuffle
		MainParsedQuery mpq = new MainParsedQuery();
		SPARQLParser sparqlParser = new SPARQLParser();
		// pour stocker les reponse aux requête
		ArrayList<ArrayList<String>> myOutput = new ArrayList<ArrayList<String>>();

		// choisir un nombre de requetes et les traiter aléatoirement
		if(warm > 0){
			for(int i=0; i<warm; i++) {
				int random = new Random().nextInt(querieStrings.size());
				ParsedQuery query = sparqlParser.parseQuery(querieStrings.get(random), baseURI);
				mpq.parseAQuery(query,mrh.hex.dico) ; 
				mpq.evaluateAQuery(mrh.hex,myOutput);}
			
		}
		else {
			for (String queryString : querieStrings) {
				ParsedQuery query = sparqlParser.parseQuery(queryString.toString(), baseURI);
				mpq.parseAQuery(query, mrh.hex.dico);
				//System.out.println("Dico dans parse query"+mrh.hex.dico.map);
				mpq.evaluateAQuery(mrh.hex,myOutput);
				//processAQuery(query);
			}
			
		}
		
		long stopTimeQUERY = System.nanoTime();
		float timeQuery = (float)(stopTimeQUERY - startTimeQUERY)/1000000;
		float timeDico = mrh.timeDictionnaire()/1000000;
		float timeHex = mrh.timeHexa()/1000000;
		String nomDonnees = dataFile;
		String nomQuery = queryFile;
		int nbrTriples = mrh.cpt;
		int nbrQuery = MainParsedQuery.cpt;
		
		// afficher les resultats trouveées par Jena
		long startTimeJena = System.nanoTime();
		if (jena){
			JenaTest jenaTest = new JenaTest(dataFile);
			List<List<String>> jenaOutput = new ArrayList<>();
			for(String queryString:  querieStrings) {
				List<String> output = jenaTest.processAQuery(queryString.toString());
				System.out.println("JENA Result " + output);
				jenaOutput.add(output);
			}
			System.out.println(jenaTest.getErrors(jenaTest.CompareWithJena(jenaOutput,myOutput)));
			System.out.println(jenaTest.CompareWithJena(jenaOutput,myOutput));
			//System.out.println("myOutPut "+myOutput.toString() );
			//System.out.println("jenOutPut "+jenaOutput.toString() );
			//System.out.println(myOutput.get(0).contains(jenaOutput.get(0).get(0)));
		}
		long stopTimeJena = System.nanoTime();
		float timeJena = (float)(stopTimeJena - startTimeJena)/1000000;


		long stopTimeProgram = System.nanoTime();
		float timeProgram = (float)(stopTimeProgram - startTimeProgram)/1000000;
		int nbrIndex = Index.cpt;
		
		CSVWriter writer = new CSVWriter(new FileWriter(output, true));
		String[] record= (nomDonnees+","+nomQuery+","+nbrTriples+","+nbrQuery+","+timeData+","+timeQuery+","+timeDico+","+nbrIndex+","+timeHex+","+timeJena+","+timeProgram).split(",");
		writer.writeNext(record);
		writer.close();
	}
	

	// ========================================================================

	/**
	 * Traite chaque requête lue dans {@link #queryFile} avec {@link #processAQuery(ParsedQuery)}.
	 */
	private static ArrayList<String> parseQueries(MainRDFHandler mrh, String queryLink ) throws FileNotFoundException, IOException {
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

	/**
	 * Traite chaque triple lu dans {@link #dataFile} avec {@link MainRDFHandler}.
	 */
	private static void parseData(MainRDFHandler mrh, String dataLink) throws FileNotFoundException, IOException {

		try (Reader dataReader = new FileReader(dataFile)) {
			//On crée le handler
			
			// On va parser des données au format ntriples
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);
			
			
			// On utilise notre implémentation de handler
			rdfParser.setRDFHandler(mrh);
			
			
			// Parsing et traitement de chaque triple par le handler
			
			rdfParser.parse(dataReader, baseURI);
		    
			mrh.hex.dico.printDico();
			//System.out.println("SPO dans parse DATA"+mrh.hex.SPO.toString());
			
			//mrh.hex.printAllIndexes();

		}
	}
}
