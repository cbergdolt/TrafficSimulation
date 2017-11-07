package application;


import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
	int scale = 20;
	
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
		BorderPane root = new BorderPane();
		obsList = root.getChildren();
		
		initialize();
		
		Simulation sim = new Simulation(25, 3, 500);	//runtime, delay, steplength
		sim.addObserver(this);	//make the UI observe the simulation
		
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				Rectangle tile = new Rectangle(i*scale, j*scale, scale, scale);
				if (sim.m.routeGrid[i][j] == 1) {
					tile.setFill(Color.ALICEBLUE);
					root.getChildren().add(tile);
				} else if(sim.m.routeGrid[i][j] == 2) {
					tile.setFill(Color.DARKGRAY);
					root.getChildren().add(tile);
				}
			}
		}
		
		Scene scene = new Scene(root,400,400);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Traffic Simulation");
		primaryStage.show();	//this should be the blank map, ready to go (TODO implement initialize)
		
		
		//somehow before this point, and probably even before we display the initial map, 
		//	we need to get user input on the map/sim specs. 
		//	and then perhaps these specs should be passed to the Simulation constructor
		
		sim.run();	//run the simulation!!!
		
		//once the sim finishes, close the stage
		//primaryStage.close();
	}


	private void initialize() {
		// TODO Auto-generated method stub
		//import/create images and image views, add to ObsList
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//update the images/image views based on the status of everything in the Simulation (the Observable o)
		
		
		//then, after everything is updated properly:
		display();
		System.out.println("updated UserInterface from Observable update");
	}



}
