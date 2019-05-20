import java.util.ArrayList;

public class Bmachine {
	static ArrayList<ArrayList<Double>> W;
	static ArrayList<Double> a;
	static ArrayList<Double> b;
	static ArrayList<Integer> d;

	public Bmachine(ArrayList<ArrayList<Double>> W, ArrayList<Double> a, ArrayList<Double> b, ArrayList<Integer> d) {
		Bmachine.W = W;
		Bmachine.a = a;
		Bmachine.b = b;
		Bmachine.d = d;
	}
	
	public ArrayList<Integer> makeH(int numb) {
		ArrayList<Integer> h = new ArrayList<Integer>();
		while (numb > 0) {
			h.add(0, numb%2);
			numb = numb/2;
		}
		while (true) {
			if (h.size() < d.size()) {
				h.add(0, 0);
			} else {
				break;
			}
		}
		return h;
	}
	
	public ArrayList<Integer> makeV(int x) {
		ArrayList<Integer> v = new ArrayList<Integer>();
		for (int i = 0; i < d.size(); i++) {
			int pos = d.size() - 1 - i;
			Integer numb = d.get(pos);
			v.add(0, x%numb);
			x = x/numb;
		}
		return v;
	}
	
	public double energy(ArrayList<Integer> v, ArrayList<Integer> h) {
		double first = 0;
		for (int i = 0; i < a.size(); i++) {
			first += a.get(i) * (double)v.get(i);
		}
		double second = 0;
		for (int j = 0; j < b.size(); j++) {
			second += b.get(j) * h.get(j);
		}
		double third = 0;
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				third += v.get(i) * W.get(i).get(j) * h.get(i);
			}
		}
		return -first-second-third;
	}
	
	public double constantZ() {
		double result = 0;
		ArrayList<Integer> v = new ArrayList<Integer>();
		ArrayList<Integer> h = new ArrayList<Integer>();
		for (int i = 0; i < a.size(); i++) {
			v = makeV(i);
			for (int j = 0; j < b.size(); j++) {
				h = makeH(j);
				result += Math.exp(-energy(v, h));
			}
		}
		return result;
	}

	public double prob(ArrayList<Integer> h, ArrayList<Integer> v) {
		return Math.exp(-energy(v, h)) / constantZ();
	}

	public double prob2(ArrayList<Integer> v) {
		double result = 0;
		ArrayList<Integer> h = new ArrayList<Integer>();
		for (int i = 0; i < b.size(); i++) {
			h = makeH(i);
			result += Math.exp(-energy(v, h));
		}
		return result/constantZ();
	}

	public double classify(ArrayList<Integer> smallV) {
		int s = d.size();
		double result = 0;
		double aux = 0;
		for (int i = 0; i < s; i++) {
			smallV.add(i);
			aux = prob2(smallV);
			if (aux > result) {
				aux = result;
			}
		}
		return result;
	}
	
	public double bingoSample(Sample Sm) {
		int bingo = 0;
		int realClass;
		double patientCalculatedClass;
		for (int i = 0; i < Sm.length(); i++) {
			ArrayList<Integer> patient = new ArrayList<Integer>();
			patient = Sm.element(i);
			realClass = patient.get(patient.size() - 1);
			patient.remove(patient.size() - 1);
			patientCalculatedClass = classify(patient);
			if (patientCalculatedClass == realClass) {
				bingo++;
			}
		}
		return bingo;
	}

	public void update(ArrayList<ArrayList<Double>> newW, ArrayList<Double> newa, ArrayList<Double> newb) {
		Bmachine.a = newa;
		Bmachine.b = newb;
		Bmachine.W = newW;
	}

}
