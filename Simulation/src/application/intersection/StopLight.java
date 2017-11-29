package application.intersection;

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
	
	public StopLight(LightState initialState, Point[] loc, int gns, int yns, int gew, int yew, int time) {
		state = initialState;
		id = 0;	//not sure how we can auto-increment this
		location = loc;
		greenNS = gns;
		yellowNS = yns;
		greenEW = gew;
		yellowEW = yew;
		timePassed = time;
	}
	
	public LightState getState() {
		return state;
	}
	
	void setState(LightState ls) {
		state = ls;
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
			System.out.println("update(StopLight): something has gone horribly wrong"); 
		}
		//System.out.println("updated stoplight " + id);
	}
}