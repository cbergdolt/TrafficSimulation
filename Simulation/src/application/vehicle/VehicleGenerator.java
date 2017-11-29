package application.vehicle;

import java.awt.Point;
import java.util.Random;

/**
 * The VehicleGenerator generates current vehicles.  
 * 
 * @author ChuchafinotaFace
 *
 */

public class VehicleGenerator {
	Point location; //determines initial location of generated vehicles
	int delay;
	private int currentTime;
	char dir;
	int type;

	public VehicleGenerator(Point loc, int d, char direction) {
		location = loc;
		delay = d;
		currentTime = 0;
		dir = direction;
		Random r = new Random();
		type = r.nextInt(3);
		System.out.println(type);
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
			
			//return new Vehicle(1, 3, 1, 1, dir, new Point(location), type);
			if (type == 0) {
				return new SledRider(dir, new Point(location), type);
			} else if (type == 1) {
				return new SkiElf(dir, new Point(location), type);
			} else {
				return new ReindeerRider(dir, new Point(location), type);
			}
		}
	}

}
