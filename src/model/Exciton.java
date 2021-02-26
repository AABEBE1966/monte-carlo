//package JavaSimulation.src;
package model;

import java.util.*;

public class Exciton extends Particle {

	public double lifetime; //life time of the exciton
	public double Ld; //the localization radius of the exciton
	public double timealive; //time the exciton has existed

	public Exciton(int x, int y, int z, double alive, double[] materialprop) {
		type = "Exciton";
		xloc = x;
		yloc = y;
		zloc = z;
		lifetime = materialprop[0];
		timealive = alive;
		Ld = materialprop[1];
	}

	public Exciton(int x, int y, int z, double[] materialprop) {
		this(x, y, z, 0, materialprop);
	}

	public Event nextEvent(ArrayList<int[]> neighbours, boolean[] acceptors, double[] energy) {
		PriorityQueue<Event> events = new PriorityQueue<Event>(126); //two additional events for recombination, dissociation

		double currentenergy = energy[62];

		double [] hoptime = genHopTimes(distances, energy, currentenergy);

		//Move events
		int i = 0;
		for (int dx = -2; dx < 3; dx++) {
			for (int dy = -2; dy < 3; dy++) {
				for (int dz = -2; dz < 3; dz++) {
					if (i != 62) {
						events.add(new Event(hoptime[i], "move",xloc,yloc,zloc, dx, dy, dz));
					}
					i++;
				}
			}
		}

		//Recomb event
		if (timealive >= lifetime) {
			Event recomb = new Event(0.0,"recomb",xloc,yloc,zloc, 0, 0, 0);
			events.add(recomb);
		}

		//Dissociation events
		events.add(getDissociateEvent(acceptors));

		Event test = events.poll();
		return test;
	}

	private double[] genHopTimes(double[] distance, double[] energies,
			double currentenergy) {
		double[] hoptime = new double[distance.length];
		for (int i=0; i<distance.length; i++) {
			double hopf = hopFunction(currentenergy, energies[i]);
			double lnx = Math.log(RandomGenerator.randDouble());
			hoptime[i] = (-1)*lifetime*Math.pow((distance[i]/Ld), 6)*lnx/hopf;
		}
		return hoptime;
	}

	public void updateLifetime(double time) {
		timealive += time;
	}

	private Event getDissociateEvent(boolean[] acceptors) {
		int[] indexcheck = {31, 32, 33, 36, 37, 38, 41, 42, 43, 56, 57, 58, 61, 62, 63, 66, 67, 68, 81, 82, 83, 86, 87, 88, 91, 92, 93};
		int dx = 0, dy = 0, dz = 0;
		Event dissoc = new Event(1E12, "dissociate", xloc, yloc, zloc, 0 ,0 ,0);
		ArrayList<Event> dissocArray = new ArrayList<Event>();
		for (int i = 0; i < indexcheck.length; i++) {

			if (i/9%9 == 0) dx = -1;
			else if (i/9%9 == 1) dx = 0;
			else dx = 1;
			if (i/3%3 == 0) dy = -1;
			else if (i/3%3 == 1) dy = 0;
			else dy = 1;
			if (i%3 == 0) dz = -1;
			else if (i%3 == 1) dz = 0;
			else dz = 1;

			if ((acceptors[indexcheck[i]] != acceptors[62]) && checkInBounds(xloc+dx, yloc+dy, zloc+dz)) {
				Event temp;
				if (acceptors[62]) {
					temp = new Event(0.0, "dissociateA", xloc,yloc,zloc, dx, dy, dz);
				}
				else {
					temp = new Event(0.0, "dissociateD", xloc,yloc,zloc, dx, dy, dz);
				}
				dissocArray.add(temp);
			}
		}
		if (dissocArray.isEmpty()) return dissoc;
		return dissocArray.get(RandomGenerator.randIndex(dissocArray.size()));
	}
}
