import java.io.Serializable;
import java.util.*;

public class Sample implements Serializable{
	private static final long serialVersionUID = 1L;
	ArrayList<ArrayList<Integer>> sample = new ArrayList<ArrayList<Integer>>();

	@Override
	public String toString() {
		return "Sample [sample=" + sample + "]";
	}

	public void add(ArrayList<Integer> intVector) {
		sample.add(intVector);
	}
	
	public double length() {
		return sample.size();
	}

	public ArrayList<Integer> element(int pos) {
		ArrayList<Integer> elementList = new ArrayList<Integer>();
		for (int i = 0; i < sample.get(pos).size(); i++) {
			elementList.add(sample.get(pos).get(i));
		}
		return elementList;
	}

	public double count(ArrayList<Integer> variables, ArrayList<Integer> values) {
		double result = 0;
		double aux = 0;
		for (int i = 0; i < sample.size(); i++) {
			for (int j = 0; j < variables.size(); j++) {
				if (values.get(j) == sample.get(i).get(variables.get(j))) {
					aux++;
				}
				if (aux == variables.size()) {
					result++;
				}
			}
			aux = 0;
		}
		return result;
	}
	
	public ArrayList<Integer> domain() {
		ArrayList<Integer> aux = new ArrayList<Integer>();
		for (int i = 0; i < sample.size(); i++) {
			Integer m = 99999;
			for (int j = 0; j < sample.get(i).size(); j++) {
				ArrayList<Integer> s = new ArrayList<Integer>();
				s = sample.get(i);
				int n = s.get(j);
				if (m > n) {
					m = sample.get(i).get(j);
				}
			}
			aux.add(m + 1);
		}
		return aux;
	}
	
//	public ArrayList<Integer> domain() {  
//		ArrayList<Integer> r = element(0);  //começamos com a primeira linha
//		for (int i = 0; i<length(); i++) {  
//			for ( int j = 0; j<r.size(); j++) {  
//				if ((sample.get(i).get(j))>(r.get(j))) {  //se houver um valor de variável maior, substituimos
//					r.set(j, sample.get(i).get(j));
//				}
//			}
//		}
//		for (int i = 0; i<r.size(); i++) { //incluímos o 0
//			r.set(i, r.get(i)+1);	
//		}
//		return r;
//	}
}