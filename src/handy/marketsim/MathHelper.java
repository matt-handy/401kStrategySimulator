package handy.marketsim;

import java.util.List;

public class MathHelper {
	public static double getAverage(List<Double> samples){
		double total = 0;
		for(Double d : samples){
			total += d;
		}
		return total / samples.size();
	}
}
