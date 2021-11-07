package DroneSimulator;

import java.util.Scanner;

public class Main {
	
	private static Scanner s1; //scanner for X coordinate
	private static Scanner s2;//scanner for Y coordinate
	private static  boolean truecoX;//declare a boolean for checking as long as it is false to keep asking for coordinates
	private static int numberXconv;//declare an integer variable which will be the one which will convert the string input to integer and pass then be passed to canvas
	private static int numberYconv;//declare an integer variable which will be the one which will convert the string input to integer and pass then be passed to canvas
	
	public static void main(String[] args) {//main method
		System.out.println("Give the X coordinate of the Arena");
		do {
			truecoX=true;//make truecoX boolean to true
			s1 = new Scanner(System.in);//get the input of user
			String numberX=s1.next();//convert to string and assign it to numberX variable
	        try{
	        	numberXconv = Integer.parseInt(numberX);//convert the string to integer and assign it to numberXconv variable
	        } catch (NumberFormatException e) {//if the conversion from a string to integer can't be made, then handle the exception
	        	truecoX=false;//make truecoX boolean to false
	            System.err.println("<"+numberX+"> cannot " + "be converted into a number. " + e);
	            System.err.println("Coordinate X can only be a number");
	            System.out.println("Give the X coordinate of the Arena");
	        }
		}while(truecoX == false);//as long as the trucoX variable is false or other as long as there is an error then the program will keep
								//asking user to give the X coordinate right
		
		System.out.println("Give the Y coordinate of the Arena");
		do {
			truecoX=true;//make truecoX boolean to true
			s2 = new Scanner(System.in);//get the input of user
			String numberY=s2.next();//convert to string and assign it to numberX variable
	        try{
	        	numberYconv = Integer.parseInt(numberY);//convert the string to integer and assign it to numberXconv variable
	        } catch (NumberFormatException e) {//if the conversion from a string to integer can't be made, then handle the exception
	        	truecoX=false;//make truecoX boolean to false
	            System.err.println("<"+numberY+"> cannot " + "be converted into a number. " + e);
	            System.err.println("Coordinate Y can only be a number");
	            System.out.println("Give the Y coordinate of the Arena");
	        }
		}while(truecoX == false);//as long as the trucoX variable is false or other as long as there is an error then the program will keep
								//asking user to give the X coordinate right
		
			ConsoleCanvas c = new ConsoleCanvas(numberXconv, numberYconv);	// create a canvas with specific coordinates and passing them to the canvas constructor
			DroneInterface r = new DroneInterface(c);		//call DroneInterface to make it run and do all the tasks
	}
}
