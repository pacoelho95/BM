import java.util.ArrayList;
import java.util.Random;

public class Learner {
	GBmachine GB;
	Sample Sm;
	int UG; // 0: unif() | 1: sampleGauss()
	int numbH;
	int times;
	double typeB;
	double ep;

	public Learner(Sample Sm, int UG, int numbH, int times, double typeB, double ep) {
		super();
		this.GB = Initializer();
		this.Sm = Sm;
		this.UG = UG;
		this.numbH = numbH;
		this.times = times;
		this.typeB = typeB;
		this.ep = ep;
	}

	public GBmachine getGB() {
		return GB;
	}

	public void setGB(GBmachine gB) {
		GB = gB;
	}



	public Learner(GBmachine gB) {
		super();
		GB = gB;
	}

	public double sampleGauss(double mean, double standdev) {
		Random gauss = new Random();
		return mean+gauss.nextGaussian()*standdev;
	}
	
	public double unif() {
		Random unif = new Random();
		return unif.nextDouble();
	}
	
	public double F(int i, int k) {
		ArrayList<Integer> vars = new ArrayList<Integer>();
		ArrayList<Integer> vals = new ArrayList<Integer>();
		vars.add(i);
		vals.add(k);
		double result = this.Sm.count(vars,vals)/this.Sm.length();
		return result;
	}
	
	// whatH = j do enunciado
	public double probH(int whatH, ArrayList<Integer> v) {
		double result = 0;
		for (int i = 0; i < GBmachine.a.size(); i++) {
			result = GBmachine.W.get(i).get(whatH)*v.get(i);
		}
		result += GBmachine.b.get(whatH);
		result = Math.exp(result)/(1+Math.exp(result));
		return result;
	}
	
	public ArrayList<Integer> algoritmo1(ArrayList<Integer> v) {
		ArrayList<Integer> h = new ArrayList<Integer>();
		double rnd = unif();
		for (int i = 0; i < GBmachine.b.size(); i++) {
			if (rnd <= probH(i, v)) {
				h.add(1);
			} else {
				h.add(0);
			}
		}
		return h;
	}
	
	// whatV = i do enunciado
	public double probV(int whatV, int k, ArrayList<Integer> h) {
		double top = 0;
		for(int j = 1; j <= GBmachine.b.size(); j++) {
			int dl = 0;
			for (int i = 0; i < whatV - 1; i++) {
				dl += GBmachine.d.get(i);
			}
			top += ( (GBmachine.W).get(k+dl).get(j-1) * h.get(j-1) );
		}
		top += Math.pow(GBmachine.a.get(whatV), k);
		
		double bot = 0;
		for (int t = 0; t < GBmachine.b.get(whatV); t++) {
			for(int j = 1; j <= GBmachine.b.size(); j++) {
				int dl = 0;
				for (int i = 0; i < whatV - 1; i++) {
					dl += GBmachine.d.get(i);
				}
				bot += ( (GBmachine.W).get(k+dl).get(j-1) * h.get(j-1) );
			}
			bot += Math.pow(GBmachine.a.get(whatV), t);
		}
		return top/bot;
	}
	
	public ArrayList<Integer> algoritmo2(ArrayList<Integer> h) {
		ArrayList<Integer> v = new ArrayList<Integer>();
		double lower, upper;
		double rnd = unif();
		for (int i = 0; i < GBmachine.d.size(); i++) {
			ArrayList<Double> probs = new ArrayList<Double>();
			for (int k = 0; k < GBmachine.d.get(k); k++) {
				probs.add(probV(i, k, h));
			}
			lower = 0;
			upper = probs.get(0);
			for(int j = 1; j < probs.size(); j++) {
				if (rnd  <= lower || rnd > upper) {
					v.add(0);
				} else {
					v.add(1);
				}
			}
			if (rnd  <= lower || rnd > upper) {
				v.add(0);
			} else {
				v.add(1);
			}
		}
		return v;
	}
	
	public GBmachine Initializer() {
		// THIS IS WHAT ARE GIVING ERRORS
		ArrayList<Integer> e = new ArrayList<Integer>();
		e = this.Sm.domain();
		// a
		ArrayList<Double> a = new ArrayList<Double>();
		for (int i = 0; i < e.size(); i++) {
			for (int j = 0; j < e.get(i); j++) {
					a.add(Math.log(F(i,j)/(1-F(i,j))));
			}
		}
		// b
		ArrayList<Double> b = new ArrayList<Double>();
		for (int i = 0; i < this.numbH; i++) {
			b.add(typeB);
		}
		// W
		double rnd;
		ArrayList<ArrayList<Double>> W = new ArrayList<ArrayList<Double>>();
		if (this.UG == 0) {
			for (int i = 0; i < a.size(); i++) {
				ArrayList<Double> miniW = new ArrayList<Double>();
				for (int j = 0; j < b.size(); j++) {
					rnd = sampleGauss(0, 0.01);
					miniW.add(rnd);
				}
				W.add(miniW);
			}
		} else if (this.UG == 1) {
			for (int i = 0; i < a.size(); i++) {
				ArrayList<Double> miniW = new ArrayList<Double>();
				for (int j = 0; j < b.size(); j++) {
					rnd = unif();
					miniW.add(rnd);
				}
				W.add(miniW);
			}
		}
		GBmachine GB = new GBmachine(W,a,b,e);
		return GB;
	}
	
	public void engine(ArrayList<Integer> v1, int Zor4) {
		ArrayList<Integer> v2 = new ArrayList<Integer>();
		ArrayList<Integer> h1 = new ArrayList<Integer>();
		ArrayList<Integer> h2 = new ArrayList<Integer>();
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> W = new ArrayList<ArrayList<Double>>();
		
		ArrayList<ArrayList<Integer>> positive = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> negative = new ArrayList<ArrayList<Integer>>();
		
		v1 = GBmachine.makeVbar(v1);
		h1 = algoritmo1(v1);
		// positive gradient
		for (int i = 0; i < v1.size(); i++ ) {
			ArrayList<Integer> v1h1 = new ArrayList<Integer>();
			for (int j = 0; j < h1.size(); j++) {
				v1h1.add(v1.get(i)*h1.get(j));
			}
			positive.add(v1h1);
		}
		
		v2 = algoritmo2(h1);
		h2 = algoritmo2(v2);
		// negative gradient
		for (int i = 0; i < v2.size(); i++ ) {
			ArrayList<Integer> v2h2 = new ArrayList<Integer>();
			for (int j = 0; j < h2.size(); j++) {
				v2h2.add(v2.get(i)*h2.get(j));
			}
			negative.add(v2h2);
		}
		
		// a
		for (int i = 0; i < GBmachine.a.size(); i++) {
			a.add(GBmachine.a.get(i) + this.ep * (v1.get(i) - v2.get(i)));
		}
		
		// b
		for (int i = 0; i < GBmachine.b.size(); i++) {
			b.add(GBmachine.b.get(i) + this.ep * (h1.get(i) - h2.get(i)));
		}
		
		// W
		for (int i = 0; i < a.size(); i++) {
			ArrayList<Double> miniW = new ArrayList<Double>();
			for (int j = 0; j < b.size(); j++) {
				miniW.add(GBmachine.W.get(i).get(j) + this.ep * (positive.get(i).get(j) - negative.get(i).get(j)));
			}
			W.add(miniW);
		}
		
		this.GB.update(W, a, b);
	}
	
	public void engineLoop(int times) {
		for (int i = 0; i < times; i++) {
			for (int j = 0; j < times; j++) {
				engine(this.Sm.element(j), -4);
			}
		}
	}
	
}





