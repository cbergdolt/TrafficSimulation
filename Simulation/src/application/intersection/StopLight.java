package application.intersection;

import java.awt.Point;

/**
 * Represents a stop light and implements switches between each of its four states
 * 
 * @author Femgineers
 *
 */

public class StopLight {
	LightState state;
	Point[] location = new Point[4];
	int timePassed; //since last light change
	int greenNS; // time spent in state GNS_REW
	int yellowNS; // time spent in state YNS_REW
	int greenEW; // time spent in state RNS_GEW
	int yellowEW; // time spent in state RNS_YEW
	
	/**
	 * Constructs a StopLight object
	 * @param initialState a LightState type that specifies the light's initial state
	 * @param loc a point array with 4 points, identical to the location of the light's "parent" intersection
	 * 		|S|W| >> |0|2|
	 * 		|E|N| >> |1|3|
	 * @param gns an integer specifying the duration of the light's GNS_REW state
	 * @param yns an integer specifying the duration of the light's YNS_REW state
	 * @param gew an integer specifying the duration of the light's RNW_GEW state
	 * @param yew an integer specifying the duration of the light's RNW_YEW state
	 * 		these durations are measured in the clock tick, which is incremented every time the light is updated
	 * 		these durations should also all be positive
	 * @param time: integer specifying the initial time on the clock, so that the light doesn't have to start at the beginning of a cycle
	 * 
	 */
	public StopLight(LightState initialState, Point[] loc, int gns, int yns, int gew, int yew, int time) {
		state = initialState;
		location = loc;
		greenNS = gns;
		yellowNS = yns;
		greenEW = gew;
		yellowEW = yew;
		timePassed = time-1;	//account for inclusive duration ranges, and keep updates consistent
	}
	
	/**
	 * Gets the light's current state
	 * @return a LightState that corresponds to the light's current state
	 */
	public LightState getState() { return state; }
	
	/**
	 * updates the state of the light based on the internal clock tick and the static values of greenNS/EW / yellowNS/EW
	 * each time update is called, the clock is incremented
	 * a check is performed to see if enough clock ticks have passed to change the light state based on each state duration
	 */
	public void update() {
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
	}
}
