package application.route;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import application.intersection.Intersection;
import javafx.util.Pair;

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
		
//		System.out.println("HEREEEE");
		
		Vector<Point> routeStops = new Vector<Point>();
		Vector<Integer> ids = new Vector<Integer>();
		Random rand = new Random();
		int r;
		while(routeStops.size() < numStops) {
//				System.out.println(routeStops.size());
				r = rand.nextInt(36);
				while (ids.contains(r)) {
					r = rand.nextInt(36);
				}
//				System.out.println(vertices[r].getClass().getSimpleName());
				if (vertices[r] instanceof Landmark) {
					routeStops.add(((Landmark)vertices[r]).location);
//					System.out.println("stop:" + ((Landmark)vertices[r]).location);
				}
				ids.add(r);
		}
		
//		System.out.println("done");
		
		
		Route route = new Route();
		
		dijkstra(route, vertices, adjList, start, routeStops.get(0));
		
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


	}
	
	
	private Map<Integer, Integer> dijkstra(Route route, Object[] vertices, int[][] adjList, Point start, Point end) {
		// TODO Auto-generated method stub
		int V = vertices.length;
		
		Map<Integer, Integer> marked = new HashMap<Integer, Integer>();
		ArrayList<Pair<Integer, Integer>> frontier = new ArrayList<Pair<Integer, Integer>>();
		
		
		// find start node
		int src = 0;
		int fin = 0;
		
		System.out.println("In Dijkstra:");
		System.out.println("starting: " + start.x + " " + start.y);
		System.out.println("ending: " + end.x + " " + end.y);
		
		for (int j = 0; j < V; j++) {			
			if (vertices[j] instanceof Landmark) {
				if (((Landmark) vertices[j]).location.equals(start)) {
					src = j;
				}
				if (((Landmark) vertices[j]).location.equals(end)) {
					fin = j;
				} 
			} else if (vertices[j] instanceof Point) {
				if (vertices[j].equals(start)) {
					src = j;
				}
				if (vertices[j].equals(end)) {
					fin = j;
				}
			} else if (vertices[j] instanceof Intersection) {
				if (((Intersection) vertices[j]).getIntPoints()[0].equals(start)) {
					src = j;
				}
				if (((Intersection) vertices[j]).getIntPoints()[0].equals(end)) {
					fin = j;
				}
			}
		}	
		
		System.out.println("start id: " + src);
		System.out.println("end id: " + fin);
		
		Pair <Integer, Integer> v = new Pair <Integer, Integer> (src, src);
		frontier.add(v);
		
		while(!frontier.isEmpty()) {
			v = frontier.remove(0);
			
			if (marked.containsValue(v.getKey())) continue;
			
			if (v.getKey() == fin) {
				marked.put(v.getKey(), v.getValue());
				break;
			}
			
			marked.put(v.getKey(), v.getValue()); 
			
			for (int u = 0; u < V; u++) {
				if (adjList[v.getKey()][u] == 1 || (u == fin && adjList[v.getKey()][u] == 2) || (u == src && adjList[v.getKey()][u] == 2)) {
					//System.out.println(u);
					frontier.add(new Pair <Integer, Integer> (u, v.getKey()));
				}
			}
		}

		for (Map.Entry entry : marked.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		System.out.println("Done\n");
		Stack<Integer> r = new Stack<Integer>();
		r = constructRoute(marked, src, fin);
		
		while(!r.isEmpty()) {
			System.out.println(r.pop());
		}
		
		return marked;
		
	}
	
	public Stack<Integer> constructRoute(Map<Integer, Integer> marked, int src, int fin) {
		//Route r = new Route();
		Stack<Integer> s = new Stack<Integer>();
		int val = fin;
		s.push(fin);
		
		while (val != src) {
			val = marked.get(val);
			s.push(val);
		}
		
		return s;
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