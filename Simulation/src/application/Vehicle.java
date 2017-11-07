package application;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class Vehicle extends Observable implements Observer{
	int maxVelocity;	//probably cells/timestep (max possible velocity)
	int curVelocity;	//
	int breakDistance;	//# cells between vehicle and other vehicle/intersection before slowing down 
	int stopDistance;	//# cells between vehicle and other vehicle/intersection at complete stop
	int length;			//length of vehicle ... not sure what units
	char direction;		//N, S, E, W -- direction of travel; not sure if this is necessary?
	Point location;		//current location of vehicle
	Point route[];
	
	Vehicle(int mv, int bd, int sd, int len, char dir, Point loc) {
		maxVelocity = mv;
		breakDistance = bd;
		stopDistance = sd;
		length = len;
		direction = dir;
		location = loc;
	}
	
	void setMaxVelocity(int v) {
		this.maxVelocity = v;
	}
	
	void setBreakDist(int d) {
		this.breakDistance = d;
	}
	
	void setStopDist(int d) {
		this.stopDistance = d;
	}
	
	void setLength(int l) {
		this.length = l;
	}
	
	void updatePos(int grid[][]) {
		
	}
	
	void setRoute() {
		
	}
	
	private void stop() {	
		this.curVelocity = 0;
	}
	
	private void decelerate() {
		this.curVelocity -= this.maxVelocity/this.breakDistance;
	}
	
	private void changeState() {
		
	}
	
	private void turn(String direcion) {
		
	}
	
	void start() {
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Vehicle) {
			//check what's up with the observed vehicle, and adjust speed accordingly
			System.out.println("updated vehicle from Vehicle observable");
		} else if (o instanceof Intersection) {
			//check the status of the stoplight at the intersection at the end of the road segment that the vehicle is driving towards
			//also check how close that intersection is
			//then adjust speed accordingly
			System.out.println("updated vehicle from RoadSegment observable");
		}
		/*while (this.curVelocity < 0 && this.curVelocity > this.maxVelocity/this.breakDistance) {
		this.decelerate();
	}*/
	}

	public void updateVehicle() {
		// TODO Auto-generated method stub
		//update location based on current location, direction of travel, velocity, etc.
		//if (location != null) { 
		switch (direction) {
		case 'N':
			location.translate(0, -1);
			break;
		case 'S':
			location.translate(0, 1);
			break;
		case 'E':
			location.translate(1, 0);
			break;
		case 'W':
			location.translate(-1, 0);
			break;
		default:
			System.out.println("something has gone horribly wrong");	
		}

		notifyObservers();
		System.out.println("performed step for vehicle" + location);
	}
}