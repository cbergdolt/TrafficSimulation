package application.vehicle;

import java.awt.Point;

import javafx.scene.image.Image;

public class ReindeerRider extends Vehicle {

	ReindeerRider(char dir, Point loc) {
		super(dir, loc);
		// TODO Auto-generated constructor stub
		maxVelocity = 1;
		breakDistance = 3;
		stopDistance = 1;
		length = 1;
		direction = dir;
		location = loc;
		curVelocity = maxVelocity;
		
		observedIntersection = null;
		startRequested = false;
		
		leftImage = new Image("images/sprites/WhiteReindeer/Left1.png", scale, scale, true, true);
		rightImage = new Image("images/sprites/WhiteReindeer/Right1.png", scale, scale, true, true);
		upImage = new Image("images/sprites/WhiteReindeer/Back1.png", scale, scale, true, true);
		downImage = new Image("images/sprites/WhiteReindeer/Forward1.png", scale, scale, true, true);
	}

}
