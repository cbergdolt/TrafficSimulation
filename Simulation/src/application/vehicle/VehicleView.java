package application.vehicle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Enables isolated access to each vehicle object and its corresponding ImageView
 * vehicle is the contained instance of the Vehicle class
 * imageView is the contained instance of ImageView
 * moveCount keeps track of the number of moves the vehicle has taken (though I'm not sure it's being used)
 * inSimulation is a toggle boolean that is flipped off whenever vehicle exits simulation bounds
 * 
 * @author Carolyn
 *
 */

public class VehicleView {
	Vehicle vehicle;
	ImageView imageView;
	int moveCount;
	boolean inSimulation;
	
	public VehicleView(Vehicle veh) {
		//int scale = 15; //hard coded to match the scale in UserInterface
		vehicle = veh;
		imageView = null;	
		inSimulation = true;
		moveCount = 0;
		//Image im = new Image("image.png", scale, scale, true, true);
		//imageView = new ImageView(im);
	}
	
	public Vehicle getVehicle() { return vehicle; }
	public void setVehicle(Vehicle v) { vehicle = v; }
	
	public int getMoveCount() { return moveCount; }
	public void setMoveCount(int m) { moveCount = m; } 
	
	public boolean getInSimulation() { return inSimulation; }
	public void setInSimulation(boolean val) { inSimulation = val; }
	
	public ImageView getImageView() { return imageView; }
	public void setImageView(ImageView iv) { imageView = iv; } 
}
