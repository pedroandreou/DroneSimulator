package drone.simulation.gui.obstacles;

import java.util.Random;

public enum Direction {
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST,
    NORTH,
    NORTHEAST;

   private static Random random = new Random();

    /**
     * return a random direction
     * @return
     */
    public static Direction randomDirection(){
        return values()[random.nextInt(values().length)];//use the random class to get a value
    }

    /**
     * multiple the ordinal value by 45
     * @return the angle
     */
    public int getAngle(){
        return ordinal() * 45;
    }

    /**
     * return a random east direction
     * @return
     */
    public Direction goEast(){
        int num =random.nextInt(3);//get one between 0,1 and 2
        if(num == 0){// if the value is 0 go to 7 ordinal position
            return NORTHEAST;
        } else if(num == 1){//if the value is 1 go to 0 ordinal position
            return EAST;
        } else {			//if the value is 2 go to 1 ordinal position
            return SOUTHEAST;
        }
    }

    /**
     * return a random south direction
     * @return
     */
    public Direction goSouth(){
        return values()[random.nextInt(3)+1];//plus 1 to get get one of the ordinal values of 1,2,3
    }

    /**
     * return a random west direction
     * @return
     */
    public Direction goWest(){
        return values()[random.nextInt(3)+3];//plus 3 to get one of the ordinal values of 3,4,5
    }

    /**
     * return a random north direction
     * @return
     */
    public Direction goNorth(){
        return values()[random.nextInt(3)+5];//plus 5 to get one of the ordinal values of 5,6,7
    }

    /**
     * return the opposite direction from the one that is now
     * @return
     */
    public Direction getOpposite(){
       if (ordinal() >= 4){				//if the ordinal value is higher than 4 is in the second half
           return values()[ordinal()-4];//so the value -4 to go to the first half
       }
       return values()[ordinal()+4];//else is in the first half go to the second half
    }

    /**
     * adds a number to the direction
     * @param num the number that you want to add
     * @return the direction with the added value
     */
    public Direction add(int n){
        if (ordinal() + n > 7){// if the new value is going out of the higher bound
            return values()[ordinal()+n-8];//subtract 8
        } else  if (ordinal() + n <0){// if the new value is going out of the lower bound
            return values()[8+ordinal()+n]; //add 8
        }
        return values()[ordinal()+n];// else just add the number
    }
}
