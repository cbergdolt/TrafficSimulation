package application.map;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import application.intersection.*;
import application.route.*;
import application.vehicle.Vehicle;
import application.vehicle.VehicleView;
import javafx.scene.control.TabPane.TabClosingPolicy;

/***
 * The Map class is the main map generator for the simulation.
 * It calls the Grid class where the map is hard-coded in and saved as the routegrid data member. 
 * The most important data members of the Map class includes the Intersections array
 * and the Landmark  array that hold the location of intersections and landmarks, respectively. 
 * The trackingGrid array will allow us to keep track of the location of our map elements.    
 * @author Femgineers
 *
 */

public class Map {
	int[][] routeGrid;
	int[][] trackingGrid;
	//RoadSegment[] roads = new RoadSegment[12];	//I have no idea how many road segments we will have, but the number should be constant once we get the map figured out
	Intersection[] intersections = new Intersection[18];
	int fourWayInt;
	int threeWayInt;
	int roundaboutInt;
	HashMap<Integer, RoundaboutSegment[]> roundabouts = new HashMap<Integer, RoundaboutSegment[]>();
	Landmark[] landmarks = new Landmark[10];
	Point[] entry_exit = new Point[8];
	
	public Map() {
		Point pt = new Point(0, 0);
		for (int i = 0; i < entry_exit.length; i++) {
			entry_exit[i] = pt;
		}
		int eeCount = 0;
		int iCount = 0;
		int landCount = 0;
		//counts for each intersection
		fourWayInt = 0;
		threeWayInt = 0;
		roundaboutInt = 0;
		
		//1 = grass, 2 = street, 3 = vehicle generator, 4 = stop light, 5 = stop sign, 6 = roundabout street, multiple of 7 = yield sign (roundabout intersection)
		Grid grid = new Grid();
		routeGrid = grid.getRouteGrid();
		for (int i = 0; i < 50; i++) {	//y
			for (int j = 0; j < 50; j++) {	//x
				if (routeGrid[j][i] == 3) {	//ENTRY EXIT POINTS (location of vehicle generators
					entry_exit[eeCount] = new Point(j, i);
					System.out.println("NEW EE: "+entry_exit[eeCount]);
					//entry_exit[eeCount].x = j;
					//entry_exit[eeCount].y = i;
					eeCount += 1;
					//top right, bottom right, top left, bottom left (on actual map, not on route grid)
				} 
				else if (routeGrid[j][i] == 8) {
					landmarks[landCount] = new Landmark("s", landCount, new Point(j, i));
					landCount +=1;
				}
				//INTERSECTIONS
				//four-way; stop light
				else if (routeGrid[j][i] == 4 && routeGrid[j][i+1] == 4 && routeGrid[j+1][i] == 4 && routeGrid[j+1][i+1] == 4) {//instantiate intersection where stop light intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					StopLight sl;
					if (fourWayInt == 0 || fourWayInt == 5) //first and last intersections (on this map)--topmost and bottom-most 
						sl = new StopLight(LightState.YNS_REW, ipoints, 10, 10, 10, 10, 0);
					else	//the middle stoplights, all in a line on this map
						sl = new StopLight(LightState.RNS_GEW, ipoints, 10, 10, 10, 10, 10 - fourWayInt*2);
					
					IntersectionType t = determineIntersectionType(ipoints);
					intersections[iCount] = new Intersection(ipoints, t/*IntersectionType.NSEW*/, sl, null);
					iCount += 1;
					fourWayInt += 1;
				} 
				//three-way; stop sign
				else if (routeGrid[j][i] == 5 && routeGrid[j][i+1] == 5 && routeGrid[j+1][i] == 5 && routeGrid[j+1][i+1] == 5) {//instantiate intersection where stop sign intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					TrafficSign ts = new TrafficSign(SignType.STOP);
					IntersectionType t = determineIntersectionType(ipoints);
					intersections[iCount] = new Intersection(ipoints, t, null, ts);
					iCount += 1;
					threeWayInt += 1;
				} 
				//roundabout; yield sign
				else if ((routeGrid[j][i]) % 7 == 0 && (routeGrid[j][i+1]) % 7 == 0 
						&& (routeGrid[j+1][i]) % 7 == 0 && (routeGrid[j+1][i+1]) % 7 == 0) {//instantiate intersection where yield sign intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					TrafficSign ts = new TrafficSign(SignType.YIELD);
					RoundaboutSegment rab = CreateRoundaboutSegment(ipoints[0], routeGrid[j][i]);
					IntersectionType t = determineIntersectionType(ipoints);
					
					//make sure there is a key in the roundabouts map that corresponds to the routeGrid number
					RoundaboutSegment[] rabsegment = roundabouts.get(routeGrid[j][i]);
					if (rabsegment == null) {
						roundabouts.put(routeGrid[j][i], new RoundaboutSegment[] {null, null, null, null});
					}
					//add roundabout segment to its roundabout "family"
					roundabouts.get(routeGrid[j][i])[rab.getSegmentID()] = rab;	
					
					intersections[iCount] = new Intersection(ipoints, t, null, ts, rab);
					rab.setIntersection(intersections[iCount]);	//give roundabout segment access to its intersection
					iCount += 1;
					roundaboutInt += 1;
				}
				//System.out.print(routeGrid[j][i] + " ");
			}
			//System.out.print("\n");
		}
		
		//link all the proper roundabout segments together
		linkRoundabout();
		
		
		//I guess this is where the grid(s) are hard-coded
		//And are the landmarks generated here, or passed in?
		// once we have the list of landmarks, we can iterate through that and place them on the grid(s)
		//create the road segments based on the map
		//intersections[0] = new Intersection(new Point(0, 0), null, new TrafficSign(SignType.STOP));
		//intersections[1] = new Intersection(new Point(1, 0), new StopLight(LightState.GNS_REW, new Point(1, 0), 1, 1, 1, 1), null);
		//roads[0] = new RoadSegment(intersections[0], intersections[1]);
	}

	public int closeRoad() {
		Random r = new Random();
		int i = r.nextInt(intersections.length);	//select intersection to close
		
		System.out.println("closing intersection " + i);
		for (int a = 0; a < intersections[i].getLocation().length; a++) System.out.print(intersections[i].getLocation()[a] + ", ");
		System.out.println("");
		
		int d = r.nextInt(4); //choose direction to close
		//make sure direction is valid
		while (!(intersections[i].getType().isOpen(d))) {
			d = r.nextInt(4);	//keep finding a random direction until it's open on intersection i
		}
		
		System.out.println("closing direction " + d);
		System.out.println("before block, intersection type = " + intersections[i].getType().toString());
		intersections[i].blockDirection(d);
		System.out.println("after block, intersection type = " + intersections[i].getType().toString());
		
		Intersection iB = findAdjacentIntersection(d, intersections[i]);
		//if the intersection actually has an adjacent intersection in direction d:
		if (iB != null) {
			iB.blockOppositeDirection(d);	//make sure vehicles can't enter the road segment from the other direction

			System.out.println("found an adjacent intersection to close, no need to find generator");
			System.out.println("intersection coordinates are:");
			for (int a = 0; a < iB.getLocation().length; a++) System.out.print(iB.getLocation()[a] + ", ");
			System.out.println("");
			
			return -1;	//found intersection, no need for the simulation to block a generator
		}
		//otherwise, the intersection must be adjacent to a generator in direction d:
		else {	//if iB is null 
			int vg = findAdjacentGenerator(d, intersections[i]);
			System.out.println("no adjacent intersection found, returning " + vg + " with location " + entry_exit[vg] + " as generator to block");
			return vg; //vg.block();		//return vg as the index of the vehicle generator to be blocked
		}
	}
	
	private RoundaboutSegment CreateRoundaboutSegment(Point intloc, int key) {
		int id = -1;
		if (roundabouts.get(key) == null) id = 0;
		else {
			RoundaboutSegment[] rab = roundabouts.get(key);
			for (int i = 0; i < rab.length; i++) {
				if (rab[i] == null) {
					id = i;
					break;
				}
			}
		}
		
		Point[] rabpoints = new Point[4];
		int i = intloc.y;
		int j = intloc.x;
		switch (id) {
			case 0:
				rabpoints[0] = new Point(j, i);
				rabpoints[1] = new Point(j-1, i);
				rabpoints[2] = new Point(j-2, i+1);
				rabpoints[3] = new Point(j-2, i+2);
				break;
			case 1:
				rabpoints[0] = new Point(j, i+1);
				rabpoints[1] = new Point(j, i+2);
				rabpoints[2] = new Point(j+1, i+3);
				rabpoints[3] = new Point(j+2, i+3);
				break;
			case 2:
				rabpoints[0] = new Point(j+1, i);
				rabpoints[1] = new Point(j+1, i-1);
				rabpoints[2] = new Point(j, i-2);
				rabpoints[3] = new Point(j-1, i-2);
				break;
			case 3:
				rabpoints[0] = new Point(j+1, i+1);
				rabpoints[1] = new Point(j+2, i+1);
				rabpoints[2] = new Point(j+3, i);
				rabpoints[3] = new Point(j+3, i-1);
				break;
			default:
				System.out.println("CreateRoundaboutSegment: something has gone horribly wrong");
		}

		RoundaboutSegment rab = new RoundaboutSegment(rabpoints, id);
		
		return rab;
	}
	
	private void linkRoundabout() {
		RoundaboutSegment[] rab;
		for (Integer key : roundabouts.keySet()) {
			rab = roundabouts.get(key);	//array of roundabout segments for the single roundabout with key key
			//populate next segments
			rab[0].setNext(rab[1]);
			rab[1].setNext(rab[3]);
			rab[2].setNext(rab[0]);
			rab[3].setNext(rab[2]);
			//populate prev segments
			rab[0].setPrev(rab[2]);
			rab[1].setPrev(rab[0]);
			rab[2].setPrev(rab[3]);
			rab[3].setPrev(rab[1]);
		}
	}

	private IntersectionType determineIntersectionType(Point[] ipoints) {
		//determine the type/direction of the intersection based on the points in the intersection
		//recall order of points in intersection point array
		//	|S|W| >> |0|2|
		//	|E|N| >> |1|3|
		boolean N, S, E, W;	//true means vehicle CAN go through intersection that direction
							//false means vehicle CANNOT go through intersection that direction
		IntersectionType t = IntersectionType.NSEW;	//it has to be initialized to something...
		
		//check point outside south location
		if (routeGrid[ipoints[0].x][ipoints[0].y+2] == 2) S = true;
		else S = false;
		//check point outside east location
		if (routeGrid[ipoints[1].x+2][ipoints[1].y] == 2) E = true;
		else E = false;
		//check point outside west location
		if (routeGrid[ipoints[2].x-2][ipoints[2].y] == 2) W = true;
		else W = false;
		//check point outside north location
		if (routeGrid[ipoints[3].x][ipoints[3].y-2] == 2) N = true;
		else N = false;
		
		t = t.getIntersectionType(N, S, E, W);		
		return t;
	}
	
	private Intersection findAdjacentIntersection(int d, Intersection iA) {
		Intersection foundInt = null;
		for (int i = 0; i < intersections.length; i++) {
			Intersection iB = intersections[i];
			if (adjacent(iA, iB, d)) {
				if ((foundInt != null && iB == closestIntersection(iA, iB, foundInt)) || foundInt == null) foundInt = iB;
			}
		}
		return foundInt;
	}
	
	private int findAdjacentGenerator(int d, Intersection iA) {
		int vg = -1;
		for (int i = 0; i < entry_exit.length; i++) {
			if (adjacent(iA, (Integer)i, d)) {
				//if it's an adjacent "generator", it should automatically be the "closest", so no need to check
				vg = i;
			}
		}
		return vg;
	}
	
	private boolean adjacent(Intersection iA, Object o, int d) {
		Intersection iB = null;
		Integer vg = -1;
		//VehicleGenerator vg = null;
		Point[] iAloc = iA.getLocation();
		if (o instanceof Intersection) {
			iB = ((Intersection)o);
			Point[] iBloc = iB.getLocation();
			switch(d) {
			case 0:	//iB should be south of iA
				if (iAloc[d].x == iBloc[d].x && iAloc[d].y < iBloc[d].y) return true;
				else return false;
			case 1: //iB should be east of iA
				if (iAloc[d].x < iBloc[d].x && iAloc[d].y == iBloc[d].y) return true;
				else return false;
			case 2: //iB should be west of iA
				if (iAloc[d].x > iBloc[d].x && iAloc[d].y == iBloc[d].y) return true;
				else return false;
			case 3: //iB should be north of iA
				if (iAloc[d].x == iBloc[d].x && iAloc[d].y > iBloc[d].y) return true;
				else return false;
			default:	//this shouldn't ever happen...
				System.out.println("Map::adjacent: something has gone horribly wrong");
				return false;
			}
			
		} else if (o instanceof Integer) {
			vg = ((Integer)o);
			Point vgloc = entry_exit[vg];
			switch(d) {
			case 0:	//generator should be south of iA
				if (iAloc[d].x == vgloc.x-1 && iAloc[d].y < vgloc.y) return true;
				else return false;
			case 1: //generator should be east of iA
				if (iAloc[d].x < vgloc.x && iAloc[d].y == vgloc.y+1) return true;
				else return false;
			case 2: //generator should be west of iA
				if (iAloc[d].x > vgloc.x && iAloc[d].y == vgloc.y-1) return true;
				else return false;
			case 3: //generator should be north of iA
				if (iAloc[d].x == vgloc.x+1 && iAloc[d].y > vgloc.y) return true;
				else return false;
			default:	//this shouldn't ever happen...
				System.out.println("Map::adjacent: something has gone horribly wrong");
				return false;
			}
		}
		return false;	//for some reason, the object wasn't an Intersection or Integer (generator index)
	}
	
	private Intersection closestIntersection(Intersection iA, Intersection iB, Intersection iC) {
		//doesn't matter which direction I use, the distance comparison will tell me everything I want to know
		Point locA = iA.getLocation()[0];
		Point locB = iB.getLocation()[0];
		Point locC = iC.getLocation()[0];
		
		if (distance(locA, locB) > distance(locA, locC)) return iC;
		else return iB;
	}
	
	private double distance(Point pta, Point ptb) {
		// TODO Auto-generated method stub
		double xdiff = pta.x - ptb.x;
		double ydiff = pta.y - ptb.y;
		double square_sum = Math.pow(xdiff, 2) + Math.pow(ydiff, 2);
		return Math.sqrt(square_sum);
	}
	
	public void updateMap() {
		//System.out.println("intersections.length = " + intersections.length);
		/*for (int i = 0; i < roads.length; i++) {
			System.out.println("i = " + i + " updating map...");
			roads[i].updateRoads();
		}*/ //this for loop will be a problem unless all elements of the roads array are filled with legitmate road segments
		//roads[0].updateRoads();
		
		//update all intersections (not roads, because those don't exist anymore)
		for (int i = 0; i < intersections.length; i++) {
			intersections[i].updateIntersection();
		}
		
	}
	
	public Point[] getEntryExit() {
		return entry_exit;
	}
	
	public Intersection[] getIntersections() { return intersections; }
	
	public void setIntersections(Intersection[] i) { intersections = i; }
	
	public Landmark[] getLandmarks() { return landmarks; }
	
	public void setLandmarks(Landmark[] l) { landmarks = l; }
	
	public int[][] getRouteGrid() { return routeGrid; }
	
	public void setRouteGrid(int [][] g) { routeGrid = g; } 
}
