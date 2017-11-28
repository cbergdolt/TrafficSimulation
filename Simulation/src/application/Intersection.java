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
	// |S|W| >> |0|2|
	// |E|N| >> |1|3|
	StopLight light;
	TrafficSign sign;
	RoundaboutSegment roundabout;
	int id;
	char dir;
	char type;	//F=four way, T=three way, R=roundabout ??? not sure if this is necessary, just check light/sign values
	Queue<Vehicle> vehicleQueue;
	Vehicle inIntersection;		//null if no car in intersection, otherwise contains the vehicle that is in the intersection

	Intersection(Point[] loc, StopLight sl, TrafficSign ts, RoundaboutSegment rab) {
		location = loc;
		light = sl;
		sign = ts;
		roundabout = rab;
		inIntersection = null;	//intersection is empty
		vehicleQueue = new LinkedList<Vehicle>();
	}
	
	Intersection(Point[] loc, StopLight sl, TrafficSign ts) {
		this(loc, sl, ts, null);
		//location = loc; 
		//light = sl;
		//sign = ts;
		//vehicleQueue = new LinkedList<Vehicle>();
	}
	
	public void updateIntersection() {
		updateInIntersection();	//determine whether there is still a car in the intersection
		if (light != null) light.update();
		if (sign != null && !vehicleQueue.isEmpty()) {
			if (roundabout != null) {
				roundabout.update();
			}
			//Vehicle next = vehicleQueue.remove();	//get the first/next vehicle to reach the intersection
			Vehicle next = vehicleQueue.peek();
			if (inIntersection == null) {	//only start the vehicle if there are no other cars in the intersection
				next.start();
				inIntersection = next;
				vehicleQueue.remove(next);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	private void updateInIntersection() {
		if (inIntersection == null) return;
		Point loc = inIntersection.location; //location of vehicle in the intersection
		//determine whether vehicle is out of intersection
		if (loc.x < location[0].x || loc.x > location[3].x || loc.y < location[2].y || loc.y > location[1].y) {
			//make vehicle observe roundabout so it will follow the roundabout
			/*if (roundabout != null) {
				roundabout.addObserver(inIntersection);
			}*/
			inIntersection = null;

		}
		//else, just leave inIntersection alone, the vehicle is still in the intersection
	}
	
	public LightState getState() {
		if (light == null) return LightState.NONE;
		else return light.getState();
	}
	public Point[] getLocation() {
		return location;
	}
}
