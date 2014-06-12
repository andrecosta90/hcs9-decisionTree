package metrics;

import input.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import metrics.GainRatio;
import metrics.Gini;
import metrics.InfoGain;
import metrics.Measure;
import metrics.VPRS;

public class CostaSigmoid extends Measure {

	@Override
	public Attribute getBestAttribute(Attribute target,
			ArrayList<Attribute> attributes) {
		
		double entropyTarget = computeEntropy(target);

		// System.out.println("Entropy Target -> " + entropyTarget);
		// System.out.println();

		Attribute bestAttribute = null;
		Double bestValue = Double.NEGATIVE_INFINITY;

		InfoGain ig = new InfoGain();
		GainRatio gr = new GainRatio();
		Gini g = new Gini();
		VPRS v = new VPRS();
		
		for (Attribute attrib : attributes) {

			double infoGain = sigmoid(ig.computeInfoGain(entropyTarget, target, attrib));
			double gainRatio = sigmoid(gr.computeGainRatio(entropyTarget, target, attrib));
			double gini = sigmoid(g.computeGini(g.computeGini(target), target, attrib));
			double vprs = sigmoid(v.computeVPRS(entropyTarget, target, attrib));
			
//			System.out.println(attrib);
//			System.out.println("infoGain="+infoGain);
//			System.out.println("GainRatio="+gainRatio);
//			System.out.println("Gini="+gini);
//			System.out.println("VPRS="+vprs);
			
			double mean = 4d/((1d/infoGain) + (1d/gainRatio) + (1d/gini) + (1d/vprs));
			double costaValue = mean;
//			System.out.println("MediaHarmonica="+mean);
//			System.out.println();
			
			
			if (costaValue > bestValue) {
				bestValue = costaValue;
				bestAttribute = attrib;
			}

			// System.out.println(attrib + "\tinfoGain: "+infoGain);
		}
		// System.out.println();
		return bestAttribute;
	}
	
	public double sigmoid(double value){
		return 1.0/(1.0 + Math.exp(-value));
	}

	

}
