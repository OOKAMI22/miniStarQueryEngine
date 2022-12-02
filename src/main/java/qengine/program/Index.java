package qengine.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Index {
	int first;
	int second;
	int third;
	Map<Integer,HashMap<Integer,ArrayList<Integer>>> Btree;
	static int cpt=0;
	public Index(int first, int second, int third) {
		this.first=first;
		this.second=second;
		this.third=third;
		this.Btree= new HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>();
		cpt++;
	}	
	
	public void updateIndex(Dictionnaire dico, ArrayList<String> tuple) {
		
		if(!this.Btree.containsKey(dico.map.get(tuple.get(this.first)))) {
			ArrayList<Integer> obj = new ArrayList<Integer>(dico.map.get(tuple.get(this.third)));
			obj.add(dico.map.get(tuple.get(this.third)));
			HashMap<Integer,ArrayList<Integer>> pred = new HashMap<Integer,ArrayList<Integer>>();
			pred.put(dico.map.get(tuple.get(this.second)), obj);
			this.Btree.put(dico.map.get(tuple.get(this.first)), pred);
		}
		else {
			if(!this.Btree.get(dico.map.get(tuple.get(this.first))).containsKey(dico.map.get(tuple.get(this.second)))) {
				ArrayList<Integer> obj = new ArrayList<Integer>(dico.map.get(tuple.get(this.third)));
				obj.add(dico.map.get(tuple.get(this.third)));
				this.Btree.get(dico.map.get(tuple.get(this.first))).put(dico.map.get(tuple.get(this.second)), obj);
				
			}
		if(!this.Btree.get(dico.map.get(tuple.get(this.first))).get(dico.map.get(tuple.get(this.second))).contains(dico.map.get(tuple.get(this.third)))){
			this.Btree.get(dico.map.get(tuple.get(this.first))).get(dico.map.get(tuple.get(this.second))).add(dico.map.get(tuple.get(this.third)));
		}
		
	}
	
	}
	
	public String toString() {
		return Btree.toString();
	}
	
	
	
	public String mapStringSort(int cpt) {
        String x="{";
        for(int i=1;i<cpt;i++) {
            if(this.Btree.containsKey(i)) {
                x=x+i+"={";
                HashMap<Integer,ArrayList<Integer>> mapExpected2=this.Btree.get(i);
                for(int j=1;j<cpt;j++) {
                    if(mapExpected2.containsKey(j)) {
                        x=x+j+"=[";
                        ArrayList<Integer> expected = mapExpected2.get(j);
                        for(int y=1;y<cpt;y++) {
                            if(expected.contains(y)) {
                                x=x+y+", ";
                            }
                        
                            
                        }
                        x=x.substring(0,x.length()-2);
                        x=x+"], ";
                        
                    }
                }
                x=x.substring(0,x.length()-2);
                x=x+"}, ";
                
                
                
            }
        }
        x=x.substring(0,x.length()-2);
        x=x+"}";
        
        return x;
        
    }
	
}
