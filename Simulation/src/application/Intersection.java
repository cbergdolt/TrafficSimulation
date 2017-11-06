package application;

import java.awt.Point;
import java.util.Queue;

public class Intersection {
	Point location;
	StopLight light;
	TrafficSign sign;
	Queue<Vehicle> vehicleQueue;
	
	Intersection(Point loc, StopLight l, TrafficSign s) {
		location = loc;
		light = l;
		sign = s;
	}
	
	public void updateIntersection() {
		// TODO Auto-generated method stub
		if (light != null) light.update();
		System.out.println("updated intersection");
	}
}
