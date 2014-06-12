package metrics;

import input.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Measure {

	public abstract Attribute getBestAttribute(Attribute target,
			ArrayList<Attribute> attributes);
	
	protected double computeEntropy(Attribute attrib) {
		List<String> values = attrib.getValues();

		Map<String, Double> Sv = getMap(attrib, values);

		int size = values.size();
		Set<String> possibleValues = attrib.getPossibleValues();

		double count = 0.0;
		for (String pv : possibleValues) { // Constant

			double prob = Sv.get(pv) / size;
			double calcLog = log2(prob);

			count += -(prob * calcLog);

		}

		return count;
	}

	protected double log2(double x) {
		if (x == 0)
			return 0.0;
		return Math.log(x) / Math.log(2);
	}

	protected Map<String, Double> getMap(Attribute attrib, List<String> values) {
		Map<String, Double> map = new HashMap();

		for (String value : values) {
			if (!map.containsKey(value)) {
				map.put(value, 0.0);
			}
			double x = map.get(value);
			x++;
			map.put(value, x);
		}
		return map;
	}

}
