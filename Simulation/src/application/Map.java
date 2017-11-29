package application;

import java.awt.Point;
import java.util.HashMap;

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
	
	Map() {
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
		Point p = new Point();
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
					p.x = j;
					p.y = i;
					landmarks[landCount] = new Landmark("s", landCount, p);
					System.out.println("Landmark count is " + landCount);
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
					intersections[iCount] = new Intersection(ipoints, sl, null);
					iCount += 1;
					fourWayInt += 1;
				} 
				//three-way; stop sign
				else if (routeGrid[j][i] == 5 && routeGrid[j][i+1] == 5 && routeGrid[j+1][i] == 5 && routeGrid[j+1][i+1] == 5) {//instantiate intersection where stop sign intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					TrafficSign ts = new TrafficSign(SignType.STOP);
					intersections[iCount] = new Intersection(ipoints, null, ts);
					iCount += 1;
					threeWayInt += 1;
				} 
				//roundabout; yield sign
				else if ((routeGrid[j][i]) % 7 == 0 && (routeGrid[j][i+1]) % 7 == 0 
						&& (routeGrid[j+1][i]) % 7 == 0 && (routeGrid[j+1][i+1]) % 7 == 0) {//instantiate intersection where yield sign intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					TrafficSign ts = new TrafficSign(SignType.YIELD);
					RoundaboutSegment rab = CreateRoundaboutSegment(ipoints[0], routeGrid[j][i]);
					
					//make sure there is a key in the roundabouts map that corresponds to the routeGrid number
					RoundaboutSegment[] rabsegment = roundabouts.get(routeGrid[j][i]);
					if (rabsegment == null) {
						roundabouts.put(routeGrid[j][i], new RoundaboutSegment[] {null, null, null, null});
					}
					//add roundabout segment to its roundabout "family"
					roundabouts.get(routeGrid[j][i])[rab.segmentID] = rab;	
					
					intersections[iCount] = new Intersection(ipoints, null, ts, rab);
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
			rab[0].next = rab[1];
			rab[1].next = rab[3];
			rab[2].next = rab[0];
			rab[3].next = rab[2];
			//populate prev segments
			rab[0].prev = rab[2];
			rab[1].prev = rab[0];
			rab[2].prev = rab[3];
			rab[3].prev = rab[1];
		}
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
}
