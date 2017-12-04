package application.route;

import java.awt.Point;
import java.util.LinkedList;

import application.intersection.Intersection;
import javafx.util.Pair;

/***
 * Route generator to form the route for the specified vehicle
 * 
 *
 */

public class RouteGenerator{
	//Route route;
	
	public Route generateRoute(int numStops, Point start, Landmark[] stops, int[][] grid, Intersection[] intersections, char dir) {
		/**
		 * Generates the route for the vehicle
		 */
		
		Route route = new Route();
		
		//don't bother with a route, can go straight through
		if (start.equals(new Point(4,0)) || start.equals(new Point(42,0)) || start.equals(new Point(5, 49)) || start.equals(new Point(43, 49)))
			return null;
		else if (start.equals(new Point(22,0))) {
			route.path.add(new RoutePair(new Point(22, 10), 'S'));
			route.path.add(new RoutePair(new Point(22, 20), 'R'));
			route.path.add(new RoutePair(new Point(22, 25), 'S'));	//roundabout exit! (point is different)
			route.path.add(new RoutePair(new Point(22, 38), 'S'));
			return route;
		}
		else if (start.equals(new Point(49, 22))) {
			route.path.add(new RoutePair(new Point(43, 22), 'W'));
			route.path.add(new RoutePair(new Point(33, 22), 'W'));
			route.path.add(new RoutePair(new Point(25, 22), 'R'));
			route.path.add(new RoutePair(new Point(20, 22), 'W'));	//roundabout exit! (point is different)
			route.path.add(new RoutePair(new Point(13, 22), 'W'));
			route.path.add(new RoutePair(new Point(5, 22), 'W'));
			return route;
		}
		else if (start.equals(new Point(0, 23))) {
			route.path.add(new RoutePair(new Point(4, 23), 'E'));
			route.path.add(new RoutePair(new Point(10, 23), 'E'));
			route.path.add(new RoutePair(new Point(20, 23), 'R'));
			route.path.add(new RoutePair(new Point(25, 23), 'E'));	//roundabout exit! (point is different)
			route.path.add(new RoutePair(new Point(32, 23), 'E'));
			route.path.add(new RoutePair(new Point(42, 23), 'E'));
			return route;
		}
		else if (start.equals(new Point(23, 49))) {
			route.path.add(new RoutePair(new Point(23, 39), 'N'));
			route.path.add(new RoutePair(new Point(23, 25), 'R'));
			route.path.add(new RoutePair(new Point(23, 20), 'N'));	//roundabout exit! (point is different)
			route.path.add(new RoutePair(new Point(23, 11), 'N'));
			return route;
		}
		else return null;
		
		/*System.out.println("start: " + start.x + " " + start.y);
		
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
		*/
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
		/*if (curr.x < stops[id].location.x) {
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
		
		return null;*/
	}
}