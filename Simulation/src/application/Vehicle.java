package application;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class Vehicle extends Observable implements Observer{
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

	public void addObserver(Observer o) {
		
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}