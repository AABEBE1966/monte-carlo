//package src;
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class calcDomain {

	int xdim = 100;
	int ydim = 100;
	int zdim = 60;

	public void doCalc(String filename) {
		int canvas[][][] = importMorphology(filename);
		//check[x][y][z] = true if have been there already
		boolean check[][][] = new boolean[xdim][ydim][zdim];
		for (int x = 0; x < xdim; x++) {
			for (int y = 0; y < ydim; y++) {
				for (int z = 0; z < zdim; z++) {
					check[x][y][z] = false;
				}
			}
		}
		int dx = 0, dy = 0, dz = 0;
		int area = 0;
		for (int x = 0; x < xdim; x++) {
			for (int y = 0; y < ydim; y++) {
				for (int z = 0; z < zdim; z++) {
					for (int i = 0; i < 6; i++) {
						if (i == 0) {
							dx = 1; dy = 0; dz = 0; }
						else if (i == 1) {
							dx = -1; dy = 0; dz = 0; }
						else if (i == 2) {
							dx = 0; dy = 1; dz = 0; }
						else if (i == 3) {
							dx = 0; dy = -1; dz = 0; }
						else if (i == 4) {
							dx = 0; dy = 0; dz = 1; }
						else if (i == 5) {
							dx = 0; dy = 0; dz = -1; }
						if (checkInBounds(x+dx, y+dy, z+dz)) {

							if ((canvas[x][y][z] != canvas[x+dx][y+dy][z+dz]) && (!check[x+dx][y+dy][z+dz])) {
								area++;
							}
						}
					}
					check[x][y][z] = true;
				}
			}
		}
		System.out.println(area);
		double vol = xdim*ydim*zdim;
		System.out.println(vol);
		double aread = area;
		System.out.println(3*vol/aread);
	}

	private int[][][] importMorphology(String filename) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int[][][] canvas = new int[xdim][ydim][zdim];
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] temp = line.split(",");
			int x = Integer.parseInt(temp[0]);
			int y = Integer.parseInt(temp[1]);
			int z = Integer.parseInt(temp[2]);
			int spin = Integer.parseInt(temp[3]);
			canvas[x][y][z] = spin;
		}
		return canvas;
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
