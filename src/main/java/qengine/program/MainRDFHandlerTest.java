package qengine.program;

/* import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;


import junit.framework.TestCase;

public class MainRDFHandlerTest extends TestCase {
	
	String baseURI = null;		
	String workingDir = "data/";	
	String dataFile = workingDir + "sample_data.nt";
	 
	
	 
	// TEST DE LA METHODE handleStatement, on compare l'array attendu sous forme de string
	public void testHandleStatementStatement() throws FileNotFoundException, IOException {
		
		try (Reader dataReader = new FileReader(dataFile)) {
			MainRDFHandler mrh = new MainRDFHandler();
			
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);
			rdfParser.setRDFHandler(mrh);
			rdfParser.parse(dataReader, baseURI);
			
			
			String expectedDico = "{http://db.uwaterloo.ca/~galuc/wsdbm/User0=1, http://schema.org/birthDate=2, \"1988-09-24\"=3, http://db.uwaterloo.ca/~galuc/wsdbm/userId=4, \"9764726\"=5, http://db.uwaterloo.ca/~galuc/wsdbm/User1=6, \"2536508\"=7, http://db.uwaterloo.ca/~galuc/wsdbm/User2=8, \"5196173\"=9, http://db.uwaterloo.ca/~galuc/wsdbm/User3=10, \"1995-12-23\"=11, \"2019349\"=12, http://db.uwaterloo.ca/~galuc/wsdbm/User4=13, \"1982-07-28\"=14, \"8378922\"=15, http://db.uwaterloo.ca/~galuc/wsdbm/User5=16, \"9279708\"=17, http://db.uwaterloo.ca/~galuc/wsdbm/User6=18, \"4432131\"=19, http://db.uwaterloo.ca/~galuc/wsdbm/User7=20, \"5345433\"=21, http://db.uwaterloo.ca/~galuc/wsdbm/User8=22, \"2351480\"=23, http://db.uwaterloo.ca/~galuc/wsdbm/User9=24, \"8256018\"=25}";
			assertTrue(expectedDico.equals(mrh.hex.dico.dicoStringSort(mrh.hex.dico)));
			
			String expectedSPO = "{1={2=[3], 4=[5]}, 6={4=[7]}, 8={4=[9]}, 10={2=[11], 4=[12]}, 13={2=[14], 4=[15]}, 16={4=[17]}, 18={4=[19]}, 20={4=[21]}, 22={4=[23]}, 24={4=[25]}}";
			assertTrue(expectedSPO.equals(mrh.hex.SPO.mapStringSort(mrh.hex.dico.cpt)));
			
			String expectedSOP = "{1={3=[2], 5=[4]}, 6={7=[4]}, 8={9=[4]}, 10={11=[2], 12=[4]}, 13={14=[2], 15=[4]}, 16={17=[4]}, 18={19=[4]}, 20={21=[4]}, 22={23=[4]}, 24={25=[4]}}";
			//assertTrue(expectedSOP.equals(mrh.hex.SOP.mapStringSort(mrh.hex.dico.cpt)));
			
			String expectedPSO = "{2={3=[1], 11=[10], 14=[13]}, 4={5=[1], 7=[6], 9=[8], 12=[10], 15=[13], 17=[16], 19=[18], 21=[20], 23=[22], 25=[24]}}";
			//assertTrue(expectedPSO.equals(mrh.hex.PSO.mapStringSort(mrh.hex.dico.cpt)));
			
			String expectedPOS = "{2={1=[3], 10=[11], 13=[14]}, 4={1=[5], 6=[7], 8=[9], 10=[12], 13=[15], 16=[17], 18=[19], 20=[21], 22=[23], 24=[25]}}";
			//assertTrue(expectedPOS.equals(mrh.hex.POS.mapStringSort(mrh.hex.dico.cpt)));
			
			String expectedOPS = "{3={2=[1]}, 5={4=[1]}, 7={4=[6]}, 9={4=[8]}, 11={2=[10]}, 12={4=[10]}, 14={2=[13]}, 15={4=[13]}, 17={4=[16]}, 19={4=[18]}, 21={4=[20]}, 23={4=[22]}, 25={4=[24]}}";
			assertTrue(expectedOPS.equals(mrh.hex.OPS.mapStringSort(mrh.hex.dico.cpt)));
			
			String expectedOSP = "{3={1=[2]}, 5={1=[4]}, 7={6=[4]}, 9={8=[4]}, 11={10=[2]}, 12={10=[4]}, 14={13=[2]}, 15={13=[4]}, 17={16=[4]}, 19={18=[4]}, 21={20=[4]}, 23={22=[4]}, 25={24=[4]}}";
			//assertTrue(expectedOSP.equals(mrh.hex.OSP.mapStringSort(mrh.hex.dico.cpt)));
			
			
		}
	}
	
    /* TEST DE LA METHODE dicoMaker en comparant le HashMap attendu avec le HashMap obtenu sous forme de string 
     * REMARQUE IMPORTANTE : On utilise la méthode dicoStringSort() définie dans la classe MainRdfHandler afin d'écrire le hashmap en tant que string dans le bon ordre, effectivement il n'y a pas de notion 
     * d'ordre dans un hashmap, le toString retourne les bonnes valeurs mais dans l'ordre des adresses mémoire 
     */
	

     /* TEST DE LA METHODE indexMaker en comparant l'indexation SPO obtenue avec celle attendue, tout ça sous forme de string, afin que cela se fasse bien on a du définir mapStringSort() afin de trier l'écriture en String

}
	//Toutes nos méthodes sont bien définies et retournent les résultats attendus
}
*/

