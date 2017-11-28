package application;

import java.awt.Point;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

/**
 * The Simulation class is in charge of making sure the vehicles are observing the correct 
 * vehicles in front of it and is in the same road segment.   
 * This class also generates new vehicles in different vehicle generators. 
 * 
 * @author Femgineers
 *
 */

public class Simulation extends Observable{
	int runTime;	//length of simulation from start to finish (number of stepLengths)
	int delay;		//rate at which generators generate cars (stepLengths/car)
	int stepLength;	//simulation time step (ms)
	Map m;
	VehicleGenerator[] vg = new VehicleGenerator[8];
	//Vector<Vehicle> vehicles = new Vector<Vehicle>();
	Vector<VehicleView> vehicles = new Vector<VehicleView>();
	RouteGenerator rg;
	
	Simulation(int rt, int d, int sl) {
		runTime = rt;
		stepLength = sl; //500 milliseconds (half a second)
		m = new Map();
		
		//create vehicle generator for each entry/exit point
		Point[] ee = m.getEntryExit();
		for (int i = 0; i < ee.length; i++) {
			System.out.println("location for generator: " + ee[i]);
			if (ee[i].y == 0) {
				vg[i] = new VehicleGenerator(new Point(ee[i]), 0, 'S');
			}
			else if (ee[i].y == 49) {
				vg[i] = new VehicleGenerator(new Point(ee[i]), 0, 'N');
			}
			else if (ee[i].x == 0) {
				vg[i] = new VehicleGenerator(new Point(ee[i]), 0, 'E');
			}
			else if (ee[i].x == 49) {
				vg[i] = new VehicleGenerator(new Point(ee[i]), 0, 'W');
			}
		}
		
		rg = new RouteGenerator();
		//newVehicles();
		//other pesky initialization things?
	}
	
	void run() {
		//int time = 0;	//clock tick implementation
		//while(time <= runTime) { //I honestly have no idea. But I think there should be a loop controlling the marjority of the simulation 
			//sleep (proper clock timing, based on stepLength specification
			try {
				Thread.sleep(stepLength);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("hey! let me sleep!"); //I'm not sure what we actually want to do with an interrupted sleep, but here's the backbone anyway
				e.printStackTrace();
			}
			
			//for each vehicle, update the vehicle
			Iterator<VehicleView> it = vehicles.iterator();
			while(it.hasNext()) {
				it.next().vehicle.updateVehicle();
			}
			
			//update other things, too, like the stoplights
			m.updateMap();
			
			
			// generate new vehicles (and so also routes)
			if (vehicles.size() < vg.length) {
				newVehicles();
			}
			/*if (vehicles.size() > 0) {
				checkBounds();
			}*/
			
			//assign observing vehicles to observable vehicles and intersections
			assignObservers();
			
			//time++;	//time keeps on ticking
			setChanged();
			notifyObservers();	//notify the observing UserInterface
			
			
			
			//System.out.println("completed another simulation iteration; time = " + time);
	//	}
		
	}

	private void assignObservers() {
		//iterates through vehicles and assigns each vehicle as an observer to both another vehicle and an intersection
		//	based on the closest vehicle and closest intersection (in the same direction as the vehicle is traveling, of course)
		
		//remove previous observers of all vehicles and intersections
		for (int i = 0; i < vehicles.size(); i++) {
			vehicles.get(i).vehicle.deleteObservers();
		}
		for (int i = 0; i < m.intersections.length; i++) {
			m.intersections[i].deleteObservers();
		}
		
		//reassign correct observers to vehicles and intersections
		for(Iterator<VehicleView> ita = vehicles.iterator(); ita.hasNext();){ //for each vehicle
			Vehicle va = ita.next().vehicle;
			Vehicle closestVehicle = null;
			for(Iterator<VehicleView> itb = vehicles.iterator(); itb.hasNext();){ //find its nearest neighboring vehicle
				Vehicle vb = itb.next().vehicle;
				if (vb.direction == va.direction && sameRoad(va, vb) && aheadOf(vb, va)) {	//vehicles going same direction and on same road and vb is ahead of va
					if (closestVehicle == null) closestVehicle = vb;
					else closestVehicle = (Vehicle)shorterDistance(va, vb, closestVehicle);
				}
			}
			if (closestVehicle != null) closestVehicle.addObserver(va);
			
			//TODO I don't want vehicles to observe roundabout intersections while in the roundabout. 
			//once in the roundabout, they can move and exit freely, and THEN they need to start observing an intersection again...
			//I'm not sure how to keep track of that yet. 
			Intersection closestIntersection = null;
			for (int i = 0; i < m.intersections.length; i++) { //find its nearest intersection
				Intersection intersection = m.intersections[i];
				if (upcomingIntersection (intersection, va)) {	//if vehicle will cross this intersection if it keeps going straight
					if (closestIntersection == null) closestIntersection = intersection;
					else closestIntersection  = (Intersection)shorterDistance(va, intersection, closestIntersection);
				}
			}
			if (closestIntersection != null) {
				closestIntersection.addObserver(va);
				
				//tell the vehicle which intersection it is observing, but only if it is a new intersection
				//the vehicle needs to know which intersection it is observing so it can add itself to the vehicleQueue when it reaches the intersection
				if (va.getObservedIntersection() != closestIntersection)
					va.setObservedIntersection(closestIntersection);
			}
		}
	}

	

	private void newVehicles() {
		//generate new vehicles at each entry/exit point	
		for (int i = 0; i < vg.length; i++) {
			Vehicle v = vg[i].generateVehicle();
			//Vehicle v = vg[4].generateVehicle();
			if (v != null) {
				Point[] route = generateRoute(v.location);
				v.route = route;
				VehicleView vv = new VehicleView(v);
				vehicles.add(vv);
			}
		}

	}
	
	private void checkBounds() {
		//clean out vehicles that are out of bounds
		for(Iterator<VehicleView> it = vehicles.iterator(); it.hasNext();){ 
			VehicleView vv = it.next(); 
			Point loc = vv.vehicle.location;
			System.out.println("vv loc " + vv.vehicle.location );
			if (vv.moveCount > 0) {
				if (loc.x < 0 || loc.x > 49 || loc.y < 0 || loc.y > 49) { 
					vv.inSimulation = false;
				}
			}
		}
		
	}

	private Point[] generateRoute(Point start) {
		// TODO Auto-generated method stub
		//I'm not sure how to determine number of stops...?
		return rg.generateRoute(4, start, m.landmarks, m.routeGrid);
		//return null;
	}
	
	//HELPER FUNCTIONS FOR ASSIGNING OBSERVERS TO VEHICLES AND INTERSECTION
	private boolean upcomingIntersection(Intersection intersection, Vehicle va) {
		// TODO Auto-generated method stub		
		switch(va.direction) {
		case 'S':
			if (va.location.x == intersection.location[0].x && va.location.y < intersection.location[0].y) return true;
			else return false;
		case 'E':
			if (va.location.x < intersection.location[1].x && va.location.y == intersection.location[1].y) return true;
			else return false;
		case 'W':
			if (va.location.x > intersection.location[2].x && va.location.y == intersection.location[2].y) return true;
			else return false;
		case 'N':
			if (va.location.x == intersection.location[3].x && va.location.y > intersection.location[3].y) return true;
			else return false;
		case 'R':
			System.out.println("upcomingIntersection: direction was 'R'");
			return false;	//I guess?
		default:
			System.out.println("upcomingIntersection: something has gone horribly wrong");
			return false;
		}
	}

	private Object shorterDistance(Vehicle target, Object a, Object b) {	//determines whether object a or object b is closer to target
		// TODO Auto-generated method stub
		Point loca;
		Point locb;
		if (a instanceof Vehicle && b instanceof Vehicle) {
			loca = ((Vehicle)a).location;
			locb = ((Vehicle)b).location;
		}
		else if (a instanceof Intersection && b instanceof Intersection) {
			loca = ((Intersection)a).location[0];
			locb = ((Intersection)b).location[0];
		}
		else return null;
		
		double dista = distance(target.location, loca);
		double distb = distance(target.location, locb);
		
		if (dista < distb) return a;
		else return b;
	}

	private double distance(Point pta, Point ptb) {
		// TODO Auto-generated method stub
		double xdiff = pta.x - ptb.x;
		double ydiff = pta.y - ptb.y;
		double square_sum = Math.pow(xdiff, 2) + Math.pow(ydiff, 2);
		return Math.sqrt(square_sum);
	}

	private boolean aheadOf(Vehicle vb, Vehicle va) {  //determines whether vehicle b (vb) is ahead of vehicle a (va)
		// TODO Auto-generated method stub
		if (!sameRoad(va, vb)) return false; //just in case; vehicle b can only be ahead of vehicle a if they are on the same road
		char dir = vb.direction;
		switch (dir) {
		case 'N':
			if (vb.location.y < va.location.y) return true;
			else return false;
		case 'S':
			if (vb.location.y > va.location.y) return true;
			else return false;
		case 'E':
			if (vb.location.x > va.location.x) return true;
			else return false;
		case 'W':
			if (vb.location.x < va.location.x) return true;
			else return false;
		case 'R':
			System.out.println("aheadOf: direction was 'R'");
			return false; //I guess?
		default:
			System.out.println("aheadOf: something has gone horribly wrong");
			return false;
		}
	}

	private boolean sameRoad(Vehicle va, Vehicle vb) {
		// TODO Auto-generated method stub
		if (va.direction != vb.direction) return false; //just in case; vehicle a and vehicle b can only be on the same road if they are headed the same direction
		char dir = va.direction;
		if((dir == 'N' || dir == 'S') && va.location.x == vb.location.x) return true;
		else if((dir == 'E' || dir == 'W') && va.location.y == vb.location.y) return true;
		else return false;
	}
	
}
