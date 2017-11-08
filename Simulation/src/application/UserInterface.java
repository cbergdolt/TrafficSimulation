package application;


import java.awt.Point;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
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

public class UserInterface extends Application implements Observer{
	ImageView views[];
	Image images[];
	ImageView RoadImageView;
	Image RoadImage;
	ImageView GrassImageView;
	Image GrassImage;
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
		
		//initialize();
		
		//somehow before this point, and probably even before we display the initial map, 
		//	we need to get user input on the map/sim specs. 
		//	and then perhaps these specs should be passed to the Simulation constructor
		//Simulation sim = new Simulation(25, 3, 500);	//runtime, delay, steplength
		sim = new Simulation(25, 3, 500);	//runtime, delay, steplength
		sim.addObserver(this);	//make the UI observe the simulation
		
		for (int j = 0; j < dimensions; j++) {
			for (int i = 0; i < dimensions; i++) {
				Rectangle tile = new Rectangle(j*scale, i*scale, scale, scale);
				if (sim.m.routeGrid[j][i] == 1) {
					tile.setFill(Color.ALICEBLUE);
				} else if(sim.m.routeGrid[j][i] == 2) {
					tile.setFill(Color.DARKGRAY);
				} else if (sim.m.routeGrid[j][i] == 3) { //ONLY TO SEE WHERE GENERATORS ARE
					tile.setFill(Color.ORANGE);	
				} else if (sim.m.routeGrid[j][i] == 4) { //ONLY TO SEE WHERE INTERSECTIONS ARE
					tile.setFill(Color.RED);	
				} 
				root.getChildren().add(tile);
			}
		}
		
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
    
	/*private void initialize() {
		// TODO Auto-generated method stub
		//import/create images and image views, add to ObsList
	}*/

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//update the images/image views based on the status of everything in the Simulation (the Observable o)
		
		
		//then, after everything is updated properly:
		display();
		updateImageViews();
		System.out.println("updated UserInterface from Observable update");
	}

	private void updateImageViews() {
		//sim.vehicles;
		for (VehicleView h: sim.vehicles) {
			if (h.imageView == null) {
				Image vImage = new Image("images/sprites/Reindeer/MovingLeft/Left1.png", scale, scale, true, true);
				h.imageView = new ImageView(vImage);
				root.getChildren().add(h.imageView);
			}
			Point loc = new Point((int) h.imageView.getX(), (int) h.imageView.getY());
			System.out.println(loc.y);
			if ((loc.x < 0 || loc.x > 49*scale || loc.y < 0 || loc.y > 49*scale)){
				root.getChildren().remove(h.imageView);
				//vehicles.remove(h);	//vehicle out of map bounds, remove from simulation
			} else {
				h.imageView.setX(h.vehicle.location.x*scale);
				h.imageView.setY(h.vehicle.location.y*scale);
			}
			
		}
	}

}
