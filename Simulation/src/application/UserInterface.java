package application;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import application.intersection.*;
import application.map.*;
import application.vehicle.*;

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
	ImageView RoundaboutImageView;
	Image RoundaboutImage;
	boolean foundRAB = false;	// found roundabout; ignore other roundabout values
	ImageView GrassImageView;
	ImageView LandmarkImageView;
	Image LandmarkImage;
	Image GrassImage;
	Image PortalImage;
	Vector<ImageView> mapImageViews = new Vector<ImageView>();
	
	Image RoadClosedImage;
	ImageView RoadClosedImageViewA;
	ImageView RoadClosedImageViewB;
	
	
	Image gns_rewImage;
	Image yns_rewImage;
	Image rns_gewImage;
	Image rns_yewImage;
	Image stopImage;
	Image yieldImage;
	ImageView[] intersectionViews;// = new ImageView[14]; //as many intersectionViews as 4-way intersections
	
	int dimensions = 50;
	int scale = 20;//15;
	Simulation sim;
	AnchorPane root;
	Scene scene;
	
	ObservableList<Node> obsList;
	
	void startSimulation(int stepLen, int delayTime, int runTime){
		Stage stage = new Stage();
		root = new AnchorPane();
		obsList = root.getChildren();

        Scene scene2 = new Scene(root,dimensions*scale,dimensions*scale);
		//Simulation sim = new Simulation(25, 3, 500);	//runtime, delay, steplength
		sim = new Simulation(25, 3, 500);	//runtime, delay, steplength
		sim.addObserver(this);	//make the UI observe the simulation
		
		intersectionViews = new ImageView[sim.m.getIntersections().length];
		
		initializeImages();	//has to happen after the Simulation instantiation, because it depends on intersection locations		
		updateImageViews();
		
		stage.setScene(scene2);
		
		stage.setTitle("Traffic Simulation");
		stage.show();	//this should be the blank map, ready to go (TODO implement initialize)
		
		startAnimation();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        GridPane g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setHgap(10);
		g.setVgap(10);
		g.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		g.add(scenetitle, 0, 0, 2, 1);

		Label runtime = new Label("Runtime:");
		g.add(runtime, 0, 1);

		TextField runtimeText = new TextField();
		g.add(runtimeText, 1, 1);

		Label delay = new Label("Delay:");
		g.add(delay, 0, 2);

		TextField delayText = new TextField();
		g.add(delayText, 1, 2);
		
		Label step = new Label("Step Length:");
		g.add(step, 0, 3);

		TextField stepText = new TextField();
		g.add(stepText, 1, 3);

		Button btn = new Button("Start Simulation");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		g.add(hbBtn, 1, 4);
		
		final Text actiontarget = new Text();
        g.add(actiontarget, 1, 6);
 
		btn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	if (stepText.getText() == null || stepText.getText().trim().isEmpty() || (delayText.getText() == null || delayText.getText().trim().isEmpty() ||(runtimeText.getText() == null || runtimeText.getText().trim().isEmpty()))) {
		    		final Text actiontarget = new Text();
	    			actiontarget.setText("Please enter in an integer for all values.");
	    	        g.add(actiontarget, 1, 6);
		    		
		    	} else {
		    		try {
		    			int stepInt = Integer.parseInt(stepText.getText());
		    			int delayInt = Integer.parseInt(delayText.getText());
		    			int runtimeInt = Integer.parseInt(runtimeText.getText());
		    			startSimulation(stepInt, delayInt, runtimeInt);
		    			primaryStage.close();
		    			
		    		} catch (NumberFormatException err) {
		    			
		    			final Text actiontarget = new Text();
		    			actiontarget.setText("Please enter in an integer for all values.");
		    	        g.add(actiontarget, 1, 6);
		    	
		    		} 		
		    	
		    }
		    }
		});
		btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ENTER)  {
		        	if (stepText.getText() == null || stepText.getText().trim().isEmpty() || (delayText.getText() == null || delayText.getText().trim().isEmpty() ||(runtimeText.getText() == null || runtimeText.getText().trim().isEmpty()))) {
			    		final Text actiontarget = new Text();
		    			actiontarget.setText("Please enter in an integer for all values.");
		    	        g.add(actiontarget, 1, 6);
			    		
			    	} else {
			    		try {
			    			int stepInt = Integer.parseInt(stepText.getText());
			    			int delayInt = Integer.parseInt(delayText.getText());
			    			int runtimeInt = Integer.parseInt(runtimeText.getText());
			    			startSimulation(stepInt, delayInt, runtimeInt);
			    			primaryStage.close();
			    			
			    		} catch (NumberFormatException err) {
			    			
			    			final Text actiontarget = new Text();
			    			actiontarget.setText("Please enter in an integer for all values.");
			    	        g.add(actiontarget, 1, 6);
			    	
			    		} 		
			    	
			    }
		        }
		    }
		});
        
        scene = new Scene(g,dimensions*scale,dimensions*scale);
		primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Welcome");        
        primaryStage.show();
        
        //startSimulation(primaryStage);
	
		//once the sim finishes, close the stage
		//
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
		RoadImage = new Image("images/textures/RoadTextureTile_light.png", scale, scale, true, true);
		GrassImage = new Image("images/textures/Snow.jpg", scale, scale, true, true);
		PortalImage = new Image("images/textures/wreathPortal.png", scale, scale, true, true);
		LandmarkImage = new Image("images/textures/RoadTexture.png", scale, scale, true, true);
		
		//roundabout image and image view
		RoundaboutImage = new Image("images/textures/Roundabout2.png", scale*6, scale*6, true, true);
		RoundaboutImageView = new ImageView(RoundaboutImage);
		
		//road closure image
		RoadClosedImage = new Image("images/textures/christmasTree1.png", scale*2, scale*2, true, true);
		RoadClosedImageViewA = new ImageView(RoadClosedImage);
		RoadClosedImageViewB = new ImageView(RoadClosedImage);
		
		//StopLight images
		gns_rewImage = new Image("images/sprites/Lights/gns_rew.png", scale*2, scale*2, true, true);
		yns_rewImage = new Image("images/sprites/Lights/yns_rew.png", scale*2, scale*2, true, true);
		rns_gewImage = new Image("images/sprites/Lights/rns_gew.png", scale*2, scale*2, true, true);
		rns_yewImage = new Image("images/sprites/Lights/rns_yew.png", scale*2, scale*2, true, true);
		stopImage    = new Image("images/textures/stoplightIcon.png", scale*2, scale*2, true, true);
		yieldImage   = new Image("images/textures/yieldimg.png", scale*2, scale*2, true, true);
		
		//StopLight/TrafficSign ImageViews (also road block images)
		boolean placedFirstRoadBlock = false;
		for (int i = 0; i < intersectionViews.length; i++) {
			Point loc = sim.m.getIntersections()[i].getLocation()[0];
			if (sim.m.getIntersections()[i].getSign() != null) {
				switch (sim.m.getIntersections()[i].getSign().getType()) {
				case STOP:
					intersectionViews[i] = new ImageView(stopImage);
					break;
				case YIELD:
					intersectionViews[i] = new ImageView(yieldImage);
					break;
				default:
					System.out.println("initializeImages: something has gone horribly wrong");
				}

				//continue;	//eventually put stop signs in here... :/
			}
			else if (sim.m.getIntersections()[i].getLight() != null) {
				intersectionViews[i] = new ImageView(gns_rewImage); //start as green N/S, red E/W. this will be updated before it's an issue
			}
			//add image view to root observable list
			root.getChildren().add(intersectionViews[i]);
			//set correct location for the image view (this location will never change)
			intersectionViews[i].setX(loc.x*scale);
			intersectionViews[i].setY(loc.y*scale);
			
			if (sim.m.getIntersections()[i].isBlocked()) {	//if the intersection has been artificially blocked
				//
				Intersection in = sim.m.getIntersections()[i];
				if (!placedFirstRoadBlock) {	//place the RoadClosedImageViewA
					placeRoadClosure(sim.m.getIntersections()[i], RoadClosedImageViewA);
					//add imageview to observable list
					root.getChildren().add(RoadClosedImageViewA);
					placedFirstRoadBlock = true;
				} else {	//place the RoadClosedImageViewB
					placeRoadClosure(sim.m.getIntersections()[i], RoadClosedImageViewB);
					//add imageview to observable list
					root.getChildren().add(RoadClosedImageViewB);
				}
			}
		}
		
		int imageViewCount = 0;
		for (int j = 0; j < dimensions; j++) {
			for (int i = 0; i < dimensions; i++) {
				if (sim.m.getRouteGrid()[j][i] == 1) {			
					mapImageViews.add(new ImageView(GrassImage));
				} else if(sim.m.getRouteGrid()[j][i] == 2 || sim.m.getRouteGrid()[j][i] == 6) {
					mapImageViews.add(new ImageView(RoadImage));	
					if (sim.m.getRouteGrid()[j][i] == 6 && !foundRAB) {
						RoundaboutImageView.setX(j*scale);
						RoundaboutImageView.setY(i*scale);
						root.getChildren().add(RoundaboutImageView);
						foundRAB = true;
					}
				} else if (sim.m.getRouteGrid()[j][i] == 3) { //ONLY TO SEE WHERE GENERATORS ARE
					mapImageViews.add(new ImageView(PortalImage));
				
				} else if (sim.m.getRouteGrid()[j][i] == 4 || sim.m.getRouteGrid()[j][i] == 5 || (sim.m.getRouteGrid()[j][i] % 7) == 0) { //ONLY TO SEE WHERE INTERSECTIONS ARE
					mapImageViews.add(new ImageView(RoadImage));	
				} else if (sim.m.getRouteGrid()[j][i] == 8) {
					mapImageViews.add(new ImageView(LandmarkImage));
//					continue;
				}
				mapImageViews.get(imageViewCount).setX(j*scale);
				mapImageViews.get(imageViewCount).setY(i*scale);
				root.getChildren().add(mapImageViews.get(imageViewCount));
				imageViewCount += 1;
			
			}
		}
		RoundaboutImageView.toFront();
		RoadClosedImageViewA.toFront();
		RoadClosedImageViewB.toFront();
	}
	
	private void placeRoadClosure(Intersection in, ImageView iv) {
		switch (in.getBlocked()) {
		case 'S':
			iv.setX((in.getLocation()[0].x)*scale);
			iv.setY((in.getLocation()[0].y+2)*scale);
			break;
		case 'E':
			iv.setX((in.getLocation()[1].x+2)*scale);
			iv.setY((in.getLocation()[1].y-1)*scale);
			break;
		case 'W':
			iv.setX((in.getLocation()[2].x-3)*scale);
			iv.setY((in.getLocation()[2].y)*scale);
			break;
		case 'N':
			iv.setX((in.getLocation()[3].x-1)*scale);
			iv.setY((in.getLocation()[3].y-3)*scale);
			break;
		default:	//this shouldn't ever happen
			System.out.println("InitializeImages: something has gone horribly wrong");
		}
	}
	
    private void updateImageViews() {
		// TODO Auto-generated method stub
		//Updates the static image views in the scene (stopLights, ...?)
    	for (int i = 0; i < intersectionViews.length; i++) {
    		//since the image views were set up with indices corresponding to the intersection indices, we can assume that is still the case
    		switch(sim.m.getIntersections()[i].getState()) {
    		case GNS_REW:
    			intersectionViews[i].setImage(gns_rewImage);
    			break;
    		case YNS_REW:
    			intersectionViews[i].setImage(yns_rewImage);
    			break;
    		case RNS_GEW:
    			intersectionViews[i].setImage(rns_gewImage);
    			break;
    		case RNS_YEW:
    			intersectionViews[i].setImage(rns_yewImage);
    			break;
    		case NONE:	//no light, nothing to update 
    			break;
    		default:
    			System.out.println("updateImageViews: something has gone horribly wrong");
    		}
    		intersectionViews[i].toFront();
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
		
		//System.out.println("size of vehicles = " + sim.vehicles.size());
		
		for (int i = 0; i < sim.vehicles.size(); i++) {
			VehicleView vv = sim.vehicles.get(i); 
			if (vv.getImageView() == null) {
				Image vImage;
				vv.setImage();
				vImage = vv.getImage();
				vv.setImageView(new ImageView(vImage));
				root.getChildren().add(vv.getImageView());
			}
			Point l = new Point((int) vv.getImageView().getX(), (int) vv.getImageView().getY());
			if (vv.getMoveCount() > 1) {
				if ((l.x < 0 || l.x > 49*scale || l.y < 0 || l.y > 49*scale)){
					root.getChildren().remove(vv.getImageView());
					sim.vehicles.remove(i);
		
					//System.out.println("size of vehicles after remove(i) = " + sim.vehicles.size());
					//vehicles.remove(h);	//vehicle out of map bounds, remove from simulation
				} 
			}
			vv.getImageView().setX(vv.getVehicle().getLocation().x*scale);
			vv.getImageView().setY(vv.getVehicle().getLocation().y*scale);
			vv.setMoveCount(vv.getMoveCount() + 1);
		}
		
	}

}
