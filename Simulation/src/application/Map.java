package application;

import java.awt.Point;

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
	Landmark[] landmarks;
	Point[] entry_exit = new Point[8];
	
	Map() {
		Point pt = new Point(0, 0);
		for (int i = 0; i < entry_exit.length; i++) {
			entry_exit[i] = pt;
		}
		int eeCount = 0;
		int iCount = 0;
		fourWayInt = 0;
		threeWayInt = 0;
		roundaboutInt = 0;
		
		//1 = grass, 2 = street, 3 = vehicle generator, 4 = stop light, 5 = stop sign, 6 = roundabout street, 7 = yield sign
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
				//INTERSECTIONS
				//four-way; stop light
				else if (routeGrid[j][i] == 4 && routeGrid[j][i+1] == 4 && routeGrid[j+1][i] == 4 && routeGrid[j+1][i+1] == 4) {//instantiate intersection where stop light intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					StopLight sl = new StopLight(LightState.GNS_REW, ipoints, 10, 10, 10, 10);
					intersections[iCount] = new Intersection(ipoints, sl, null);
					iCount += 1;
					fourWayInt += 1;
				} 
				//three-way; stop sign
				else if (routeGrid[j][i] == 5 && routeGrid[j][i+1] == 5 && routeGrid[j+1][i] == 5 && routeGrid[j+1][i+1] == 5) {//instantiate intersection where stop sign intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					//StopLight sl = new StopLight(LightState.GNS_REW, ipoints, 10, 10, 10, 10);
					TrafficSign ts = new TrafficSign(SignType.STOP);
					intersections[iCount] = new Intersection(ipoints, null, ts);
					iCount += 1;
					threeWayInt += 1;
				} 
				//roundabout; yield sign
				else if (routeGrid[j][i] == 7 && routeGrid[j][i+1] == 7 && routeGrid[j+1][i] == 7 && routeGrid[j+1][i+1] == 7) {//instantiate intersection where yield sign intersection is
					Point[] ipoints = {new Point(j, i), new Point(j, i+1), new Point(j+1, i), new Point(j+1, i+1)};
					//StopLight sl = new StopLight(LightState.GNS_REW, ipoints, 10, 10, 10, 10);
					TrafficSign ts = new TrafficSign(SignType.YIELD);
					intersections[iCount] = new Intersection(ipoints, null, ts);
					iCount += 1;
					roundaboutInt += 1;
				}
				//System.out.print(routeGrid[j][i] + " ");
			}
			//System.out.print("\n");
		}
		
		
		//I guess this is where the grid(s) are hard-coded
		//And are the landmarks generated here, or passed in?
		// once we have the list of landmarks, we can iterate through that and place them on the grid(s)
		//create the road segments based on the map
		//intersections[0] = new Intersection(new Point(0, 0), null, new TrafficSign(SignType.STOP));
		//intersections[1] = new Intersection(new Point(1, 0), new StopLight(LightState.GNS_REW, new Point(1, 0), 1, 1, 1, 1), null);
		//roads[0] = new RoadSegment(intersections[0], intersections[1]);
	}
	
	//I don't think we really want a main function in the Map class. The map doesn't really do anything...
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

	public void updateMap() {
		System.out.println("intersections.length = " + intersections.length);
		/*for (int i = 0; i < roads.length; i++) {
			System.out.println("i = " + i + " updating map...");
			roads[i].updateRoads();
		}*/ //this for loop will be a problem unless all elements of the roads array are filled with legitmate road segments
		//roads[0].updateRoads();
		
		//update all intersections (not roads, because those don't exist anymore)
		for (int i = 0; i < intersections.length; i++) {
			intersections[i].updateIntersection();
		}
		
		System.out.println("updated map");
	}
	
	public Point[] getEntryExit() {
		return entry_exit;
	}
}
