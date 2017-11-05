package application;

import java.awt.Point;
import java.util.Observer;

public class Vehicle {
	int maxVelocity;
	int breakDistance;
	int stopDistance;
	int length;
	char direction;
	Point location;
	Point route[];
	
	void removeObserver(Observer o) {
		
	}
	
	void updateObservers() {
		
	}

	void addObserver(Observer o) {
		
	}
	
	void setMaxVelocity(int v) {
		this.maxVelocity = v;
	}
	
	void setBreakDist(int d) {
		this.breakDistance = d;
	}
	
	void setStopDist(int d) {
		this.stopDistance = d;
	}
	
	void setLength(int l) {
		this.length = l;
	}
	
	void updatePos(int grid[][]) {
		
	}
	
	void setRoute() {
		
	}
	
	void stop() {
		
	}
	
	void decelerate() {
		
	}
	
	void changeState() {
		
	}
	
	void turn(String direcion) {
		
	}
	
	void start() {
		
	}
}