package application;

import java.awt.Point;
import java.util.Observable;

/***
 * Special kind of Intersection that doesn't really behave as an intersection at all
 * It really has no relation to Intersection aside from the way its location is read from the map grid
 * 
 * The RoundaboutSegment contains the positions that the vehicle will need to traverse within the intersection (position)
 * 		and the point on the map where the roundabout "intersection" was identified (intersection)
 * 	The fields segmentID, next and prev maintain the order and flow of the vehicles in the roundabout
 * 	vehicleLocation is used to tell whether the segment is empty or how close it is to being empty
 * 
 * @author Carolyn
 *
 */

public class RoundaboutSegment extends Observable{

	Point[] position = new Point[4];	//position1 (entrance), position2, position3, position4 when driving on roundabout
		//goes in a roughly quarter circle motion/pattern
	int segmentID;	// 1-4 based on the order the segments are encountered when being read in from map grid
		// | |1| |
		// |2| |3|
		// | |4| |
	RoundaboutSegment next;	//connect this segment to the next segment (1->2, 2->4, 4->3, 3->1)
	RoundaboutSegment prev;	//connect this segment to the previous segment (1->3, 3->4, 4->2, 2->1)
	int vehicleLocation;	//which point in position the car in the segment is at; -1 if segment is empty
		//this will need to change if we are allowing more than one car in the segment at a time
	//private int[] rotation = new int[4];	//the degrees of rotation being approximated by the four points in position
		//may not be used, I'm not sure yet. This seems to be the best way to determine image rotation values (though are we rotating the image at all?)
	
	RoundaboutSegment(Point[] pos, int id) {
		position = pos;
		segmentID = id;
	}
	
	public void update() {
		notifyObservers();
	}
}
