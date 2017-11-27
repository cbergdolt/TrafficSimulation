package application;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

/**
 * The Vehicle class is responsible for updating the location of the vehicles. 
 * It also determines whether or not it needs to stop because of a change in the 
 * state of the stop light. The location of the vehicles is updated depending on 
 * the direction it is moving. 
 * 
 * @author Femgineers
 *
 */

public class Vehicle extends Observable implements Observer{
	double maxVelocity;	//probably cells/timestep (max possible velocity)
	double curVelocity;	//
	double breakDistance;	//# cells between vehicle and other vehicle/intersection before slowing down 
	double stopDistance;	//# cells between vehicle and other vehicle/intersection at complete stop
	double length;			//length of vehicle ... not sure what units
	char direction;		//N, S, E, W -- direction of travel -or- R -- roundabout
	Point location;		//current location of vehicle
	Point route[];
	int type;
	
	Intersection observedIntersection;	//so vehicle can add itself to a sign-governed intersection's vehicleQueue
	boolean startRequested;	//used to momentarily prevent stop() being called and changing the curVelocity back to 0
	
	Vehicle(char dir, Point loc, int t) {
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
		//stopIterations += 1;
		if (observedIntersection.sign != null) {
			observedIntersection.vehicleQueue.add(this);
		}
	}
	
	private void decelerate() {
		this.curVelocity -= this.maxVelocity/this.breakDistance;
	}
	
	private void changeState() {
		
	}
	
	private void turn(String direcion) {
		
	}
	
	void start() {
		this.curVelocity = this.maxVelocity;
		startRequested = true;
	}
	
	public Intersection getObservedIntersection() {
		return observedIntersection;
	}
	
	public void setObservedIntersection(Intersection intersection) {
		observedIntersection = intersection;
		startRequested = false;	//a start has not been requested for this observed intersection yet
			//^^^this assumes setObservedIntersection is only called once per new observed intersection
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Vehicle) {
			//check what's up with the observed vehicle, and adjust speed accordingly
			double d = distance(((Vehicle) o).location, location);
			if (d <= stopDistance) {
				stop();
			}else if (d <= breakDistance) {
				decelerate();
			} else {
				this.curVelocity = maxVelocity;
			}
		} else if (o instanceof Intersection) {
			//check the status of the stoplight at the intersection at the end of the road segment that the vehicle is driving towards
			//also check how close that intersection is
			//then adjust speed accordingly
			
			//if the intersection has a light
			if (((Intersection)o).light != null) {
				StopLight light = ((Intersection) o).light;
				Point[] lightLoc = ((Intersection) o).getLocation();
				lightResponse(light, lightLoc);
			}
			//if the intersection has a sign
			else if (((Intersection)o).sign != null) {
				TrafficSign sign = ((Intersection) o).sign;
				Point[] signLoc = ((Intersection) o).getLocation();
				signResponse(sign, signLoc);
			}
		}
		/*while (this.curVelocity < 0 && this.curVelocity > this.maxVelocity/this.breakDistance) {
		this.decelerate();
	}*/
	}

	private void signResponse(TrafficSign sign, Point[] loc) {
		//changes the vehicle's velocity based on its direction and proximity to the sign (and the sign type)
		//SignType stype = sign.getType();
		double dist = 0;
		
		switch (direction) { //South, East, West, North
		case 'N':
			dist = distance(loc[3], location);
			if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
			else if (dist <= this.stopDistance && dist > 0 && !startRequested) this.stop();
			break;
		case 'S':
			dist = distance(loc[0], location);
			if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
			else if (dist <= this.stopDistance && dist > 0 && !startRequested) this.stop();
			break;
		case 'E':
			dist = distance(loc[1], location);
			if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
			else if (dist <= this.stopDistance && dist > 0 && !startRequested) this.stop();
			break;
		case 'W':
			dist = distance(loc[2], location);
			if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
			else if (dist <= this.stopDistance && dist > 0 && !startRequested) this.stop();
			break;
		case 'R':	//roundabout
			//not sure if we actually need this case?
			break;
		default:
			System.out.println("something has gone horribly wrong");	
		}
	}

	private void lightResponse(StopLight sl, Point[] loc) {
		//changes the vehicle's velocity based on its direction and proximity to the stoplight (and the stoplight's state)
		LightState lstate = sl.getState();
		double dist;
		
		switch (direction) { //South, East, West, North
		case 'N':
			dist = distance(loc[3], location);
			if ((lstate == LightState.RNS_GEW) || (lstate == LightState.RNS_YEW))  {
				if (dist <= this.stopDistance) this.stop();
			} else if (lstate == LightState.YNS_REW) {
				if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
				else if (dist <= this.stopDistance && dist > 0) this.stop();
			} else this.curVelocity = this.maxVelocity;
			break;
		case 'S':
			dist = distance(loc[0], location);
			if ((lstate == LightState.RNS_GEW) || (lstate == LightState.RNS_YEW))  {
				if (dist <= this.stopDistance) this.stop();
			} else if (lstate == LightState.YNS_REW) {
				if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
				else if (dist <= this.stopDistance && dist > 0) this.stop();
			} else this.curVelocity = this.maxVelocity;
			break;
		case 'E':
			dist = distance(loc[1], location);
			if ((lstate == LightState.GNS_REW) || (lstate == LightState.YNS_REW))  {
				if (dist <= this.stopDistance) this.stop();
			} else if (lstate == LightState.YNS_REW) {
				if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
				else if (dist <= this.stopDistance && dist > 0) this.stop();
			} else this.curVelocity = this.maxVelocity;
			break;
		case 'W':
			dist = distance(loc[2], location);
			if ((lstate == LightState.GNS_REW) || (lstate == LightState.YNS_REW))  {
				if (dist <= this.stopDistance) this.stop();
			} else if (lstate == LightState.YNS_REW) {
				if (dist <= this.breakDistance && dist > this.stopDistance) this.decelerate();
				else if (dist <= this.stopDistance && dist > 0) this.stop();
			} else this.curVelocity = this.maxVelocity;
			break;
		case 'R':	//roundabout
			//not sure if we actually need this case?
			break;
		default:
			System.out.println("something has gone horribly wrong");	
		}
	}

	public void updateVehicle() {
		// TODO Auto-generated method stub
		//update location based on current location, direction of travel, velocity, etc.
		//if (location != null) { 
		switch (direction) {
		case 'N':
			location.y -= this.curVelocity;
			//location.translate(0, -1);
			break;
		case 'S':
			location.y += this.curVelocity;
			//location.translate(0, 1);
			break;
		case 'E':
			location.x += this.curVelocity;
			//location.translate(1, 0);
			break;
		case 'W':
			location.x -= this.curVelocity;
			//location.translate(-1, 0);
			break;
		default:
			System.out.println("something has gone horribly wrong");	
		}

		notifyObservers();
	}
	
	private double distance(Point pta, Point ptb) {
		// TODO Auto-generated method stub
		double xdiff = pta.x - ptb.x;
		double ydiff = pta.y - ptb.y;
		double square_sum = Math.pow(xdiff, 2) + Math.pow(ydiff, 2);
		return Math.sqrt(square_sum);
	}

}