package qengine.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hexastore {
	
	Index SPO = new Index(0,1,2);
	/*Index SOP = new Index(0,2,1);*/
	/*Index PSO = new Index(1,2,0);*/
	//Index POS = new Index(1,0,2);
	//Index OSP = new Index(2,0,1);
	Index OPS = new Index(2,1,0);
	Dictionnaire dico;
	
	
	public Hexastore() {
		this.dico = new Dictionnaire();
	}
	
	public void updateHexastore(ArrayList<String> tuple) {
		this.SPO.updateIndex(this.dico, tuple);
		//this.SOP.updateIndex(this.dico, tuple);
		//this.PSO.updateIndex(this.dico, tuple);
		//this.POS.updateIndex(this.dico, tuple);
		//this.OSP.updateIndex(this.dico, tuple);
		this.OPS.updateIndex(this.dico, tuple);
		
	}
	
	public void printAllIndexes() {
		System.out.println("SPO :");
		//System.out.println(this.SPO.mapStringSort(this.dico.cpt));
		System.out.println("SOP :");
		//System.out.println(this.SOP.mapStringSort(this.dico.cpt));
		System.out.println("PSO :");
		//System.out.println(this.PSO.mapStringSort(this.dico.cpt));
		System.out.println("POS :");
		//System.out.println(this.POS.mapStringSort(this.dico.cpt));
		System.out.println("OSP :");
		//System.out.println(this.OSP.mapStringSort(this.dico.cpt));
		System.out.println("OPS");
		//System.out.println(this.OPS.mapStringSort(this.dico.cpt));
		
	}
		
	
	
	

}
