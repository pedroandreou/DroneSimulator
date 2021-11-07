package DroneSimulator;

import java.util.ArrayList;
import java.util.Random;

import DroneSimulator.Drone.Direction;
import lombok.Getter;

public class DroneArena {
	Random rand = new Random(); // create instance of Random class
	ConsoleCanvas canvas;
	private int newDirCoX; // newCordinateofX after canMovehere was true
	private int newDirCoY; // newCordinateofY after canMovehere was true
	private ArrayList<Drone> TemporaryManyDrones; // create an ArrayList
	public ArrayList<Drone> manyDrones;

	public int droneID;
	
	@Getter
	public int DroneArenaX; // X coordinate of DroneArena
	@Getter
	public int DroneArenaY; // Y coordinate of DroneArena

	public DroneArena(ConsoleCanvas canvas) {
		this.canvas = canvas;
		this.DroneArenaX = ConsoleCanvas.consoleCanvasX;
		this.DroneArenaY = ConsoleCanvas.consoleCanvasY;
		this.manyDrones = new ArrayList<Drone>();
	}

	public void addDrone() { // add drone to random location
		int newX = DroneArenaX - 1; // X coordinate of arena -1
		int newY = DroneArenaY - 1; // Y coordinate of arena -1
		int a = (int) (Math.random() * (newX - 1 + 1) + 1); // random x coordinate of new drone can get a value between
															// 1 and (X coordinate of arena)-1
		int b = (int) (Math.random() * (newY - 1 + 1) + 1); // random y coordinate of new drone can get a value between
															// 1 and (Y coordinate of arena)-1
		
		Drone drone = new Drone(a, b, Direction.getRandomDirection(), this); // pass new random coordinates of drone,
		// random direction and the whole Drone
		// Arena
		
		if (canMoveHere(a, b) == true) {
			drone.idboolean(true); // increase the id of the drone
			manyDrones.add(drone); // add the new drone in the ArrayList of manyDrones
			canvas.showIt(a, b, 'D');// add the new drone to the canvas array
		} else {
			//drone.idboolean(false); // do not increase the id as the Drone can not move here
			addDrone(); // call the addDrone() method again so the random generator can give other new
						// random coordinates for the drone
		}
	}

	/**
	 * search ArrayList of drones to see if there is a drone at x,y
	 * 
	 * @param x
	 * @param y
	 * @return null if no Drone there, otherwise return drone
	 */

	public Drone getDroneAt(int x, int y) {// check if a drone exists already to those new coordinates
		for (int i = 0; i < manyDrones.size(); i++) { // go through the whole ArrayList and check if with these new
														// coordinates, another drone exists
			if (manyDrones.get(i).getX() == x && manyDrones.get(i).getY() == y) {
				return manyDrones.get(i);
			}
		}
		return null;// return null when a drone does not exist
	}

	/**
	 * show all the drones in the interface
	 * 
	 * @param c the canvas in which drones are shown
	 */
	public void showDrones() {// it is called when 'I' button is pressed and it shows all the info
		// << loop through all the Drones calling the displayDrone method >>
		String droneArena = "Drone Arena size is " + DroneArenaX + " by " + DroneArenaY + "\n";
		for (Drone y : this.manyDrones)
			droneArena += y.toString() + "\n";
		System.out.println(droneArena);

	}

	public boolean canMoveHere(int x, int y) {// the coordinates of a drone are checked and if meets all the criteria,
												// can move here (true)
		if (getDroneAt(x, y) != null || x >= DroneArenaX || y >= DroneArenaY || x < 0 || y < 0 || canvas.canvas[x][y] == '#') {
			return false;
		} else {
			return true;
		}
	}

	public void newDirectionCoordinates(int x, int y) {
		this.newDirCoX = x; // new updated coordinate of X after has successfully changed direction in the
							// tryToMove() method
		this.newDirCoY = y; // new updated coordinate of Y after has successfully changed direction in the
							// tryToMove() method
	}

	@SuppressWarnings("unchecked")//this warning is for casting the this.manyDrones to this.TemporaryManyDrones
	public void MoveAllDrones() {// ConsoleCanvas canvas
		// this.TemporaryManyDrones = new ArrayList<Drone>(); //create new Temporary
		// ArraList
		this.TemporaryManyDrones = (ArrayList<Drone>) this.manyDrones.clone(); // copy the manyDrones ArrayList to the
		// new Temporary ArrayList
		this.manyDrones.clear(); // clear the manyDrones ArrayList so the updated drones can be added to it in
									// the for loop
		this.canvas.fillingtheConsoleCanvas(); // fill the canvas array with the hashes and blank spaces like it is
												// empty without any drone in the arena
		for (Drone d : this.TemporaryManyDrones) { // go through all the old drones which have been copied from
													// manyDrones to the new ArraList
			d.tryToMove(); // call tryToMove method so all drones can change direction
			this.canvas.showIt(this.newDirCoX, this.newDirCoY, 'D');// add the new drone to the canvas array
			manyDrones.add(d); // add the drone with the new direction to the manyDrones ArrayList which has
								// been cleared above in order to add the new updated drones
		}
		this.TemporaryManyDrones.clear(); // clear the new ArrayList so when the function will be called again, will be
											// empty for being cloned/copied with the updated drones
	}

}
