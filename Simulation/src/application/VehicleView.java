package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VehicleView {
	Vehicle vehicle;
	ImageView imageView;
	int moveCount;
	boolean inSimulation;
	
	VehicleView(Vehicle veh) {
		//int scale = 15; //hard coded to match the scale in UserInterface
		vehicle = veh;
		imageView = null;	
		inSimulation = true;
		moveCount = 0;
		//Image im = new Image("image.png", scale, scale, true, true);
		//imageView = new ImageView(im);
	}
}
