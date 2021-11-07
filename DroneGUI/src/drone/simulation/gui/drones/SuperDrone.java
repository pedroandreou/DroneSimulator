package drone.simulation.gui.drones;

import drone.simulation.gui.obstacles.Direction;
import lombok.Getter;

public class SuperDrone extends AbstractDrone {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5481837455050041904L;
	@Getter
	private static int counter = 0;

    /**
     * Create a shielded drone at the position x,y of the size rad with a speed
     * @param x : the x  position
     * @param y : the y position
     * @param rad: the size
     * @param droneSpeed : the speed
     */
    public SuperDrone(double x, double y, double rad, double droneSpeed) {
        super(x, y, rad, droneSpeed, 'b');
        ID = counter;
        counter++;
    }

    public SuperDrone(double x, double y, double rad, double droneSpeed,int ID, Direction direction) {
        super(x, y, rad,droneSpeed,'b');
        this.ID = counter;
        this.direction = direction;
    }



    @Override
    public String getType() {
        return "SUPER Drone";
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void resetSuperCounter(){
        counter = 0;
    }

}