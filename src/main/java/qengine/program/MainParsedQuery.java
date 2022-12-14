package qengine.program;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;


public class MainParsedQuery {
	
	List<ArrayList<Integer>> processedQuery = new ArrayList<ArrayList<Integer>>();
	String projectionElement = "Subject";
	static int cpt = 0;
	public void parseAQuery(ParsedQuery query, Dictionnaire dico) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());
		
		for(StatementPattern pattern : patterns) {
			ArrayList<Integer>  tmpProcess = new ArrayList<Integer>();
			
			if(pattern.getSubjectVar().getValue() == null) {	
		
				tmpProcess.add(dico.map.get(pattern.getPredicateVar().getValue().toString()));
				tmpProcess.add(dico.map.get(pattern.getObjectVar().getValue().toString()));
				this.projectionElement = "Subject";
		
			}
		else {
	
			tmpProcess.add(dico.map.get(pattern.getPredicateVar().getValue().toString()));
			tmpProcess.add(dico.map.get(pattern.getSubjectVar().getValue().toString()));
			this.projectionElement = "Object";		
	
		}
		this.processedQuery.add(tmpProcess);
			
		}
		System.out.println(queryToString(patterns));
		this.cpt=this.cpt+1;
	}
	
	public ArrayList<Integer> evaluateAPattern(ArrayList<Integer> pattern, Hexastore hex) {
		 ArrayList<Integer> result = new  ArrayList<Integer> (); 
		 Boolean b = true;
		
		if (this.projectionElement.equals("Subject")){
			
			// Ici on on peut prendre soit OPS ou POS on prend par défaut OPS
			
			if(hex.OPS.Btree.containsKey(pattern.get(1))) {
				if(hex.OPS.Btree.get(pattern.get(1)).containsKey(pattern.get(0))) {
				result = hex.OPS.Btree.get(pattern.get(1)).get(pattern.get(0));
				} else {
					b=false;
				}
				
			} else {
				b=false;
			}
			
			
		}
		else {
			// Ici on on peut prendre soit SPO ou PSO on prend par défaut SPO
			if(hex.SPO.Btree.containsKey(pattern.get(1))) {
				if(hex.SPO.Btree.get(pattern.get(1)).containsKey(pattern.get(0))) {
					result =  hex.SPO.Btree.get(pattern.get(1)).get(pattern.get(0));
				} else {
					b=false;
				}
				
				
			} else {
				b=false;
			}
			
				
			
		}
		
		if(!b) {System.out.println("Il n'y a pas de résultat.");}
		
		
		return result;		
	}
	
	public void evaluateAQuery(Hexastore hex,ArrayList<ArrayList<String>> myOutput) {
		ArrayList<Integer> result = evaluateAPattern(processedQuery.get(0),hex);
		ArrayList<String> output = new ArrayList<String>();
		
		for(int i =1;i < processedQuery.size();i++) {
			result.retainAll(evaluateAPattern(processedQuery.get(i),hex));
		}
		int j=0;
		for(Integer i : result) {
			j++;
			output.add(Dictionnaire.getKey(hex.dico.map,i).toString());
			System.out.println("résultat "+j+"  : "+Dictionnaire.getKey(hex.dico.map,i));
		}
		System.out.println("----------------");
		myOutput.add(output);
	}
	
	public static String queryToString(List<StatementPattern> patterns) {
		String query = "Query: SELECT ?v0 WHERE { \n";
		for(int i=0; i<patterns.size();i++) {
			query = query+"?v0";
			query = query+patterns.get(i).getPredicateVar().getValue();
			query = query + " ";
			query = query + patterns.get(i).getObjectVar().getValue();
			query = query + " .";
			if(i == patterns.size()-1) {query = query + "}";}
			query = query + "\n";
			}
		return query;
		}
	}
	
	
	

