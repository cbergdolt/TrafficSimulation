package application;

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
	
	Landmark(String tag, int num, Point loc){
		name = tag;
		id = num;
		location = loc;
	}
}
