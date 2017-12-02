package application.intersection;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import application.vehicle.*;

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
	char blocked;	//0 (not blocked) or N/S/E/W, for which direction is blocked
	int id;
	char dir;
	IntersectionType type;
	Queue<Vehicle> vehicleQueue;
	Vehicle inIntersection;		//null if no car in intersection, otherwise contains the vehicle that is in the intersection

	public Intersection(Point[] loc, IntersectionType t, StopLight sl, TrafficSign ts, RoundaboutSegment rab) {
		location = loc;
		type = t;
		light = sl;
		sign = ts;
		roundabout = rab;
		blocked = 0;	//intersection not blocked by default
		inIntersection = null;	//intersection is empty
		vehicleQueue = new LinkedList<Vehicle>();
	}
	
	public Intersection(Point[] loc, IntersectionType t, StopLight sl, TrafficSign ts) {
		this(loc, t, sl, ts, null);
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
		Point loc = inIntersection.getLocation(); //location of vehicle in the intersection
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
	
	public void blockDirection(int d) { 
		type = type.blockDirection(d);	//actually change the intersection type to block traffic
		if (d == 0) blocked = 'S';
		else if (d == 1) blocked = 'E';
		else if (d == 2) blocked = 'W';
		else if (d == 3) blocked = 'N';
		else System.out.println("Intersection::blockOppositeDirection: something has gone horribly wrong");
	}
	public void blockOppositeDirection(int d) {
		switch (d) {
		case 0: //S
			type = type.blockDirection(3);	//block N
			blocked = 'N';
			break;
		case 1: //E
			type = type.blockDirection(2);	//block W
			blocked = 'W';
			break;
		case 2: //W
			type = type.blockDirection(1);	//block E
			blocked = 'E';
			break;
		case 3: //N
			type = type.blockDirection(0);	//block S
			blocked = 'S';
			break;
		default: //this shouldn't ever happen...
			System.out.println("Intersection::blockOppositeDirection: something has gone horribly wrong");
		}
	}
	public char getBlocked() { return blocked; }
	public boolean isBlocked() { if (blocked == 0) return false; else return true; }
	
	public TrafficSign getSign() { return sign; }
	
	public StopLight getLight() { return light; }
	
	public RoundaboutSegment getRoundabout() { return roundabout; }
	
	public Vehicle getInIntersection() { return inIntersection; }
	public void setInIntersection(Vehicle v)  { inIntersection = v; }
	
	public IntersectionType getType() { return type; }
	public void setType(IntersectionType t) { type = t; }
	
	public void addRoundaboutObserver(Object o) {
		roundabout.addObserver((Observer) o);
	}
	
	public void addToVehicleQueue(Vehicle v) {
		vehicleQueue.add(v);
	}
	
	public Point[] getIntPoints() { return location; }
}
