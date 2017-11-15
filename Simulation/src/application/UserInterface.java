package application;

import java.awt.Point;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/***
 * The UserInterface class is the main running class that controls the entire simulation. 
 * This is the class that must be run to start the simulation. 
 * The stop lights are each assigned images depending on which state it currently is in.
 * The routegrid from the Map class is used to determine what to render in the specified grid spot.
 * The updateVehicles class updates the ImageView of each VehicleView object contained in the vehicles array
 * It also deletes the VehicleView and removes its ImageView from the scene's child nodes if Vehicle is out of bounds
 *   
 * @author Femgineers
 *
 */

public class UserInterface extends Application implements Observer{
	ImageView views[];
	Image images[];
	
	ImageView RoadImageView;
	Image RoadImage;
	ImageView GrassImageView;
	Image GrassImage;
	Image PortalImage;
	Vector<ImageView> mapImageViews = new Vector();
	
	
	Image gns_rewImage;
	Image yns_rewImage;
	Image rns_gewImage;
	Image rns_yewImage;
	ImageView[] stoplightViews = new ImageView[14]; //as many stoplightViews as intersections
	
	int dimensions = 50;
	int scale = 15;
	Simulation sim;
	AnchorPane root;
	
	ObservableList<Node> obsList;
	
	
	void display() {
		//If the entire map is going to be images, I don't think we need this function
		//  we can just change the location of the images when necessary, and javafx magic takes care of displaying them?
		//On the other hand, if we need actual map data to do this display, that might be a problem;
		//	the only communication line between the two classes is observer/observable relationship right now.
		
	}
	
	void startSimulation(){
		//I also don't think this function is necessary
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new AnchorPane();
		obsList = root.getChildren();
		

		
		//somehow before this point, and probably even before we display the initial map, 
		//	we need to get user input on the map/sim specs. 
		//	and then perhaps these specs should be passed to the Simulation constructor
		//Simulation sim = new Simulation(25, 3, 500);	//runtime, delay, steplength
		sim = new Simulation(25, 3, 500);	//runtime, delay, steplength
		sim.addObserver(this);	//make the UI observe the simulation
		
		initializeImages();	//has to happen after the Simulation instantiation, because it depends on intersection locations		
		updateImageViews();
		
		Scene scene = new Scene(root,dimensions*scale,dimensions*scale);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Traffic Simulation");
		primaryStage.show();	//this should be the blank map, ready to go (TODO implement initialize)
		
		startAnimation();
		//sim.run();	//run the simulation!!!
		
		//once the sim finishes, close the stage
		//primaryStage.close();
	}



	private void startAnimation(){
	    	final AnimationTimer timer = new AnimationTimer() {
	    		@Override
	    		public void handle(final long now) {
	    			sim.run();
	    		}
	    	}; 
	    	timer.start();
    }
    
	private void initializeImages() {
		//import/create images and image views, add to ObsList
		//System.out.println(x);
		//road, snow, and portal images
		RoadImage = new Image("RoadTexture.png", scale, scale, true, true);
		GrassImage = new Image("images/textures/Snow.jpg", scale, scale, true, true);
		PortalImage = new Image("images/textures/wreathPortal.png", scale, scale, true, true);
		
		//StopLight images
		gns_rewImage = new Image("images/sprites/Lights/gns_rew.png", scale*2, scale*2, true, true);
		yns_rewImage = new Image("images/sprites/Lights/yns_rew.png", scale*2, scale*2, true, true);
		rns_gewImage = new Image("images/sprites/Lights/rns_gew.png", scale*2, scale*2, true, true);
		rns_yewImage = new Image("images/sprites/Lights/rns_yew.png", scale*2, scale*2, true, true);
		//StopLight ImageViews
		for (int i = 0; i < stoplightViews.length; i++) {
			Point loc = sim.m.intersections[i].location[0];
			stoplightViews[i] = new ImageView(gns_rewImage); //start as green N/S, red E/W. this will be updated before it's an issue
			root.getChildren().add(stoplightViews[i]);
			//set correct location for the image view (this location will never change)
			stoplightViews[i].setX(loc.x*scale);
			stoplightViews[i].setY(loc.y*scale);

		}
		int imageViewCount = 0;
		for (int j = 0; j < dimensions; j++) {
			for (int i = 0; i < dimensions; i++) {
				if (sim.m.routeGrid[j][i] == 1) {			
					mapImageViews.add(new ImageView(GrassImage));
				} else if(sim.m.routeGrid[j][i] == 2) {
					mapImageViews.add(new ImageView(RoadImage));	
				} else if (sim.m.routeGrid[j][i] == 3) { //ONLY TO SEE WHERE GENERATORS ARE
					mapImageViews.add(new ImageView(PortalImage));
				
				} else if (sim.m.routeGrid[j][i] == 4) { //ONLY TO SEE WHERE INTERSECTIONS ARE
					mapImageViews.add(new ImageView(RoadImage));	
				} 
				mapImageViews.get(imageViewCount).setX(j*scale);
				mapImageViews.get(imageViewCount).setY(i*scale);
				root.getChildren().add(mapImageViews.get(imageViewCount));
				imageViewCount += 1;
			
			}
		}
	}
	
    private void updateImageViews() {
		// TODO Auto-generated method stub
		//Updates the static image views in the scene (stopLights, ...?)
    	for (int i = 0; i < stoplightViews.length; i++) {
    		//since the image views were set up with indices corresponding to the intersection indices, we can assume that is still the case
    		switch(sim.m.intersections[i].getState()) {
    		case GNS_REW:
    			stoplightViews[i].setImage(gns_rewImage);
    			break;
    		case YNS_REW:
    			stoplightViews[i].setImage(yns_rewImage);
    			break;
    		case RNS_GEW:
    			stoplightViews[i].setImage(rns_gewImage);
    			break;
    		case RNS_YEW:
    			stoplightViews[i].setImage(rns_yewImage);
    			break;
    		default:
    			System.out.println("something has gone horribly wrong");
    		}
    		stoplightViews[i].toFront();
    	}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//update the images/image views based on the status of everything in the Simulation (the Observable o)
		
		
		//then, after everything is updated properly:
		//display();
		updateVehicleViews();
		updateImageViews();
		//System.out.println("updated UserInterface from Observable update");
	}

	private void updateVehicleViews() {
		//sim.vehicles;
		//Updates the ImageView of each VehicleView object contained in vehicles
		//Also deletes the VehicleView and removes its ImageView from the scene's child nodes if Vehicle is out of map bounds
		
		System.out.println("size of vehicles = " + sim.vehicles.size());
		
		for (int i = 0; i < sim.vehicles.size(); i++) {
			VehicleView vv = sim.vehicles.get(i); 
			if (vv.imageView == null) {
				Image vImage = new Image("images/sprites/Reindeer/MovingLeft/Left1.png", scale, scale, true, true);
				vv.imageView = new ImageView(vImage);
				root.getChildren().add(vv.imageView);
			}
			Point l = new Point((int) vv.imageView.getX(), (int) vv.imageView.getY());
			if (vv.moveCount > 1) {
				if ((l.x < 0 || l.x > 49*scale || l.y < 0 || l.y > 49*scale)){
					root.getChildren().remove(vv.imageView);
					sim.vehicles.remove(i);
		
					//System.out.println("size of vehicles after remove(i) = " + sim.vehicles.size());
					//vehicles.remove(h);	//vehicle out of map bounds, remove from simulation
				} 
			}
			vv.imageView.setX(vv.vehicle.location.x*scale);
			vv.imageView.setY(vv.vehicle.location.y*scale);
			vv.moveCount+=1;
		}
		
	}

}
