package metrics;

import input.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GainRatio extends Measure {

	@Override
	public Attribute getBestAttribute(Attribute target,
			ArrayList<Attribute> attributes) {
		
		double entropyTarget = computeEntropy(target);

		Attribute bestAttribute = null;
		Double bestValue = Double.NEGATIVE_INFINITY;

		for (Attribute attrib : attributes) {


			double gainRatio = computeGainRatio(entropyTarget, target, attrib);
			
			if (gainRatio > bestValue) {
				bestValue = gainRatio;
				bestAttribute = attrib;
			}

			// System.out.println(attrib + "\tinfoGain: "+infoGain);
		}
		// System.out.println();
		return bestAttribute;
	}

	protected double computeGainRatio(double entropyTarget, Attribute target,
			Attribute attrib) {
		InfoGain measure = new InfoGain();
		
		double infoGain = measure.computeInfoGain(entropyTarget, target, attrib);
		double splitInfo = splitInformation(entropyTarget, target, attrib);
		
		double gainRatio;
		
		if (splitInfo == 0){
			gainRatio = infoGain/0.001;
		} else{
			gainRatio = infoGain/splitInfo;
		}
		
//		System.out.println(attrib+" => Gain Ratio: "+gainRatio+" | Entropia "+splitInfo+" | InfoGain: "+infoGain);
		return gainRatio;
	}

	private double splitInformation(double entropyTarget, Attribute target,
			Attribute attrib) {
		return computeEntropy(attrib);
	}



}
