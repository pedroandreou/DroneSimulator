package drone.simulation.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MyCanvas  {
    private GraphicsContext gc;//gc is an instance of graphics context
    private double x;//canvas X dimension
    private double y;//canvas Y dimension

    /**
     * The constructor sets up relevant graphics context and the size of the canvas
     * @param graphicsContext relevant graphics context
     * @param x the width
     * @param y the height
     */
    public MyCanvas(GraphicsContext graphicsContext, double x, double y) {//the Constructor of MyCanvas
        this.gc = graphicsContext;
        this.x = x;
        this.y = y;
        
    }

    /**
     * clears the canvas
     */
    public void clearCanvas(){//clear all the canvas
        gc.clearRect(0,0,x,y);
    }

    public void setX(double newx) {//Setter method of X
      this.x = newx;
    }
    
    public void setY (double newy) {//Setter method of Y
    	this.y= newy;
    }
    /**
     * Passes the first letter of an color and returns the color
     * @param color the first letter of the color
     * @return the color that the first character represents
     */
    private Color colorFromChar (char color){
        Color answer = Color.BLACK; //default value is black so if an invalid argument is passed, black will be used
        //using a switch statement to check the color that represents the color
        switch (color){
            case 'y':
                answer = Color.YELLOW;
                break;
            case 'w':
                answer = Color.WHITE;
                break;
            case 'r':
                answer = Color.RED;
                break;
            case 'g':
                answer = Color.GREEN;
                break;
            case 'b':
                answer = Color.BLUE;
                break;
            case 'o':
                answer = Color.ORANGE;
        }
        return answer;
    }
    public void fillCanvas(int width, int height) {
		//sets colour, size and formatting for canvas
    	gc.setFill(Color.YELLOW);
    	gc.fillRect(0, 0, width, height);
    	gc.setStroke(Color.BLACK);
    	gc.strokeRect(0, 0, width, height);
	}

    public void setFillColour (Color color){
        gc.setFill(color);
    }
    

    /**
     * Draws a circle
     * @param x : the x position that the center of the circle will be
     * @param y : the y position that the center of the circle will be
     * @param rad : the size of the circle
     * @param col : the color of the circle
     */
    public void showCircle(double x, double y, double rad, char col){
        setFillColour(colorFromChar(col));
       gc.fillArc(x-rad,y-rad, rad*2,rad*2,0,360, ArcType.ROUND);
    }
    
    
}
