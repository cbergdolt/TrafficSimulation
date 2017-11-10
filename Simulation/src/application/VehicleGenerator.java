package application;

import java.awt.Point;

public class VehicleGenerator {
	Point location; //determines initial location of generated vehicles
	int delay;
	private int currentTime;
	char dir;

	VehicleGenerator(Point loc, int d, char direction) {
		location = loc;
		delay = d;
		currentTime = 0;
		dir = direction;
	}
	
	public Vehicle generateVehicle() {
		//System.out.println("direction is " + dir);
		if (currentTime < delay) {
			currentTime++;	//increment time; progress in generator cycle
			return null;	//delay cycle not complete, don't make a new vehicle
		} else {
			currentTime = 0; //reset generator cycle
			// need a random generated number to get the vehicle type
			//determine parameters of vehicle somehow
			
			return new Vehicle(1, 3, 1, 1, dir, new Point(location));
		}
	}

}
