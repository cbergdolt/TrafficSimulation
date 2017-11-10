package application;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Test;

public class Sprint1Test {

	@Test
	public void test1() {
		Vector<Vehicle> vehicles = new Vector<Vehicle>();
		VehicleGenerator vg = new VehicleGenerator(null, 0, 'S');
		Vehicle v = vg.generateVehicle();
		vehicles.add(v);
		System.out.println(vehicles.size());
		assert(vehicles.size() == 1);
	}
	
	//@Test
	/*public void test2() {
		Vector<Vehicle> vehicles = new Vector<Vehicle>();
		VehicleGenerator vg = new VehicleGenerator(null, 0, 'S');
		Vehicle v = vg.generateVehicle();
		vehicles.add(v);
		assert(vehicles.size() == 1);
	}*/

}
