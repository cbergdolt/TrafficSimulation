package application.route;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import application.intersection.Intersection;

/***
 * Route generator to form the route for the specified vehicle
 * 
 *
 */

public class RouteGenerator{
	//Route route;
	
	public Route generateRoute(int numStops, Point start, Landmark[] stops, int[][] grid, Intersection[] intersections, char dir, Object[] vertices, int[][] adjList) {
		/**
		 * Generates the route for the vehicle
		 */
		
		System.out.println("HEREEEE");
		
		Vector<Point> routeStops = new Vector<Point>();
		Vector<Integer> ids = new Vector<Integer>();
		Random rand = new Random();
		int r;
		while(routeStops.size() < numStops) {
				System.out.println(routeStops.size());
				r = rand.nextInt(36);
				while (ids.contains(r)) {
					r = rand.nextInt(36);
				}
				System.out.println(vertices[r].getClass().getSimpleName());
				if (vertices[r] instanceof Landmark) {
					routeStops.add(((Landmark)vertices[r]).location);
					System.out.println("stop:" + ((Landmark)vertices[r]).location);
				}
				ids.add(r);
		}
		
		System.out.println("done");
		
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
		
		
		
		
		
		return null;
		
		// for alg: if curr is landmark, add 'L' as direction
		

		/*
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


	}
	
	
	public char getDirection(Point old, Point curr) {
		// if point is one of 4 roundabout points, direction should be r until it exits roundabout
		// if both points roundabout, return R
		if (isRoundabout(old) && isRoundabout(curr)) {
			return 'R';
		} else if (old.x == curr.x) {
			if (old.y < curr.y) {
				return 'S';
			} else if (old.y > curr.y) {
				return 'N';
			}
		} else if (old.y == curr.y) {
			if (old.x < curr.x) {
				return 'E';
			} else if (old.x > curr.x) {
				return 'W';
			}
		} else {
			System.out.println("Unable to determine direction");
			return ' ';
		}
		return ' ';
	}
	
	public Boolean isRoundabout(Point i) {
		if (i.equals(new Point (22,20)) || i.equals(new Point(20, 22)) || i.equals(new Point(22, 24)) || i.equals(new Point(24, 22))) {
			return true;
		} else {
			return false;
		}
	}
	
	public Point realPointForPath(Intersection[] intersections, Point xing, char dir, char oldDir) {
		// determine which point to send back if dir = R
		if (dir == 'R') {
			for (Intersection i: intersections) {
				if (i.getIntPoints()[0].equals(xing)) {
					switch(oldDir) {
					case 'N':
						return i.getIntPoints()[3];
					case 'S':
						return i.getIntPoints()[0];
					case 'E':
						return i.getIntPoints()[1];
					case 'W':
						return i.getIntPoints()[2];
					default: 
						System.out.println("Point not found");
					}
				}
			}
		} else {
			for (Intersection i: intersections) {
				if (i.getIntPoints()[0].equals(xing)) {
					switch(dir) {
					case 'N':
						return i.getIntPoints()[3];
					case 'S':
						return i.getIntPoints()[0];
					case 'E':
						return i.getIntPoints()[1];
					case 'W':
						return i.getIntPoints()[2];
					default: 
						System.out.println("Point not found");
					}
				}
			}
		}
		
		return null;
	}
}