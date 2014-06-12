package test;


import metrics.Costa;
import metrics.CostaSigmoid;
import metrics.GainRatio;
import metrics.Gini;
import metrics.InfoGain;
import metrics.VPRS;
import core.DecisionTree;
import input.Structure;


public class Principal {


	public static void main(String[] args) {
		
//		Structure train = new Structure("base_dados_jogarTenis.txt");
		
		
//		Structure train = new Structure("breast_cancer.txt");
//		Structure train = new Structure("iris.txt");
//		Structure train = new Structure("audiology.standardized2.csv");
//		Structure train = new Structure("soybean-large.csv");
//		Structure train = new Structure("tic-tac-toe.csv");
		Structure train = new Structure("car_evaluation.txt");
		
		
//		Structure train = new Structure("kr-vs-kp.txt");

		
		
		
//		
//		DecisionTree dt = new DecisionTree(train,new InfoGain());
//		DecisionTree dt = new DecisionTree(train,new GainRatio());
//		DecisionTree dt = new DecisionTree(train,new Gini());
//		DecisionTree dt = new DecisionTree(train,new VPRS());
//		DecisionTree dt = new DecisionTree(train,new Costa());
		DecisionTree dt = new DecisionTree(train,new CostaSigmoid());
		
//		dt.crossValidation(5);
//		dt.crossValidation(10);
//		dt.crossValidation(15);
		
		dt.crossValidation(train.getSize());
		System.out.println(dt.getSize());
		
//		dt.train();
		
//		Structure test = new Structure("test.txt");
		

		
		
	}

}
