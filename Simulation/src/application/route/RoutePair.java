package application.route;

import java.awt.Point;

public class RoutePair {
	Point point;
	char direction;
	
	public RoutePair(Point p, char d) {
		point = p;
		direction = d;
	}
	
	public Point getPoint() { return point; }
	public char getDirection() { return direction; }
}
