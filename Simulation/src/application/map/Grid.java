package application.map;

/**
 * Hard-coded grid instance, useful for isolating map changes and cleaning up the UserInterface class
 * Grid Key:
 * 1 = "negative space," map area that is nothing in particular: not road, not landmark, not generation point, not intersection
 * 2 = standard road
 * 3 = vehicle generation point (where the vehicles enter the simulation)
 * 4 = intersection with a stoplight (four way intersection)
 * 5 = intersection with a stopsign (three way intersection)
 * 6 = roundabout road
 * 7 = intersection with a yield sign (roundabout intersection)
 * 8 = Landmark (testing static ones)
 * 9 = Landmark (large) image location
 * 
 * @author Femgineers
 *
 */

public class Grid {
	int[][] routeGrid = {
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,3,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,9, 1,1,9,1,1, 9,1,1,9,1, 1,9,1,1,8, 1,1,2,2,1, 1,9,1,1,9, 1,1,9,1,1, 9,1,1,9,1, 1,9,1,1,9, 1,1,9,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 2,2,2,2,2, 2,2,2,2,3},
			 {1,1,1,9,1, 1,9,1,1,1, 2,2,1,1,9, 1,1,9,1,1, 9,1,2,2,1, 1,1,9,1,1, 9,1,1,8,1, 1,9,1,2,2, 9,1,1,9,1, 1,9,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,8,1,1, 1,1,1,1,1, 1,9,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,9,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,9, 1,1,9,1,1, 9,1,2,2,1, 1,1,9,1,1, 9,1,1,9,1, 1,9,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,9,1, 1,9,1,1,9, 1,1,2,2,1, 1,1,9,1,1, 9,1,1,9,1, 1,9,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,9,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 8,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,8, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,9,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,9,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,9,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,9,1,1, 1,1,1,1,1, 6,/*want to detect this roundabout location for image placement*/
				 											6,7,7,6, 6,1,1,1,1, 1,1,1,1,1, 1,1,9,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,9,1,1, 9,1,1,9,1, 2,2,1,1,1, 9,1,1,9,1, 6,6,7,7,6, 6,1,9,1,1, 9,1,1,9,1, 1,1,1,2,2, 1,9,1,1,9, 1,1,9,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 7,7,1,1,7, 7,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 7,7,1,1,7, 7,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,3},
			 {9,1,1,9,1, 1,9,1,1,9, 2,2,1,1,1, 9,1,1,9,1, 6,6,7,7,6, 6,1,9,1,1, 9,1,1,9,1, 1,1,9,2,2, 1,1,9,1,1, 9,1,1,9,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,9,1,1, 1,1,1,1,1, 6,6,7,7,6, 6,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 8,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,8, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,9,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,9,1,1, 1,1,1,1,1, 1,9,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,9,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,9,1,1, 9,1,1,9,1, 1,9,2,2,1, 9,1,1,9,1, 1,8,1,1,1, 9,1,1,2,2, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,9,1, 1,9,1,1,9, 1,1,2,2,1, 1,9,1,1,9, 1,1,9,1,1, 9,1,1,2,2, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 2,2,1,1,1, 1,1,1,1,1, 1,9,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,9,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,9,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,9, 2,2,1,1,1, 1,1,1,1,1, 1,9,2,2,8, 1,1,1,1,1, 1,1,1,1,1, 1,1,9,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,9,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 9,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,9,1, 1,9,1,1,1, 2,2,1,1,1, 1,9,1,1,9, 1,1,2,2,1, 1,9,1,1,9, 1,1,9,1,1, 9,1,1,2,2, 1,1,9,1,1, 9,1,9,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 5,5,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,5,5, 2,2,2,2,2, 2,2,2,2,3},
			 {9,1,1,9,1, 1,9,1,1,9, 1,1,9,1,1, 9,1,1,9,1, 1,9,2,2,1, 1,9,1,1,8, 1,1,9,1,1, 9,1,1,9,1, 1,9,1,1,9, 1,1,9,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,9, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,9,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,3,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1}};
	

	int[][] routeGridB = {	/*old grid, please use this if changes ruin everything*/
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,3,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,3},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 6,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 2,2,1,1,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 2,2,1,1,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,3},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 4,4,2,2,2, 2,2,2,2,2, 2,2,4,4,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,4,4, 2,2,2,2,2, 2,2,2,2,3},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,3,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1}};
	
	int[][] adjList = { /* Adjacency list Hard coded*/
			{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{2,0,0,0,0,0,1,0,0,0,2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,2,0,1,0,1,0,0,2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,2,0,2,2,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,2,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,2,0,2,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,2,0,0,2,2,0,0,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,2,0,1,0,0,0,0,0,2,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,2,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,2,2,0,2,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,2,0,0,0,1,0,1,0,2,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,2},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0}
			
	};
	
	
	Grid() {
		System.out.println("hi");
	}

	public int[][] getRouteGrid() {
		return routeGrid;
	}
	
	public int[][] getAdjList(){
		return adjList;
	}
}
