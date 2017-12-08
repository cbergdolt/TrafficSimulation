package application.route;

import java.awt.Point;

/***
 * Landmark class to hold the main destination stops
 * for the vehicles
 * 
 * @author Femgineers
 *
 */

public class Landmark {
	int id;
	Point[] location; 
	LandmarkType type;
	
	public Landmark(int num, Point[] loc, LandmarkType t){
		id = num;
		location = loc;
		type = t;
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
	
	public LandmarkType getType() { return type; }
	
}
