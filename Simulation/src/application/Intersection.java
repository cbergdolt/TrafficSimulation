package application;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

/**
 * Intersection class holds the state of the lights and its location.  
 * 
 * @author ChuchafinotaFace
 *
 */

public class Intersection extends Observable{
	Point[] location = new Point [4]; //South, East, West, North (see Diagram below)
	// |S|W|
	// |E|N|
	StopLight light;
	TrafficSign sign;
	int id;
	char dir;
	char type;	//F=four way, T=three way, R=roundabout ??? not sure if this is necessary, just check light/sign values
	Queue<Vehicle> vehicleQueue;
	
	Intersection(Point[] loc, StopLight sl, TrafficSign ts) {
		location = loc; 
		light = sl;
		sign = ts;
		vehicleQueue = new LinkedList<Vehicle>();
	}
	
	public void updateIntersection() {
		if (light != null) light.update();
		if (sign != null && !vehicleQueue.isEmpty()) {
			Vehicle next = vehicleQueue.remove();	//get the first/next vehicle to reach the intersection
			next.start();
		}
		setChanged();
		notifyObservers();
	}
	
	public LightState getState() {
		if (light == null) return LightState.NONE;
		else return light.getState();
	}
	public Point[] getLocation() {
		return location;
	}
}
