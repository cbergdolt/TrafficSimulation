package application;

import java.awt.Point;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

public class Simulation extends Observable{
	int runTime;	//length of simulation from start to finish (number of stepLengths)
	int delay;		//rate at which generators generate cars (stepLengths/car)
	int stepLength;	//simulation time step (ms)
	Map m;
	VehicleGenerator[] vg = new VehicleGenerator[8];
	Vector<Vehicle> vehicles = new Vector<Vehicle>();
	RouteGenerator rg;
	
	Simulation(int rt, int d, int sl) {
		runTime = rt;
		stepLength = sl; //500 milliseconds (half a second)
		m = new Map();
		
		//create vehicle generator for each entry/exit point
		Point[] ee = m.getEntryExit();
		for (int i = 0; i < ee.length; i++) {
			vg[i] = new VehicleGenerator(ee[i], delay);
		}
		
		rg = new RouteGenerator();
		
		//other pesky initialization things?
	}
	
	void run() {
		int time = 0;	//clock tick implementation
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
			Iterator<Vehicle> it = vehicles.iterator();
			while(it.hasNext()) {
				it.next().updateVehicle();
			}
			
			//update other things, too, like the stoplights
			m.updateMap();
			
			// generate new vehicles (and so also routes)
			newVehicles();
			
			time++;	//time keeps on ticking
			notifyObservers();	//notify the observing UserInterface
			System.out.println("completed another simulation iteration; time = " + time);
		//}
		
	}

	private void newVehicles() {
		//generate new vehicles at each entry/exit point
		for (int i = 0; i < vg.length; i++) {
			Vehicle v = vg[i].generateVehicle();
			if (v == null) continue;	//no vehicle generated
			else {
				Point[] route = generateRoute(v.location);	//generate route starting at vehicle's location
				v.route = route;	//assign route to vehicle
				vehicles.add(v);	//add newly generated vehicle to vehicle list
			}
		}
		
		//clean out vehicles that are out of bounds
		Iterator<Vehicle> it = vehicles.iterator();
		while(it.hasNext()) {
			//check it.Next().location
			Point loc = it.next().location;
			//if loc is out of map bounds, do vehicles.remove(loc);
		}
	}

	private Point[] generateRoute(Point start) {
		// TODO Auto-generated method stub
		//I'm not sure how to determine number of stops...?
		return rg.generateRoute(4, start, m.landmarks, m.routeGrid);
		//return null;
	}
	
	
	
}
