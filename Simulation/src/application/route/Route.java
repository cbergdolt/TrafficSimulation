package application.route;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/***
 * Class to hold the route the vehicle will take 
 * to reach its destinations
 *
 */

public class Route {
	Queue<Queue<RoutePair> > path;
	Vector<Landmark> landmarks = new Vector<Landmark>();
	int landmarkCounter = 0; //keeps track of which landmark in the vector the vehicle is currently heading towards
	// variable for top of the queue point
	
	public Route() {
		path = new LinkedList<>();
	}
	
	public Queue<Queue<RoutePair> > getPath() { return path; }
	
	public Queue<RoutePair> getNextPath() {
		landmarkCounter += 1;
		return path.poll(); 
	}
	
	public void remove() { path.remove(); }
	
	public void addLandmark(Landmark i) {
		landmarks.add(i);
	}
	
	public void setLandmarks(Vector<Landmark> lands) {
		landmarks = lands;
	}
	
	public int getLandmarkCounter() { return landmarkCounter; }
	public Landmark getLandmark(int i) { return landmarks.get(i); }
	
}
