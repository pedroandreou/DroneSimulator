package drone.simulation.gui;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class DroneApp extends Application {
	
	 private StatusTimer timer;//animation timer
	    private static DroneArena arena;//arena for the drones
	    private MyCanvas myCanvas; //canvas that the animation will be drawn on
	    private VBox rtPane; //pane to display information
	    
	   
	    /**
		 * show information about map objects in pane on the left
		 */
		public void drawStatus() {
			rtPane.getChildren().clear();					// clear pane
			ArrayList<String> allBs = arena.describeAll(); //create ArrayList type String 
			for (String s : allBs) {
				Label l = new Label(s); 		//turn description into a label
				l.setTextFill(Color.WHITE);		//set text's color of label to white
				rtPane.getChildren().add(l);	// add label	
			}	
		}
		
		 /**
	     * Function to show a message,
	     *
	     * @param TStr title of message block
	     * @param CStr content of message
	     */
	    private void showMessage(String TStr, String CStr) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle(TStr);
	        alert.setHeaderText(null);
	        alert.setContentText(CStr);

	        alert.showAndWait();
	    }
		
		
		/**
	     * Function to set up the menu
	     */
	    MenuBar setMenu() {
	        MenuBar menuBar = new MenuBar();        // create menu

	        Menu mHelp = new Menu("Help");            // have entry for help
	        // then add sub menus for About and Help
	        // add the item and then the action to perform
	        MenuItem mAbout = new MenuItem("About");
	        mAbout.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent actionEvent) {
	                showAbout();                // show the about message
	            }

				private void showAbout() { //show the instructions of the game 
					        showMessage("About", "There are 3 types of drones:\n" +
			                " The Hulk drone- green\n" +
			                " The Thanos drone - black and white\n" +
			                " The Super drone - blue\n" +
			                "\nThere are 2 types of obstacles:" +
			                "\n The normal obstacle:\n" +
			                "turns ThanosDrone into HulkDrone\n"
			                + "destroys the SuperDrone\n"
			                + "\nThe fire obstacle:\n"+
			                "turns the ThanosDrone into HulkDrone\n"
			                +"is getting destroyed by HulkDrone \n"
			                + "destroys the SuperDrone");
					
				}
	        });
	        mHelp.getItems().addAll(mAbout);    // add submenus to Help

	        //now add File menu
	        Menu mFile = new Menu("File");
	        MenuItem mExit = new MenuItem("Exit");
	        mExit.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent t) {
	                System.exit(0);                        // quit program
	            }
	        });
	        
	        mFile.getItems().addAll(mExit);
	        menuBar.getMenus().addAll(mFile, mHelp);    // menu has File and Help

	        return menuBar;                    // return the menu, so can be added
	    }
		
	    
	    public void start(Stage stagePrimary) throws Exception {
	    	
	    	stagePrimary.setTitle("Petros's Drone Simulator");//Title
	    	Group root = new Group();//canvas and arena 
			Canvas canvas = new Canvas(500, 500);//canvas dimensions
			root.getChildren().add(canvas);//adding canvas to the stage
			myCanvas = new MyCanvas(canvas.getGraphicsContext2D(), 500, 500);
			arena = new DroneArena(500, 500); //new arena for the drones
			myCanvas.fillCanvas(500, 500); //fill canvas color and border
			
			timer = new StatusTimer() {// creating a new timer for the application
            public void handle(long l) {//the commands that will run while the timer is running
	                arena.checkDrones();
	                arena.adjustDrones();
	                drawWorld();
	                arena.obstaclehit();
	                arena.fireObstacle();
	                drawStatus();
	            }

	        };
	        
	        //Start button
			Button Start_btn = new Button("Start");
			Start_btn.setOnAction(e -> timer.start());
			Start_btn.setMinSize(200, 30);
			Start_btn.setMaxSize(200, 30);
			
            //Stop button
			Button Stop_btn = new Button("Stop");//Stop button
			Stop_btn.setOnAction(e -> timer.stop());
			Stop_btn.setMinSize(200, 30);
			Stop_btn.setMaxSize(200, 30);
			
			//Thanos Drone
			Button btn_thanos = new Button("Add Thanos drone");//Add super drone button
			btn_thanos.setOnAction(e -> addThanos());
			btn_thanos.setMinSize(200, 30);
			btn_thanos.setMaxSize(200, 30);
			
			//Hulk Drone button
			Button btn_hulk = new Button("Add Hulk drone");//Add normal drone button
			btn_hulk.setOnAction(e -> addHulkDrone());
			btn_hulk.setMinSize(200, 30);
			btn_hulk.setMaxSize(200, 30);
			
			
			//Super Drone button
			Button btn_super = new Button("Add Super drone");//Add weak drone button
			btn_super.setOnAction(e -> addSuperior());
			btn_super.setMinSize(200, 30);
			btn_super.setMaxSize(200, 30);
			
			//Obstacle button
			Button btn_obstacle = new Button("Add obstacle");//Add obstacle button
			btn_obstacle.setOnAction(e -> arena.addObstacle());
			btn_obstacle.setMinSize(200, 30);
			btn_obstacle.setMaxSize(200, 30);
			
			//Save button
			Button bton_save = new Button("Save");
			bton_save.setOnAction(e -> SaveLoadArena.save(this.arena));
			bton_save.setMinSize(200, 30);
			bton_save.setMaxSize(200, 30);
			
			//Load button
			Button btn_load = new Button("Load");
			btn_load.setOnAction(e -> loadArena());
			btn_load.setMinSize(200, 30);
			btn_load.setMaxSize(200, 30);
			
	    
		    //VBox1
			VBox vbox1 = new VBox();//Vertical Box for the buttons
			vbox1.setAlignment(Pos.BOTTOM_CENTER);
			vbox1.setStyle("-fx-border-style: solid;"
	                + "-fx-border-width: 1;"
	                + "-fx-border-color: black");
			vbox1.setPadding(new Insets(0, 0, 220, 0));
			vbox1.getChildren().addAll(Start_btn, Stop_btn, btn_thanos, btn_hulk, btn_super, btn_obstacle, bton_save, btn_load);//Add all buttons to the stage
			vbox1.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));//set background color of vbox to black
		
			
			//BorderPane
	        BorderPane borderPane = new BorderPane();//new border pane (window)
	        borderPane.setStyle("-fx-background-color: 	#000000;");//set border pane to black color
	        
	        rtPane = new VBox();											// set vBox for information panel
		    rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
			rtPane.setStyle("-fx-border-style: solid;"
	                + "-fx-border-width: 1;"
	                + "-fx-border-color: black");
		    
		    rtPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));//set black background color to information panel
		    rtPane.setStyle("-fx-padding: 125;");
			
		    borderPane.setTop(setMenu());                        // create menu, add to top
			borderPane.setCenter(root); //set root(canvas) in the center
	        borderPane.setRight(vbox1); //set box of buttons on the Right
	        borderPane.setLeft(rtPane); //set information panel on the left
	        //create new scene
	        Scene scene1 = new Scene(borderPane, 1200, 750); 
	        stagePrimary.setScene(scene1); // Put the scene in the window
            stagePrimary.show(); //show the scene
	    }
	    
	   
	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    private void drawWorld(){
		       myCanvas.clearCanvas();//clear the canvas
	            myCanvas.fillCanvas(500, 500);//create new canvas
		        arena.drawArena(myCanvas);//draw the arena with the drones
		}
	    
	    public void addThanos(){
	        if(!arena.addThanos()){//check if a Thanos drone can be added and if it can, add it
	            cannotaddAlert();//if it cannot, then display an error
	        }
	    }
	    
        public void addHulkDrone(){
	        if(!arena.addHulk()){ //check if a Hulk drone can be added and if it can, add it
	            cannotaddAlert();//if it cannot, then display an error
	        }
        }
	    
	    public void addSuperior(){
	        if(!arena.addSuper()){//check if a Super drone can be added and if it can, add it
	            cannotaddAlert();//if it cannot, then display an error
	        }
	    }
	    
	    public void loadArena(){
	        DroneArena newArena = SaveLoadArena.load(this.arena);//get the new arena 
	        if (!(newArena == null)) { //if file is not null set the arena
	            arena = newArena;
	            timer.start();
	        }
	    }
	   
	    private void cannotaddAlert(){ //display alert of error
	        Alert alert = new Alert(Alert.AlertType.ERROR);// create a new alert
	        alert.setTitle("Adding the drone failed. Try again!");//set the title
	        alert.setHeaderText(null);//null because we don't want a header
	        alert.setContentText("Another object is already in that position");
	        alert.showAndWait();
	    }
	
}
