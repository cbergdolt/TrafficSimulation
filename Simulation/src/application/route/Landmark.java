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
	Point disappear; // point on road to determine when to disappear 
	// will always only be one spot N or S or E or W
	// add new direction L to not render / increment counter in vehicle
	// create function to 
	// multiples of 7 reserved for roundabout in grid
	// in intersection 
	// entry and exit in the intersection
	
	
	public Landmark(String tag, int num, Point loc){
		name = tag;
		id = num;
		location = loc;
	}
	
	public void setDisappear(int x, int y) {
		disappear = new Point(x, y);
	}
}
