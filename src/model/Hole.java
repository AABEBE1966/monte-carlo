//package JavaSimulation.src;
package model;

import java.util.ArrayList;
import java.util.PriorityQueue;


public class Hole extends Particle {

	public boolean nearelectron;
	public boolean nearcontact;
	private double a;
	private double b;
	private double mu1;
	private double mu2;
	private double sel1;
	private double sel2;
	private double q;
	private double k;
	private double T;
	private double e; //work function added energy
	private double recombrate;

	public Hole(int x, int y, int z, double[] materialprop) {
		type = "Hole";
		xloc = x;
		yloc = y;
		zloc = z;
		a = materialprop[6];
		b = materialprop[10];
		sel1 = materialprop[12];
		sel2 = materialprop[13];
		mu1 = materialprop[2];
		mu2 = materialprop[11];
		q = materialprop[3];
		k = materialprop[4];
		T = materialprop[5];
		recombrate = materialprop[7];
		e = materialprop[9];
		nearelectron = false;
		nearcontact = true;
	}

	public Event nextEvent(ArrayList<int[]> neighbors, boolean[] acceptors, double[] energy) {
		PriorityQueue<Event> events = new PriorityQueue<Event>(130);

		double[] energies = genEnergies(acceptors, energy);
		double currentenergy = energy[62];
		applyCoulomb(neighbors, energies);

		double [] hoptime = genHopTimes(distances, energies, currentenergy);

		//Move events
		int i = 0;
		for (int dx = -2; dx < 3; dx++) {
			for (int dy = -2; dy < 3; dy++) {
				for (int dz = -2; dz < 3; dz++) {
					if (i != 62) {
						events.add(new Event(hoptime[i], "move", xloc, yloc, zloc, dx, dy, dz));
					}
					i++;
				}
			}
		}

		Event recomb = getRecombEvent(neighbors);
		events.add(recomb);
		Event charge_collection = genCollectionEvent();
		events.add(charge_collection);
		Event ret = events.poll();
		return ret;
	}

	private double[] genHopTimes(double[] distances, double[] energies,
			double currentenergy) {
		double [] hoptime = new double[distances.length];
		for (int i = 0; i < distances.length; i++) {
			double hopf = hopFunction(currentenergy, energies[i]);
			double lnx = Math.log(RandomGenerator.randDouble());
			int latticeselector = RandomGenerator.randInt();
			int latticeselector2 = RandomGenerator.randInt();

			if (latticeselector <= sel1 && latticeselector2 <= sel2)	{
				hoptime[i] = -1*lnx*q*Math.pow(a, 2)*
					Math.exp(4*a*distances[i]-4*Math.pow(a,2))/(6*k*T*mu1*hopf);
			}
			else {
				hoptime[i] = -1*lnx*q*Math.pow(b, 2)*
						Math.exp(4*b*distances[i]-4*Math.pow(b,2))/(6*k*T*mu2*hopf);
				}
			}
		return hoptime;
	}

	private Event getRecombEvent(ArrayList<int[]> neighbors) {
		int dx = 0, dy = 0, dz = 0;
		Event recombevent = new Event(1E12, "recomb", xloc, yloc,
				zloc, 0 ,0 ,0);
		ArrayList<Event> recombArray = new ArrayList<Event>();
		for (int i = 0; i < neighbors.size(); i++) {
			int[] neighbor = neighbors.get(i);
			if (neighbor[3] == -1) {
				dx = neighbor[0];
				dy = neighbor[1];
				dz = neighbor[2];
				double lnx = Math.log(RandomGenerator.randDouble());
				double time = (-1)*lnx*1/recombrate;
				Event temp = new Event(time, "recomb", xloc, yloc, zloc,
						dx, dy, dz);
				recombArray.add(temp);
			}
		}
		if (recombArray.isEmpty()) return recombevent;
		return recombArray.get(RandomGenerator.randIndex(recombArray.size()));
	}

	private double[] genEnergies(boolean[] acceptors, double[] energy) {
		// TODO Auto-generated method stub
		double[] energies = new double[acceptors.length];
		for(int i = 0; i < acceptors.length;i++) {
			if (!acceptors[i]) {
				energies[i] = energy[i];
			}
			else {
				energies[i] = energy[i] + 100000;
			}
		}

		int i = 0;
		for (int dx = -2; dx < 3; dx++) {
			for (int dy = -2; dy < 3; dy++) {
				for (int dz = -2; dz < 3; dz++) {
					double enchange = (e/(zdim+30)*(zloc+dz+30));
					energies[i] -= enchange;
					i++;
				}
			}
		}

		return energies;
	}
}
