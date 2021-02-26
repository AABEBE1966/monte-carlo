//package JavaSimulation.src;
package model;

import java.io.*;
import java.util.*;

public class MonteCarloDriver {
	private final int xdim = 100;
	private final int ydim = 100;
	private final int zdim = 60;

	private Particle[][][] particles;
	private PriorityQueue<Event> eventQueue;

	private Canvas canvas;
	private ExcitonGenerator ExciGenerator;
	private double Time;
	private int CCEvent;
	private int holesCollected; //number of holes collected
	private int electronsCollected; //number of electrons collected
	private int excitonsCreated; //number of excitons created
	private int excitonsDissociated; //number of excitons dissociated
	private int numCarriers; //number of charge carriers in the system
	private int CCRecomb; //number of charge carriers recombine events
	private CalcEfficiency Efficiency;
	private double excirate;

	public MonteCarloDriver() throws IOException {
		CCEvent = 0;
		Time = 0;
		holesCollected = 0;
		electronsCollected = 0;
		excitonsCreated = 0;
		excitonsDissociated = 0;
		numCarriers = 0;
		CCRecomb = 0;
		eventQueue = new PriorityQueue<Event>();
		Particle.genDistances();
		Particle.setCanvasSize(xdim, ydim, zdim);
		RandomGenerator.initRand();
		canvas = new Canvas(xdim, ydim, zdim);
		ExciGenerator = new ExcitonGenerator();
		Efficiency = new CalcEfficiency();
		excirate = ExciGenerator.getExcitonRate();
		particles = new Particle[xdim][ydim][zdim];
	}

	public void runSimulation() throws IOException {
		FileWriter create = new FileWriter("output.csv");
		PrintWriter out = new PrintWriter(create);
		out.println("Time, Num Carriers, CC CE, CC RE, #H C , #El C, #Ex C, #Ex D");
		int[] exciloc = ExciGenerator.genExciton(xdim, ydim);
		Event excigen = new Event(0.0, "excigen", exciloc[0], exciloc[1], exciloc[2], 0, 0, 0);
		eventQueue.add(excigen);
		int counter = 0;
		ArrayList<double[]> rhos = new ArrayList<double[]>();
		while(eventQueue.size() > 0 && CCEvent < 4001) // Pop events if there is one to pop.
		{
			Event temp = eventQueue.poll();

			// update times
			double time = temp.getTime();
			updateTime(time);
			Time += time;

			//parse event and add new events
			processEvent(temp, out);
			double rho = 1;
			if (CCEvent > 0 && (CCEvent % 50) == 0 && (counter-CCEvent) != 0) {
				counter = CCEvent;
				rho = Efficiency.calcSteady();
				System.out.println(rho);
				double[] rhotime = {Time, rho};
				rhos.add(rhotime);
			}
		}
//		while (CCEvent < 201) {
//			Event temp = eventQueue.poll();
//
//			// update times
//			double time = temp.getTime();
//			updateTime(time);
//			Time += time;
//
//			//parse event and add new events
//			processEvent(temp, out);
//		}
		for (int i = 0; i < rhos.size(); i++) {
			double[] rhotime = rhos.get(i);
			out.printf("%d, %d", rhotime[0], rhotime[1]);
		}
		out.close();
	}

	private void printStuff(PrintWriter out) {
		System.out.printf("%f, %d, %d, %d, %d, %d, %d, %d \n", Time, numCarriers, CCEvent, CCRecomb, holesCollected, electronsCollected, excitonsCreated, excitonsDissociated);
		out.printf("%f, %d, %d, %d, %d, %d, %d, %d \n", Time, numCarriers, CCEvent, CCRecomb, holesCollected, electronsCollected, excitonsCreated, excitonsDissociated);
	}

	private void processEvent(Event temp, PrintWriter out) throws IOException {

		String action = temp.getAction();  //Get the Event action

		int[] pos = temp.getPosition();  //Get the position of the particle for which this action is associated
		int x = pos[0]; int y = pos[1]; int z=pos[2];

		int[] change = temp.getChange();  //Get the change associated with this action
		int dx = change[0]; int dy = change[1]; int dz = change[2];

		Particle part = particles[x][y][z];
		String type = null;
		if (part != null) {
			type = part.type; //Get the particle type (Hole, Electron, or Exciton)
		}

		if(particles[x][y][z] == null && !action.equals("excigen")) {
			//particle has been destroyed prior
			return;
		}

		else if(action.equals("collection")) {
			particles[x][y][z] = null;		// destroy particle
			CCEvent++;
			if (type.equals("Electron")) {
				electronsCollected++;
			}
			else {
				holesCollected++;
			}
			numCarriers--;
			Efficiency.add(numCarriers);
			printStuff(out);
		}
		else if(action.equals("excigen")) {
			if (particles[x][y][z] == null) {//If location is empty, generate exciton
				particles[x][y][z] = new Exciton(x,y,z,canvas.getMaterialproperties(x, y, z));
				eventQueue.add(particles[x][y][z].nextEvent(getNeighbours(x,y,z),
						getCanvastype(x,y,z), getEnergy(x,y,z)));
			}
			else {//If location is occupied, try another location
				int[] loc = ExciGenerator.genExciton(xdim, ydim);
				x = loc[0]; y = loc[1];	z = loc[2];
				while (particles[x][y][z] != null) {//keep trying locations until we find an empty one
					loc = ExciGenerator.genExciton(xdim, ydim);
					x = loc[0]; y = loc[1]; z = loc[2];
				}
				particles[x][y][z] = new Exciton(x,y,z,canvas.getMaterialproperties(x, y, z));
				eventQueue.add(particles[x][y][z].nextEvent(getNeighbours(x,y,z),
						getCanvastype(x,y,z), getEnergy(x,y,z)));
			}
			excitonsCreated++;
			printStuff(out);
			int[] loc = ExciGenerator.genExciton(xdim, ydim);
			Event exci = new Event((100000/canvas.getGenrate(loc[0], loc[1], loc[2]))*-Math.log(RandomGenerator.randDouble()), "excigen", loc[0], loc[1], loc[2], 0,0,0);
			eventQueue.add(exci);
		}
		else if(action.equals("recomb")) {
			if (type.equals("Exciton")) {
				particles[x][y][z] = null;		// destroy particle
			}
			else {
				String opposite = "Hole";
				if (type.equals("Hole")) {
					opposite = "Electron";
				}
				boolean reset = true;
				if (particles[x+dx][y+dy][z+dz] != null) {
					Particle part2 = particles[x+dx][y+dy][z+dz];
					String type2 = part2.type;
					if (type2.equals(opposite)) {
						particles[x][y][z] = null;
						particles[x+dx][y+dy][z+dz] = null;
						reset = false;
						CCRecomb++;
						numCarriers -= 2;
						printStuff(out);
					}
				}
				if (reset) {
					eventQueue.add(particles[x][y][z].nextEvent(
							getNeighbours(x,y,z),
							getCanvastype(x,y,z),
							getEnergy(x,y,z)));
				}
			}
		}
		else if(action.startsWith("dissociate")) {

			boolean reset = true;

			if (action.charAt(10) == 'A') {
				if (particles[x + dx][y + dy][z + dz] == null) {

					particles[x][y][z] = new Electron(x, y, z,
							canvas.getMaterialproperties(x, y, z));
					particles[x + dx][y + dy][z + dz] = new Hole(x + dx, y + dy, z + dz,
							canvas.getMaterialproperties(x + dx, y + dy, z + dz));
					eventQueue.add(particles[x + dx][y + dy][z + dz].nextEvent(
							getNeighbours(x + dx,y + dy,z + dz),
							getCanvastype(x + dx,y + dy,z + dz),
							getEnergy(x + dx,y + dy,z + dz)));
					eventQueue.add(particles[x][y][z].nextEvent(
							getNeighbours(x,y,z),
							getCanvastype(x,y,z),
							getEnergy(x,y,z)));
					reset = false;
				}
			}
			else {
				if (particles[x + dx][y + dy][z + dz] == null) {

					particles[x][y][z] = new Hole(x, y, z,
							canvas.getMaterialproperties(x, y, z));
					particles[x + dx][y + dy][z + dz] = new Electron(x + dx, y + dy, z + dz,
							canvas.getMaterialproperties(x + dx, y + dy, z + dz));
					eventQueue.add(particles[x][y][z].nextEvent(
							getNeighbours(x,y,z),
							getCanvastype(x,y,z),
							getEnergy(x,y,z)));
					eventQueue.add(particles[x + dx][y + dy][z + dz].nextEvent(
							getNeighbours(x + dx,y + dy,z + dz),
							getCanvastype(x + dx,y + dy,z + dz),
							getEnergy(x + dx,y + dy,z + dz)));
					reset = false;
				}
			}

			if (reset) {
				Event newevent = new Event(0.0, "move", x, y, z, -dx, -dy, -dz );
				eventQueue.add(newevent);
			}
			else {
				excitonsDissociated++;
				numCarriers += 2;
				printStuff(out);
			}
		}
		else if(action.startsWith("move")) {

			if(!checkInBounds(x+dx,y+dy,z+dz)) {
				eventQueue.add(particles[x][y][z].nextEvent(
					getNeighbours(x,y,z),
					getCanvastype(x,y,z),
					getEnergy(x,y,z)));
			}
			else if(particles[x+dx][y+dy][z+dz] == null) {	// doesn't have a particle in it already
				if (type.equals("Hole")) {
					particles[x+dx][y+dy][z+dz] = new Hole(x+dx, y+dy, z+dz,
						canvas.getMaterialproperties(x+dx, y+dy, z+dz));
				}
				else if (type.equals("Electron")) {
					particles[x+dx][y+dy][z+dz] = new Electron(x+dx, y+dy, z+dz,
						canvas.getMaterialproperties(x+dx, y+dy, z+dz));
				}
				else {
					Exciton excipart = (Exciton) part;
					particles[x+dx][y+dy][z+dz] = new Exciton(x+dx, y+dy, z+dz,
							excipart.timealive, canvas.getMaterialproperties(x+dx, y+dy, z+dz));;

				}
				particles[x][y][z] = null;
				eventQueue.add(particles[x+dx][y+dy][z+dz].nextEvent(
					getNeighbours(x+dx, y+dy, z+dz),
					getCanvastype(x+dx, y+dy, z+dz),
					getEnergy(x+dx,y+dy,z+dz)));
			}
			else {  	// regenerate event for particle
				eventQueue.add(particles[x][y][z].nextEvent(
					getNeighbours(x,y,z),
					getCanvastype(x,y,z),
					getEnergy(x,y,z)));
			}
		}

	}

	private void updateTime(double time) {
		Event[] eventtemp = new Event[eventQueue.size()];
		int index = 0;
		for(Event x : eventQueue)
		{
			double t = x.getTime();
			x.setTime(t - time);
			eventtemp[index] = x;
			index++;
		}
		eventQueue.clear();
		for (Event x: eventtemp) {
			eventQueue.add(x);
		}
		for(int x = 0; x < xdim; x++) {
			for(int y = 0; y < ydim; y++) {
				for(int z = 0; z < zdim; z++) {
					if(particles[x][y][z] != null) {           // Contains a particle
						String type = particles[x][y][z].type;
						if(type.compareTo("Exciton")==0) {
							Exciton temp = (Exciton) particles[x][y][z];
							temp.updateLifetime(time);
							particles[x][y][z] = temp;
						}
					}
				}
			}
		}
	}


	private ArrayList<int[]> getNeighbours(int x, int y, int z) {
		ArrayList<int[]> neighbours = new ArrayList<int[]>();
		for (int dx = -4; dx < 5; dx++) {
			for (int dy = -4; dy < 5; dy++) {
				for (int dz = -4; dz < 5; dz++) {
					if (dx == 0 && dy == 0 && dz == 0) {
						continue;
					}
					if(checkInBounds(x+dx, y+dy, z+dz)) {
						if (particles[x+dx][y+dy][z+dz] != null) {
							Particle temp = particles[x+dx][y+dy][z+dz];
							if (temp.type.equals("Electron")) {
								int[] temploc = {dx,dy,dz,-1};
								neighbours.add(temploc);
							}
							else if (temp.type.equals("Hole")) {
								int[] temploc = {dx,dy,dz,1};
								neighbours.add(temploc);
							}
						}
					}
				}
			}
		}
		return neighbours;
	}

	private boolean[] getCanvastype(int x, int y, int z) {
		boolean[] acceptors = new boolean[125];
		int index = 0;
		for (int dx = -2; dx < 3; dx++) {
			for (int dy = -2; dy < 3; dy++) {
				for (int dz = -2; dz < 3; dz++) {
					if(!checkInBounds(x+dx, y+dy, z+dz))
						acceptors[index] = false;
					else
						acceptors[index] = canvas.getAcceptor(x+dx, y+dy, z+dz);
					index++;
				}
			}
		}
		return acceptors;
	}

	private double[] getEnergy(int x, int y, int z) {
		double[] energy = new double[125];
		int index = 0;
		for (int dx = -2; dx < 3; dx++) {
			for (int dy = -2; dy < 3; dy++) {
				for (int dz = -2; dz < 3; dz++) {
					if(!checkInBounds(x+dx, y+dy, z+dz))
						energy[index] = -1;
					else
						energy[index] = canvas.getEnergy(x+dx, y+dy, z+dz);
					index++;
				}
			}
		}
		return energy;
	}

	private boolean checkInBounds(int x, int y, int z)
	{
		//false not inbounds, true inbounds
		if(x < 0 || y < 0 || z < 0)
			return false;
		if(x >= xdim || y >= ydim || z >= zdim)
			return false;
		return true;
	}
}
