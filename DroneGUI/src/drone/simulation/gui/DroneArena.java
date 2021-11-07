package drone.simulation.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import drone.simulation.gui.drones.AbstractDrone;
import drone.simulation.gui.drones.HulkDrone;
import drone.simulation.gui.drones.SuperDrone;
import drone.simulation.gui.drones.ThanosDrone;
import drone.simulation.gui.obstacles.Direction;
import drone.simulation.gui.obstacles.FireObstacle;
import drone.simulation.gui.obstacles.mapObject;
import lombok.Getter;
import drone.simulation.gui.obstacles.NormalObstacle;

public class DroneArena implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8000633756776898717L;
	@Getter
    double x, y; //X, Y Arena's Dimensions
	@Getter
    public List<mapObject> objects; //list for all the objects on the map

    public DroneArena(double x, double y) {
        this.x = x;
        this.y = y;
       objects = new ArrayList<>(); // array list of all the objects, initially empty
       //adding 3 drones, one of each type for when the DroneArena is being created
        objects.add(new HulkDrone(x/2,y/2,10,2));//add HulkDrone
        objects.add(new SuperDrone(x-20,y-55,10,2));//add SuperDrone
        objects.add(new ThanosDrone(x-100,y-96,15,2));//add ThanosDrone
    }
    
    /**
     * draws all the objects into the canvas
     * @param myCanvas the canvas we want to draw
     */
    public void drawArena(MyCanvas myCanvas){//Drawing all objects into the Drone Arena
        for (mapObject object : objects){ //loop through the list of Objects
            object.drawObject(myCanvas);    //call drawObject method to draw the object
        }
    }

    /**
     * checks all kinds of drones to see if it needs to change their angle
     */
    public void checkDrones(){
        for (mapObject drone : objects){
            if(drone instanceof AbstractDrone) { // only the drones need to change angles
                ((AbstractDrone) drone).checkDrone(this);
            }
        }
    }

    /**
     * adjust all the objects that are moving on the map (drones and fireObstacle)
     */
    public void adjustDrones(){
        for (mapObject drone : objects){
            if(drone instanceof AbstractDrone) {
                ((AbstractDrone) drone).adjustDrone();
            } else if(drone instanceof FireObstacle){
                ((FireObstacle) drone).adjustObstaclePosition();
            }
        }
    }

  
    public Direction CheckDroneAngle(double x, double y, double rad, Direction direction, int notID){
        Direction answer = direction; // to store the answer that will return
        //check if the drone hit a wall
        if (x < rad){// check if it hit the left wall
            answer = direction.goEast(); // send the drone east
        }

        if(x > this.x - rad){// check to see if it hit the right wall
            answer = direction.goWest(); // send the drone west
        }

        if (y < rad ){// check to see if it hit the top wall
            answer = direction.goSouth();// send the drone south
        }

        if(y > this.y - rad){// check to see if it hit the bottom wall
            answer = direction.goNorth();// send the drone north
        }

        // check if the drone hit another drone
        for (mapObject object : objects) {
            if(object instanceof AbstractDrone) {// specify to check only the drones
                // check all the drones except the one with the given ID
                if (object.getID() != notID && ((AbstractDrone) object).hitting(x, y, rad)) {
                    // if it hits another drone send the drones to the opposite direction
                    answer = direction.getOpposite();
                }
            }
        }

        return answer;// return the new direction of the drone
    }

    /**
     * adds a normal drone
     * @return true if the drone can be added in the position and false otherwise
     */
    public boolean addHulk(){
        if(isHere(x / 2, y / 2)){
            return false;
        }
        objects.add(new HulkDrone(x/2,y/2,10,2));
        
        
        return true;
    }

    /**
     * adds a Super drone
     * @return true if the drone can be added in the position and false otherwise
     */
    public boolean addSuper(){
        if(isHere(x / 2, y / 2)){
            return false;
        }
        objects.add(new SuperDrone(x/2,y/2,10,2));
        return true;
    }

    /**
     * adds a sThanos drone
     * @return true if the drone can be added in the position and false otherwise
     */
    public boolean addThanos(){
        if(isHere(x / 2, y / 2)){
            return false;
        }
        objects.add(new ThanosDrone(x/2,y/2,15,2));
        return true;
    }

    /**
     * add a obstacle to the list
     */
    public void addObstacle(){
        //get a random value to place the obstacle
        Random random = new Random();
        // subtracting 100 and adding 50 to be in the range 50 to x-50 to be inside the map
        int x = random.nextInt((int)this.x-100)+50;
        int y =random.nextInt((int) this.y-100)+50;
        objects.add(new NormalObstacle(x,y,10));
    }

    /**
	 * return list of strings to describe objects on the map
	 * @return
	 */
	public ArrayList<String> describeAll() {
		ArrayList<String> ans = new ArrayList<String>();		// set up empty arraylist
		for (mapObject b : objects) ans.add(b.toString());			// add string defining each object
		return ans;												// return string list
	}

    /**
     * checks to see if a drone hit an obstacle and if it hit
     * based on the type of the drone take relevant action
     */
    public void obstaclehit(){
        // split the drones and the obstacles
        List<AbstractDrone> drones = new ArrayList<>();// create lists to separate objects
        List<NormalObstacle> obstacles = new ArrayList<>();
        for (mapObject object : objects){
            if (object instanceof AbstractDrone){
                drones.add((AbstractDrone) object);
            } else {
                obstacles.add((NormalObstacle) object);
            }
        }
        // create variables for the drone and the obstacle that hit each other
        AbstractDrone droneHit = null;
        NormalObstacle obstacleHit = null;
        boolean hit = false;
        for (AbstractDrone drone : drones){
            for (NormalObstacle obstacle: obstacles){
                // check to see if the drone hit an obstacle
                if(drone.hitting(obstacle.getX(),obstacle.getY(),obstacle.getRad())){
                    hit = true;
                    droneHit = drone;
                    obstacleHit = obstacle;
                }
            }
        }
        if (hit){
            if (droneHit instanceof HulkDrone) { //drone that hit was a Hulk drone
                normalHit((HulkDrone) droneHit, obstacleHit);
            } else if (droneHit instanceof SuperDrone){//drone that hit ws a Super drone
                sillyDroneHit((SuperDrone) droneHit, obstacleHit);
            } else if(droneHit instanceof ThanosDrone){//drone that hit was a Thanos drone
               shieldedDroneHit((ThanosDrone) droneHit, obstacleHit);
            } else if(droneHit instanceof HulkDrone){//drone that hit was a Hulk drone
            	onHitTransfromHulk((HulkDrone) droneHit, obstacleHit);
             }
        }
    }

    /**
     * for the normal drone to avoid the obstacles
     */
    private void normalHit(HulkDrone normalDrone, NormalObstacle normalObstacle){
        if (!normalDrone.isHitObstacle()) {
            normalDrone.avoid(normalObstacle);
        }
    }

    /**
     * for the silly drone to hit the obstacles and both the obstacle and the drone to be destroyed
     * @param  the Superdrone that was in the collision
     * @param NormalObstacle the obstacle that was in the collision
     */
    private void sillyDroneHit(SuperDrone superDrone, NormalObstacle normalObstacle){
        // destroy the Super drone
        objects.remove(superDrone);
      }

    /**
     * super drone hits the obstacle, obstacle destroyed and Thanos becomes Hulk
     */
    private void shieldedDroneHit(ThanosDrone shieldedDrone, NormalObstacle normalObstacle){
        objects.remove(normalObstacle); //  destroy the obstacle
        int index = objects.indexOf(shieldedDrone); // get the index of the super drone
        objects.remove(index);// remove that drone from the object list
        // add a new drone to the same position with the same direction and the same id but normal drone instead
        objects.add(index, new HulkDrone(shieldedDrone.getX(),shieldedDrone.getY(),
                shieldedDrone.getRad()-5, shieldedDrone.getDroneSpeed(), shieldedDrone.getID(), shieldedDrone.getDirection()));
        ((HulkDrone) objects.get(index)).setWasShielded(true);// set the wasShielded of the normal drone to true
    }
    
    private void onHitTransfromHulk(HulkDrone hulkDrone, NormalObstacle normalObstacle){
        objects.remove(normalObstacle); //  destroy the obstacle
        int index = objects.indexOf(hulkDrone); // get the index of the super drone
        objects.remove(index);// remove that drone from the object list
        // add a new drone to the same position with the same direction and the same id but normal drone instead
        objects.add(index, new SuperDrone(hulkDrone.getX(),hulkDrone.getY(),
        		hulkDrone.getRad()-5, hulkDrone.getDroneSpeed(), hulkDrone.getID(), hulkDrone.getDirection()));
    }
    
    /**
     * method to control the fireObstacle
     */
    public void fireObstacle(){
        // use a random to generate fireObstacle on the map
        Random r = new Random();
        int num = r.nextInt(80);
        Random randomEntry = new Random();
        if (num == 0){// if the random number is 0 generate a bird
            int yCor = randomEntry.nextInt((int) this.getY());// get a random number for the bird that the height will appear
            objects.add(new FireObstacle(10,yCor,10));
        }
        checkIfFireObstacleReachedEnd();
    }

    /**
     * checking for fireObstacle if end of the map is being reached
     */
    private void checkIfFireObstacleReachedEnd(){
        mapObject FireObstacle = null;// variable to store the fire obstacle if it reached the end
        boolean found = false;// set true if reached end
        for (mapObject object : objects){// loops through all the objects
            if (object instanceof FireObstacle){// if it's a fire obstacle
                if(object.getX() >= this.getX()){// check if the x is greater or equal
                    found = true;// set the found to true because fire obstacle reached the end
                    FireObstacle = object;// store the fire obstacle
                }
            }
        }
        //remove that fire obstacle outside of the loop to avoid exceptions
        if (found){
            objects.remove(FireObstacle);
        }
    }

    /**
     * checks if a drone exist in the specified place
     * @param x the width position that will be checked
     * @param y the height position that will be checked
     * @return true if there is a drone in the position and false otherwise
     */
    private boolean isHere(double x, double y){
        for (mapObject object: objects){// loops through all the objects
            if ((x >= object.getX()-40 && x<=object.getX()+40)  && (y >=object.getY()-40 && y <= object.getY()+40)){
                return true;
            }
        }
        return false;
    }
}