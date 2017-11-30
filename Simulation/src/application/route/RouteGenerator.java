package application.route;

import java.awt.Point;
import java.util.LinkedList;

import application.intersection.Intersection;

/***
 * Route generator to form the route for the specified vehicle
 * 
 *
 */

public class RouteGenerator{
	Route route;
	
	public Point[] generateRoute(int numStops, Point start, Landmark[] stops, int[][] grid, Intersection[] intersections) {
		/**
		 * Generates the route for the vehicle
		 */
		System.out.println("start: " + start.x + " " + start.y);
		
		// Set Disappear point for landmarks
		int i;
		for (i = 0; i < stops.length; i++) {
			int x = stops[i].location.x;
			int y = stops[i].location.y;
			if (grid[x-1][y] != 1) { // check if road is E
				stops[i].setDisappear(x-1, y);
			}
			else if (grid[x+1][y] != 1) { // check if road is W
				stops[i].setDisappear(x+1, y);
			}
			else if (grid[x][y-1] != 1) { // check if road is N
				stops[i].setDisappear(x, y-1);
			}
			else if (grid[x][y+1] != 1) { // check if road is N
				stops[i].setDisappear(x, y+1);
			}
		}

		// randomly chose x = numStops landmark ids
		// will create route with one landmark stop between start and end
		int id = 3;
		int[] nums = {1, 3, 5};
		Point curr = start;
		// start with start and first random id and generate route
		// while current point is not end point 
//		System.out.println(id);
		for (Intersection l: intersections) {
			System.out.println(l.getIntPoints()[0].x + " " + l.getIntPoints()[0].y);
		}
		for (int ii: nums) {
			System.out.println("LANDMARK TO GET TO: " + stops[ii].location.x + " " + stops[ii].location.y);
			// for every path to generate, reinstate the intersections
			int sectX, sectY;
			for (i = 0; i < intersections.length; i++) {
				sectX = intersections[i].getIntPoints()[0].x;
				sectY = intersections[i].getIntPoints()[0].y;
//				System.out.println("int: " + sectX + " " + sectY);
				if (curr.x <= sectX && curr.y <= sectY && sectX <= stops[ii].location.x && sectY <= stops[ii].location.y) {
					System.out.println("intersection: "+sectX + " " + sectY);
					curr = new Point(sectX, sectY);
				}
				else if (curr.x > sectX && curr.y > sectY && sectX > stops[id].location.x && sectY > stops[id].location.y) {
					System.out.println("intersection 2: "+sectX + " " + sectY);
					curr = new Point(sectX, sectY);
				}
//				System.out.println("point: " + curr.x + " " + curr.y);
				
			}
			curr = new Point(stops[ii].location.x, stops[ii].location.y);
			System.out.println("point now: " + curr.x + " " + curr.y);
		}
		//System.out.println("LANDMARK AT: " + stops[id].location.x + " " + stops[id].location.y);
		// for every path to generate, reinstate the intersections
		/*int sectX, sectY;
		for (i = 0; i < intersections.length; i++) {
			sectX = intersections[i].getIntPoints()[1].x;
			sectY = intersections[i].getIntPoints()[1].y;
//			System.out.println("int: " + sectX + " " + sectY);
			if (curr.x <= sectX && curr.y <= sectY && sectX <= stops[id].location.x && sectY <= stops[id].location.y) {
				System.out.println("intersection: "+sectX + " " + sectY);
				curr = new Point(sectX, sectY);
			}
			else if (curr.x > sectX && curr.y > sectY && sectX > stops[id].location.x && sectY > stops[id].location.y) {
				System.out.println("intersection 2: "+sectX + " " + sectY);
				curr = new Point(sectX, sectY);
			}
//			System.out.println("point: " + curr.x + " " + curr.y);
			
		}*/
		if (curr.x < stops[id].location.x) {
			System.out.println("first");
			// landmark/stop is to the right of the start so Direction = E
			// iterate through intersections to the left of the start 
			// while stop.x < x of intersections.locations[0] 
			// add Direction W and intersections.locations[0] to queue
			// iterate to next locations 
		}
		else if (curr.x > stops[id].location.x) {
			// landmark is left of start 
			System.out.println("second");
		}
		else {
			// landmark is above/below start
			System.out.println("last");
		}
		
		return null;
	}
}