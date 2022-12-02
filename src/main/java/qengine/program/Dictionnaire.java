package qengine.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.jsonldjava.shaded.com.google.common.base.Objects;
import com.github.jsonldjava.shaded.com.google.common.collect.Multiset.Entry;

public class Dictionnaire {
	int cpt = 1;
	public Map<String,Integer> map;
	
	
	
	public Dictionnaire() {
		this.map = new HashMap<String,Integer>();
		
	}
	
	public void updateDico(ArrayList<String> tuple ) {
	
			if (!this.map.containsKey(tuple.get(0))){
				this.map.put(tuple.get(0),this.cpt);
				
				this.cpt+=1;
				
			}
			
			if (!this.map.containsKey(tuple.get(1))){
				this.map.put(tuple.get(1),this.cpt);
			
				this.cpt+=1;
			}
			
			if (!this.map.containsKey(tuple.get(2))){
				this.map.put(tuple.get(2),this.cpt);

				this.cpt+=1;
			}
			
		
		
	}
	
	
	
	
	public void printDico() {
		//System.out.println("Dictionnaire :");
		//System.out.println(this.map);
	}
	
	
	
	
	
	public static <K, V> K getKey(Map<K, V> map, V value)
    {
        for (Map.Entry<K, V> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
	
	
	/// PAS TOUCHE ///
	
	public String dicoStringSort(Dictionnaire dico) {
		map = dico.map;
        String x="{";
        for(int i=1;i<this.cpt;i++) {
            for (String name : map.keySet()) {
                if(map.get(name)==i) {
                    x=x+""+name+"="+i+", ";
                }
            }
            
               
        }
        x=x.substring(0, x.length()-2);
        x=x+"}";
        
        return x;
    }

}
