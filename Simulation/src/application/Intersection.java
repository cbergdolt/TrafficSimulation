package application;

import java.awt.Point;
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
	}
	
	public void updateIntersection() {
		// TODO Auto-generated method stub
		if (light != null) light.update();
		setChanged();
		notifyObservers();
		//System.out.println("updated intersection");
	}
	
	public LightState getState() {
		if (light == null) return LightState.NONE;
		else return light.getState();
	}
	public Point[] getLocation() {
		return location;
	}
}
