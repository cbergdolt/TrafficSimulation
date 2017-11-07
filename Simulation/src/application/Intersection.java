package application;

import java.awt.Point;
import java.util.Queue;

public class Intersection {
	Point location;
	StopLight light;
	char type;
	Queue<Vehicle> vehicleQueue;
	
	Intersection(Point loc, StopLight sl) {
		location = loc; 
		//type = t;
		light = sl;
	}
	
	public void updateIntersection() {
		// TODO Auto-generated method stub
		if (light != null) light.update();
		System.out.println("updated intersection");
	}
}
