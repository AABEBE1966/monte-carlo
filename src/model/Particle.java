//package JavaSimulation.src;
package model;

import java.util.ArrayList;

public abstract class Particle {

	public String type;
	//The location of the particle
	public int xloc;
	public int yloc;
	public int zloc;
	private double T = 300; //temperature
	protected static int xdim;
	protected static int ydim;
	protected static int zdim;
	/*
	 * Comparator for Events
	 * compares by the time
	 */

	protected static double[] distances;

	public static void setCanvasSize(int x, int y, int z) {
		xdim = x;
		ydim = y;
		zdim = z;
	}

	protected static void genDistances() {
		int index = 0;
		double a = 1.61; //lattice constant for 100 in nm
		double b = 0.393; //lattice constant for 010 in nm
		double sel1 = 99; //percentage of 100 IP sites
		double sel2 = 1; //percentage of 100 OOP sites
		distances = new double[125];
		for (double dx = -2.0; dx < 3; dx++) {
			for (double dy = -2.0; dy < 3; dy++) {
				for (double dz = -2.0; dz < 3; dz++) {
					int latticeselector = RandomGenerator.randInt();
					int latticeselector2 = RandomGenerator.randInt();
					double dist = 0;

					if (latticeselector <= sel1) 	{
						dist = Math.pow(a*100*dx, 2.0) + Math.pow(a*100*dy, 2.0);
						if (latticeselector2 <= sel2) {
							distances[index] = Math.sqrt(dist + Math.pow(a*dz, 2.0));
						}
						else {
							distances[index] = Math.sqrt(dist + Math.pow(b*dz, 2.0));
						}
						index++;
					}
					else {
						dist = Math.pow(b*100*dx, 2.0) + Math.pow(b*100*dy, 2.0);
						if (latticeselector2 <= sel2) {
							distances[index] = Math.sqrt(dist + Math.pow(a*dz, 2.0));
						}
						else {
							distances[index] = Math.sqrt(dist + Math.pow(b*dz, 2.0));
						}
						index++;
					}

				}
			}
		}
	}

	/**
	 * Generates the hop function used to calculate
	 * hopping rates and frequency
	 * @param Ej is the energy at location j
	 * @param Ei is the energy at location i
	 * @return returns f(Ei, Ej) used to calculate the rate of hop
	 */
	double hopFunction(double Ei, double Ej) {
		double kb = 0.086173; // meV/K
		if ((Ej-Ei)>0) {
			double hopf = Math.exp(-(Ej-Ei)/(kb*T));
			return hopf;
		}
		return 1;
	}

	/**Gives the next event for this particle by sorting through
	 * all possible events and giving back event queued to happen
	 * next
	 * @param events is an array of Events the particle can experience
	 * @return returns the event that comes first
	 */

	abstract Event nextEvent(ArrayList<int[]> neighbours, boolean[] acceptors, double[] energy);

	protected boolean checkInBounds(int x, int y, int z)
	{
		//false not inbounds, true inbounds
		if(x < 0 || y < 0 || z < 0)
			return false;
		if(x >= xdim || y >= ydim || z >= zdim)
			return false;
		return true;
	}

	protected void applyCoulomb(ArrayList<int[]> neighbors, double[] energies) {
		ArrayList<double[]> electrons = new ArrayList<double[]>();
		ArrayList<double[]> holes = new ArrayList<double[]>();
		for (int i = 0; i < neighbors.size(); i++) {
			int[] temp = neighbors.get(i);
			double[] temploc = {temp[0],temp[1],temp[2]};
			if (temp[3] == -1) {
				electrons.add(temploc);
			}
			else{
				holes.add(temploc);
			}
		}

		if (type.compareTo("Electron")==0) {
			while(!electrons.isEmpty()) {
				double[] location = electrons.get(0);
				energies = coulombenChange(energies, location, false);
				electrons.remove(0);
			}
			while(!holes.isEmpty()) {
				double[] location = holes.get(0);
				energies = coulombenChange(energies, location, true);
				holes.remove(0);
			}
		}
		if (type.compareTo("Hole")==0) {
			while(!electrons.isEmpty()) {
				double[] location = electrons.get(0);
				energies = coulombenChange(energies, location, true);
				electrons.remove(0);
			}
			while(!holes.isEmpty()) {
				double[] location = holes.get(0);
				energies = coulombenChange(energies, location, false);
				holes.remove(0);
			}
		}
	}

	protected Event genCollectionEvent() {
		Event Cevent;
		if (type.compareTo("Electron") == 0 && zloc == (zdim -1)) {
			Cevent = new Event(0,"collection",xloc,yloc,zloc,0,0,0);
		}
		else if (type.compareTo("Hole") == 0 && zloc == 0)	{
			Cevent = new Event(0,"collection",xloc,yloc,zloc,0,0,0);
		}
		else {
			Cevent = new Event(1E12,"collection",xloc,yloc,zloc,0,0,0);
		}
		return Cevent;
	}

	private double[] coulombenChange(double energies[], double location[], boolean add) {
		double one_nm = 411.4; //coulomb energy(meV) from distance of 1nm
		int index = 0;
		double a = 1.61; //lattice constant for 100 in nm
		double b = 0.393; //lattice constant for 010 in nm
		double sel1 = 99; //percentage of 100 IP sites
		double sel2 = 1; //percentage of 100 OOP sites
		for (double dx = -2.0; dx < 3; dx++) {
			for (double dy = -2.0; dy < 3; dy++) {
				for (double dz = -2.0; dz < 3; dz++) {
					int latticeselector = RandomGenerator.randInt();
					int latticeselector2 = RandomGenerator.randInt();
					double dist = 0;
					double distance = 0;

					if (latticeselector <= sel1)	{
						dist = Math.pow(a*100*(dx-location[0]), 2.0) + Math.pow(a*100*(dy-location[1]), 2.0);

						if (latticeselector2 <= sel2) {
							distance = Math.sqrt(dist + Math.pow(a*(dz-location[2]), 2.0));
						}
						else {
							distance = Math.sqrt(dist + Math.pow(b*(dz-location[2]), 2.0));
						}

						if (add) {
							energies[index] = energies[index] + one_nm/distance;
						}
						else {
							energies[index] = energies[index] - one_nm/distance;
						}
					}
					else	{
						dist = Math.pow(b*100*(dx-location[0]), 2.0) + Math.pow(b*100*(dy-location[1]), 2.0);

						if (latticeselector2 <= sel2) {
							distance = Math.sqrt(dist + Math.pow(a*(dz-location[2]), 2.0));
						}
						else {
							distance = Math.sqrt(dist + Math.pow(b*(dz-location[2]), 2.0));
						}

						if (add) {
							energies[index] = energies[index] + one_nm/distance;
						}
						else {
							energies[index] = energies[index] - one_nm/distance;
						}
					}

					index++;
				}
			}
		}
		return energies;
	}

}
