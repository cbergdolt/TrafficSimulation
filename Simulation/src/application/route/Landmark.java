package application.route;

import java.awt.Point;

/***
 * Landmark class to hold the main destination stops
 * for the vehicles
 *
 */

public class Landmark {
	String name;
	int id;
	Point location; 
	Point disappearA; // point on road to determine when to disappear 
	Point disappearB;
	// will always only be one spot N or S or E or W
	// add new direction L to not render / increment counter in vehicle
	// create function to 
	// multiples of 7 reserved for roundabout in grid
	// in intersection 
	// entry and exit in the intersection
	
	
	public Landmark(String tag, int num, Point loc, Point a, Point b){
		name = tag;
		id = num;
		location = loc;
		disappearA = a;
		disappearB = b;
	}
	
	public void setDisappearA(int x, int y) {
		disappearA = new Point(x, y);
	}
	
	public void setDisappearB(int x, int y) {
		disappearB = new Point(x, y);
	}
	
	public Point getLocation() {
		return location;
	}
	
}
