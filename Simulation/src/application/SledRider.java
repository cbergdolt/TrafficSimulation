package application;

import java.awt.Point;

public class SledRider extends Vehicle {

	SledRider(char dir, Point loc, int t) {
		super(dir, loc, t);
		// TODO Auto-generated constructor stub
		maxVelocity = 1;
		breakDistance = 3;
		stopDistance = 1;
		length = 1;
		direction = dir;
		location = loc;
		curVelocity = maxVelocity;
		type = t;
		
		observedIntersection = null;
		startRequested = false;
	}

}
