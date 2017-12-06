package application.test;

import java.awt.Point;
import java.util.Arrays;

import org.junit.Test;

import application.intersection.StopLight;
import application.intersection.LightState;

/**
 * exhaustively test the functionality and correctness of the StopLight class
 * @author Femgineers
 *
 */
public class StopLightTest {

	/**
	 * just a Point array, used as the location for the test StopLights
	 */
	Point[] location = {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)};
	
	/**
	 * tests whether the constructor produces a StopLight object
	 */
	@Test
	public void testCreation() {
		StopLight light = new StopLight(LightState.GNS_REW, location, 10, 10, 10, 10, 0);
		assert(light instanceof StopLight);
	}
	
	/**
	 * tests whether the state of the light is assigned properly in the constructor
	 */
	@Test
	public void testStateAssignment() {
		StopLight light = new StopLight(LightState.GNS_REW, location, 10, 10, 10, 10, 0);
		assert(light.getState() == LightState.GNS_REW);
	}
	
	/**
	 * tests whether the update functionality of the light produces the expected sequence of states
	 */
	@Test
	public void testUpdate() {
		LightState[] expectedSequence = {
				LightState.GNS_REW,
				LightState.GNS_REW,
				LightState.GNS_REW,
				LightState.GNS_REW,
				LightState.GNS_REW,
				LightState.YNS_REW,
				LightState.YNS_REW,
				LightState.RNS_GEW,
				LightState.RNS_GEW,
				LightState.RNS_GEW,
				LightState.RNS_GEW,
				LightState.RNS_YEW,
				LightState.RNS_YEW,
				LightState.RNS_YEW
		};
		
		
		int [] dur = {4, 1, 3, 2}; //durations: [gns, yns, gew, yew]
		StopLight light = new StopLight(LightState.GNS_REW, location, dur[0], dur[1], dur[2], dur[3], 0);
		
		assert(Arrays.equals(expectedSequence, updateSequence(light, dur)));
	}
	
	/**
	 * Generates an sequence of light stages for a given light and its stage durations
	 * @param light, a StopLight that will be updated through a single cycle of the given durations
	 * @param durations, integer array containing gns, yns, gew, yew duration values (that should correspond with the durations of light
	 * @return array of LightState values, the sequence of light states traversed given the light and durations
	 */
	private LightState[] updateSequence(StopLight light, int[] durations) {
		int totalSum = 0;
		int sequenceCount = 0;
		for (int d: durations) totalSum += d + 1;	//sum durations, with extra + 1 to account for inclusive ranges on light state changes
		LightState[] sequence = new LightState[totalSum];
		
		for (int j = 0; j < durations.length; j++) {
			for (int i = 0; i <= durations[j]; i++) {
				light.update();
				sequence[sequenceCount] = light.getState();
				System.out.println(sequence[sequenceCount]);
				sequenceCount += 1;
			}	
		}
		return sequence;
	}

}
