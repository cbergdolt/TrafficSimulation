package application.route;

import java.awt.Point;

/***
 * Landmark class to hold the main destination stops
 * for the vehicles
 *
 */

public class Landmark {
	//String name;
	int id;
	Point[] location; 
	//Point disappearA; // point on road to determine when to disappear 
	//Point disappearB;
	LandmarkType type;
	// will always only be one spot N or S or E or W
	// add new direction L to not render / increment counter in vehicle
	// create function to 
	// multiples of 7 reserved for roundabout in grid
	// in intersection 
	// entry and exit in the intersection
	
	
	public Landmark(int num, Point[] loc, LandmarkType t){
		//name = tag;
		id = num;
		location = loc;
		type = t;
		//disappearA = a;
		//disappearB = b;
	}
	
	public void setDisappearA(int x, int y) {
		location[1] = new Point(x, y);
	}
	
	public void setDisappearB(int x, int y) {
		location[2] = new Point(x, y);
	}
	
	public Point getLocation() {
		return location[0];
	}
	public Point[] getLocationArray() { return location; }
	
}
