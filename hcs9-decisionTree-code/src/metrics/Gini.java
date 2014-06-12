package metrics;

import input.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Gini extends Measure{

	@Override
	public Attribute getBestAttribute(Attribute target,
			ArrayList<Attribute> attributes) {
		
		
		double giniTarget = computeGini(target);
		
		Attribute bestAttribute = null;
		Double bestValue = Double.NEGATIVE_INFINITY;

		for (Attribute attrib : attributes) {

			double gini = computeGini(giniTarget, target, attrib);
			if (gini > bestValue) {
				bestValue = gini;
				bestAttribute = attrib;
			}

			// System.out.println(attrib + "\tinfoGain: "+infoGain);
		}
		// System.out.println();
		return bestAttribute;
		
	
		
	}

	protected double computeGini(double giniTarget, Attribute target,
			Attribute attrib) {
		Set<String> possibleValues = attrib.getPossibleValues();

		List<String> targetValues = target.getValues();
		List<String> attribValues = attrib.getValues();

		int size = attribValues.size();

		double sum = 0.0;
		for (String pValue : possibleValues) { // Constant
			Attribute newTarget = new Attribute(pValue);

			for (int i = 0; i < size; i++) { // O(n)

				if (pValue.equals(attribValues.get(i))) {
					newTarget.addValue(targetValues.get(i));
				}

			}

			double gini = computeGini(newTarget); // Constant
			// System.out.println(entropy);

			List<String> values = newTarget.getValues();

			Map<String, Double> Sv = getMap(newTarget, values);

			double count = 0.0;
			for (String pv : newTarget.getPossibleValues()) {
				count += Sv.get(pv);
			}
			sum += (count / size) * gini;

		}

		return giniTarget - sum;

	}

	protected double computeGini(Attribute attrib) {
		List<String> values = attrib.getValues();

		Map<String, Double> Sv = getMap(attrib, values);

		int size = values.size();
		Set<String> possibleValues = attrib.getPossibleValues();

		double count = 0.0;
		for (String pv : possibleValues) { // Constant

			double prob = Sv.get(pv) / size;

			count += Math.pow(prob, 2);

		}

		return 1.0 - count;
	}


}
