package application.route;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/***
 * Class to hold the route the vehicle will take 
 * to reach its destinations
 *
 */

public class Route {
	//Queue<Point> path;
	Queue<RoutePair> path;
	Landmark[] landmarks;
	// variable for top of the queue point
	
	public Route() {
		path = new LinkedList<RoutePair>();
		
	}
	
	public Queue<RoutePair> getPath() { return path; }
	
	public void remove() { path.remove(); }
}
