package application;

import java.awt.Point;
import java.util.Random;

/**
 * The StopLight class updates the stop light state. 
 * 
 * @author Femgineers
 *
 */

public class StopLight {
	LightState state;
	int id;
	Point[] location = new Point[4];
	int timePassed; //since last light change
	int greenNS; // time spent in state GNS_REW
	int yellowNS; // time spent in state YNS_REW
	int greenEW; // time spent in state RNS_GEW
	int yellowEW; // time spent in state RNS_YEW
	
	StopLight(LightState initialState, Point[] loc, int gns, int yns, int gew, int yew) {
		state = initialState;
		id = 0;	//not sure how we can auto-increment this
		location = loc;
		timePassed = 0;
		greenNS = gns;
		yellowNS = yns;
		greenEW = gew;
		yellowEW = yew;
	}
	
	LightState getState() {
		return state;
	}
	
	void setState(LightState ls) {
		state = ls;
	}
	
	void randInit() {
		//sets time and state values for light randomly
		//intended for re-initialization, but nothing prevents it from being used any other time
		Random rand = new Random();
		
		//determine state
		int newState = rand.nextInt(4); //random number between 0 and 3
		if (newState == 0) state = LightState.GNS_REW;
		else if (newState == 1) state = LightState.RNS_GEW;
		else if (newState == 2) state = LightState.RNS_YEW;
		else if (newState == 3) state = LightState.YNS_REW;
		else System.out.println("something has gone horribly wrong");
		
		//determine timePassed
		timePassed = rand.nextInt(10); //random number between 0 and 9
		
	}
	
	public void update() {
		//updates the state of the light based on the time passed since last state change
		//and the static values of greenNS/EW / yellowNS/EW
		timePassed++; //increment time
		
		switch(state) {
		case GNS_REW:
			if (timePassed > greenNS) {
				state = LightState.YNS_REW; // turn NS light yellow
				timePassed = 0;
			}
			break;
		case YNS_REW:
			if (timePassed > yellowNS) {
				state = LightState.RNS_GEW; // turn NS light red and EW light green
				timePassed = 0;
			}
			break;
		case RNS_GEW:
			if (timePassed > greenEW) {
				state = LightState.RNS_YEW; // turn EW light yellow
				timePassed = 0;
			}
			break;
		case RNS_YEW:
			if (timePassed > yellowEW) {
				state = LightState.GNS_REW; // turn NS light green and EW light red
				timePassed = 0;
			}
			break;
		default:
			//if the state is something other than these four options, we have a serious issue...
			System.out.println("something has gone horribly wrong"); 
		}
		//System.out.println("updated stoplight " + id);
	}
}
