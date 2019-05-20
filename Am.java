import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Am implements Serializable {
	private static final long serialVersionUID = 1L;
	
	ArrayList<int[]> lista = new ArrayList<int[]>();
	
	public void add(int[] a){
		lista.add(a);
	}
	
	
	@Override
	public String toString() {
		String r = "";
		for (int[] el : lista) 
			r += ""+ Arrays.toString(el)+"\n";
		return r;
	}


}