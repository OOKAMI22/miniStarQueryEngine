package qengine.program;
import java.lang.String;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;

/**
 * Le RDFHandler intervient lors du parsing de données et permet d'appliquer un traitement pour chaque élément lu par le parseur.
 * 
 * <p>
 * Ce qui servira surtout dans le programme est la méthode {@link #handleStatement(Statement)} qui va permettre de traiter chaque triple lu.
 * </p>
 * <p>
 * À adapter/réécrire selon vos traitements.
 * </p>
 */
public final class MainRDFHandler extends AbstractRDFHandler {
	Hexastore hex = new Hexastore();
	int cpt=0;
	ArrayList<Long> timeDico = new ArrayList<Long>();
	ArrayList<Long> timeHex = new ArrayList<Long>();
	
	@SuppressWarnings("deprecation")
	@Override
	/*handleStatement() permet d'ajouter les triplets à la liste de triplets tupleRDF qui est attribut de notre classe*/
	public void handleStatement(Statement st) {
		
		ArrayList<String> tupleTmp = new ArrayList<String>();
		tupleTmp.add(st.getSubject().toString());
		tupleTmp.add(st.getPredicate().toString());
		tupleTmp.add(st.getObject().toString());
		timeDico.add((long)System.nanoTime());
		this.hex.dico.updateDico(tupleTmp);
		timeDico.add((long)System.nanoTime());
		
		timeHex.add((long)System.nanoTime());
		this.hex.updateHexastore(tupleTmp);
		timeHex.add((long)System.nanoTime());
		this.cpt=this.cpt+1;
	};
	/*dicoMaker() permet de créer le dictionnaire qui attribut à chaque terme (subject, predicate ou object) un integer*/
	
	
	/*indexMaker() permet de créer une indexation SPO */
	
	
	public float timeDictionnaire() {
		return (float) (timeDico.get(timeDico.size()-1)-timeDico.get(0));
	}
	
	public float timeHexa() {
		return (float) (timeHex.get(timeHex.size()-1)-timeHex.get(0));
	}
	
	
	
	/*Ces deux méthodes permettent d'écrire les différentes hashmap en string avec un ordre croissant, en effet le toString() classique d'un hashmap retourne une chaine de caractère mais dans l'ordre des adresses mémoires
	 * Le but de ces deux méthodes est de pouvoir faire des tests junit avec en comparant ce que l'on attend avec ce que l'on obtient dans un ordre précis*/
	
	
	
	
	
		
		
	}
