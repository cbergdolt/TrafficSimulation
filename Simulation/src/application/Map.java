package application;

import java.awt.Point;

public class Map {
	int[][] routeGrid;
	int[][] trackingGrid;
	RoadSegment[] roads = new RoadSegment[12];	//I have no idea how many road segments we will have, but the number should be constant once we get the map figured out
	Intersection[] intersections = new Intersection[10];
	Landmark[] landmarks;
	Point[] entry_exit = new Point[8];
	
	Map() {
		Point pt = new Point(0, 0);
		entry_exit[0] = pt;
		System.out.println("the new point is at " + pt.x + ", " + entry_exit[0].y);
		//I guess this is where the grid(s) are hard-coded
		//And are the landmarks generated here, or passed in?
		// once we have the list of landmarks, we can iterate through that and place them on the grid(s)
		//create the road segments based on the map
		intersections[0] = new Intersection(new Point(0, 0), null, new TrafficSign(SignType.STOP));
		intersections[1] = new Intersection(new Point(1, 0), new StopLight(LightState.GNS_REW, new Point(1, 0), 1, 1, 1, 1), null);
		roads[0] = new RoadSegment(intersections[0], intersections[1]);
	}
	
	//I don't think we really want a main function in the Map class. The map doesn't really do anything...
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

	public void updateMap() {
		System.out.println("roads.length = " + roads.length);
		/*for (int i = 0; i < roads.length; i++) {
			System.out.println("i = " + i + " updating map...");
			roads[i].updateRoads();
		}*/ //this for loop will be a problem unless all elements of the roads array are filled with legitmate road segments
		roads[0].updateRoads();
		System.out.println("updated map");
	}
	
	public Point[] getEntryExit() {
		return entry_exit;
	}
}
