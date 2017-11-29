package application.route;

import java.awt.Point;
import java.util.LinkedList;

/***
 * Route generator to form the route for the specified vehicle
 * 
 *
 */

public class RouteGenerator{
	Route route;
	
	public Point[] generateRoute(int numStops, Point start, Landmark[] stops, int[][] grid) {
		/**
		 * Generates the route for the vehicle
		 */
//		route.path = new LinkedList<Point>();
		System.out.println(start.x + " " + start.y);
//		int i;
//		for (i = 0; i < stops.length; i++ ) {
//				System.out.println("Landmark id: " + stops[i].id);		
//		}

		// randomly chose x = numStops landmark ids
		
		// start with start and first random id and generate route
		
		return null;
	}
}