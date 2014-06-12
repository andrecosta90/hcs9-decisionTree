package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import metrics.GainRatio;
import metrics.InfoGain;
import metrics.Measure;

import input.Attribute;
import input.Structure;

public class DecisionTree {

	private Structure structure;
	private Node raiz;

	private Measure measure;

	public DecisionTree(Structure structure) {
		this.structure = structure;
		this.raiz = new Node();
		this.measure = new InfoGain();

	}

	public int getSize() {
		LinkedList<Node> fila = new LinkedList<>();
		fila.add(raiz);

		HashMap<Node, Integer> h = new HashMap<>();
		h.put(raiz, 0);

		while (!fila.isEmpty()) {
			Node node = fila.removeFirst();
			Map<String, Node> links = node.getLinks();

			for (String key : links.keySet()) {
				Node u = links.get(key);
				if (!h.containsKey(key)) {
					fila.add(u);
					h.put(u, h.get(node) + 1);
				}

			}

		}

		int max = Integer.MIN_VALUE;
		for(Node node : h.keySet()){
			int value = h.get(node);
			if (max  <value){
				max = value;
			}
		}
//		System.out.println(max);
		return max;

	}

	// private Attribute getBestAttribute(Attribute target,
	// ArrayList<Attribute> attributes) {
	// double entropyTarget = computeEntropy(target);
	//
	// // System.out.println("Entropy Target -> " + entropyTarget);
	// // System.out.println();
	//
	// Attribute bestAttribute = null;
	// Double bestValue = Double.NEGATIVE_INFINITY;
	//
	// for (Attribute attrib : attributes) {
	//
	// double infoGain = computeInfoGain(entropyTarget, target, attrib);
	// if (infoGain > bestValue) {
	// bestValue = infoGain;
	// bestAttribute = attrib;
	// }
	//
	// // System.out.println(attrib + "\tinfoGain: "+infoGain);
	// }
	// // System.out.println();
	// return bestAttribute;
	//
	// }

	public DecisionTree(Structure structure, Measure measure) {
		this.structure = structure;
		this.measure = measure;
		this.raiz = new Node();
	}

	public void train() {
		ArrayList<Attribute> attributes = structure.getAttributes();
		Attribute target = structure.getTarget();

		ID3(raiz, target, attributes);
		// System.out.println();
		// System.out.println("percorrer");
		// System.out.println();
		percorre(raiz, target.getPossibleValues(), "");
		int i = 0;

	}

	private void percorre(Node node, Set<String> set, String tab) {
		String label = node.getLabel();

		if (!set.contains(label)) {
			System.out.print(tab + "| Se ");
			System.out.print(label);
			System.out.println(" ==");
		} else {
			System.out.println(tab + "\t>> " + label);
		}
		Map<String, Node> links = node.getLinks();
		if (links != null) {
			for (String key : links.keySet()) {
				System.out.println(tab + "\t| == " + key);
				percorre(links.get(key), set, tab + "\t");

			}
		}

	}

	private void ID3(Node node, Attribute target,
			ArrayList<Attribute> attributes) {

		if (allEquals(target)) {
			node.setLabel(target.getValues().get(0));
			return;
		}

		if (attributes.size() == 0) {
			List<String> targetValues = target.getValues();
			node.setLabel(getMostCommonElement(targetValues));
			return;
		}

		Measure measure = new InfoGain();
		Attribute bestAttribute = measure.getBestAttribute(target, attributes);
		// System.out.println(bestAttribute);

		List<String> targetValues = target.getValues();

		Set<String> possibleValues = bestAttribute.getPossibleValues();

		String description = bestAttribute.getDescription();

		node.setLabel(description);
		for (String pValue : possibleValues) { // constant

			// System.out.println(pValue);
			ArrayList<Attribute> newAttributes = getNewAttributes(description,
					attributes);
			Attribute newTarget = new Attribute(target.getDescription());

			node.makeLink(pValue);

			ArrayList<String> values = bestAttribute.getValues();
			for (int i = 0; i < values.size(); i++) { // O(n)
				String instance = values.get(i);

				if (instance.equals(pValue)) {
					// System.out.println(instance);

					for (Attribute a : attributes) { // constant
						if (!description.equals(a.getDescription())) {

							String aValue = a.getValues().get(i);
							int index = getAttributeIndex(a.getDescription(),
									newAttributes); // constant
							Attribute newAttrib = newAttributes.get(index);
							newAttrib.addValue(aValue);

						}

					}
					newTarget.addValue(targetValues.get(i));
				}

			}
			Node otherNode = node.getNode(pValue);
			if (newTarget.getValues().size() == 0) {

				otherNode.setLabel(getMostCommonElement(targetValues));
			} else {
				ID3(otherNode, newTarget, newAttributes);
			}

		}

	}

	public String getMostCommonElement(List<String> l) {
		int counter = 0;
		String pointer = null;

		for (String e : l) {
			if (counter == 0) {
				pointer = e;
				counter++;
			} else {
				if (pointer.equals(e)) {
					counter++;
				} else {
					counter--;
				}
			}
		}

		return pointer;
	}

	// private Node ID3_copy(Node node, Attribute target,
	// ArrayList<Attribute> attributes) {
	// node = new Node();
	//
	// if (allEquals(target)) {
	// node.setLabel(target.getValues().get(0));
	// return node;
	// }
	//
	// if (attributes.size() == 0) {
	// List<String> targetValues = target.getValues();
	// node.setLabel(getMostCommonElement(targetValues));
	// return node;
	// }
	//
	// Attribute bestAttribute = getBestAttribute(target, attributes);
	//
	// // System.out.println("Melhor Atributo -> " + bestAttribute);
	// // System.out.println();
	//
	// List<String> targetValues = target.getValues();
	//
	// Set<String> possibleValues = bestAttribute.getPossibleValues();
	//
	// String description = bestAttribute.getDescription();
	//
	// // System.out.println(newAttributes);
	//
	// node.setLabel(description);
	//
	// for (String pValue : possibleValues) { // constant
	// ArrayList<Attribute> newAttributes = getNewAttributes(description,
	// attributes);
	// Attribute newTarget = new Attribute(target.getDescription());
	//
	// node.makeLink(pValue);
	// // System.out.println(pValue);
	//
	// ArrayList<String> values = bestAttribute.getValues();
	// for (int i = 0; i < values.size(); i++) { // O(n)
	// String instance = values.get(i);
	//
	// if (instance.equals(pValue)) {
	//
	// for (Attribute a : attributes) { // constant
	// String aValue = a.getValues().get(i);
	// if (!aValue.equals(pValue)) {
	//
	// int index = getAttributeIndex(a.getDescription(),
	// newAttributes); // constant
	// Attribute newAttrib = newAttributes.get(index);
	// newAttrib.addValue(aValue);
	//
	// }
	// }
	// newTarget.addValue(targetValues.get(i));
	// }
	//
	// }
	// Node otherNode = node.getNode(pValue);
	// if (newTarget.getValues().size() == 0) {
	//
	// otherNode.setLabel(getMostCommonElement(targetValues));
	// return otherNode;
	// } else {
	// ID3(otherNode, newTarget, newAttributes);
	// }
	//
	// }
	//
	// return node;
	//
	// }

	private ArrayList<Attribute> getNewAttributes(String description,
			ArrayList<Attribute> attributes) {
		ArrayList<Attribute> newAttributes = new ArrayList();

		for (Attribute attribute : attributes) {

			String newDescription = attribute.getDescription();
			if (!description.equals(attribute.getDescription())) {
				newAttributes.add(new Attribute(newDescription));
			}
		}

		return newAttributes;
	}

	private int getAttributeIndex(String description,
			ArrayList<Attribute> attributes) {
		for (int i = 0; i < attributes.size(); i++) {
			String attributeDescription = attributes.get(i).getDescription();
			if (description.equals(attributeDescription))
				return i;
		}
		return -1;
	}

	private boolean allEquals(Attribute target) {
		List<String> targetValues = target.getValues();

		String s = targetValues.get(0);
		for (int i = 1; i < targetValues.size(); i++) {
			if (!s.equals(targetValues.get(i))) {
				return false;
			}
		}
		return true;
	}

	// private double computeInfoGain(double entropyTarget, Attribute target,
	// Attribute attrib) {
	//
	// Set<String> possibleValues = attrib.getPossibleValues();
	//
	// List<String> targetValues = target.getValues();
	// List<String> attribValues = attrib.getValues();
	//
	// int size = attribValues.size();
	//
	// double sum = 0.0;
	// for (String pValue : possibleValues) { // Constant
	// Attribute newTarget = new Attribute(pValue);
	//
	// for (int i = 0; i < size; i++) { // O(n)
	//
	// if (pValue.equals(attribValues.get(i))) {
	// newTarget.addValue(targetValues.get(i));
	// }
	//
	// }
	//
	// double entropy = computeEntropy(newTarget); // Constant
	// // System.out.println(entropy);
	//
	// List<String> values = newTarget.getValues();
	//
	// Map<String, Double> Sv = getMap(newTarget, values);
	//
	// double count = 0.0;
	// for (String pv : newTarget.getPossibleValues()) {
	// count += Sv.get(pv);
	// }
	// sum += (count / size) * entropy;
	//
	// }
	//
	// return entropyTarget - sum;
	//
	// }

	// private Map<String, Double> getMap(Attribute attrib, List<String> values)
	// {
	// Map<String, Double> map = new HashMap();
	//
	// for (String value : values) {
	// if (!map.containsKey(value)) {
	// map.put(value, 0.0);
	// }
	// double x = map.get(value);
	// x++;
	// map.put(value, x);
	// }
	// return map;
	// }

	// private double computeEntropy(Attribute attrib) {
	//
	// List<String> values = attrib.getValues();
	//
	// Map<String, Double> Sv = getMap(attrib, values);
	//
	// int size = values.size();
	// Set<String> possibleValues = attrib.getPossibleValues();
	//
	// double count = 0.0;
	// for (String pv : possibleValues) { // Constant
	//
	// double prob = Sv.get(pv) / size;
	// double calcLog = log2(prob);
	//
	// count += -(prob * calcLog);
	//
	// }
	//
	// return count;
	// }
	//
	// private double log2(double x) {
	// if (x == 0)
	// return 0.0;
	// return Math.log(x) / Math.log(2);
	// }

	public String predict(Instance instance) {

		return predict(raiz, instance);
	}

	private String predict(Node node, Instance instance) {
		if (node.getLinks().size() > 0) {
			String label = node.getLabel();

			String value = instance.getValue(label);

			// System.out.println(instance);
			// System.out.println(label + " "+value);

			Node otherNode = node.getNode(value);
			return predict(otherNode, instance);

		}

		return node.getLabel();
	}

	public void crossValidation(int k) {
		int size = structure.getSize();
		int sizeFold = this.structure.getSize() / k;
		int sizeRem = this.structure.getSize() % k; // add no ultimo fold

		System.out.println("Size ->" + size);
		System.out.println("SizeFold ->" + sizeFold);
		System.out.println("Sobra ->" + sizeRem);
		System.out.println();

		ArrayList<Attribute> attributes = structure.getAttributes();
		Attribute target = structure.getTarget();
		ArrayList<String> targetValues = target.getValues();

		int pointer = 0;
		int max = sizeFold;

		double avgAcc = 0.0;

		for (int i = 0; i < k; i++) {
			// System.out.print("Treinando Fold " + (i + 1)+"... ");

			if (i == k - 1) {
				max += sizeRem;
			}

			Set<Integer> set = new HashSet();
			ArrayList<Instance> instances = new ArrayList();
			ArrayList<String> output = new ArrayList();

			for (int j = pointer; j < max; j++) {
				set.add(j);
				instances.add(structure.getInstance(j));
				output.add(targetValues.get(j));
				// System.out.println(structure.getInstance(j)+"->"+targetValues.get(j));
			}

			this.raiz = new Node();
			ID3(raiz, target, attributes, set);

			int instanceSize = instances.size();
			double counter = 0.0;
			for (int j = 0; j < instances.size(); j++) {
				Instance instance = instances.get(j);
				String out = output.get(j);

				try {
					String predict = predict(raiz, instance);

					// System.out.println("Predict ->"+predict);
					// System.out.println("Target ->"+out);

					if (out.equals(predict))
						counter++;
				} catch (NullPointerException e) {
					if (instanceSize > 1) {
						// counter++;
						// instanceSize--;
					}
					//
					// System.out.println(e.getMessage());
				}

			}

			double acc = (100.0 * counter / instanceSize);
			avgAcc += acc;
			// System.out.println("Acurácia = "+acc+"%");
			// System.out.println(counter);
			// System.out.println(instanceSize);
			// System.out.println();

			pointer = max;
			max += sizeFold;

		}

		avgAcc /= k;
		System.out.println("Acurária (Cross-Validation) = " + avgAcc + "%");

	}

	private void ID3(Node node, Attribute target,
			ArrayList<Attribute> attributes, Set<Integer> set) {

		if (allEquals(target)) {
			node.setLabel(target.getValues().get(0));
			return;
		}

		if (attributes.size() == 0) {
			List<String> targetValues = target.getValues();
			node.setLabel(getMostCommonElement(targetValues));
			return;
		}

		// Mudar a metrica aqui!!!!
		// Measure measure = new InfoGain();
		// Measure measure = new GainRatio();

		Attribute bestAttribute = measure.getBestAttribute(target, attributes);
		// Attribute bestAttribute = getBestAttribute(target, attributes);
		// System.out.println(bestAttribute);

		List<String> targetValues = target.getValues();

		Set<String> possibleValues = bestAttribute.getPossibleValues();

		String description = bestAttribute.getDescription();

		node.setLabel(description);
		for (String pValue : possibleValues) { // constant

			// System.out.println(pValue);
			ArrayList<Attribute> newAttributes = getNewAttributes(description,
					attributes);
			Attribute newTarget = new Attribute(target.getDescription());

			node.makeLink(pValue);

			ArrayList<String> values = bestAttribute.getValues();
			for (int i = 0; i < values.size(); i++) { // O(n)
				if (!set.contains(i)) {
					String instance = values.get(i);

					if (instance.equals(pValue)) {
						// System.out.println(instance);

						for (Attribute a : attributes) { // constant
							if (!description.equals(a.getDescription())) {

								String aValue = a.getValues().get(i);
								int index = getAttributeIndex(
										a.getDescription(), newAttributes); // constant
								Attribute newAttrib = newAttributes.get(index);
								newAttrib.addValue(aValue);

							}

						}
						newTarget.addValue(targetValues.get(i));
					}
				}

			}
			Node otherNode = node.getNode(pValue);
			if (newTarget.getValues().size() == 0) {

				otherNode.setLabel(getMostCommonElement(targetValues));
			} else {
				ID3(otherNode, newTarget, newAttributes, set);
			}

		}

	}

}
