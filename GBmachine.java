import java.util.ArrayList;

public class GBmachine extends Bmachine {

	public GBmachine(ArrayList<ArrayList<Double>> W, ArrayList<Double> a, ArrayList<Double> b, ArrayList<Integer> d) {
		super(W, a, b, d);
	}
	
	public static ArrayList<Integer> makeVbar(ArrayList<Integer> v) {
		ArrayList<Integer> Vbar = new ArrayList<Integer>();
		double sum = 0;
		for (int i = 0; i < d.size(); i++) {
			sum += a.get(i);
		}
		for (int i = 0; i < d.size(); i++) {
			sum += Vbar.get(i);
		}
		for (int i = 0; i < sum; i++) {
			Vbar.add(0);
		}
		int place = 0;
		for (Integer i = 0; i < v.size(); i++) {
			for (Integer j = 0; j < i; j++) {
				place += d.get(j);
			}
			Vbar.set(place + v.get(i), 1);
			place = 0;
		}
		return Vbar;
	}
	
	@Override
	public double energy(ArrayList<Integer> v, ArrayList<Integer> h) {
		return energy(makeVbar(v), h);
	}
	
}