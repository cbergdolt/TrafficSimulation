package application;

public class Grid {
	int[][] routeGrid = {
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,3,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,3},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {3,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,1,1,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2},
			 {2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,1,1,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,3},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 2,2,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,2,2, 1,1,1,1,1, 1,1,1,1,1},
			 {2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,3},
			 {3,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2, 2,2,2,2,2},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,2,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1},
			 {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,3,2,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1}};
	
	Grid() {
		System.out.println("hi");
	}

	public int[][] getRouteGrid() {
		// TODO Auto-generated method stub
		return routeGrid;
	}
}
