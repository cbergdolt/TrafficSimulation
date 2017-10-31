package application;

import java.awt.Point;

public class StopLight {
	LightState state;
	int id;
	Point location;
	int timePassed;
	int greenNS;
	int yellowNS;
	int greenEW;
	int yellowEW;
	
	void updateState() {
		//updates the state of the light based on the time passed since last state change
		//and the static values of greenNS/EW and yellowNS/EW
	}
}
