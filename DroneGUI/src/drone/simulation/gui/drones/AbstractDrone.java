package drone.simulation.gui.drones;

import java.io.Serializable;

import drone.simulation.gui.DroneArena;
import drone.simulation.gui.obstacles.Direction;
import drone.simulation.gui.obstacles.mapObject;
import lombok.Getter;


public abstract class AbstractDrone extends mapObject implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8048323265721420893L;
	static int droneID = 0; //identifier
	@Getter
    protected Direction direction;// drone Direction
	@Getter
    protected double droneSpeed;// speef of Drone

  
    public AbstractDrone(double x, double y, double rad, double droneSpeed, char col) {//Drone Constructor
        super(x, y, rad, col);
        droneID++; //increasing drone identifier
        direction = Direction.randomDirection();// get a random direction for the drone
        this.droneSpeed = droneSpeed;
    }

    /**
     * sets the droneCounter to zero
     */
    protected static void resetDroneCounter() {//Reset method
        AbstractDrone.droneID = 0;
    }

    /**
     * return the string description of the object
     * @return
     */
    @Override
    public String toString() {
        return super.toString() +" going " + direction;
    }

   
    public boolean hitting (double x, double y, double rad){
        return (x-this.x) * (x-this.x) + (y - this.y) * (y-this.y) < (rad + this.rad) * (rad + this.rad)+10;
    }

    /**
     * change the direction of the drone if the drone is hitting a wall or another object
     * @param droneArena the arena that the drones are in
     */
    public void checkDrone(DroneArena droneArena) {//Check Drone 
        direction = droneArena.CheckDroneAngle(x,y,rad,direction,ID);//if the Drone gets hit
        															//new direction will be set in the checkDrone method of DroneArena class and 
        															//new Direction will be returned
    }

    /**
     * move the drone
     */
    public void adjustDrone() {
        double radAngle = direction.getAngle() * Math.PI/180;//degrees to radians conversion
        x += droneSpeed * Math.cos(radAngle);//new X position of Drone
        y += droneSpeed * Math.sin(radAngle);//new Y position of Drone
    }
}
