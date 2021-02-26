//package JavaSimulation.src;
package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CalcEfficiency {

	public Queue<Double> carrierNum;
	public int size;

	public CalcEfficiency() {
		carrierNum = new LinkedList<Double>();
		size = carrierNum.size();
	}

	private double calcSTD() {
		double std = 0;
		double sum = 0;
		double avg = calcAVG();
		Iterator<Double> i = carrierNum.iterator();
		while (i.hasNext()) {
			sum += Math.pow((avg - i.next()),2);
		}
		std = Math.sqrt(sum/size);
		return std;
	}

	private double calcAVG() {
		double sum = 0;
		Iterator<Double> i = carrierNum.iterator();
		while (i.hasNext()) {
			sum += i.next();
		}
		return sum/size;
	}

	public double calcSteady() {
		return calcSTD()/calcAVG();
	}

	public void add(double num) {
		if (size < 500) {
			carrierNum.add(num);
			size++;
		}
		else {
			carrierNum.remove();
			carrierNum.add(num);
		}
	}
}
