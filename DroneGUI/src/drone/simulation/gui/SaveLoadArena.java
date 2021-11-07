package drone.simulation.gui;

import javafx.scene.control.Alert;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;


public class SaveLoadArena {

    /**
     * method to help the user choose the file that he want to take an action on
     * @return the file that the user choose
     */
    private static File filePicker(){
        FileFilter filter =  new FileFilter() {// adding a filter for only the .petros files
            @Override
            //return only the directories and the .petros files
            public boolean accept(File file) {
                if (file.getAbsolutePath().endsWith(".petros"))// if a file is a .petros file return it
                    return true;
                return file.isDirectory();// if is is directory return it
            }

            @Override
            public String getDescription() {
                return ".petros";
            }
        };
        File file = null;// set the file to null to return null if something fails
        JFileChooser chooser = new JFileChooser("C:\\Users\\user\\Desktop\\Java\\Java Week 5\\Files");// use the file chooser to make the user choose a file
        chooser.setFileFilter(filter);// add the filter so teh user can only choose .jar files
        int returnVal = chooser.showOpenDialog(null);// it has no parent
        if(returnVal == JFileChooser.APPROVE_OPTION){
            file = chooser.getSelectedFile();// get the file that the user selected
        }
        return file;//return the file
    }

    /**
     * method to serialize and save the application
     * @param arena the arena that will be saved
     */
    public static void save(DroneArena arena){
        File saveFile = filePicker();// get the file from the method
        try {
            String extension = getFileExtension(saveFile);// get the extension of the file that the user choose
            FileOutputStream fileOut;
            if(!extension.equals(".petros")){// if it is not .petros added
                fileOut = new FileOutputStream(saveFile+".petros");
            } else {
                fileOut = new FileOutputStream(saveFile);
            }
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(arena);// write to the file the seriazed object
            //close everything
            out.close();
            fileOut.close();
        } catch (Exception ignored) {// ignore any exception and continue the application running
        }
    }

    /**
     * method t de-serialize the save state and load it to the application
     * @param arena the arena that the currenly is in the application
     * @return return the new arena
     */
    public static DroneArena load(DroneArena arena){
        File loadFile = filePicker();// get the file from the method
        if (loadFile == null){// if somethign went wrong or the user decided to cancel
            return null;//return null
        }
        try {
            FileInputStream fileIn = new FileInputStream(loadFile);
            ObjectInputStream in =  new ObjectInputStream(fileIn);
            arena = (DroneArena) in.readObject();// deserialize the file that was choosest
            //close everything
            in.close();
            fileIn.close();
        } catch (Exception ignored) {// if any exception occur ignore them and say to the user that something went wrong
            wrongFile();
            return null;// return null as the loaded arena was not created
        }
        return arena;// return the loaded arena
    }

    /**
     * method to notify the user that something went wrong while loading the arena
     */
    private static void wrongFile(){
        Alert alert =  new Alert(Alert.AlertType.ERROR);// new alert of type error
        alert.setTitle("Error: file");// set the title
        alert.setHeaderText(null);
        alert.setContentText("Something went wrong with the file you chose, maybe was the wrong type of file");
        alert.showAndWait();// show the message until the user close it 
    }

    /**
     * gets the extension of a file
     * @param file the file that the extension will be returned
     * @return the extension or an empty string if there is no extension
     */
    private static String getFileExtension(File file) {
        String name = file.getName();// get the file as the string
        int lastIndexOf = name.lastIndexOf(".");// get the last . because after that usually follows the extension
        if (lastIndexOf == -1) {// if it didnt found any . there is not an extension
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);// return the extension
    }
}

