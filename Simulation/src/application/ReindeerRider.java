package application;

import java.awt.Point;

public class ReindeerRider extends Vehicle {

	ReindeerRider(char dir, Point loc, int t) {
		super(dir, loc, t);
		// TODO Auto-generated constructor stub
		maxVelocity = 2;
		breakDistance = 3;
		stopDistance = 2;
		length = 1.5;
		direction = dir;
		location = loc;
		curVelocity = maxVelocity;
		type = t;
		
		observedIntersection = null;
		startRequested = false;
	}

}
