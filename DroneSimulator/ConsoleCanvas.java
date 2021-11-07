package DroneSimulator;

import lombok.Setter;

public class ConsoleCanvas {
	char[][] canvas = null;
	@Setter
	public static int consoleCanvasX;
	@Setter
	public static int consoleCanvasY;
	
	public ConsoleCanvas(int x, int y) { //ConsoleCanvas constructor
		consoleCanvasX=x; //Initialising coordinates of canvas so they can be used later in DroneArena where I will initialise the arena
		consoleCanvasY=y;
		canvas = new char[consoleCanvasX][consoleCanvasY]; //declare the 2D char array of canvas
		fillingtheConsoleCanvas();//call the method of fillingtheConsoleCanvas() so the canvas can be filled like it is empty
	}
	
	public void fillingtheConsoleCanvas() {
		for(int row=0; row<canvas.length; row++) { //fill the canvas array with hashes and blank spaces
   			for(int col=0; col<canvas[row].length; col++) {
   				if(row == 0 || col == 0) {
   					canvas[row][col]='#';
   				}
   				else if(row == canvas.length - 1) {
   					canvas[row][col]='#';
   				}
   				else if(col == (canvas[row].length - 1)) {
   					canvas[row][col]='#';
			    }
   				else {
   					canvas[row][col]= ' ';
			    }
	    	}
	    }
	}
	
	public void showIt(int x, int y, char d){//a new drone gets into the canvas array
		canvas[x][y] = d;
	}

}
