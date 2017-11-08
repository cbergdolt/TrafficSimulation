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
				vg[i] = new VehicleGenerator(new Point(ee[i]), delay, 'S');
			}
			else if (ee[i].y == 49) {
				vg[i] = new VehicleGenerator(new Point(ee[i]), delay, 'N');
			}
			else if (ee[i].x == 0) {
				vg[i] = new VehicleGenerator(new Point(ee[i]), delay, 'E');
			}
			else if (ee[i].x == 49) {
				vg[i] = new VehicleGenerator(new Point(ee[i]), delay, 'W');
			}
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
			Iterator<VehicleView> it = vehicles.iterator();
			while(it.hasNext()) {
				it.next().vehicle.updateVehicle();
			}
			
			//update other things, too, like the stoplights
			m.updateMap();
			
			// generate new vehicles (and so also routes)
			if (vehicles.size()<=0) {
				newVehicles();
			}
			checkBounds();
			
			time++;	//time keeps on ticking
			setChanged();
			notifyObservers();	//notify the observing UserInterface
			System.out.println("completed another simulation iteration; time = " + time);
	//	}
		
	}

	private void newVehicles() {
		//generate new vehicles at each entry/exit point
		/*for (int i = 0; i < vg.length; i++) {
			Vehicle v = vg[i].generateVehicle();
			if (v == null) continue;	//no vehicle generated
			else {
				Point[] route = generateRoute(v.location);	//generate route starting at vehicle's location
				v.route = route;	//assign route to vehicle
				vehicles.add(v);	//add newly generated vehicle to vehicle list
			}
		}*/
		
		Vehicle v = vg[0].generateVehicle();
		if (v != null) {
			Point[] route = generateRoute(v.location);
			v.route = route;
			VehicleView vv = new VehicleView(v);
			vehicles.add(vv);
		}
		
		
	}
	
	private void checkBounds() {
		//clean out vehicles that are out of bounds
		for(Iterator<VehicleView> it = vehicles.iterator(); it.hasNext();){ 
			VehicleView vv = it.next(); 
			Point loc = vv.vehicle.location;
			if (loc.x < 0 || loc.x > 49 || loc.y < 0 || loc.y > 49) { 
				it.remove(); // right call  
			}
		}

		//this is wrong and results in a ConcurrentModificationException when you try to delete from vehicles
		//the above iteration will work properly
		/*for (VehicleView h: vehicles) {
			//check it.Next().location
			Point loc = h.vehicle.location;
			if (loc.x < 0 || loc.x > 49 || loc.y < 0 || loc.y > 49) {
			
				vehicles.remove(h);	//vehicle out of map bounds, remove from simulation
			}
			//if loc is out of map bounds, do vehicles.remove(loc);
		}*/
	}

	private Point[] generateRoute(Point start) {
		// TODO Auto-generated method stub
		//I'm not sure how to determine number of stops...?
		return rg.generateRoute(4, start, m.landmarks, m.routeGrid);
		//return null;
	}
	
	
	
}
