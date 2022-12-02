package qengine.program;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JenaTest {

    JenaTest(String dataFile){
        model = RDFDataMgr.loadModel(dataFile) ;
    }
    private Model model;

    public  List<String> processAQuery(String queryString) {
        ArrayList<String> results = new ArrayList<>();

        // Construction de la requete
        Query query = QueryFactory.create(queryString);
        // Execution de la requette
        QueryExecution qe = QueryExecutionFactory.create(query, this.model);
        // Récupération des resultats
        Iterator<QuerySolution> res = qe.execSelect();
        // On sauvegarde les resultats dans notre arraylist

        //System.out.println(queryString);
        for ( ; res.hasNext() ; )
        {
            QuerySolution soln = res.next() ;
            results.add(soln.get("v0").toString());
            //System.out.println(soln.get("v0"));
        }
        qe.close();
        //System.out.println(results.size());
        return results;
    }
}
