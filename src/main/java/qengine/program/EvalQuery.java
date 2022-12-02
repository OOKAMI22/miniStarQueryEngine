package qengine.program;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;



public class EvalQuery {
	MainRDFHandler mrh = new MainRDFHandler();
	List<StatementPattern> queries = new ArrayList<StatementPattern>();
	
	EvalQuery(MainRDFHandler mrh,List<StatementPattern> queries){
		this.mrh = mrh;
		this.queries = queries;
		
	}
	public void eval() {
		
	}

}
