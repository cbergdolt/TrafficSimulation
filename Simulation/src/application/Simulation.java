package application;

import java.awt.Point;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;
import application.map.*;
import application.vehicle.*;
import application.route.*;
import application.intersection.*;

/**
 * The Simulation class is in charge of making sure the vehicles are observing the correct 
 * vehicles in front of it and is in the same road segment.   
 * This class also generates new vehicles in different vehicle generators. 
 * 
 * @author Femgineers
 *
 */

public class Simulation extends Observable{
	int runTime;	// Length of simulation from start to finish (number of stepLengths)
	int delay;		// Rate at which generators generate cars (stepLengths/car)
	int stepLength;	// Simulation time step (ms)
	Map m;
	VehicleGenerator[] vg = new VehicleGenerator[8];
	Vector<VehicleView> vehicles = new Vector<VehicleView>();
	RouteGenerator rg;
	
	Simulation(int rt, int d, int sl) {
		runTime = rt;
		stepLength = sl; //500 milliseconds
		m = new Map();
		Random r = new Random();
		int roadClosure = r.nextInt(2);	//so that the closures happen 50% of the time
		int blockedVG;
		if (roadClosure == 0) blockedVG = -1; //definitely don't block a generator
		else blockedVG = m.closeRoad();	//close a road segment
		
		// Create vehicle generator for each entry/exit point
		Point[] ee = m.getEntryExit();
		for (int i = 0; i < ee.length; i++) {
			if (ee[i].y == 0) {
				vg[i] = new VehicleGenerator(i, new Point(ee[i]), 0, 'S');
			}
			else if (ee[i].y == 49) {
				vg[i] = new VehicleGenerator(i, new Point(ee[i]), 0, 'N');
			}
			else if (ee[i].x == 0) {
				vg[i] = new VehicleGenerator(i, new Point(ee[i]), 0, 'E');
			}
			else if (ee[i].x == 49) {
				vg[i] = new VehicleGenerator(i, new Point(ee[i]), 0, 'W');
			}
			
			// Block vehicle generator if it's the one indicated by the m.closeRoad() call earlier
			if (blockedVG != -1 && vg[i].getID() == blockedVG) vg[i].block();
		}
		
		rg = new RouteGenerator();
	}
	
	void run() {
			try {
				Thread.sleep(stepLength);
			} catch (InterruptedException e) {
				System.out.println("hey! let me sleep!");
				e.printStackTrace();
			}
			
			// For each vehicle, update the vehicle
			Iterator<VehicleView> it = vehicles.iterator();
			while(it.hasNext()) {
				VehicleView v = it.next();
				v.getVehicle().updateVehicle();
				v.setMyImage();
				
			}
			
			// Update other things, too, like the stoplights
			m.updateMap();
			
			if (vehicles.size() < vg.length) {
				newVehicle();
			}
			
			//assign observing vehicles to observable vehicles and intersections
			assignObservers();
			
			setChanged();
			notifyObservers();	//notify the observing UserInterface
			
	}

	private void assignObservers() {
		// Iterates through vehicles and assigns each vehicle as an observer to both another vehicle and an intersection
		//	Based on the closest vehicle and closest intersection (in the same direction as the vehicle is traveling, of course)
		
		//remove previous observers of all vehicles and intersections
		for (int i = 0; i < vehicles.size(); i++) {
			vehicles.get(i).getVehicle().deleteObservers();
		}
		for (int i = 0; i < m.getIntersections().length; i++) {
			m.getIntersections()[i].deleteObservers();
		}
		
		//reassign correct observers to vehicles and intersections
		for(Iterator<VehicleView> ita = vehicles.iterator(); ita.hasNext();){ //for each vehicle
			Vehicle va = ita.next().getVehicle();
			//find nearest upcoming vehicle and observe it
			Vehicle closestVehicle = null;
			for(Iterator<VehicleView> itb = vehicles.iterator(); itb.hasNext();){ //find its nearest neighboring vehicle
				Vehicle vb = itb.next().getVehicle();
				if (vb.getDirection() == va.getDirection() && sameRoad(va, vb) && aheadOf(vb, va)) {	//vehicles going same direction and on same road and vb is ahead of va
					if (closestVehicle == null) closestVehicle = vb;
					else closestVehicle = (Vehicle)shorterDistance(va, vb, closestVehicle);
				}
			}
			if (closestVehicle != null) {
				closestVehicle.addObserver(va);
				va.setObservedVehicle(closestVehicle);
			}
			//find nearest upcoming intersection and observe it
			Intersection closestIntersection = null;
			for (int i = 0; i < m.getIntersections().length; i++) { //find its nearest intersection
				Intersection intersection = m.getIntersections()[i];
				if (upcomingIntersection (intersection, va)) {	//if vehicle will cross this intersection if it keeps going straight
					if (closestIntersection == null) closestIntersection = intersection;
					else closestIntersection  = (Intersection)shorterDistance(va, intersection, closestIntersection);
				}
			}
			if (closestIntersection != null) {
				closestIntersection.addObserver(va);
				// Tell the vehicle which intersection it is observing, but only if it is a new intersection
				// The vehicle needs to know which intersection it is observing so it can add itself to the 
				//	vehicleQueue when it reaches the intersection
				if (va.getObservedIntersection() != closestIntersection)
					va.setObservedIntersection(closestIntersection);
			}
		}
	}
	
	private void newVehicle() {
		// Generate new vehicle from a randomly selected vehicle generator
		Random r = new Random();
		int w = r.nextInt(vg.length);
		Vehicle v = vg[w].generateVehicle();
		if (v != null) {
			Route route = generateRoute(v.getLocation(), v.getDirection());
			v.setRoute(route);
			VehicleView vv = new VehicleView(v);
			vehicles.add(vv);
		}
	}

	private Route generateRoute(Point start, char dir) {
		return rg.generateRoute(4, start, m.getLandmarks(), m.getRouteGrid(), m.getIntersections(), dir, m.getVertices(), m.getAdjList());
	}
	//HELPER FUNCTIONS FOR ASSIGNING OBSERVERS TO VEHICLES AND INTERSECTION
	private boolean upcomingIntersection(Intersection intersection, Vehicle va) {
		Point vloc = va.getLocation();
		Point[] iloc = intersection.getLocation();
		switch(va.getDirection()) {
		case 'S':
			if (vloc.x == iloc[0].x && vloc.y < iloc[0].y) return true;
			else return false;
		case 'E':
			if (vloc.x < iloc[1].x && vloc.y == iloc[1].y) return true;
			else return false;
		case 'W':
			if (vloc.x > iloc[2].x && vloc.y == iloc[2].y) return true;
			else return false;
		case 'N':
			if (vloc.x == iloc[3].x && vloc.y > iloc[3].y) return true;
			else return false;
		case 'R':
			return false;
		case 'L':
			return false;
		default:
			System.out.println("upcomingIntersection: something has gone horribly wrong");
			return false;
		}
	}

	private Object shorterDistance(Vehicle target, Object a, Object b) {	// Determines whether object a or object b is closer to target
		Point loca;
		Point locb;
		if (a instanceof Vehicle && b instanceof Vehicle) {
			loca = ((Vehicle)a).getLocation();
			locb = ((Vehicle)b).getLocation();
		}
		else if (a instanceof Intersection && b instanceof Intersection) {
			loca = ((Intersection)a).getLocation()[0];
			locb = ((Intersection)b).getLocation()[0];
		}
		else return null;
		
		double dista = distance(target.getLocation(), loca);
		double distb = distance(target.getLocation(), locb);
		
		if (dista < distb) return a;
		else return b;
	}

	private double distance(Point pta, Point ptb) {
		double xdiff = pta.x - ptb.x;
		double ydiff = pta.y - ptb.y;
		double square_sum = Math.pow(xdiff, 2) + Math.pow(ydiff, 2);
		return Math.sqrt(square_sum);
	}

	private boolean aheadOf(Vehicle vb, Vehicle va) {  // Determines whether vehicle b (vb) is ahead of vehicle a (va)
		if (!sameRoad(va, vb)) return false; // Just in case; vehicle b can only be ahead of vehicle a if they are on the same road
		char dir = vb.getDirection();
		switch (dir) {
		case 'N':
			if (vb.getLocation().y < va.getLocation().y) return true;
			else return false;
		case 'S':
			if (vb.getLocation().y > va.getLocation().y) return true;
			else return false;
		case 'E':
			if (vb.getLocation().x > va.getLocation().x) return true;
			else return false;
		case 'W':
			if (vb.getLocation().x < va.getLocation().x) return true;
			else return false;
		case 'R':
			return false;
		case 'L': 
			return false;
		default:
			System.out.println("aheadOf: something has gone horribly wrong");
			return false;
		}
	}

	private boolean sameRoad(Vehicle va, Vehicle vb) {
		if (va.getDirection() != vb.getDirection()) return false; //just in case; vehicle a and vehicle b can only be on the same road if they are headed the same direction
		char dir = va.getDirection();
		if((dir == 'N' || dir == 'S') && va.getLocation().x == vb.getLocation().x) return true;
		else if((dir == 'E' || dir == 'W') && va.getLocation().y == vb.getLocation().y) return true;
		else return false;
	}
	
}
