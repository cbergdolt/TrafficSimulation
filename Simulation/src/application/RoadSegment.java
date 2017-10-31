package application;

import java.util.Observable;
import java.util.Observer;

public class RoadSegment extends Observable{
	int id;
	Intersection intA;
	Intersection intB;
	char direction; //'N' for N/S, 'E' for E/W; I don't know if this is even necessary?
	
	void registerObserver(Observer o) {
		
	}
	
	void updateObservers() {
		
	}
	
	void removeObserver() {
		
	}
}
