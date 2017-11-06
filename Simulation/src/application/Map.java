package application;

public class Map {
	int[][] routeGrid;
	int [][] trackingGrid;
	RoadSegment[] roads;
	Landmark[] landmarks;	
	
	Map() {
		//I guess this is where the grid(s) are hard-coded
		//And are the landmarks generated, or passed in?
		// once we have the list of landmarks, we can iterate through that and place them on the grid(s)
		//I'm also not sure if we need the road segments here?
	}
	
	//I don't think we really want a main function in the Map class. The map doesn't really do anything...
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

	public void updateMap() {
		
		
	}
}
