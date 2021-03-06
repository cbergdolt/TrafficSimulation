package application.route;

import java.awt.Point;
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
	
	public Route generateRoute(int numStops, Point start, Landmark[] stops, int[][] grid, Intersection[] intersections, char dir, Object[] vertices, int[][] adjList) {
		/**
		 * Generates the route for the vehicle
		 */
		
		Vector<Point> routeStops = new Vector<Point>();
		Vector<Integer> ids = new Vector<Integer>();
		routeStops.add(start);
		Random rand = new Random();
		int r;
		Vector<Landmark> lands = new Vector<Landmark>();
		while(routeStops.size() < numStops+1) {
				r = rand.nextInt(36);
				while (ids.contains(r)) {
					r = rand.nextInt(36);
				}

				if (vertices[r] instanceof Landmark) {
					Landmark l = (Landmark) vertices[r]; 
					routeStops.add(l.getLocation());
					lands.add(l);
				} 
				ids.add(r);
		}
		
		routeStops.add(start);

		Route route = new Route();
		route.setLandmarks(lands);
		
		Stack<Pair<Integer, Character>> routeStack;
		
		// all intersections with exception of the first and last pairs
		for (int i = 1; i< routeStops.size(); i++) {
			routeStack = dijkstra(route, vertices, adjList, routeStops.get(i-1), routeStops.get(i));
			//convert stack to useful path/route points
			Queue<RoutePair> routeSegment = makeSegment(routeStack, vertices);
			if (routeSegment == null) {
				System.out.println("ROUTE SEGMENT IS NULL LINE 68 in Route Generator");
			}
			route.path.add(routeSegment);
		}
		
		return route; //type is Queue<Queue<RoutePair>>
		
	}
	
	private Queue<RoutePair> makeSegment(Stack<Pair<Integer, Character>> initStack, Object[] vertices) {
		Queue<RoutePair> myQueue = new LinkedList<RoutePair>();
		char prevDir = initStack.peek().getValue();// = 'L';
		while (!initStack.empty()) {
			Pair<Integer, Character> stackPair = initStack.pop();
			int index = stackPair.getKey();

			Point p = getPointfromIndex(vertices[index], stackPair.getValue(), prevDir);
			prevDir = stackPair.getValue();
			RoutePair r = new RoutePair(p, prevDir);
			myQueue.add(r);
		}
		return myQueue;
	}
	
	
		
	
	private Stack<Pair<Integer, Character>> dijkstra(Route route, Object[] vertices, int[][] adjList, Point start, Point end) {		
		//instantiated the data structures necessary to run dijkstra's 
		int V = vertices.length;
		Map<Integer, Integer> marked = new HashMap<Integer, Integer>(); // visited nodes the key = current node; value = previous node
		PriorityQueue<Entry> frontier = new PriorityQueue<Entry>(); // the next nodes to be visited
																							//Pair <next node, prev node>
		// find start node
		int src = 0;
		int fin = 0;
		
		src = findIndex(vertices, adjList, start);
		fin = findIndex(vertices, adjList, end);
		
		Entry  v = new Entry(src, src); //edge
		frontier.add(v);
		
		while(!frontier.isEmpty()) {
			v = frontier.poll();
			
			if (marked.containsValue(v.getKey())) continue;
			
			if (v.getKey() == fin) {
				marked.put(new Integer(v.getKey()), new Integer(v.getValue()));
				break;
			}
			
			marked.put(v.getKey(), v.getValue()); 
			for (int u = 0; u < V; u++) { //traverses adj matrix and determines which nodes things are adj to
				if (adjList[v.getKey()][u] == 1 || 
						(u == fin && adjList[v.getKey()][u] == 2) || 
						(u == src && adjList[v.getKey()][u] == 2)) {
					if (uturnTest(v.getKey(), v.getValue(), u, vertices) == true) {
						frontier.add(new Entry(u, v.getKey()));
					}
					
				}
			}
		}

		//constructs the route based off of dijkstra's
		Stack<Pair<Integer, Character>> r = new Stack<Pair<Integer, Character>>();
		if (r != null) {
			r = constructRoute(vertices, marked, src, fin);
		}
		return r;
		}
	
	public boolean uturnTest(int currNode, int currPrev, int nextNode, Object[] vertices) { //return false if path cannot be traveled 
		Point currNodePos = getPoint(currNode, vertices);
		Point currPrevPos = getPoint(currPrev, vertices);
		Point nextNodePos = getPoint(nextNode, vertices);
		
		char currDirection = getDirection(currPrevPos, currNodePos);
		char prevDirection = getDirection(currNodePos, nextNodePos);
		
		if ((currDirection == 'E' && prevDirection =='W') || (currDirection == 'W' && prevDirection =='E')) {
			return false;
		} else if ((currDirection == 'N' && prevDirection =='S') || (currDirection == 'S' && prevDirection =='W')) {
			return false;
		}
		
		return true;
	}
	
	public int findIndex(Object[] vertices, int[][] adjList, Point p) {
		int V = vertices.length;
		int index = -1;
		//determine the location in adj matrix of the start and destination objects
				for (int j = 0; j < V; j++) {			
					if (vertices[j] instanceof Landmark) {
						if (((Landmark) vertices[j]).location[0].equals(p)) {
							index = j;
						}
					} else if (vertices[j] instanceof Point) {
						if (vertices[j].equals(p)) {
							index = j;
						}
					} else if (vertices[j] instanceof Intersection) {
						if (((Intersection) vertices[j]).getIntPoints()[0].equals(p)) {
							index = j;
						}
					} 
				}	
				
		if (index == -1) {
			System.out.println("INDEX NOT FOUND PROPERLY IN ADJ MATRIX");
			System.out.println("Point entered: " + p.x + ", " + p.y);
		}
		
		return index;
	}
	
	public Stack<Pair<Integer, Character>> constructRoute(Object[] vertices, Map<Integer, Integer> marked, int src, int fin) {
		Stack<Pair<Integer, Character>> s = new Stack<Pair<Integer, Character>>();
		char direction = 'o';
		int val = fin;
		Point p1 = new Point();
		Point p2 = new Point();
		
		Pair<Integer, Character> p = new Pair<Integer, Character>(fin, 'L');

		s.push(p);

		val = marked.get(val);
			
		while (val != src) {
			p1 = getPoint(val, vertices);
			direction = getDirection(p1, getPoint(s.peek().getKey(), vertices));
			
			Pair<Integer, Character> pair = new Pair<Integer, Character>(val, direction);
			s.push(pair);

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
			}		
		}		
		return s;
	}

	public Point getPoint(int index, Object[] vertices) {	
		if (vertices[index] instanceof Landmark) {
			return ((Landmark) vertices[index]).getLocation();
		} else if (vertices[index] instanceof Point) {
			return (Point) vertices[index];
		} else {
			return ((Intersection) vertices[index]).getIntPoints()[0];	
		} 
		
	}
	
	public char getDirection(Point old, Point curr) {
		// if point is one of 4 roundabout points, direction should be r until it exits roundabout
		// if both points roundabout, return R
		if (isRoundabout(old) && isRoundabout(curr)) {
			return 'R';
		} 
		if (old.y == curr.y && old.x == curr.x) {
			return 'L';
		} else if (old.y != curr.y && old.x != curr.x) {
			if (Math.abs(curr.x-old.x) > Math.abs(curr.y-old.y)) {
				if (old.x < curr.x) {
					return 'E';
				} else {
					return 'W';
				}
			} else {
				if (old.y < curr.y) {
					return 'S';
				} else {
					return 'N';
				}
			}	
		} else if (old.x == curr.x && old.y != curr.y) {
			if (old.y < curr.y) {
				return 'S';
			} else if (old.y > curr.y) {
				return 'N';
			}
		} else if (old.y == curr.y && old.x != curr.x) {
			if (old.x < curr.x) {
				return 'E';
			} else if (old.x > curr.x) {
				return 'W';
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
	
	private Point getPointfromIndex(Object vertex, char dir, char prevDir) {
		switch(prevDir) {
		case 'N':
			if (vertex instanceof Intersection) {
				Intersection inter = (Intersection) vertex;
				switch(dir) {
				case 'N':
					return new Point(inter.getLocation()[3]);
				case 'S':
					System.out.println("prevDir = N; No U-Turns Allowed!");
					return null;
				case 'E':
					return new Point(inter.getLocation()[3]);
				case 'W':
					return new Point(inter.getLocation()[2]);
				case 'R':
					return new Point(inter.getRoundabout().getPosition()[0]);
				case 'L':
					System.out.println("prevDir = N; intersection direction = L; this should not ever happen");
					return null;
				default:
					System.out.println("prevDir = N; intersection direction was not a valid direction");
					return null;
				} 
			}else if (vertex instanceof Landmark) {
				Landmark land = (Landmark) vertex;
				if (land.getType() == LandmarkType.NORTH) {
					return new Point(land.getLocationArray()[1]);
				} else if (land.getType() == LandmarkType.SOUTH){
					return new Point(land.getLocationArray()[2]);
				} else if (land.getType() == LandmarkType.EAST || land.getType() == LandmarkType.WEST){
					System.out.println("prevDir = N; landmark type was " + land.getType().toString() + ", this cannot happen"); 
					return null;
				} else {
					System.out.println("prevDir = N; landmark type was not recognizable"); 
					return null;
				}
			} else if (vertex instanceof Point){
				Point p = (Point) vertex;
				if (dir == 'L') { //L means it's an exit
					return new Point(p.x+1, p.y);
				} else {	//not L means it's an entrace
					System.out.println("prevDir = N; vertex was a point, and dir != L");
					return new Point(p);
					//return null;
				}
			}
		case 'S':
			if (vertex instanceof Intersection) {
				Intersection inter = (Intersection) vertex;
				switch(dir) {
				case 'S':
					return new Point(inter.getLocation()[0]);
				case 'N':
					System.out.println("prevDir = S; No U-Turns Allowed!");
					return null;
				case 'W':
					return new Point(inter.getLocation()[0]);
				case 'E':
					return new Point(inter.getLocation()[1]);
				case 'R':
					return new Point(inter.getRoundabout().getPosition()[0]);
				case 'L':
					System.out.println("prevDir = S; intersection direction = L; this should not ever happen");
					return null;
				default:
					System.out.println("prevDir = S; intersection direction was not a valid direction");
					return null;
				} 
			}else if (vertex instanceof Landmark) {
				Landmark land = (Landmark) vertex;
				if (land.getType() == LandmarkType.SOUTH) {
					return new Point(land.getLocationArray()[1]);
				} else if (land.getType() == LandmarkType.NORTH){
					return new Point(land.getLocationArray()[2]);
				} else if (land.getType() == LandmarkType.EAST || land.getType() == LandmarkType.WEST){
					System.out.println("prevDir = S; landmark type was " + land.getType().toString() + ", this cannot happen"); 
					return null;
				} else {
					System.out.println("prevDir = S; landmark type was not recognizable"); 
					return null;
				}
			} else {
				Point p = (Point) vertex;
				if (dir == 'L') {
					return new Point(p.x-1, p.y);
				} else {
					System.out.println("prevDir = S; vertex was a point, and dir != L");
					return new Point(p);
				}
			}
			case 'E':
				if (vertex instanceof Intersection) {
					Intersection inter = (Intersection) vertex;
					switch(dir) {
					case 'E':
						return new Point(inter.getLocation()[1]);
					case 'W':
						System.out.println("prevDir = E; No U-Turns Allowed!");
						return null;
					case 'S':
						return new Point(inter.getLocation()[1]);
					case 'N':
						return new Point(inter.getLocation()[3]);
					case 'R':
						return new Point(inter.getRoundabout().getPosition()[0]);
					case 'L':
						System.out.println("prevDir = E; intersection direction = L; this should not ever happen");
						return null;
					default:
						System.out.println("prevDir = E; intersection direction was not a valid direction");
						return null;
					} 
				}else if (vertex instanceof Landmark) {
					Landmark land = (Landmark) vertex;
					if (land.getType() == LandmarkType.EAST) {
						return new Point(land.getLocationArray()[1]);
					} else if (land.getType() == LandmarkType.WEST){
						return new Point(land.getLocationArray()[2]);
					} else if (land.getType() == LandmarkType.NORTH || land.getType() == LandmarkType.SOUTH){
						System.out.println("prevDir = E; landmark type was " + land.getType().toString() + ", this cannot happen"); 
						return null;
					} else {
						System.out.println("prevDir = E; landmark type was not recognizable"); 
						return null;
					}
				} else {
					Point p = (Point) vertex;
					if (dir == 'L') {
						return new Point(p.x, p.y+1);
					} else {
						System.out.println("prevDir = E; vertex was a point, and dir != L");
						return new Point(p);
					}
				}
			case 'W':
				if (vertex instanceof Intersection) {
					Intersection inter = (Intersection) vertex;
					switch(dir) {
					case 'W':
						return new Point(inter.getLocation()[2]);
					case 'E':
						System.out.println("prevDir = W; No U-Turns Allowed!");
						return null;
					case 'N':
						return new Point(inter.getLocation()[2]);
					case 'S':
						return new Point(inter.getLocation()[0]);
					case 'R':
						return new Point(inter.getRoundabout().getPosition()[0]);
					case 'L':
						System.out.println("prevDir = W; intersection direction = L; this should not ever happen");
						return null;
					default:
						System.out.println("prevDir = W; intersection direction was not a valid direction");
						return null;
					} 
				} else if (vertex instanceof Landmark) {
					Landmark land = (Landmark) vertex;
					if (land.getType() == LandmarkType.WEST) {
						return new Point(land.getLocationArray()[1]);
					} else if (land.getType() == LandmarkType.EAST){
						return new Point(land.getLocationArray()[2]);
					} else if (land.getType() == LandmarkType.NORTH || land.getType() == LandmarkType.SOUTH){
						System.out.println("prevDir = W; landmark type was " + land.getType().toString() + ", this cannot happen"); 
						return null;
					} else {
						System.out.println("prevDir = W; landmark type was not recognizable"); 
						return null;
					}
				} else {
					Point p = (Point) vertex;
					if (dir == 'L') {
						return new Point(p.x, p.y-1);
					} else {
						System.out.println("prevDir = W; vertex was a point, and dir != L");
						return new Point(p);
					}
				}
			case 'R':
				if (vertex instanceof Intersection) {
					Intersection inter = (Intersection) vertex;
					if (dir == 'N'||dir == 'S'||dir == 'E'||dir == 'W') {
						return new Point(inter.getRoundabout().getPrev().getPosition()[3]);
					} else if (dir == 'R') {
						return new Point(inter.getRoundabout().getPosition()[0]);
					} else if (dir == 'L') {
						System.out.println("prevDir = R; intersection direction = L, this should not ever happen");
						return null;
					} else {
						System.out.println("prevDir = W; intersection direction was not a valid direction");
						return null;
					}
				} else if (vertex instanceof Landmark) {
					Landmark land = (Landmark) vertex;
					System.out.println("prevDir = R; landmark type was " + land.getType().toString() + ", this cannot happen"); 
					return null;
				} else if (vertex instanceof Point){ 
					System.out.println("prevDir = R; vertex was a point; cannot exit map from roundabout"); 
				} 
			case 'L': //	FIRST THING OFF OF THE """"""STACK"""""""
				if (vertex instanceof Landmark) {
					Landmark land = (Landmark) vertex;
					switch(land.getType()) {
					case NORTH:
						if (dir == 'N') {
							return new Point(land.getLocationArray()[1]);
						} else {
							return new Point(land.getLocationArray()[2]);
						}
					case SOUTH:
						if (dir == 'S') {
							return new Point(land.getLocationArray()[1]);
						} else {
							return new Point(land.getLocationArray()[2]);
						}
					case EAST:
						if (dir == 'E') {
							return new Point(land.getLocationArray()[1]);
						} else {
							return new Point(land.getLocationArray()[2]);
						}
					case WEST:
						if (dir == 'W') {
							return new Point(land.getLocationArray()[1]);
						} else {
							return new Point(land.getLocationArray()[2]);
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
				default:
					return null;
			}
		}
	
}