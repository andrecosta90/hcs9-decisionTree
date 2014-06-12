package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Node;



public class Teste {
	public static void main(String[] args) {
	System.out.println(sigmoid(0));

	}
	
	public static double sigmoid(double value){
		return 1.0/(1.0 + Math.exp(-value));
	}

	private static String getMostCommonElement(List<String> l) {
		int counter = 0;
		String pointer = null;

		for (String e : l) {
			if (counter == 0){
				pointer = e;
				counter++;
			} else{
				if (pointer.equals(e)){
					counter++;
				} else{
					counter--;
				}
			}
		}

		return pointer;
	}
}
