//package JavaSimulation.src;
package model;

public class Event implements Comparable<Event>{

	private String action;
	private double time;
	private int[] location;
	private int[] change;
	/**
	 * The constructor for an Event which has time the event occurs
	 * the action of the event, and who the event occurs for
	 * who is the location of the particle
	 * @param t is the time of the event
	 * @param a is the action of the event
	 * @param x is the x value for the particle this event is associated with
	 * @param y is the y value for the particle this event is associated with
	 * @param z is the z value for the particle this event is associated with
	 */
	public Event(double t, String a, int x, int y, int z, int dx, int dy, int dz) {
		action = a;
		time = t;
		location = new int[3];
		change = new int[3];
		location[0] = x;
		location[1] = y;
		location[2] = z;
		change[0] = dx;
		change[1] = dy;
		change[2] = dz;
	}


	public int compareTo(Event b) {
		if (this.time>b.time) return 1;
		if (this.time<b.time) return -1;
		return 0;
	}

	public void setTime(double t) {
		time = t;
	}

	public String getAction() {
		return action;
	}

	public double getTime() {
		return time;
	}

	public int[] getPosition() {
		return location;
	}

	public int[] getChange() {
		return change;
	}
}
