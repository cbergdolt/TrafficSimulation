package application;

import java.util.Observable;
import java.util.Observer;

public class RoadSegment extends Observable{
	int id;
	Intersection intA;
	Intersection intB;
	char direction; //'N' for N/S, 'E' for E/W; I don't know if this is even necessary?
	
	RoadSegment(Intersection a, Intersection b) {
		id = 0; //another auto-increment thing?
		intA = a;
		intB = b;
	}
	
	void registerObserver(Observer o) {
		
	}
	
	void updateObservers() {
		
	}
	
	void removeObserver() {
		
	}

	public void updateRoads() {
		// TODO Auto-generated method stub
		intA.updateIntersection();
		intB.updateIntersection();
		System.out.println("updated road segment " + id);
	}
}
