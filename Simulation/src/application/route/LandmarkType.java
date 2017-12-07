package application.route;

/**
 * EAST: landmark is adjacent to east bound side of the road (below horizontal road segment)
 * NORTH: landmark is adjacent to north bound side of the road (right of vertical road segment)
 * SOUTH: landmark is adjacent to south bound side of the road (left of vertical road segment)
 * WEST: landmark is adjacent to west bound side of the road (above horizontal road segment)
 * @author Carolyn & Shelby
 *
 */

public enum LandmarkType {
	NORTH,
	SOUTH,
	EAST,
	WEST
}
