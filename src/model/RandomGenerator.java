//package JavaSimulation.src;
package model;

import java.util.*;

public class RandomGenerator {

	private static Random rand;

	public static void initRand() {
		rand = new Random(0);
	}

	public static double randDouble() {
		return rand.nextDouble();
	}

	public static int randInt() {
		rand = new Random();
		return rand.nextInt(100);
	}

	public static int randIndex(int size){
		return rand.nextInt(size);
	}

	public static boolean coinFlip(double p)
	{
		if(rand.nextDouble() < p)
			return true;
		return false;
	}

	private double nextNextGaussian;
	private boolean haveNextNextGaussian = false;

	public double nextGaussian(double variance) {
		if (haveNextNextGaussian) {
			haveNextNextGaussian = false;
			return nextNextGaussian;
		} else {
			double v1, v2, s;
			do {
				v1 = 2 * rand.nextDouble() - 1;   // between -1.0 and 1.0
				v2 = 2 * rand.nextDouble() - 1;   // between -1.0 and 1.0
				s = v1 * v1 + v2 * v2;
			} while (s >= 1 || s == 0);
			double multiplier = StrictMath.sqrt(-2*variance * StrictMath.log(s)/s);
			nextNextGaussian = v2 * multiplier;
			haveNextNextGaussian = true;
			return v1 * multiplier;
		}
	}
}
