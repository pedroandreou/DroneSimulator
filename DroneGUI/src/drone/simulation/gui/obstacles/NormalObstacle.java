package drone.simulation.gui.obstacles;

public class NormalObstacle extends mapObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5441679977194949804L;
	private static int counter = 0;// to count the number of obstacles

    /**
     * Create a regular obstacle at the position x,y and of the size rad
     * @param x the width position
     * @param y the height position
     * @param rad the size
     */
    public NormalObstacle(double x, double y, double rad) {
        super(x, y, rad, 'a');
        ID = counter++;// add the counter for the unique identifier and add one to the count
    }

    @Override
    public String getType() {
        return "Normal Obstacle";
    }
}
