package drone.simulation.gui.obstacles;

import java.io.Serializable;

import drone.simulation.gui.MyCanvas;
import lombok.Getter;

public abstract class mapObject implements Serializable {
	@Getter
	protected static int counter = 0;//obstacles' identifier
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 9102118912036700743L;
	@Getter
	protected double x,y,rad; // the position and the size of the object
    @Getter
    protected int ID; //a unique identifier for each object
    protected char col; //the color of the object
    
    
    public mapObject(double x, double y, double rad, char col) {
        this.x = x;
        this.y = y;
        this.rad = rad;
        this.col = col;
    }


    public void setXY(double x, double y){//Setter method of X and Y coordinates
        this.x = x;
        this.y = y;
    }


    public void drawObject(MyCanvas myCanvas){//Draw object method on Canvas by getting the canvas as argument
        myCanvas.showCircle(x,y,rad,col);
    }

    public String toString(){
        return getType() + " " + ID + " " + "At " + Math.round(x)+ ", "+Math.round(y);//print X and Y to their nearest integer
    }


    public abstract String getType();

	public static void setCounter(int counter) {
		mapObject.counter = counter;
	}
}
