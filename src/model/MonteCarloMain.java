//package src;
package model;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MonteCarloMain {

	public static void main(String args[]) {

		AllSimulation all=new AllSimulation("/Users/abebeamare/Desktop/Desktop/Fall2020/ece493/SRLabMonteCarloCode/microscales");
		all.runAllSimulations();
		System.out.println("Getting started with Monte Carlo simulation");
	}
//		MonteCarloDriver simulation = null;
//		try {
//		simulation = new MonteCarloDriver();
//		} catch (FileNotFoundException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//		} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		}
//		try {
//		simulation.runSimulation();
//		} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		}
//		System.out.println("Getting started with Monte Carlo simulation");
//	}
}



