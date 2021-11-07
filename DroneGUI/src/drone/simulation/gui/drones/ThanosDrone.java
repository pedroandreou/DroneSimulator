package drone.simulation.gui.drones;

import drone.simulation.gui.MyCanvas;
import lombok.Getter;

public class ThanosDrone extends AbstractDrone{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1326229217016113921L;
	@Getter
	private static int counter = 0;// 

    /**
     * Create a shielded drone at the position x,y of the size rad with a speed
     * @param x : the x position
     * @param y : the y position
     * @param rad :  the size
     * @param droneSpeed : the speed
     */
    public ThanosDrone(double x, double y, double rad, double droneSpeed) {
        super(x, y, rad,droneSpeed,'w');
        ID = counter;
        counter++;
    }

    @Override
    public String getType() {
        return "THANOS DRONE";
    }
 
    public void drawObject(MyCanvas myCanvas){
        myCanvas.showCircle(x, y, rad, 's');//the outsize circle
        myCanvas.showCircle(x, y, rad-5 , col);// the inside circle
    }

    public static void resetShieldedCounter(){
        counter = 0;
    }
}
