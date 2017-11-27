package application;

import java.awt.Point;

public class SkiElf extends Vehicle {
	SkiElf (char dir, Point loc, int t) {
		super(dir, loc, t);
		// TODO Auto-generated constructor stub
		maxVelocity = 1.5;
		breakDistance = 3;
		stopDistance = 1.5;
		length = 0.5;
		direction = dir;
		location = loc;
		curVelocity = maxVelocity;
		type = t;
		
		observedIntersection = null;
		startRequested = false;
	}

}
