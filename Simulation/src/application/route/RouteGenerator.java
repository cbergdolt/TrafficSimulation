package application.route;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
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
		//routeStops.add(start);
		Random rand = new Random();
		int r;
		Vector<Landmark> lands = new Vector<Landmark>();
		while(routeStops.size() < numStops+1) {
//				System.out.println(routeStops.size());
				r = rand.nextInt(36);
				while (ids.contains(r)) {
					r = rand.nextInt(36);
				}
//				System.out.println(vertices[r].getClass().getSimpleName());
				if (vertices[r] instanceof Landmark) {
					Landmark l = (Landmark) vertices[r];
					routeStops.add(l.getLocation());
					lands.add(l);
//					System.out.println("stop:" + ((Landmark)vertices[r]).location);
				}
				ids.add(r);
		}
		routeStops.add(start);
		
//		System.out.println("done");
		
		Route route = new Route();
		route.setLandmarks(lands);
		
		Stack<Pair<Integer, Character>> routeStack;
		Queue<RoutePair> routeSegment;
		// all intersections with exception of the first and last pairs
		
		for (int i = 1; i< routeStops.size(); i++) {
			routeStack = dijkstra(route, vertices, adjList, routeStops.get(i-1), routeStops.get(i));
			routeSegment = makeSegment(routeStack, vertices);
			route.addSegment(routeSegment);
			//convert stack to useful path/route points
		}
		
		return route; //Queue<Queue<RoutePair>>
		
	}
	
	private Queue<RoutePair> makeSegment(Stack<Pair<Integer, Character>> initStack, Object[] vertices) {
		Queue<RoutePair> myQueue = new LinkedList<RoutePair>();
		char prevDir = 'L';
		while (!initStack.empty()) {
			Pair<Integer, Character> stackPair = initStack.pop();
			int index = stackPair.getKey();
			Point p = getPointfromIndex(vertices[index], stackPair.getValue(), prevDir);
		}
		
		return myQueue;
	}
	
	private Point getPointfromIndex(Object vertex, char dir, char prevDir) {
		switch(prevDir) {
		case 'N':
			if (vertex instanceof Intersection) {
				Intersection inter = (Intersection) vertex;
				switch(dir) {
				case 'N':
					return inter.getLocation()[3];
				case 'S':
					System.out.println("No U-Turns Allowed!");
					return null;
				case 'E':
					return inter.getLocation()[3];
				case 'W':
					return inter.getLocation()[2];
				case 'R':
					return inter.getRoundabout().getPosition()[0];
				case 'L':
					System.out.println("I don't even know what you did wrong");
					return null;
				default:
					return null;
				} 
			}else if (vertex instanceof Landmark) {
				Landmark land = (Landmark) vertex;
				if (land.getType() == LandmarkType.NORTH) {
					return land.getLocationArray()[1];
				} else {
					return land.getLocationArray()[2];
				}
			} else {
				return (Point) vertex;
			}
		case 'S':
			if (vertex instanceof Intersection) {
				Intersection inter = (Intersection) vertex;
				switch(dir) {
				case 'S':
					return inter.getLocation()[0];
				case 'N':
					System.out.println("No U-Turns Allowed!");
					return null;
				case 'W':
					return inter.getLocation()[0];
				case 'E':
					return inter.getLocation()[1];
				case 'R':
					return inter.getRoundabout().getPosition()[0];
				case 'L':
					System.out.println("I don't even know what you did wrong");
					return null;
				default:
					return null;
				} 
			}else if (vertex instanceof Landmark) {
				Landmark land = (Landmark) vertex;
				if (land.getType() == LandmarkType.SOUTH) {
					return land.getLocationArray()[1];
				} else {
					return land.getLocationArray()[2];
				}
			} else {
				return (Point) vertex;
			}
			case 'E':
				if (vertex instanceof Intersection) {
					Intersection inter = (Intersection) vertex;
					switch(dir) {
					case 'E':
						return inter.getLocation()[1];
					case 'W':
						System.out.println("No U-Turns Allowed!");
						return null;
					case 'S':
						return inter.getLocation()[1];
					case 'N':
						return inter.getLocation()[3];
					case 'R':
						return inter.getRoundabout().getPosition()[0];
					case 'L':
						System.out.println("I don't even know what you did wrong");
						return null;
					default:
						return null;
					} 
				}else if (vertex instanceof Landmark) {
					Landmark land = (Landmark) vertex;
					if (land.getType() == LandmarkType.EAST) {
						return land.getLocationArray()[1];
					} else {
						return land.getLocationArray()[2];
					}
				} else {
					return (Point) vertex;
				}
			case 'W':
				if (vertex instanceof Intersection) {
					Intersection inter = (Intersection) vertex;
					switch(dir) {
					case 'W':
						return inter.getLocation()[2];
					case 'E':
						System.out.println("No U-Turns Allowed!");
						return null;
					case 'N':
						return inter.getLocation()[2];
					case 'S':
						return inter.getLocation()[0];
					case 'R':
						return inter.getRoundabout().getPosition()[0];
					case 'L':
						System.out.println("I don't even know what you did wrong");
						return null;
					default:
						return null;
					} 
				} else if (vertex instanceof Landmark) {
					Landmark land = (Landmark) vertex;
					if (land.getType() == LandmarkType.WEST) {
						return land.getLocationArray()[1];
					} else {
						return land.getLocationArray()[2];
					}
				} else {
					return (Point) vertex;
				}
			case 'R':
				if (vertex instanceof Intersection) {
					Intersection inter = (Intersection) vertex;
					if (dir == 'N'||dir == 'S'||dir == 'E'||dir == 'W') {
						return inter.getRoundabout().getPrev().getPosition()[3];
					} else if (dir == 'R') {
						return inter.getRoundabout().getPosition()[0];
					} else {
						System.out.println("Roundabout Shennanigans");
						return null;
					}
				}
			case 'L': //	FIRST THING OFF OF THE """"""STACK"""""""
				if (vertex instanceof Landmark) {
					Landmark land = (Landmark) vertex;
					switch(land.getType()) {
					case NORTH:
						if (dir == 'N') {
							return land.getLocationArray()[1];
						} else {
							return land.getLocationArray()[2];
						}
					case SOUTH:
						if (dir == 'S') {
							return land.getLocationArray()[1];
						} else {
							return land.getLocationArray()[2];
						}
					case EAST:
						if (dir == 'E') {
							return land.getLocationArray()[1];
						} else {
							return land.getLocationArray()[2];
						}
					case WEST:
						if (dir == 'W') {
							return land.getLocationArray()[1];
						} else {
							return land.getLocationArray()[2];
						}
					}
					
				} else if (vertex instanceof Point) {
					//Point p;
					switch(dir) {
					case 'N':
						return new Point(((Point) vertex).x, ((Point) vertex).y-1);
					case 'S':
						return new Point(((Point) vertex).x, ((Point) vertex).y+1);
					case 'E':
						return new Point(((Point) vertex).x+1, ((Point) vertex).y);
					case 'W':
						return new Point(((Point) vertex).x-1, ((Point) vertex).y);
					}
				}
				
			}
		}
		
	
	private Stack<Pair<Integer, Character>> dijkstra(Route route, Object[] vertices, int[][] adjList, Point start, Point end) {
		// TODO Auto-generated method stub
		int V = vertices.length;
		//instantiated the data structures necessary to run dijkstra's 
		Map<Integer, Integer> marked = new HashMap<Integer, Integer>(); // visited nodes the key = current node; value = previous node
		ArrayList<Pair<Integer, Integer>> frontier = new ArrayList<Pair<Integer, Integer>>(); // the next nodes to be visited
																							//Pair <next node, prev node>
		// find start node
		int src = 0;
		int fin = 0;
		
		System.out.println("In Dijkstra:");
		//System.out.println("starting: " + start.x + " " + start.y);
		//System.out.println("ending: " + end.x + " " + end.y);
		
		//determine the location in adj matrix of the start and destination objects
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
		
		Pair <Integer, Integer> v = new Pair <Integer, Integer> (src, src); //edge
		frontier.add(v);
		
		while(!frontier.isEmpty()) {
			v = frontier.remove(0);
			
			if (marked.containsValue(v.getKey())) continue;
			
			if (v.getKey() == fin) {
				marked.put(v.getKey(), v.getValue());
				break;
			}
			
			marked.put(v.getKey(), v.getValue()); 
			for (int u = 0; u < V; u++) { //traverses adj matrix and determines which nodes things are adj to
				if (adjList[v.getKey()][u] == 1 || (u == fin && adjList[v.getKey()][u] == 2) || (u == src && adjList[v.getKey()][u] == 2)) {
					//System.out.println(u);
					frontier.add(new Pair <Integer, Integer> (u, v.getKey()));
				}
			}
		}
		//this is a test print statement; it prints out everything in marked 
		for (Map.Entry entry : marked.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		System.out.println("Done");
		//constructs the route based off of dijkstra's
		Stack<Pair<Integer, Character>> r = new Stack<Pair<Integer, Character>>();
		r = constructRoute(vertices, marked, src, fin);
		return r;
		/*
		//prints out the route, erase this when done
		Pair<Integer, Character> p;
		p = r.pop();
		char oldDir = p.getValue();
		Point at = new Point(0,0);
		while(!r.isEmpty()) {
			p = r.pop();
			//System.out.println(p.getKey());
			at = getPoint(p.getKey(), vertices);
			//System.out.println(at.x+", "+at.y);
			at = getNewPoint(p.getKey(), vertices, p.getValue(), oldDir);
			System.out.println(at.x +" , " + at.y + " +++++ "+p.getValue());
			//System.out.println(p.getValue());
			route.path.add(new RoutePair(at, p.getValue()));
			oldDir = p.getValue();
		}
		if (vertices[fin] instanceof Landmark) {
			route.addLandmark((Landmark)vertices[fin]);
		}
		//return route;
		*/
	}
	
	public Stack<Pair<Integer, Character>> constructRoute(Object[] vertices, Map<Integer, Integer> marked, int src, int fin) {
		//Route r = new Route();
		Stack<Pair<Integer, Character>> s = new Stack<Pair<Integer, Character>>();
		char direction = 'o';
		int val = fin;
		Point p1 = new Point();
		Point p2 = new Point();
		
		Pair<Integer, Character> p = new Pair<Integer, Character>(fin, 'L');
//		System.out.println("Fin "+ p.toString());
		s.push(p);
		val = marked.get(val);
		while (val != src) {
			p1 = getPoint(val, vertices);
			direction = getDirection(p1, getPoint(s.peek().getKey(), vertices));
			
			Pair<Integer, Character> pair = new Pair<Integer, Character>(val, direction);
			s.push(pair);
//			System.out.println(pair.toString());
			val = marked.get(val);
			if (val == src) { //allows the starting location to be pushed down on the stack b/c while condition will terminate before it otherwise
				p2 = getPoint(s.peek().getKey(), vertices);
				if (vertices[val] instanceof Landmark) {
					p1 = ((Landmark) vertices[val]).getLocation();
				} else {
					p1 = (Point) vertices[val];
				}
				direction = getDirection(p1, p2);
				Pair<Integer, Character> finalPair = new Pair<Integer, Character>(val, direction);
				s.push(finalPair);
//				System.out.println("SRC "+ finalPair.toString());
			}
			
		}
		
		return s;
	}

	public Point getPoint(int index, Object[] vertices) {	
		if (vertices[index] instanceof Landmark) {
			return ((Landmark) vertices[index]).location;
		} else if (vertices[index] instanceof Point) {
			return (Point) vertices[index];
		} else {
			return ((Intersection) vertices[index]).getIntPoints()[0];	
		} 
		
	}

	public Point getNewPoint(int index, Object[] vertices, char dir, char oldDir) {	
		if (vertices[index] instanceof Landmark) {
			return ((Landmark) vertices[index]).location;
		} else if (vertices[index] instanceof Point) {
			return (Point) vertices[index];
		} else {
			if (oldDir == 'N') {
				switch(dir) {
					case 'N':
						return ((Intersection) vertices[index]).getIntPoints()[3];
					case 'E':
						return ((Intersection) vertices[index]).getIntPoints()[3];
						
					case 'W':
						return ((Intersection) vertices[index]).getIntPoints()[2];
						
					case 'R':
						return ((Intersection) vertices[index]).getRoundabout().getPrev().getPosition()[3];
					case 'L':
						System.out.println("L");
				}	
			} else if (oldDir =='S') {
				switch(dir) {
				case 'S':
					return ((Intersection) vertices[index]).getIntPoints()[0];
					
				case 'E':
					return ((Intersection) vertices[index]).getIntPoints()[1];
					
				case 'W':
					return ((Intersection) vertices[index]).getIntPoints()[0];
				case 'R':
					return ((Intersection) vertices[index]).getRoundabout().getPrev().getPosition()[3];
				case 'L':
					System.out.println("L");
				}
			} else if (oldDir == 'E') {
				switch(dir) {
				case 'N':
					return ((Intersection) vertices[index]).getIntPoints()[3];
				case 'S':
					return ((Intersection) vertices[index]).getIntPoints()[1];
					
				case 'E':
					return ((Intersection) vertices[index]).getIntPoints()[1];
				case 'R':
					return ((Intersection) vertices[index]).getRoundabout().getPrev().getPosition()[3];
				case 'L':
					System.out.println("L");
				}
			} else if (oldDir == 'W') {
				switch(dir) {
				case 'N':
					return ((Intersection) vertices[index]).getIntPoints()[2];
				case 'S':
					return ((Intersection) vertices[index]).getIntPoints()[0];					
				case 'W':
					return ((Intersection) vertices[index]).getIntPoints()[2];
				case 'R':
					return ((Intersection) vertices[index]).getRoundabout().getPrev().getPosition()[3];
				case 'L':
					System.out.println("L");
				}
			} else if (oldDir == 'L'){
				System.out.println('L');
			}else {
					return ((Intersection) vertices[index]).getRoundabout().getPosition()[0];
			}
		}
		return null; 
		
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
		} else if (old.y != curr.y && old.x != old.y) {
			if (Math.abs(curr.x-old.x) > Math.abs(curr.y-old.y)) {
				if (old.x < curr.x) {
					return 'E';
				} else if (old.x > curr.x) {
					return 'W';
				}
			} else {
				if (old.y < curr.y) {
					return 'S';
				} else if (old.y > curr.y) {
					return 'N';
				}
			}
			
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
	
}