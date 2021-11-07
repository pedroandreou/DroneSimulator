package DroneSimulator;


import java.util.Random;

import lombok.Getter;

public class Drone {
		
	@Getter
	private int x; //drone x getter
	@Getter
	private int y; //drone y getter
	@Getter
	public static Direction droneDirection; //drone direction getter
	
	private int id;
	private DroneArena myArena;
	
	public Drone(int xy, int yx, Direction direction, DroneArena droneArena) {
		x = xy; 
		y = yx;
		droneDirection = direction;
		this.myArena = droneArena;
		this.id = myArena.droneID;
	}
	
	public void idboolean(boolean newid) {
		if(newid == true) {
			myArena.droneID++; 
			this.id = myArena.droneID;
		} else { // if it false make the id equal to 0. the only case to happen is if a file is being loaded
			 myArena.droneID = 0;
			 this.id = myArena.droneID;
		}
	}
	
	/*public  void resetid() {
		droneID=0;
	}*/
	
	public String toString() {
		return "Drone number " + (this.id) + " in coordinates " + this.x + " " + this.y + " with direction to " + Drone.droneDirection;
	}
	 
	public String toString(int x, int y) {
		return "Drone number " + (this.id) + " in coordinates " + this.x + " " + this.y + " with direction to " + Drone.droneDirection;
	}
	
	/**
     * Direction enum.
     */
    public enum Direction {
        North,
        East,
        South,
        West;

        /**
         * Pick a random value of the Direction enum.
         * @return a random Direction.
         */
        public static Direction getRandomDirection() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
        public static Direction nextDirection() {
    		int size = Direction.values().length - 1;
    		System.out.println(size);
    		if (droneDirection.ordinal() == size)
    			return values()[0];
    		else
    			return values()[droneDirection.ordinal() + 1];
    	}
    }
    
    public void tryToMove() {
        switch (Drone.droneDirection) {
        case North:
        	int northXPos = this.x - 1;
            if (myArena.canMoveHere(northXPos, this.y)) { // checks move eligibility
                this.x = northXPos; // drone moves
                myArena.newDirectionCoordinates(this.x, this.y);
            }
            else {
            	Drone.droneDirection = Direction.nextDirection(); //droneDirection = droneDirection.nextDirection(); // changes direction
            	tryToMove();
            }
            break;
        case East:
        	int eastYPos = this.y + 1;
            if (myArena.canMoveHere(this.x, eastYPos)) {
                this.y = eastYPos;
                myArena.newDirectionCoordinates(this.x, this.y);
            }
            else {
            	Drone.droneDirection = Direction.nextDirection(); //droneDirection = droneDirection.nextDirection();
            	tryToMove();
            }
            break;
        case South:
        	int southXPos = this.x + 1;
            if (myArena.canMoveHere(southXPos, this.y)) {
                this.x = southXPos;
                myArena.newDirectionCoordinates(this.x, this.y);
            }
            else {
            	Drone.droneDirection = Direction.nextDirection(); //droneDirection = droneDirection.nextDirection();
            	tryToMove();
            }
            break;
        case West:
        	int westYPos = this.y - 1;
            if (myArena.canMoveHere(this.x, westYPos)) {
                this.y = westYPos;
                myArena.newDirectionCoordinates(this.x, this.y);
            }
            else {
            	Drone.droneDirection = Direction.nextDirection(); //droneDirection = droneDirection.nextDirection();
            	tryToMove();
            }
            break;
        default:
            break;
        }
    }
    
}
