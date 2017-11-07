package application;

import java.awt.Point;
import java.util.Queue;

public class Intersection {
	Point location;
	StopLight light;
	TrafficSign sign;
	char type;
	Object object;
	Queue<Vehicle> vehicleQueue;
	
	Intersection(Point loc, StopLight sl, TrafficSign t) {
		location = loc;
		//object = obj;
		//type = t;
		light = sl;
		sign = t;
	}
	
	public void updateIntersection() {
		// TODO Auto-generated method stub
		if (light != null) light.update();
		System.out.println("updated intersection");
	}
}
