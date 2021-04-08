//package src;
package model;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MonteCarloMainCommand {

    //    public MonteCarloMainCommand(String folderPath) {
//        AllSimulation all=new AllSimulation(folderPath);
//        all.runAllSimulations();
//        System.out.println("Getting started with Monte Carlo simulation");
//    }
    public static void main(String[] args) {
        System.out.println(args[0]);
        AllSimulation all=new AllSimulation(args[0]);
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



