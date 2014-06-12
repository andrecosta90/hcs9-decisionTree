package metrics;

import input.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VPRS extends Measure {

	@Override
	public Attribute getBestAttribute(Attribute target,
			ArrayList<Attribute> attributes) {

		double entropyTarget = computeEntropy(target);

		// System.out.println("Entropy Target -> " + entropyTarget);
		// System.out.println();

		Attribute bestAttribute = null;
		Double bestValue = Double.NEGATIVE_INFINITY;

		Attribute bestAttribute2 = null;
		Double bestValue2 = Double.NEGATIVE_INFINITY;

		for (Attribute attrib : attributes) {

			double vprs = computeVPRS(entropyTarget, target, attrib);
			// System.out.println(attrib+" ==> vprs="+vprs);
			if (vprs > bestValue) {
				// bestValue2 = bestValue;
				// bestAttribute2 = bestAttribute;

				bestValue = vprs;
				bestAttribute = attrib;
			}

			else if (vprs <= bestValue && vprs > bestValue2) {
				bestValue2 = vprs;
				bestAttribute2 = attrib;
			}
			// System.out.println(attrib + "\tinfoGain: "+infoGain);
		}
		// System.out.println();
		// System.out.println(bestAttribute + " ==> " + bestValue);
		// System.out.println(bestAttribute2 + " ==> " + bestValue2);
		// System.out.println();

		if ((bestAttribute2 == null)) {
			// System.out.println("retorna =>" + bestAttribute + "\n");
			return bestAttribute;
		}

		double wd = wrough_difference(bestValue, bestValue2);
		double c = complexity(bestValue, bestValue2);

		// System.out.println("wd=>" + wd);
		// System.out.println("c=>" + c);

		// DEFINIR PARAMETROS!!!
		// breast-cancer:: minwrd=0.2;mincomp=0.8

		// audiology:: minwrd=0.5;mincomp=0.5 79,5%/21
		// audiology:: minwrd=0.5;mincomp=0.1 81.5%/21
		// audiology:: minwrd=0.4;mincomp=0.1 82%/22

		// soybean:: minwrd=0.2;mincomp=0.8:: 87,3%/14
		// soybean:: minwrd=0.5;mincomp=0.5:: 87.94788%/14
		
		//tic-tac-toe:: minwrd=0.2;mincomp=0.8 82,15/7
		//tic-tac-toe:: minwrd=0.2;mincomp=0.7 85.07306889/7
		
		//car_eval:: minwrd=0.2;mincomp=0.8   86,979 / 6
		//car_eval:: minwrd=0.2;mincomp=0.9   87,3264 / 6
		//car_eval:: minwrd=0.1;mincomp=0.9   87,44 / 6
		//car_eval:: minwrd=0.1;mincomp=0.5   87,61 / 6
		double minwrd = 0.1;
		double mincomp = 0.5;

		if (wd >= minwrd) {
			// System.out.println("retorna =>" + bestAttribute + "\n");
			return bestAttribute;
		}

		if (c < mincomp) {
			// System.out.println("retorna =>" + bestAttribute + "\n");
			return bestAttribute;
		}

		// System.out.println("retorna =>" + bestAttribute2 + "\n");
		return bestAttribute2;

	}

	private double complexity(Double bestValue, Double bestValue2) {
		// TODO Auto-generated method stub
		return (bestValue - bestValue2) / bestValue;
	}

	private double wrough_difference(Double bestValue, Double bestValue2) {
		return bestValue - bestValue2;

	}

	protected double computeVPRS(double entropyTarget, Attribute target,
			Attribute attrib) {
		InfoGain measure = new InfoGain();

		double infoGain = measure
				.computeInfoGain(entropyTarget, target, attrib);
		double splitInfo = splitInformation(entropyTarget, target, attrib);

		double gainRatio;

		if (splitInfo == 0) {
			gainRatio = infoGain / 0.001;
		} else {
			gainRatio = infoGain / splitInfo;
		}

		// System.out.println(attrib+" => Gain Ratio: "+gainRatio+" | Entropia "+splitInfo+" | InfoGain: "+infoGain);
		return gainRatio;
	}

	private double splitInformation(double entropyTarget, Attribute target,
			Attribute attrib) {
		return computeEntropy(attrib);
	}

}
