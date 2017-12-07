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
	Queue<Queue<RoutePair> > path;
	Vector<Landmark> landmarks = new Vector<Landmark>();
	// variable for top of the queue point
	
	public Route() {
		path = new LinkedList<>();//<LinkedList<RoutePair>>();
		
	}
	
	public Queue<Queue<RoutePair> > getPath() { return path; }
	
	public Queue<RoutePair> getNextPath() { return path.remove(); }
	
	public void remove() { path.remove(); }
	
	public void addSegment(Queue<RoutePair> seg) {
		path.add(seg);
	}
	
	public void addLandmark(Landmark i) {
		landmarks.add(i);
	}
	
	public void setLandmarks(Vector<Landmark> lands) {
		landmarks = lands;
	}
	
}
