package drone.simulation.gui.obstacles;

import drone.simulation.gui.MyCanvas;

public class FireObstacle extends NormalObstacle implements ObstacleInterface {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3198656325667808054L;
	private double speed;//fire Obstacle's speed
    private final Direction DIRECTION;//fire Obstacle's direction

    /**
     * Create a fireball at the position x,y of the size rad
     * @param x : x position
     * @param y : y position
     * @param rad : size
     */
    public FireObstacle(double x, double y, double rad) {
        super(x, y, rad);
        speed = 15;
        DIRECTION = Direction.EAST;
        col = 'r';
    }
    
    //draw the fireobstacle with multiple colors (circle inside another circle)
	@Override
    public void drawObject(MyCanvas myCanvas) {
    	 myCanvas.showCircle(x, y, rad , 'b');
    	 myCanvas.showCircle(x, y, rad-2 , 'w');
    	 myCanvas.showCircle(x, y, rad-5 , 'r');
    	 myCanvas.showCircle(x, y, rad-8 , 'w');
    }


	@Override
	public void adjustObstaclePosition() {
            double radAngle = DIRECTION.getAngle() * Math.PI/180;
            x += speed * Math.cos(radAngle);
            y += speed * Math.sin(radAngle);
    }

    /**
     * the type of the obstacle
     * @return a string fire Obstacle
     */
    @Override
    public String getType() {
        return "FIRE OBSTACLE";
    }
}
