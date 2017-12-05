package application.route;

import java.awt.Point;

public class Vertex {
	Point point;
	int id;
	
	public Vertex(Point p, int i) {
		point = p;
		id = i;
	}
	
	public Point getPoint() { return point; }
	public int getId() { return id; }
}
