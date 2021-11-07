/**
 * 
 */
package DroneSimulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import DroneSimulator.Drone.Direction;



/**
 * Simple program to show arena with multiple drones
 * @author
 *
 */
public class DroneInterface {
	
	private Scanner s;							// scanner used for input from user
    private DroneArena myArena;					// arena in which drones are shown
    private ConsoleCanvas c;
   
    /**
    	 * constructor for DroneInterface
    	 * sets up scanner used for input and the arena
    	 * then has main loop allowing user to enter commands
     */
    public DroneInterface(ConsoleCanvas canvas) {
    	
    	 s = new Scanner(System.in);			// set up scanner for user input
    	 myArena = new DroneArena(canvas);		// create arena of size the same with canvas
    	 c = canvas;
 		
    	
        char ch = ' ';
        do {
        	System.out.print("Enter (N) for a new Arena, (A)dd drone, get (I)nformation, get (D)isplay, (M)ove drones once, (S)Move Drones 10 times, (F)ile Options  or e(X)it > ");
        	ch = s.next().charAt(0);
        	s.nextLine();
        	
        	switch (ch) {
    				case 'A' :
    				case 'a' :
        							myArena.addDrone();	// add a new drone to arena
        							break;
        		case 'I' :
        		case 'i' :
        							myArena.showDrones(); // show size of arena and information about the new drones that have been added
        							break;
        							
        		case 'x' : 			ch = 'X';				// when X detected program ends
        							break;
        		case 'D' : 
        		case 'd' : 			doDisplay(canvas); // display the canvas array
        							break;
        		case 'M':
        		case 'm': 			myArena.MoveAllDrones(); // move all drones once
        							doDisplay(canvas);// display the canvas array
        							break;
        		case 'S':
        		case 's': 			for (int i = 0; i < 10; i++) { // move all drones and redraw the arena 10 times
										System.out.println("-----------------------------------");
										myArena.MoveAllDrones(); // call MoveAllDrones() for moving drones once each time
										doDisplay(canvas); //display the canvas
										myArena.showDrones(); // show size of arena and information about the new updated drones
											try {
												TimeUnit.MILLISECONDS.sleep(200); // Wait for 200ms
											} catch (InterruptedException e) {
												System.err.format("IOException: %s%n", e);
											}
									}
        							break;
        		case 'n':
        		case 'N':
        					Main.main(null); // call Main() method for creating new arena and start from the beginning all over again
        					break;
        		case 'F':
    			case 'f':
    						fileOptions(canvas); // file options menu (Saving/Loading/Exit)
        	}
    		} while (ch != 'X');						// test if end
        
       s.close();									// close scanner
    }
    
    /**
	 * Sets up menu for the file options.
	 * 
	 * The user is given the option to save the current arena/drone build in a file
	 * with their preferred name or load a pre-existing one.
	 * 
	 * Creates a scanner to get users input and acts accordingly
	 * 
	 * Note that the user must choose the file he wants to save into or load from.
	 * 
	 * Load/Save options incorporate error handling in case of something song wrong
	 * in loading
	 */
	void fileOptions(ConsoleCanvas canvas) {
		s = new Scanner(System.in); // scanner for user input
		char ch = ' ';
		System.out.print("Enter " + "(S)ave File, " + "(L)oad File, " + "e(X)it");
		ch = s.next().charAt(0); //get the first character of the input that the user will give
		s.nextLine();
		switch (ch) {
		case 'S':
		case 's':
				try {
					saveFile();
				} catch (Exception e) {
					System.err.print("Save could not be achieved ");// error message
				}
				break;
		case 'L':
		case 'l':
				try {
					loadFile(canvas);
				} catch (Exception a) {
					//System.err.print("Load could not be achieved "); // error message
				}
				break;
		case 'x':
				ch = 'X';
				break;
		default:
				break;
		}
	}
	
	/*
	 * Allows the user to chose a file name and directory to save a file
	 * 
	 * The method throws exception so the when it is eventually called, error
	 * handling can take place
	 *
	 * No filter added to give user freedom of choosing whatever file they want
	 */
	void saveFile() throws IOException {

		// JFileChooser properties
		JFileChooser chooser = new JFileChooser("C:\\Users\\user\\Desktop\\Java Week 5\\Files");// my directory where the dialogue will show me
																							//to save it by default
		chooser.setDialogTitle("Save arena to: "); // window title which is on the top left of the dialogue box
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // the dialogue box will show only files and directories to save
																			// my arena in

		/**
		 * The following method is a filter for the file chooser. 
		 * It is used for displaying directories and files with the .arena extensions that are created from saving an arena
		 */
		chooser.setFileFilter(new FileFilter() {// it restricts the listing in JFileChooser to only those files/directories
												//that end with ".arena"
			public String getDescription() {
				return "Arena Files (*.arena)";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {//if the file is a Directory
					return true;
				} else {
					String fileName = f.getName().toLowerCase();
					return fileName.endsWith(".arena");
				}
			}
		});

		// this where the windows opens
		s = new Scanner(System.in); // scanner
		String userInput = " "; // to be the users file name
		System.out.println("Enter the name of the file");
		userInput = s.next(); // gets all the file name before a space is given
		chooser.setApproveButtonText("Save");// changes approve button
		chooser.setApproveButtonToolTipText("Save location");
		int returnVal = chooser.showOpenDialog(null); // null for now, stores if user clicks open or cancel
		if (returnVal == JFileChooser.APPROVE_OPTION) { // if a file is selected
			// gets file selected by user
			File userFile = new File(chooser.getSelectedFile() + "\\" + userInput + ".arena");
			System.out.println("Arena saved!\n" + "File Name: " + userInput + ".arena" + "\nDirectory: "+ userFile.getAbsolutePath()); // prints file chosen and directory
			// Saving Process
			FileWriter fileWriter = new FileWriter(userFile); // creates a new file writer
			BufferedWriter writer = new BufferedWriter(fileWriter); // adds the write to buffer
			// First saves the arena X dimension on first line
			writer.write(Integer.toString(myArena.DroneArenaX));
			writer.write(" ");//space
			// saves the arena Y dimension
			writer.write(Integer.toString(myArena.DroneArenaY));
			writer.newLine(); // change line
			// Each line store one drone in the form of X , Y , Direction
			for (int i=0;i<myArena.manyDrones.size();i++) {
				writer.write(Integer.toString(myArena.manyDrones.get(i).getX()));//X coordinate of Drone
				writer.write(" ");//space
				writer.write(Integer.toString(myArena.manyDrones.get(i).getY()));//Y coordinate of Drone
				writer.write(" ");//space
				writer.write(Integer.toString(Drone.droneDirection.ordinal()));//Drone Direction
				writer.newLine();
			}
			writer.close();
		}
	}

	/*
	 * Allows the user to pick a file to load a pre existing arena build using
	 * JFileChooser and buffer reader to read from text files
	 * 
	 * The method throws exception so the when it is eventually called, error
	 * handling can take place
	 * 
	 * No filter added to give user freedom of choosing whatever file they want
	 */
	void loadFile(ConsoleCanvas canvas) throws IOException {

		// JFileChooser properties
		JFileChooser chooser = new JFileChooser("C:\\Users\\user\\Desktop\\Java Week 5\\Files");// directory
		chooser.setDialogTitle("Load arena from: ");// Window title
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);// What files are shown
		// This will eventually store the file contents
		String fileContents = " ";
		/**
		 * The following is a filter for the file chooser.
		 * It is used for displaying directories and files with the .arena extensions that are created from saving an arena
		 */
		chooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "Arena Files (*.arena)";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String fileName = f.getName().toLowerCase();
					return fileName.endsWith(".arena");
				}
			}
		});
		// This where the windows opens
		int returnVal = chooser.showOpenDialog(null); // stores if user clicks open/cancel
		if (returnVal == JFileChooser.APPROVE_OPTION) {// if user presses open
			File userFile = chooser.getSelectedFile(); // gets the file selected by user
			if (chooser.getSelectedFile().isFile()) { // if the file exists
				System.out.println("Yes!!! Arena Loaded!\n" + "File Name: " + userFile.getName() + "\nDirectory: "
						+ userFile.getAbsolutePath());// prints file chosen and directory

					myArena.manyDrones.clear();//clear it 
					canvas.fillingtheConsoleCanvas();
				// Loading process
				FileReader fileReader = new FileReader(userFile); // creates a new file reader
				BufferedReader reader = new BufferedReader(fileReader); //adds the reader to buffer

				fileContents = reader.readLine(); //reads the first line
				String[] loadSize = fileContents.split(" "); // it splits the line where there is a space to assign it to variables
				int newConsoleCanvasX = Integer.parseInt(loadSize[0]); // First integer is arena X dimension
				int newConsoleCanvasY = Integer.parseInt(loadSize[1]); // Second integer is arena Y dimension
				ConsoleCanvas.consoleCanvasX = newConsoleCanvasX;	//passing the X loaded coordinate to canvas
				ConsoleCanvas.consoleCanvasY = newConsoleCanvasY; //passing the Y loaded coordinate to canvas
				//myArena = new DroneArena(c); // creates a new arena with the gathered dimensions
				boolean id = true;
				while (fileContents != null) { // while not in the end of the file
					fileContents = reader.readLine();
					String[] numbers = fileContents.split(" ");
					int x = Integer.parseInt(numbers[0]); // First integer is drone X coordinate
					int y = Integer.parseInt(numbers[1]); // Second integer is drone Y coordinate
					int ordinal = Integer.parseInt(numbers[2]); // Third integer is drone facing Direction
					// creates drone and adds it do list
					Drone drone = new Drone(x, y, Direction.values()[ordinal], myArena);
					if(id) { // it is only true once in order to to send false to idboolean() method once and make the drone equal to 0
						drone.idboolean(false);
						id = false;
					}
					else {
						drone.idboolean(true); // increase the id of the drone
					}
					myArena.manyDrones.add(drone);//add drone to the manyDrones ArrayList
					c.showIt(x, y, 'D');// add the new drone to the canvas array
				}
				reader.close();
			}
		}

	}
	
	
    
    /**
   /**
    * Display the drone arena on the console
    * 
    */
   void doDisplay( ConsoleCanvas canvas) { // display the canvas array
	   for(int row=0; row<canvas.canvas.length; row++) {
  			for(int col=0; col<canvas.canvas[row].length; col++) {
  				System.out.print(canvas.canvas[row][col]);
  			}
  			System.out.println(" ");
		}	 
   }
   
 }
