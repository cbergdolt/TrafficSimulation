package application;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Test;

public class Sprint1Test {

	@Test
	public void test1() {
		Vector<Vehicle> vehicles = new Vector<Vehicle>();
		Point p = new Point(0,0);
		VehicleGenerator vg = new VehicleGenerator(p, 0, 'S');
		Vehicle v = vg.generateVehicle();
		vehicles.add(v);
		System.out.println(vehicles.size());
		assert(vehicles.size() == 1);
	}
	
	@Test
	public void test2() {
		Vector<Vehicle> vehicles = new Vector<Vehicle>();
		Point p = new Point(0,0);
		VehicleGenerator vg = new VehicleGenerator(p, 0, 'S');
		Vehicle v = vg.generateVehicle();
		vehicles.add(v);
		vehicles.remove(v);
		assert(vehicles.size() == 0);
	}

}
