package application;

import java.awt.Point;
import java.util.Observable;
import java.util.Queue;

public class Intersection extends Observable{
	Point[] location = new Point [4]; //South, East, West, North (see Diagram below)
	// |S|W|
	// |E|N|
	StopLight light;
	int id;
	char dir;
	char type;
	Queue<Vehicle> vehicleQueue;
	
	Intersection(Point[] loc, StopLight sl) {
		location = loc; 
		light = sl;
	}
	
	public void updateIntersection() {
		// TODO Auto-generated method stub
		if (light != null) light.update();
		setChanged();
		notifyObservers();
		//System.out.println("updated intersection");
	}
	
	public LightState getState() {
		return light.getState();
	}
}
