package drone.simulation.gui.drones;

import drone.simulation.gui.obstacles.Direction;
import drone.simulation.gui.obstacles.NormalObstacle;

public class HulkDrone extends AbstractDrone {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5505502009908480459L;
	private boolean hitObstacle = false; // boolean to see if the drone is hitting an obstacle
    private boolean wasThanos = false;// boolean to see if the drone was a shielded before it became a regular
    private static int counter = 0;


    public HulkDrone(double x, double y, double rad, double droneSpeed) {
        super(x, y, rad,droneSpeed,'g');// calling the super constructor with the green character to create a green drone
        ID = counter;
        counter++;
    }

    /**
     * Constructor to use to create a Hulk drone that was Thanos before
     * @param x : x position 
     * @param y : y position 
     * @param rad : the size 
     * @param droneSpeed : the speed of the drone
     * @param ID the id it had as a Thanos drone
     * @param direction : the direction that was going
     */
    public HulkDrone(double x, double y, double rad, double droneSpeed,int ID, Direction direction) {
        super(x, y, rad,droneSpeed,'g');
        this.ID = counter;
        this.direction = direction;
    }

    /**
     * return if the obstacle is hitting a obstacle
     * @return
     */
    public boolean isHitObstacle() {
        return hitObstacle;
    }

    public void setHitObstacle(boolean hitObstacle) {
        this.hitObstacle = hitObstacle;
    }

    /**
     * the type of the drone 
     * @return a string with the type
     */
    @Override
    public String getType() {
        return "HULK DRONE";
    }


    public void setWasShielded(boolean wasShielded) {
        this.wasThanos = wasShielded;
    }

    /**
     * return the string with details of the drone
     * @return
     */
    @Override
    public String toString() {
        if(!wasThanos) {
            return super.toString();
        }
        return super.toString() + "\nThanos Drone is now Hulk Drone";
    }

    /**
     * method to make the drone avoid  obstacles
     * @param obstacle the obstacle that the drone is going to hit
     */
    public void avoid(NormalObstacle obstacle){
        if (direction.ordinal() % 2 !=0){// if the drone is going side ways turn it straight
            direction = direction.add(1);
            return;// return so the method will run again and now it will be straight
        }
        int change = 0;// method to store the change to the direction needed
        if(!hitObstacle) {
            if (direction.ordinal() == 2 || direction.ordinal() == 6) {// the drone will hit from top or bottom
                // based on the way that the drone will hit the obstacle find the optimal solution
                if ((x < obstacle.getX() && y > obstacle.getY()) || (x > obstacle.getX() && y < obstacle.getY())) {
                    change = -2;
                } else change = 2;
            } else {// the drone will hit from left or right
                // based on the way that the drone will hit the obstacle find the optimal solution
                if ((x < obstacle.getX() && y < obstacle.getY()) || (x > obstacle.getX() && y > obstacle.getY())) {
                    change = -2;
                } else change = 2;

            }
        }

        int finalChange = change;
        new Thread(() -> {// create a new thread that will handle the movement of the drone
            setHitObstacle(true);// the drone is currently in the thread so dont call again the methhod
            direction = direction.add(finalChange);// add the change to the direction
            while (hitting(obstacle.getX(),obstacle.getY(), obstacle.getRad())){
                adjustDrone();// adjust the drone will the drone still hits the obstacle
            }
            try {//sleep so the movement seems more natural
                Thread.sleep(50);
            } catch (InterruptedException e) {//catch the exception the if the thread cant sleep
                e.printStackTrace();
            }
            direction = direction.add(-finalChange);//make the direction of the drone the starting one
            setHitObstacle(false);// set that the thread had finish
        }).start();
    }
}
