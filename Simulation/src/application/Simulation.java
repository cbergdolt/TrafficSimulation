package application;

import java.util.Observable;

public class Simulation extends Observable{
	int runTime;
	int delay;
	int stepLength;
	Map m;
	VehicleGenerator vg;
	Vehicle[] vehicles;
	RouteGenerator rg;
	
	Simulation() {
		runTime = 25;
		stepLength = 500; //500 milliseconds (half a second)
		//other pesky initialization things
	}
	
	void run() {
		int time = 0;
		while(time <= runTime) { //I honestly have no idea. But I think there should be a loop controlling the marjority of the simulation 
			//sleep
			try {
				Thread.sleep(stepLength);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("hey! let me sleep!"); //I'm not sure what we actually want to do with an interrupted sleep, but here's the backbone anyway
				e.printStackTrace();
			}
			
			//for each vehicle, update the vehicle, based on the current time
			//update other things, too, like the stoplights
			// generate new vehicles (and so also routes)
			time++;
			notifyObservers();
		}
		
	}
	
	
	
}
