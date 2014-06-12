package metrics;

import input.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InfoGain extends Measure {

	@Override
	public Attribute getBestAttribute(Attribute target,
			ArrayList<Attribute> attributes) {
		
		double entropyTarget = computeEntropy(target);

		// System.out.println("Entropy Target -> " + entropyTarget);
		// System.out.println();

		Attribute bestAttribute = null;
		Double bestValue = Double.NEGATIVE_INFINITY;

		for (Attribute attrib : attributes) {

			double infoGain = computeInfoGain(entropyTarget, target, attrib);
			if (infoGain > bestValue) {
				bestValue = infoGain;
				bestAttribute = attrib;
			}

			// System.out.println(attrib + "\tinfoGain: "+infoGain);
		}
		// System.out.println();
		return bestAttribute;
	}

	protected double computeInfoGain(double entropyTarget, Attribute target,
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

			double entropy = computeEntropy(newTarget); // Constant
			// System.out.println(entropy);

			List<String> values = newTarget.getValues();

			Map<String, Double> Sv = getMap(newTarget, values);

			double count = 0.0;
			for (String pv : newTarget.getPossibleValues()) {
				count += Sv.get(pv);
			}
			sum += (count / size) * entropy;

		}

		return entropyTarget - sum;

	}

	

}
