//package JavaSimulation.src;
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Canvas {

	private double[] acceptorproperties;
	private double[] donorproperties;
	private boolean[][][] acceptor;
	private Particle[][][] particles;
	private double[][][] energy;
	private double[][][] genrate;

	private int dimx, dimy, dimz;
	private double q = 1.602E-10; //ns*A (coulomb = A*s)
	private double k = 1.38E-23; //boltzmann constant (J/K = kg*nm^2/(ns^2*K))
	private double T = 300; //temperature (K)
	private double a = 1.61; //lattice constant for 100 in nm
	private double b = 0.393; //lattice constant for 010 in nm
	private int morphology = 2;

	public boolean getAcceptor(int x, int y, int z) {
		return acceptor[x][y][z];
	}

	public boolean[][][] getAcceptor() {
		return acceptor;
	}

	public Particle getParticles(int x, int y, int z) {
		return particles[x][y][z];
	}


	public double getEnergy(int x, int y, int z) {
		return energy[x][y][z];
	}

	public double getGenrate(int x, int y, int z) {
		return genrate[x][y][z];
	}

	public Canvas(int xdim, int ydim, int zdim) throws IOException {
		initVar(xdim, ydim, zdim);
		acceptorproperties = new double[14];
		acceptorproperties[0] = 1.25; //exciton lifetime in ns
		acceptorproperties[1] = 1.710; //exciton delocalization radius from cube root of diffusion length (nm)
		acceptorproperties[2] = 3E11; //carrier mobility (nm^2/(V*s))
		acceptorproperties[3] = q;
		acceptorproperties[4] = k;
		acceptorproperties[5] = T;
		acceptorproperties[6] = a;
		acceptorproperties[7] = 6E-4; //recomb rate in 1/ns
		acceptorproperties[8] = 1; //photon to exciton conversion
		acceptorproperties[9] = 500;
		acceptorproperties[10] = b;
		acceptorproperties[11] = 3E11; //carrier mobility (nm^2/(V*s))
		acceptorproperties[12] = 99; //percentage of 100 IP sites
		acceptorproperties[13] = 1; //percentage of 100 OOP sites
		donorproperties = new double[14];
		donorproperties[0] = 0.6; //exciton lifetime in ns
		donorproperties[1] = 2.759; //exciton delocalization radius from cube root of diffusion length (nm)
		donorproperties[2] = 2E10; //hole carrier mobility for IP (nm^2/(V*s))
		donorproperties[3] = q;
		donorproperties[4] = k;
		donorproperties[5] = T;
		donorproperties[6] = a;
		donorproperties[7] = 6E-4; //recomb rate in 1/ns
		donorproperties[8] = 1; //photon to exciton conversion
		donorproperties[9] = 500;
		donorproperties[10] = b;
		donorproperties[11] = 2E10; //hole carrier mobility for OOP (nm^2/(V*s))
		donorproperties[12] = 99; //percentage of 100 IP sites
		donorproperties[13] = 1; //percentage of 100 OOP sites
		try {
			printstuff("canvas.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printstuff(String filename) throws IOException {
		FileWriter outFile = new FileWriter(filename);
		PrintWriter out = new PrintWriter(outFile);
		for (int x = 0; x < dimx; x++) {
			for (int y = 0; y < dimy; y++) {
				for (int z = 0; z < dimz; z++) {
					int flip = 0;
					if (acceptor[x][y][z]) {
						flip = 1;
					}
					else {
						flip = -1;
					}
					out.printf("%4d,%4d,%4d,%4d",x,y,z,flip);
					out.println();
				}
			}
		}

		out.close();
	}

	public double[] getMaterialproperties(int x, int y, int z) {
		if (acceptor[x][y][z] == true) {
			return acceptorproperties;
		}
		else {
			return donorproperties;
		}
	}

	public double[] getAcceptorProp() {
		return acceptorproperties;
	}

	public double[] getDonorProp() {
		return donorproperties;
	}

	private void initVar(int xdim, int ydim, int zdim) throws IOException {
		RandomGenerator rand = new RandomGenerator();
		dimx = xdim;
		dimy = ydim;
		dimz = zdim;
		particles = new Particle[xdim][ydim][zdim];
		acceptor = new boolean[xdim][ydim][zdim];
		energy = new double[xdim][ydim][zdim];

		if (morphology == 0) {
			for (int x = 0; x < dimx; x ++) {
				for (int y = 0; y < dimy; y++) {
					for (int z = 0; z < dimz; z++) {
						if (z < 20) {
							acceptor[x][y][z] = false;
						}
						else {
							acceptor[x][y][z] = true;
						}
					}
				}
			}
		}
		else if (morphology == 1){
			for (int x = 0; x < dimx; x++) {
				for (int y = 0; y < dimy; y++) {
					for (int z = 0; z < dimz; z++) {
						int tempx = x/5;
						int tempy = y/5;
						int test = (tempx+tempy) % 2;
						if (test == 0) {
							acceptor[x][y][z] = false;
						}
						else {
							acceptor[x][y][z] = true;
						}

					}
				}
			}
		}
		else if (morphology == 2) {
			// /Users/abebeamare/Desktop/Desktop/spring2021/ECE493/MonteCarlo/JavaSimulation/JavaSimulation/src/abebe_test_structure_with_fifth_column.csv
			acceptor = importMorphology("/Users/abebeamare/Desktop/Desktop/spring2021/ECE493/MonteCarlo" +
					"/SimulationJava/src/model/abebe_test_structure_with_fifth_column.csv");
		}
		printstuff("canvas.csv");
		for (int x = 0; x < dimx; x++) {
			for (int y = 0; y < dimy; y++) {
				for (int z = 0; z < dimz; z++) {
					if (acceptor[x][y][z]) {
						energy[x][y][z] = rand.nextGaussian(3969);
					}
					else {
						energy[x][y][z] = rand.nextGaussian(3969);
					}
				}
			}
		}
	}

	//public double getGenrate(int x, int y, int z) {
	//	Scanner scanner = null;
	//	try {
	//		scanner = new Scanner(new File("import.csv"));
	//	} catch (FileNotFoundException e) {
	//		e.printStackTrace();
	//	}
	//
	//	double[][][] genrate = new double[dimx][dimy][dimz];
	//
	//	while (scanner.hasNextLine()) {
	//		String line = scanner.nextLine();
	//		String[] temp = line.split(",");
	//		genrate[x][y][z] = Double.parseDouble(temp[4]);
	//	}
	//	return genrate[x][y][z];
	//}

	private boolean[][][] importMorphology(String filename) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		boolean[][][] canvas = new boolean[dimx][dimy][dimz];
		genrate = new double[dimx][dimy][dimz];

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] temp = line.split(",");
			int x = Integer.parseInt(temp[0]);
			int y = Integer.parseInt(temp[1]);
			int z = Integer.parseInt(temp[2]);
			int spin = Integer.parseInt(temp[3]);
			if (spin == 1) {
				canvas[x][y][z] = true;
			}
			else {
				canvas[x][y][z] = false;
			}
			genrate[x][y][z] = Double.parseDouble(temp[4]);
		}
		return canvas;
	}

}

