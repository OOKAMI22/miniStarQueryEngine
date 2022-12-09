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
    public List<Boolean> CompareWithJena(ArrayList<ArrayList<String>> jenaOutput, ArrayList<ArrayList<String>> myOutput) {
        List<Boolean>  result = new ArrayList<Boolean>();
        Boolean tmp;
        for(int i = 0; i<jenaOutput.size();i++){
            tmp = false;
            if (jenaOutput.get(i).size() == myOutput.get(i).size()) {
                for (int j = 0; j < jenaOutput.get(i).size(); i++) {

                    if (myOutput.contains(jenaOutput.get(i).get(j))) {
                        tmp  = true;
                    }
                    else{
                        tmp = false;
                        break;
                    }
                }
            }
            result.add(tmp);
        }

        return result;
    }


    public String getErrors(List<Boolean> comparaison){
        Integer cpt = 0;

        for (Boolean bool : comparaison){
            if (!bool){
                cpt += 1;
            }
        }
        String result = cpt.toString()+"/"+comparaison.size();

        return result;
    }
}
