package application;

import java.awt.Point;

public class Map {
	int[][] routeGrid;
	int[][] trackingGrid;
	//RoadSegment[] roads = new RoadSegment[12];	//I have no idea how many road segments we will have, but the number should be constant once we get the map figured out
	Intersection[] intersections = new Intersection[56];
	Landmark[] landmarks;
	Point[] entry_exit = new Point[8];
	
	Map() {
		Point pt = new Point(0, 0);
		for (int i = 0; i < entry_exit.length; i++) {
			entry_exit[i] = pt;
		}
		int eeCount = 0;
		int iCount = 0;
		
		//1 = grass, 2 = street, 3 = vehicle generator, 4 = stoplight, 5 = stop sign
		Grid grid = new Grid();
		routeGrid = grid.getRouteGrid();
		Point p = new Point();
		for (int i = 0; i < 50; i++) {	//y
			for (int j = 0; j < 50; j++) {	//x
				if (routeGrid[i][j] == 3) {
					entry_exit[eeCount] = new Point(j, i);
					//entry_exit[eeCount].x = j;
					//entry_exit[eeCount].y = i;
					eeCount += 1;
				} else if (routeGrid[i][j] == 5) {//instantiates intersection where stoplight intersection is
					p.x = j;
					p.y = i;
					StopLight sl = new StopLight(LightState.GNS_REW, new Point(p), 10, 10, 10, 10);
					intersections[iCount] = new Intersection(new Point(p), sl);
					iCount += 1;
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
