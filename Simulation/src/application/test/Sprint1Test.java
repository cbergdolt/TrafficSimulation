package application.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Test;

import application.vehicle.*;
import application.intersection.*;

public class Sprint1Test {

	@Test
	public void test1() {
		Vector<Vehicle> vehicles = new Vector<Vehicle>();
		Point p = new Point(0,0);
		VehicleGenerator vg = new VehicleGenerator(0, p, 0, 'S');
		Vehicle v = vg.generateVehicle();
		vehicles.add(v);
		System.out.println(vehicles.size());
		assert(vehicles.size() == 1);
	}
	
	@Test
	public void test2() {
		Point p = new Point(0,0);
		VehicleGenerator vg = new VehicleGenerator(0, p, 0, 'S');
		Vehicle v = vg.generateVehicle();
		v.setMaxVelocity(25);
		assert(v.getMaxVelocity() == 25);
	}
	
	@Test
	public void test3() {
		Point[] p = new Point[1];
		p[0] = new Point(0,0);
		StopLight sl = new StopLight(LightState.GNS_REW, p, 2, 2, 2, 2, 0);
		for (int i = 0; i < 3; i++) {
			sl.update();	
		}
		System.out.println(sl.getState());
		assert(sl.getState() != LightState.GNS_REW);
	}

}
